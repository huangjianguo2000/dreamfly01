package com.huang.dao;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.huang.mapper.BlogMapper;
import com.huang.pojo.Blog;
import com.huang.pojo.Discuss;
import com.huang.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


@Service
public class BlogDao {

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    BlogMapper blogMapper;


    Constant constant = new Constant();

    public String getAllBlog() {
        String ans = (String) redisTemplate.opsForValue().get(constant.DreamFly_AllBlog);
        if (ans == null) {
            ans = JSON.toJSONString(blogMapper.selectList(null));
            redisTemplate.opsForValue().set(constant.DreamFly_AllBlog, ans, 5, TimeUnit.HOURS);
        }
        return ans;
    }

    public String getBlogByUserId(int userId) {
        String ans = (String) redisTemplate.opsForValue().get(constant.getDreamFly_AllBlog_User(userId));
        if (ans == null) {
            QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("userId", userId);
            ans = JSON.toJSONString(blogMapper.selectList(queryWrapper));
            redisTemplate.opsForValue().set(constant.getDreamFly_AllBlog_User(userId), ans, 5, TimeUnit.HOURS);
        }
        return ans;
    }

    public String searchBlogById(int id){
        QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        String ans = JSON.toJSONString(blogMapper.selectOne(queryWrapper));
        return ans;
    }

    public void insertBlog(Blog blog){
        blogMapper.insert(blog);

        List<Blog> blogList = JSON.parseArray((String)redisTemplate.opsForValue().get(constant.DreamFly_AllBlog),Blog.class);
        if (blogList != null) {
            blogList.add(blog);
            redisTemplate.opsForValue().set(constant.DreamFly_AllBlog, JSON.toJSONString(blogList), 5, TimeUnit.HOURS);
        }

        List<Blog> blogList2 = JSON.parseArray((String)redisTemplate.opsForValue().get(constant.getDreamFly_AllBlog_User(blog.getUserId())),Blog.class);
        if (blogList2 != null) {
            blogList2.add(blog);
            redisTemplate.opsForValue().set(constant.getDreamFly_AllBlog_User(blog.getUserId()), JSON.toJSONString(blogList2), 5, TimeUnit.HOURS);
        }
    }

    public void updateImgUrl(String url, int id){
        UpdateWrapper<Blog> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("userId", id).set("imgUrl",url);
        blogMapper.update(null, updateWrapper);
        String ans = (String) redisTemplate.opsForValue().get(constant.DreamFly_AllBlog);
        if (ans != null) {
            List<Blog> blogList = JSON.parseArray(ans, Blog.class);
            for(int i = 0; i < blogList.size(); i++){
                blogList.get(i).setImgUrl(url);
            }
            redisTemplate.opsForValue().set(constant.DreamFly_AllBlog, JSON.toJSONString(blogList), 5, TimeUnit.HOURS);
        }
    }

}
