package com.lnsoft.test.quartz;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created By Chr on 2019/2/27/0027.
 * 或者把任务，触发器写在db，通过界面修改任务执行方式
 */
public class ChrJob1 implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //Map
        JobDataMap dataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        System.err.println("         " +
                simpleDateFormat.format(date) +
                "任务一执行了：" +
                dataMap.getString("Chr"));

    }
}
