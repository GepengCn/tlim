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


            JobDetail job = newJob(UserSchedule.class)
                    .withIdentity("UserSchedule", "tlim")
                    .build();
            Trigger trigger = newTrigger()
                    .withIdentity("UserScheduleTrigger", "tlim")
                    .startNow()
                    .withSchedule(simpleSchedule()
                            .withIntervalInMinutes(1)
                            .repeatForever())

                    .build();
            sched.scheduleJob(job, trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }

    }
}
