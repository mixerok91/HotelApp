package by.stepanov.hotel.service.schedule;

import by.stepanov.hotel.service.ServiceException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

public class ScheduleRegister {

    public void execute() throws ServiceException {
        try {
            Scheduler scheduler = new StdSchedulerFactory().getScheduler();

            scheduler.scheduleJob(
                    newJob(CheckReservationStatusJob.class)
                            .withIdentity("checkReservationStatusJob", Scheduler.DEFAULT_GROUP)
                            .build(),
                    newTrigger()
                            .withIdentity("defaultTrgger", Scheduler.DEFAULT_GROUP)
                            .withSchedule(cronSchedule("* * 1/12 * * ?"))
                            .build());

            scheduler.start();

        } catch (SchedulerException e) {
//        TODO Logger
            throw new ServiceException(e);
        }
    }
}
