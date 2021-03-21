package com.huang.redis;

import com.alibaba.fastjson.JSON;
import com.huang.mapper.ChatMsgMapper;
import com.huang.mapper.DiscussMapper;
import com.huang.mapper.UserMapper;
import com.huang.mapper.VideoMapper;
import com.huang.pojo.Chatmsg;
import com.huang.pojo.Discuss;
import com.huang.pojo.User;
import com.huang.pojo.Video;
import com.huang.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RedisService {

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    UserMapper userMapper;
    @Autowired
    ChatMsgMapper chatMsgMapper;
    @Autowired
    DiscussMapper discussMapper;
    @Autowired
    VideoMapper videoMapper;

    Constant constant = new Constant();

    //用户更新的缓存更新到数据库
    public void updateUserToDataBase() {

        //把更新列表的数据更新到数据库
        for (int i = 0; i < redisTemplate.opsForList().size(constant.DreamFly_user_update_list); i++) {
            String t = (String) redisTemplate.opsForList().index(constant.DreamFly_user_update_list, i);
            User user = JSON.parseObject(t, User.class);
            userMapper.updateById(user);
        }

        //把更新列表清空
        while (redisTemplate.opsForList().size(constant.DreamFly_user_update_list) != 0) {
            redisTemplate.opsForList().leftPop(constant.DreamFly_user_update_list);
        }
    }

    //聊天记录缓存更新到数据库
    public void updateChatMsgToDataBase() {

        for (int i = 0; i < redisTemplate.opsForList().size(constant.DreamFly_ChatMsg_List); i++) {
            String connection = (String) redisTemplate.opsForList().index(constant.DreamFly_ChatMsg_List, i);
            List<Chatmsg> msgList = JSON.parseArray((String) redisTemplate.opsForValue().get(constant.DreamFly_ChatMsg + connection), Chatmsg.class);
            if (msgList == null)
                msgList = new ArrayList<>();
            for (int j = 0; j < msgList.size(); j++) {
                chatMsgMapper.insert(msgList.get(j));
            }
            redisTemplate.delete(constant.DreamFly_ChatMsg + connection);
        }


        while (redisTemplate.opsForList().size(constant.DreamFly_ChatMsg_List) != 0) {
            redisTemplate.opsForList().leftPop(constant.DreamFly_ChatMsg_List);
        }
    }

    public void updateDiscussViewToDateBase() {
        for (int i = 0; i < redisTemplate.opsForList().size(constant.DreamFly_Discuss_Update_list); i++) {
            int k = (int) redisTemplate.opsForList().index(constant.DreamFly_Discuss_Update_list, i);
            String ans = (String) redisTemplate.opsForHash().get(constant.DreamFly_Discuss,k);
            Discuss discuss = JSON.parseObject(ans, Discuss.class);
            discussMapper.updateById(discuss);
            redisTemplate.opsForHash().delete(constant.DreamFly_Discuss,k);
        }

        while (redisTemplate.opsForList().size(constant.DreamFly_Discuss_Update_list) != 0) {
            redisTemplate.opsForList().leftPop(constant.DreamFly_Discuss_Update_list);
        }
    }

    public void updateVideoToDatabase(){
        for (int i = 0; i < redisTemplate.opsForList().size(constant.DreamFly_Video_Update_List); i++){
            Video video = JSON.parseObject((String)redisTemplate.opsForList().index(constant.DreamFly_Video_Update_List, i), Video.class);
            videoMapper.updateById(video);
        }

        while (redisTemplate.opsForList().size(constant.DreamFly_Video_Update_List) != 0) {
            redisTemplate.opsForList().leftPop(constant.DreamFly_Video_Update_List);
        }
    }
}
