package com.oulam.dingding.service;

import com.oulam.dingding.exception.DingYunMessageException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Oulam on 2018/10/10.
 */
@Service
public class ScheduledService {
    protected static final Logger log = LoggerFactory.getLogger(ScheduledService.class);

    @Autowired
    MessageService msgService;

    /**
     * 支持cron语法：
     * 每个参数的意义分别是： second, minute, hour, day of month, month, day of week
     *
     * 如下：每分钟查看一次云表数据库中的消息表
     */
    //@Scheduled(cron = "*/10 * * * * ?")
    //@Scheduled(cron = "0 0 2 * * ?")
    //@Scheduled(fixedRate=12000000)
    //@Scheduled(fixedRate=60000)
    public void scheduled() throws DingYunMessageException {
        msgService.MessageTasks();
    }

}
