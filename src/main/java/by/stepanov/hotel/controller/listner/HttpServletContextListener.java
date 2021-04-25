package by.stepanov.hotel.controller.listner;

import by.stepanov.hotel.dao.impl.connectionpool.ConnectionPoolException;
import by.stepanov.hotel.dao.impl.connectionpool.ConnectionPoolProvider;
import by.stepanov.hotel.service.ServiceException;
import by.stepanov.hotel.service.schedule.ScheduleRegister;
import org.apache.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class HttpServletContextListener implements ServletContextListener {

    private static final String DATABASE_PROPERTIES_NAME = "database.properties";

    private static final Logger log = Logger.getLogger(HttpServletContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ScheduleRegister sr = new ScheduleRegister();
        try {
            sr.execute();
            log.info("HttpServletContextListener.contextInitialized");
        } catch (ServiceException e) {
            log.error("Service exception", e);
        }

        ConnectionPoolProvider.getConnectionPool().initPoolData(DATABASE_PROPERTIES_NAME);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        try {
            ConnectionPoolProvider.getConnectionPool().dispose();
            log.info("Connection pool was dispose");
        } catch (ConnectionPoolException e) {
            log.error("Problem with connection pool disposing", e);
        }
        log.info("HttpServletContextListener.contextDestroyed");
    }
}
