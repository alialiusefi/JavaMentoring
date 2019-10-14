package com.epam.esm.pool;

import com.epam.esm.exception.PersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.datasource.AbstractDataSource;

import javax.annotation.PreDestroy;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;

@Lazy
public final class ConnectionPool extends AbstractDataSource {

    private static final Logger LOGGER = LogManager.getLogger(ConnectionPool.class);
    private String url;
    private String userName;
    private String password;
    private int timeoutConnectionLimit;
    private ArrayBlockingQueue<CustomPooledConnection> freeConnections;
    private Set<CustomPooledConnection> usedConnections
            = new LinkedHashSet<>();
    private int maxConnections;


    public ConnectionPool(String driverClass, String URL, String userName,
                          String password, int startConnections, int maxConnections,
                          int timeout) {
        try {
            Class.forName(driverClass);
            this.url = URL;
            this.maxConnections = maxConnections;
            this.userName = userName;
            this.password = password;
            this.timeoutConnectionLimit = timeout;
            this.freeConnections = new ArrayBlockingQueue<>(startConnections);
            for (int i = 0; i < startConnections; i++) {
                freeConnections.add(createNewConnection());
            }
        } catch (ClassNotFoundException | SQLException e) {
            LOGGER.error("Couldn't create initial available Connections to database!", e);
            throw new PersistentException(e.getMessage(), e);
        }
    }

    public CustomPooledConnection getConnection(String userName, String password) {
        return getConnection();
    }

    public CustomPooledConnection getConnection() {
        CustomPooledConnection connection = null;
        while (connection == null) {
            try {
                if (!freeConnections.isEmpty()) {
                    connection = freeConnections.poll();
                    if (!connection.isValid(timeoutConnectionLimit)) {
                        try {
                            connection.getConnection().close();
                        } catch (SQLException e) {
                            LOGGER.warn(e.getMessage(), e);
                            throw new PersistentException(e.getMessage(), e);
                        }
                        connection = null;
                    }
                } else if (usedConnections.size() < maxConnections) {
                    connection = createNewConnection();
                } else {
                    LOGGER.error("The limit of number of database connections is exceeded");
                    throw new PersistentException();
                }
            } catch (SQLException e) {
                LOGGER.error("Cannot connect to database", e);
                throw new PersistentException(e.getMessage(), e);
            }
        }
        usedConnections.add(connection);
        LOGGER.debug("Connection was used, Available connections:" + freeConnections.size()
                + " Busy Connections: " + usedConnections.size()
        );
        return connection;
    }

    private CustomPooledConnection createNewConnection() throws SQLException, PersistentException {
        if ((usedConnections.size() + freeConnections.size()) < maxConnections) {
            return new CustomPooledConnection(DriverManager.
                    getConnection(this.url, this.userName, this.password));
        }
        throw new PersistentException(
                "Cannot create connections more than max. amount of connections");
    }

    public void freeConnection(CustomPooledConnection connection) {
        try {
            if (connection.isValid(timeoutConnectionLimit)) {
                connection.clearWarnings();
                connection.setAutoCommit(true);
                usedConnections.remove(connection);
                freeConnections.add(connection);
                LOGGER.debug("Connection Cleared.");
            }
        } catch (SQLException e) {
            LOGGER.error("Couldn't clear connection!", e);
            try {
                connection.close();
            } catch (SQLException e1) {
                LOGGER.error("Couldn't clear connection!", e1);
            }

        }
    }

    @PreDestroy
    public void destroy() {
        if (freeConnections != null && usedConnections != null) {
            usedConnections.addAll(freeConnections);
            freeConnections.clear();
            for (CustomPooledConnection connection : usedConnections) {
                try {
                    connection.getConnection().close();
                } catch (SQLException e) {
                    LOGGER.warn(e.getMessage(), e);
                }
            }
            usedConnections.clear();
        }
    }

    @Override
    protected void finalize() throws Throwable {
        destroy();
    }

}
