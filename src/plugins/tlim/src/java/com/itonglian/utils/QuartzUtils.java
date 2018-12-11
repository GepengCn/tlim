package com.itonglian.utils;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

public class QuartzUtils {

    public void run(){

        SchedulerFactory schedFact = new StdSchedulerFactory();

        try {
            Scheduler sched = schedFact.getScheduler();

            sched.start();


            JobDetail job = newJob(HelloJob.class)
                    .withIdentity("myJob", "group1")
                    .build();
            Trigger trigger = newTrigger()
                    .withIdentity("myTrigger", "group1")
                    .startNow()
                    .withSchedule(simpleSchedule()
                            .withIntervalInMinutes(5)
                            .repeatForever())

                    .build();
            sched.scheduleJob(job, trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }

    }
}
