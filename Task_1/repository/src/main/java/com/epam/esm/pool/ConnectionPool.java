package com.epam.esm.pool;

import com.epam.esm.exception.PersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.datasource.AbstractDataSource;

import javax.annotation.PreDestroy;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
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
    private ArrayBlockingQueue<Connection> freeConnections;
    private Set<Connection> usedConnections
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

    public Connection getConnection(String userName, String password) {
        return getConnection();
    }

    public Connection getConnection() {
        Connection connection = null;
        while (connection == null) {
            try {
                if (!freeConnections.isEmpty()) {
                    connection = freeConnections.poll();
                    if (!connection.isValid(timeoutConnectionLimit)) {
                        try {
                            connection.close();
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

    private Connection createNewConnection() throws SQLException, PersistentException {
        if ((usedConnections.size() + freeConnections.size()) < maxConnections) {
            Connection pooledConnection = (Connection) Proxy.newProxyInstance(Connection.class.getClassLoader(), new Class[]{Connection.class},
                    new ConnectionInvocationHandler(DriverManager.
                            getConnection(this.url, this.userName, this.password)));
            return pooledConnection;
        }
        throw new PersistentException(
                "Cannot create connections more than max. amount of connections");
    }

    public void returnConnection(Connection connection) {
        try {
            if (connection.isValid(timeoutConnectionLimit)) {
                connection.clearWarnings();
                if (!connection.getAutoCommit()) {
                    connection.setAutoCommit(true);
                }
                if (connection.isReadOnly()) {
                    connection.setReadOnly(false);
                }
                if (!usedConnections.remove(connection)) {
                    throw new PersistentException("Cannot remove connection from used connections");
                }
                if (!freeConnections.add(connection)) {
                    throw new PersistentException("Cannot add connection to free connections");
                }
                LOGGER.debug("Connection Returned.");
            }
        } catch (SQLException e) {
            LOGGER.error("Couldn't clear connection!", e);
            try {
                connection.close();
            } catch (SQLException e1) {
                LOGGER.error("Couldn't close connection!", e1);
            }
        }
    }

    //@PreDestroy
    public void destroy() {
        if (freeConnections != null && usedConnections != null) {
            usedConnections.addAll(freeConnections);
            freeConnections.clear();
            for (Connection connection : usedConnections) {
                Connection nonPooledConnection =
                        ((ConnectionInvocationHandler) Proxy.getInvocationHandler(connection))
                                .getNonPooledConnection();
                try {
                    nonPooledConnection.close();
                } catch (SQLException e) {
                    LOGGER.warn(e.getMessage(), e);
                }
            }
            usedConnections.clear();
        }
    }

    private class ConnectionInvocationHandler implements InvocationHandler {

        public Connection nonPooledConnection;

        public ConnectionInvocationHandler(Connection nonPooledConnection) {
            this.nonPooledConnection = nonPooledConnection;
        }

        public Connection getNonPooledConnection() {
            return nonPooledConnection;
        }

        public void setNonPooledConnection(Connection nonPooledConnection) {
            this.nonPooledConnection = nonPooledConnection;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (method.getName().equals("close")) {
                Connection proxyConnection = (Connection) proxy;
                returnConnection(proxyConnection);
                return null;
            }
            if (method.getName().equals("equals")) {
                if (proxy == args[0]) {
                    return true;
                }
            }
            return method.invoke(nonPooledConnection, args);
        }
    }

}
