package com.huang.dao;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.huang.mapper.ChatMsgMapper;
import com.huang.pojo.Chatmsg;
import com.huang.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class ChatMsgDao {
    @Autowired
    ChatMsgMapper chatMsgMapper;

    @Autowired
    RedisTemplate redisTemplate;

    Constant constant = new Constant();

    public String getAllChatMsg(int me, int you){
        String connection = me + "-" + you;
        System.out.println(connection);
        redisTemplate.opsForHash().delete(constant.DreamFly_NotRead_Message, me);
        String ans = (String) redisTemplate.opsForValue().get(constant.DreamFly_AllChatMessage + connection);
        if(ans == null){
            QueryWrapper<Chatmsg> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("connection", connection);
            ans = JSON.toJSONString(chatMsgMapper.selectList(queryWrapper));
            redisTemplate.opsForValue().set(constant.DreamFly_AllChatMessage + connection, ans);
        }
      return ans;
    }

    public void insertMessage( int me, int you, String msg, String time){
        String connection = me + "-" + you;
        String connection2 = you + "-" + me;
        Chatmsg chatmsg = new Chatmsg();
        chatmsg.setMsg(msg);
        chatmsg.setConnection(connection);
        chatmsg.setTime(time);
        chatmsg.setSendId(me);
        chatmsg.setSendToId(you);
        chatmsg.setState(0);
        List<Chatmsg> msgList = JSON.parseArray((String) redisTemplate.opsForValue().get(constant.DreamFly_AllChatMessage + connection), Chatmsg.class);
        List<Chatmsg> updateList = JSON.parseArray((String) redisTemplate.opsForValue().get(constant.DreamFly_ChatMsg + connection), Chatmsg.class);
        if (msgList == null){
            msgList = new ArrayList<>();
        }
        if(updateList == null){
            updateList = new ArrayList<>();
        }
        updateList.add(chatmsg);
        msgList.add(chatmsg);
        redisTemplate.opsForValue().set(constant.DreamFly_AllChatMessage + connection, JSON.toJSONString(msgList),5, TimeUnit.HOURS);
        redisTemplate.opsForValue().set(constant.DreamFly_ChatMsg + connection, JSON.toJSONString(updateList));

        List<Chatmsg> msgList2 = JSON.parseArray((String) redisTemplate.opsForValue().get(constant.DreamFly_AllChatMessage + connection2), Chatmsg.class);
        List<Chatmsg> updateList2 = JSON.parseArray((String) redisTemplate.opsForValue().get(constant.DreamFly_ChatMsg + connection2), Chatmsg.class);
        if (msgList2 == null){
            msgList2 = new ArrayList<>();
        }
        if(updateList2 == null){
            updateList2 = new ArrayList<>();
        }
        chatmsg.setConnection(connection2);
        updateList2.add(chatmsg);
        msgList2.add(chatmsg);
        redisTemplate.opsForValue().set(constant.DreamFly_AllChatMessage + connection2, JSON.toJSONString(msgList), 5, TimeUnit.HOURS);
        redisTemplate.opsForValue().set(constant.DreamFly_ChatMsg + connection2, JSON.toJSONString(updateList));

        redisTemplate.opsForList().leftPush(constant.DreamFly_ChatMsg_List, connection);
        redisTemplate.opsForList().leftPush(constant.DreamFly_ChatMsg_List, connection2);


        List<Chatmsg> t = JSON.parseArray((String) redisTemplate.opsForHash().get(constant.DreamFly_NotRead_Message,you),Chatmsg.class);
        if(t == null)
            t = new ArrayList<>();
        t.add(chatmsg);
       // System.out.println(t);
        redisTemplate.opsForHash().put(constant.DreamFly_NotRead_Message, you, JSON.toJSONString(t));
    }


    public String getNoReadMessage(int id){
//        redisTemplate.opsForHash().delete(constant.DreamFly_NotRead_Message, 3);
//        redisTemplate.opsForHash().delete(constant.DreamFly_NotRead_Message, 6);
        System.out.println(id);
        return (String) redisTemplate.opsForHash().get(constant.DreamFly_NotRead_Message, id);
    }


}
