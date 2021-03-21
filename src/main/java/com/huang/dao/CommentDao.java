package com.huang.dao;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.huang.mapper.CommentMapper;
import com.huang.pojo.Answer;
import com.huang.pojo.Comment;
import com.huang.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class CommentDao {
    @Autowired
    CommentMapper commentMapper;
    @Autowired
    RedisTemplate redisTemplate;

    Constant constant = new Constant();

    public String getAllCommentByVideoId(int videoId){
        String ans = (String) redisTemplate.opsForValue().get(constant.getDreamFly_Comment_VideoId(videoId));
        if(ans == null){
            QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("videoId", videoId);
            ans = JSON.toJSONString(commentMapper.selectList(queryWrapper));
            redisTemplate.opsForValue().set(constant.getDreamFly_Comment_VideoId(videoId), ans, 5, TimeUnit.HOURS);
        }
        return ans;
    }

    public void insertOneComment(Comment comment){
        commentMapper.insert(comment);
        String ans = (String) redisTemplate.opsForValue().get(constant.getDreamFly_Comment_VideoId(comment.getVideoId()));
        List<Comment> commentList = JSON.parseArray(ans, Comment.class);
        if(commentList == null)
            commentList = new ArrayList<>();
        commentList.add(comment);
        redisTemplate.opsForValue().set(constant.getDreamFly_Comment_VideoId(comment.getVideoId()),JSON.toJSONString(commentList), 5, TimeUnit.HOURS);
    }

}
