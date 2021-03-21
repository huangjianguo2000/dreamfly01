package com.huang.dao;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.huang.mapper.AlgorithmMapper;
import com.huang.pojo.Algorithm;
import com.huang.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlgorithmDao {

    @Autowired
    AlgorithmMapper algorithmMapper;

    @Autowired
    RedisTemplate redisTemplate;
    Constant constant = new Constant();

    public String getAll(int pageIndex){
        Page<Algorithm> page = new Page<>(pageIndex, 50);
        algorithmMapper.selectPage(page, null);
        List<Algorithm> algorithmList = page.getRecords();
        redisTemplate.opsForValue().set(constant.DreamFly_All_Question_PageSize, page.getTotal());
        return JSON.toJSONString(algorithmList);
    }
    public String getPageSize(){
      //  System.out.println(redisTemplate.opsForValue().get(constant.DreamFly_All_Question_PageSize));
        return redisTemplate.opsForValue().get(constant.DreamFly_All_Question_PageSize) + "";
    }

    public String getQuestion(int id){
        String ans = (String) redisTemplate.opsForValue().get(constant.getDreamFly_Question_id(id));
        if(ans == null){
            QueryWrapper<Algorithm> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("id", id);
            ans = JSON.toJSONString(algorithmMapper.selectOne(queryWrapper));
        }
        return ans;
    }
}
