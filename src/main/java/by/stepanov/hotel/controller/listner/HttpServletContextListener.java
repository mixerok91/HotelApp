package by.stepanov.hotel.controller.listner;

import by.stepanov.hotel.service.ServiceException;
import by.stepanov.hotel.service.schedule.ScheduleRegister;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class HttpServletContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("HttpServletContextListener.contextInitialized()");
        ScheduleRegister sr = new ScheduleRegister();
        try {
//TODO add logger
            sr.execute();
        } catch (ServiceException e) {

            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("HttpServletContextListener.contextDestroyed()");
    }
}
