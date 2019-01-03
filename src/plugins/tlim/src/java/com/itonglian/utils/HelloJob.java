package com.itonglian.utils;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class HelloJob implements Job {
    private static final Logger Log = LoggerFactory.getLogger(Job.class);

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        Log.error("HelloJob:"+new Date().getTime());
    }
}
