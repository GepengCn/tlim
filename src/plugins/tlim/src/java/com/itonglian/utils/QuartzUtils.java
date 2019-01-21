package com.itonglian.utils;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

public class QuartzUtils {

    private final Logger Log = LoggerFactory.getLogger(this.getClass());


    public void run(){

        SchedulerFactory schedFact = new StdSchedulerFactory();

        try {
            Scheduler sched = schedFact.getScheduler();

            sched.start();


            JobDetail job = newJob(UserSchedule.class)
                    .withIdentity("UserSchedule", "tlim")
                    .build();
            Trigger trigger = newTrigger()
                    .withIdentity("UserScheduleTrigger", "tlim")
                    .startNow()
                    .withSchedule(simpleSchedule()
                            .withIntervalInMinutes(XMLProperties.getUserASyncInterval())
                            .repeatForever())

                    .build();
            sched.scheduleJob(job, trigger);
        } catch (SchedulerException e) {
            Log.error(ExceptionUtils.getFullStackTrace(e));
        }

    }
}
