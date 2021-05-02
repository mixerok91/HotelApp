package by.stepanov.hotel.service.schedule;

import by.stepanov.hotel.service.ServiceException;
import org.apache.log4j.Logger;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

public class ScheduleRegister {

    private static final Logger log = Logger.getLogger(ScheduleRegister.class);

    public void execute() throws ServiceException {
        try {
            Scheduler scheduler = new StdSchedulerFactory().getScheduler();

            scheduler.scheduleJob(
                    newJob(CheckReservationStatusJob.class)
                            .withIdentity("checkReservationStatusJob", Scheduler.DEFAULT_GROUP)
                            .build(),
                    newTrigger()
                            .withIdentity("defaultTrigger", Scheduler.DEFAULT_GROUP)
                            .withSchedule(cronSchedule("0 0 0/12 * * ?"))
                            .build());

            scheduler.start();
            log.info("Schedule start");
        } catch (SchedulerException e) {
            log.error("Service exception", e);
            throw new ServiceException(e);
        }
    }
}
