package com.huang.dao;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.huang.mapper.BuyedMapper;
import com.huang.pojo.Buyed;
import com.huang.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class BuyedDao {
    @Autowired
    BuyedMapper buyedMapper;
    @Autowired
    RedisTemplate redisTemplate;
    Constant constant = new Constant();

    public String getAllBuyed(int userId){
        String ans = (String) redisTemplate.opsForValue().get(constant.getDreamFly_BuyVideo_userId(userId));
        if(ans == null){
            QueryWrapper<Buyed> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("userId", userId);
            ans = JSON.toJSONString(buyedMapper.selectList(queryWrapper));
            redisTemplate.opsForValue().set(constant.getDreamFly_BuyVideo_userId(userId), ans, 5, TimeUnit.HOURS);
        }
        return ans;
    }

    public void insertBuyed(int userId, int videoId){
        Buyed buyed = new Buyed(userId,videoId);
        buyedMapper.insert(buyed);
        String ans = (String) redisTemplate.opsForValue().get(constant.getDreamFly_BuyVideo_userId(userId));
        if(ans != null){
            List<Buyed> buyedList = JSON.parseArray(ans, Buyed.class);
            buyedList.add(buyed);
            ans = JSON.toJSONString(buyedList);
            redisTemplate.opsForValue().set(constant.getDreamFly_BuyVideo_userId(userId), ans, 5, TimeUnit.HOURS);
        }
    }
}
