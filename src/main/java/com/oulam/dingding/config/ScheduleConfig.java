package com.oulam.dingding.config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.Executors;

/**
 * 多线程执行定时任务
 * @author
 *
 */
@Configuration
//所有的定时任务都放在一个线程池中，定时任务启动时使用不同都线程。
public class ScheduleConfig implements SchedulingConfigurer {
    @Value("${com.oulam.threadNum}")
    private int threadNum;
    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        //设定一个长度10的定时任务线程池
        taskRegistrar.setScheduler(Executors.newScheduledThreadPool(threadNum));
    }
}
