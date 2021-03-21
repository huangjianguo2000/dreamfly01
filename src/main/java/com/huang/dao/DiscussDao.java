package com.huang.dao;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.huang.mapper.AnswerMapper;
import com.huang.mapper.DiscussMapper;
import com.huang.pojo.Answer;
import com.huang.pojo.Blog;
import com.huang.pojo.Discuss;
import com.huang.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.object.UpdatableSqlQuery;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class DiscussDao {

    @Autowired
    AnswerMapper answerMapper;
    @Autowired
    DiscussMapper discussMapper;
    @Autowired
    RedisTemplate redisTemplate;

    Constant constant = new Constant();

    public String getAllDiscuss() {
        String ans = (String) redisTemplate.opsForValue().get(constant.DreamFly_AllDiscuss);
        if (ans == null) {
            ans = JSON.toJSONString(discussMapper.selectList(null));
            redisTemplate.opsForValue().set(constant.DreamFly_AllDiscuss, ans, 5, TimeUnit.HOURS);
        }
        return ans;
    }

    public String getDiscussByUserId(int userId) {
        String ans = (String) redisTemplate.opsForValue().get(constant.getDreamFly_AllDiscuss_User(userId));
        if (ans == null) {
            QueryWrapper<Discuss> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("userId", userId);
            ans = JSON.toJSONString(discussMapper.selectList(queryWrapper));
            redisTemplate.opsForValue().set(constant.getDreamFly_AllDiscuss_User(userId), ans, 5, TimeUnit.HOURS);
        }
        return ans;
    }

    public String addOneViewOfDiscuss(int id) {

        String ans = (String) redisTemplate.opsForHash().get(constant.DreamFly_Discuss, id);
        if (ans == null) {
            QueryWrapper<Discuss> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("id", id);
            ans = JSON.toJSONString(discussMapper.selectOne(queryWrapper));
        }
       // System.out.println(ans);
        Discuss discuss = JSON.parseObject(ans, Discuss.class);
        discuss.addView();
        //redisTemplate.opsForHash().delete(constant.DreamFly_Discuss,id);
        redisTemplate.opsForHash().put(constant.DreamFly_Discuss, id, JSON.toJSONString(discuss));
        redisTemplate.opsForList().remove(constant.DreamFly_Discuss_Update_list, 0, id);
        redisTemplate.opsForList().leftPush(constant.DreamFly_Discuss_Update_list,id);
        return ans;
    }

    public void insertDiscuss(Discuss discuss){
        discussMapper.insert(discuss);

        List<Discuss> discussList = JSON.parseArray((String)redisTemplate.opsForValue().get(constant.DreamFly_AllBlog),Discuss.class);
        if (discussList != null) {
            discussList.add(discuss);
            redisTemplate.opsForValue().set(constant.DreamFly_AllBlog, JSON.toJSONString(discussList), 5, TimeUnit.HOURS);
        }

        List<Discuss> discussList2 = JSON.parseArray((String)redisTemplate.opsForValue().get(constant.getDreamFly_AllDiscuss_User(discuss.getUserId())),Discuss.class);
        if (discussList2 != null) {
            discussList2.add(discuss);
            redisTemplate.opsForValue().set(constant.getDreamFly_AllDiscuss_User(discuss.getUserId()), JSON.toJSONString(discussList2), 5, TimeUnit.HOURS);
        }

    }

    public String getAnswerByDiscussId(int discussId){
        String ans = (String) redisTemplate.opsForValue().get(constant.getDreamFly_Answer_Of_Discuss(discussId));
        if(ans == null){
            QueryWrapper<Answer> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("discussId", discussId);
            ans = JSON.toJSONString(answerMapper.selectList(queryWrapper));
            redisTemplate.opsForValue().set(constant.getDreamFly_Answer_Of_Discuss(discussId), ans, 5, TimeUnit.HOURS);
        }
        return ans;
    }

    public void insertAnswer(Answer answer){
        answerMapper.insert(answer);
        List<Answer> answerList = JSON.parseArray((String) redisTemplate.opsForValue().get(constant.getDreamFly_Answer_Of_Discuss(answer.getDiscussId())), Answer.class);
        if(answerList != null){
            answerList.add(answer);
            redisTemplate.opsForValue().set(constant.getDreamFly_Answer_Of_Discuss(answer.getDiscussId()), JSON.toJSONString(answerList), 5, TimeUnit.HOURS);
        }
    }

    public void updateImgUrl(String url, int id){
        UpdateWrapper<Discuss> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("userId", id).set("imgUrl",url);
        discussMapper.update(null, updateWrapper);
        String ans = (String) redisTemplate.opsForValue().get(constant.DreamFly_AllDiscuss);
        if (ans != null) {
            List<Discuss> discussList = JSON.parseArray(ans, Discuss.class);
            for(int i = 0; i < discussList.size(); i++){
                discussList.get(i).setImgUrl(url);
            }
            redisTemplate.opsForValue().set(constant.DreamFly_AllDiscuss, JSON.toJSONString(discussList), 5, TimeUnit.HOURS);
        }
    }
}
