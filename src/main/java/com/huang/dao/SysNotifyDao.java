package com.huang.dao;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.huang.mapper.SysnotifyMapper;
import com.huang.pojo.Blog;
import com.huang.pojo.Sysnotify;
import com.huang.utils.Constant;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class SysNotifyDao {

    @Autowired
    SysnotifyMapper sysnotifyMapper;
    @Autowired
    RedisTemplate redisTemplate;

    Constant constant = new Constant();
    public String getAllNotify(int userId) {
        String ans = (String) redisTemplate.opsForValue().get(constant.getDreamFly_All_Notify_Of_User(userId));
        if (ans == null) {
            QueryWrapper<Sysnotify> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("userId", userId);
            ans = JSON.toJSONString(sysnotifyMapper.selectList(queryWrapper));
            redisTemplate.opsForValue().set(constant.getDreamFly_All_Notify_Of_User(userId), ans, 5, TimeUnit.HOURS);
        }
        return ans;
    }

    public void insertNotify(Sysnotify sysnotify){
        sysnotifyMapper.insert(sysnotify);
        String ans = (String) redisTemplate.opsForValue().get(constant.getDreamFly_All_Notify_Of_User(sysnotify.getUserId()));
        if (ans != null) {
            List<Sysnotify> sysnotifies = JSON.parseArray(ans, Sysnotify.class);
            sysnotifies.add(sysnotify);
            redisTemplate.opsForValue().set(constant.getDreamFly_All_Notify_Of_User(sysnotify.getUserId()), JSON.toJSONString(sysnotifies), 5, TimeUnit.HOURS);
        }
    }

    public void updateState(int userId){
        UpdateWrapper<Sysnotify> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("userId", userId).set("state", 1);
        sysnotifyMapper.update(null,updateWrapper);
        String ans = (String) redisTemplate.opsForValue().get(constant.getDreamFly_All_Notify_Of_User(userId));
        if (ans != null) {
            List<Sysnotify> sysnotifies = JSON.parseArray(ans, Sysnotify.class);
            for(int i = 0; i < sysnotifies.size(); i++){
                sysnotifies.get(i).setState(1);
            }
            redisTemplate.opsForValue().set(constant.getDreamFly_All_Notify_Of_User(userId), JSON.toJSONString(sysnotifies), 5, TimeUnit.HOURS);
        }
    }


}
