package com.huang.dao;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.huang.mapper.UserMapper;
import com.huang.pojo.User;
import com.huang.service.ChatMsgService;
import com.huang.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class UserDao {

    @Autowired
    UserMapper userMapper;
    @Autowired
    ChatMsgService chatMsgService;

    @Autowired
    RedisTemplate redisTemplate;
    Constant constant = new Constant();

    public User selectOneByPhone(String phoneNumber){
        String t =(String) redisTemplate.opsForValue().get(constant.DreamFly_user + phoneNumber);
        if(t == null){
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("phoneNumber", phoneNumber);
            User user = userMapper.selectOne(queryWrapper);
            redisTemplate.opsForValue().set(constant.DreamFly_user + phoneNumber, JSON.toJSONString(user),5, TimeUnit.HOURS);
            t = JSON.toJSONString(user);
        }
        return JSON.parseObject(t,User.class);
    }
    public User selectOneById(int id){
        String t =(String) redisTemplate.opsForValue().get(constant.getDreamFly_user(id));
        if(t == null){
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("id", id);
            User user = userMapper.selectOne(queryWrapper);
            redisTemplate.opsForValue().set(constant.getDreamFly_user(id), JSON.toJSONString(user),5, TimeUnit.HOURS);
            t = JSON.toJSONString(user);
        }
        return JSON.parseObject(t,User.class);
    }

    public void insertUser(User user){
        userMapper.insert(user);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phoneNumber", user.getPhoneNumber());
        user = userMapper.selectOne(queryWrapper);
        //添加到缓存中
        redisTemplate.opsForValue().set(constant.DreamFly_user+ user.getPhoneNumber(), JSON.toJSONString(user),5, TimeUnit.HOURS);
       //给新用户说一句hello
        chatMsgService.sayHello(user.getPhoneNumber());
    }

    public void updateNickName(User user, String phoneNumber, String nickName){
        //先将列表中之前存的去掉
        redisTemplate.opsForList().remove(constant.DreamFly_user_update_list, 1, JSON.toJSONString(user));
        user.setNickName(nickName);
        redisTemplate.opsForValue().set(constant.DreamFly_ChatMsg_List + phoneNumber, JSON.toJSONString(user));
        //加入新的值
        redisTemplate.opsForList().leftPush(constant.DreamFly_user_update_list, JSON.toJSONString(user));
    }

    public List<User> getAllUserNotMe(int userId){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.ne("id", userId);
        return userMapper.selectList(queryWrapper);
    }


    public void updateImgUrl(String url, int id){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        User user = userMapper.selectOne(queryWrapper);
        user.setImgUrl(url);
        userMapper.updateById(user);
        redisTemplate.opsForValue().set(constant.DreamFly_user + user.getPhoneNumber(), JSON.toJSONString(user), 5, TimeUnit.HOURS);
    }

    public void updateVip(int userId, String vip, int sum){
        String t =(String) redisTemplate.opsForValue().get(constant.getDreamFly_user(userId));
        if(t == null){
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("id", userId);
            User user = userMapper.selectOne(queryWrapper);
            t = JSON.toJSONString(user);
        }
        User u = JSON.parseObject(t, User.class);
        u.setVip(vip);
        u.setSurplus(sum);
        userMapper.updateById(u);
        redisTemplate.opsForValue().set(constant.getDreamFly_user(userId), JSON.toJSONString(u),5, TimeUnit.HOURS);
    }
}
