package com.huang.job;

import com.huang.mapper.UserMapper;
import com.huang.redis.RedisService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeJob extends QuartzJobBean {

    @Autowired
    RedisService redisService;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        //获取JobDetail中关联的数据
        String msg = (String) jobExecutionContext.getJobDetail().getJobDataMap().get("msg");
        System.out.println("current time :"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "---" + msg + "更新数据库完成");
        redisService.updateUserToDataBase();
        redisService.updateChatMsgToDataBase();
        redisService.updateDiscussViewToDateBase();
        redisService.updateVideoToDatabase();
    }
}