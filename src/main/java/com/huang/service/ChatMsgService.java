package com.huang.service;


import com.alibaba.fastjson.JSON;
import com.huang.pojo.Chatmsg;
import com.huang.pojo.User;
import com.huang.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class ChatMsgService {

    @Autowired
    RedisTemplate redisTemplate;

    Constant constant = new Constant();

    //给新用户说一句hello
    public void sayHello(String phoneNumber) {
        User user = JSON.parseObject((String) redisTemplate.opsForValue().get(constant.DreamFly_user + phoneNumber), User.class);
        int id = user.getId();
        Chatmsg msg = new Chatmsg();
        msg.setSendId(3);
        msg.setSendToId(user.getId());
        msg.setMsg("你好， 欢迎使用it交流平台！！！");
        msg.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        msg.setConnection(user.getId() + "-" + 3);
        msg.setState(0);
        redisTemplate.opsForList().leftPush(constant.DreamFly_ChatMsg + msg.getConnection(), JSON.toJSONString(msg));
        redisTemplate.opsForList().leftPush(constant.DreamFly_ChatMsg_List, msg.getConnection());
    }
}
