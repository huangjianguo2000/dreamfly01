package com.huang.dao;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.huang.mapper.CollectionMapper;
import com.huang.pojo.Buyed;
import com.huang.pojo.Collection;
import com.huang.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class CollectionDao {

    @Autowired
    CollectionMapper collectionMapper;
    @Autowired
    RedisTemplate redisTemplate;
    Constant constant = new Constant();

    public String getAllByUserId(int userId){
        String ans = (String) redisTemplate.opsForValue().get(constant.getDreamFly_Collection_Video_userId(userId));
        if(ans == null){
            QueryWrapper<Collection> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("userId", userId);
            ans = JSON.toJSONString(collectionMapper.selectList(queryWrapper));
            redisTemplate.opsForValue().set(constant.getDreamFly_Collection_Video_userId(userId), ans, 5, TimeUnit.HOURS);
        }
        return ans;
    }

    public void insertBuyed(int userId, int videoId){
        Collection collection = new Collection(userId,videoId);
        collectionMapper.insert(collection);
        String ans = (String) redisTemplate.opsForValue().get(constant.getDreamFly_Collection_Video_userId(userId));
        if(ans != null){
            List<Collection> buyedList = JSON.parseArray(ans, Collection.class);
            buyedList.add(collection);
            ans = JSON.toJSONString(buyedList);
            redisTemplate.opsForValue().set(constant.getDreamFly_Collection_Video_userId(userId), ans, 5, TimeUnit.HOURS);
        }
    }

}
