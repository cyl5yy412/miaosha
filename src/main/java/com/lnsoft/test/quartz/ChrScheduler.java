package com.lnsoft.test.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.context.annotation.Bean;

/**
 * Created By Chr on 2019/2/27/0027.
 * <p>
 * RamJobStore:关机失效
 * JDBC：持久化，可以把任务调度内容存到数据库中，去官网下源码/docs，有各种数据库的sql执行文件建表
 */
public class ChrScheduler {
    public static void main(String args[]) throws SchedulerException {
        //JobDetail:任务，做什么事
        JobDetail jobDetail = JobBuilder.newJob(ChrJob1.class)
                .withIdentity("job1", "group1")
                .usingJobData("Chr", "quartz~~~~~")
                .usingJobData("Moon", 5.20F).build();

        //Trigger：触发器，任务按照什么样的规律允许
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger1", "group1").startNow()
                .withSchedule(SimpleScheduleBuilder
                        .simpleSchedule()
                        .withIntervalInSeconds(5)
                        .repeatForever()).build();

        //SchedulerFactory:调度工厂
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();

        //Scheduler：谁来指挥，单例，必定有API
        Scheduler scheduler = schedulerFactory.getScheduler();

        //绑定关系：那个任务用哪个触发器运行
        scheduler.scheduleJob(jobDetail, trigger);
        scheduler.start();
    }

    @Bean
    public Scheduler getScheduler() throws SchedulerException {
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        return schedulerFactory.getScheduler();
    }
}
