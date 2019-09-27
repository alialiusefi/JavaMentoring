package com.epam.esm.config;

import com.java.esm.exception.PersistentException;
import com.java.esm.pool.ConnectionPool;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

public class GiftCertificateWebAppInitializer
        implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {

        AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();
        applicationContext.setServletContext(servletContext);
        try {
            initDataSource();
        } catch (PersistentException e) {
            throw new ServletException(e);
        }
        applicationContext.register(WebConfig.class);
        applicationContext.refresh();
        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", new DispatcherServlet(applicationContext));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");
    }

    private void initDataSource() throws PersistentException {
        ConnectionPool.getInstance().initialize(
                "org.postgresql.Driver",
                "jdbc:postgresql://localhost:5432/giftcertdb",
                "postgres",
                "admin",
                10,
                50,
                300
        );
    }
}
