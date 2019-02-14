package com.itonglian.history;

import com.itonglian.utils.UserSchedule;
import com.itonglian.utils.XMLProperties;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeUtils;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

public class MessageCleanUp {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public void run(){

        SchedulerFactory schedulerFactory = new StdSchedulerFactory();

        try {
            Scheduler scheduler = schedulerFactory.getScheduler();

            scheduler.start();

            JobDetail job = newJob(CleanUpJob.class)
                    .withIdentity("CleanUpSchedule", "tlim")
                    .build();
            logger.info("启动历史消息清理调度器...");

            Date next = DateTime.now().plusDays(1).dayOfMonth().roundFloorCopy().toDate();

            logger.info("下次启动时间:"+next.getTime());
            Trigger trigger = newTrigger()
                    .withIdentity("CleanUpScheduleTrigger", "tlim")
                    .startAt(next)
                    .withSchedule(simpleSchedule()
                            .withIntervalInHours(XMLProperties.getCleanUpInterval())
                            .repeatForever())

                    .build();
            scheduler.scheduleJob(job, trigger);
        } catch (SchedulerException e) {
            logger.error("error",e);
        }

    }
}
