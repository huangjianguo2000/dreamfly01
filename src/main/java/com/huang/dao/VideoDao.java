package com.huang.dao;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.huang.mapper.VideoMapper;
import com.huang.pojo.Video;
import com.huang.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class VideoDao {

    @Autowired
    VideoMapper videoMapper;

    @Autowired
    RedisTemplate redisTemplate;

    Constant constant = new Constant();

    public void insertVideo(Video video) {
        videoMapper.insert(video);
        List<Video> videoList = JSON.parseArray((String) redisTemplate.opsForValue().get(constant.DreamFly_All_Video), Video.class);
        if (videoList != null) {
            videoList.add(video);
            redisTemplate.opsForValue().set(constant.DreamFly_All_Video, JSON.toJSONString(videoList), 5, TimeUnit.HOURS);
        }
        redisTemplate.opsForValue().set(constant.getDreamFly_Video(video.getId()), JSON.toJSONString(video));

        List<Video> videoList2 = JSON.parseArray((String) redisTemplate.opsForValue().get(constant.getDreamFly_Video_UserId(video.getUpId())), Video.class);
        if (videoList2 != null) {
            videoList2.add(video);
            redisTemplate.opsForValue().set(constant.getDreamFly_Video_UserId(video.getUpId()), JSON.toJSONString(videoList2));
        }
    }

    public String getAllVideo() {
        String ans = (String) redisTemplate.opsForValue().get(constant.DreamFly_All_Video);
        if (ans == null) {
            List<Video> videoList = videoMapper.selectList(null);
            ans = JSON.toJSONString(videoList);
            redisTemplate.opsForValue().set(constant.DreamFly_All_Video, ans, 5, TimeUnit.HOURS);
        }
        return ans;
    }


    public String getVideoByUserId(int userId) {
        String ans = (String) redisTemplate.opsForValue().get(constant.getDreamFly_Video_UserId(userId));
        if (ans == null) {
            QueryWrapper<Video> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("UpId", userId);
            ans = JSON.toJSONString(videoMapper.selectList(queryWrapper));
            redisTemplate.opsForValue().set(constant.getDreamFly_Video_UserId(userId), ans, 5, TimeUnit.HOURS);
        }
        return ans;
    }

    public String addView(int id) {
        String ans = (String) redisTemplate.opsForValue().get(constant.getDreamFly_Video(id));
        if (ans == null) {
            QueryWrapper<Video> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("id", id);
            ans = JSON.toJSONString(videoMapper.selectOne(queryWrapper));
        }
        Video video = JSON.parseObject(ans, Video.class);
        video.addView();
        redisTemplate.opsForValue().set(constant.getDreamFly_Video(id), JSON.toJSONString(video), 5, TimeUnit.HOURS);
        redisTemplate.opsForList().remove(constant.DreamFly_Video_Update_List, 1, ans);
        redisTemplate.opsForList().leftPush(constant.DreamFly_Video_Update_List, JSON.toJSONString(video));
        return JSON.toJSONString(video);
    }

    public void addPoint(int id) {
        String ans = (String) redisTemplate.opsForValue().get(constant.getDreamFly_Video(id));
        if (ans == null) {
            QueryWrapper<Video> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("id", id);
            ans = JSON.toJSONString(videoMapper.selectOne(queryWrapper));
        }
        Video video = JSON.parseObject(ans, Video.class);
        video.addPoint();
        System.out.println(video.getPoint());
        redisTemplate.opsForValue().set(constant.getDreamFly_Video(id), JSON.toJSONString(video), 5, TimeUnit.HOURS);
        System.out.println(redisTemplate.opsForValue().get(constant.getDreamFly_Video(id)));
        redisTemplate.opsForList().remove(constant.DreamFly_Video_Update_List, 1, ans);
        redisTemplate.opsForList().leftPush(constant.DreamFly_Video_Update_List, JSON.toJSONString(video));
    }

    public void addCollection(int id) {
        String ans = (String) redisTemplate.opsForValue().get(constant.getDreamFly_Video(id));
        if (ans == null) {
            QueryWrapper<Video> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("id", id);
            ans = JSON.toJSONString(videoMapper.selectOne(queryWrapper));
        }
        Video video = JSON.parseObject(ans, Video.class);
        video.addCollection();
        redisTemplate.opsForValue().set(constant.getDreamFly_Video(id), JSON.toJSONString(video), 5, TimeUnit.HOURS);

        redisTemplate.opsForList().remove(constant.DreamFly_Video_Update_List, 1, ans);
        redisTemplate.opsForList().leftPush(constant.DreamFly_Video_Update_List, JSON.toJSONString(video));
    }

    public String getVideoById(int id){
        String ans = (String) redisTemplate.opsForValue().get(constant.getDreamFly_Video(id));
        if (ans == null) {
            QueryWrapper<Video> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("id", id);
            ans = JSON.toJSONString(videoMapper.selectOne(queryWrapper));
            redisTemplate.opsForValue().set(constant.getDreamFly_Video(id), ans, 5, TimeUnit.HOURS);
        }
        return ans;
    }

    public String getVideoList(){
        String ans = (String) redisTemplate.opsForValue().get(constant.DreamFly_Video_tuijian);
        if(ans == null){
            QueryWrapper<Video> queryWrapper = new QueryWrapper<>();
            queryWrapper.orderByDesc("views");
            Page<Video> page = new Page<>(1, 10);
            videoMapper.selectPage(page, queryWrapper);
            ans = JSON.toJSONString(page.getRecords());
            redisTemplate.opsForValue().set(constant.DreamFly_Video_tuijian, ans, 5, TimeUnit.HOURS);
        }
        return ans;
    }

}
