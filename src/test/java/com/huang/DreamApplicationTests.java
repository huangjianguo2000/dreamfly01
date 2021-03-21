package com.huang;

import com.alibaba.fastjson.JSON;
import com.huang.mapper.UserMapper;
import com.huang.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.messaging.handler.annotation.SendTo;

import javax.mail.internet.MimeMessage;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DreamApplicationTests {

    @Autowired
    RedisTemplate redisTemplate;

    @Test
    void tset001(){

        System.out.println(  redisTemplate.opsForValue().increment("meckill002", 0));
    }


    @Test
    public void test(){
        System.out.println("哈哈哈哈");
    }
//    @Autowired
//    JavaMailSenderImpl mailSender;
//
//    @Test
//    void contextLoads() {
//        SimpleMailMessage mailMessage = new SimpleMailMessage();
//        mailMessage.setSubject("你好");
//        mailMessage.setText("123456");
//        mailMessage.setTo("306119715@qq.com");
//        mailMessage.setFrom("306119715@qq.com");
//        mailSender.send(mailMessage);
//    }

//    @Autowired
//    UserMapper userMapper;
//    @Autowired
//    RedisTemplate redisTemplate;
//
//    @Autowired
//    StringRedisTemplate stringRedisTemplate;
//    @Test
//    void insert(){
//        System.out.println(redisTemplate.opsForValue().get("DreamFly_user19162809952"));
//        //  redisTemplate.opsForValue().set("user", JSON.toJSONString(userMapper.selectList(null)));
////        List<User> ans = JSON.parseArray((String) redisTemplate.opsForValue().get("user"), User.class);
////        for(int i = 0; i < ans.size(); i++){
////            System.out.println(ans.get(i));
////        }
//
//    }

    //测试用户缓存更新到数据库操作
//    @Test
//    public void test(){
//        List<User> ans = userMapper.selectList(null);
//        for(int i = 0; i < ans.size(); i++){
//            User user = ans.get(i);
//            redisTemplate.opsForList().leftPush("DreamFly_user_update", JSON.toJSONString(user));
//        }
//        for(int i = 0; i < redisTemplate.opsForList().size("DreamFly_user_update"); i++){
//            System.out.println(redisTemplate.opsForList().index("DreamFly_user_update", i));
//        }
//        System.out.println();
//        User t = ans.get(1);
//        redisTemplate.opsForList().remove("DreamFly_user_update", 1, JSON.toJSONString(t));
//        t.setImgUrl("hahaha");
//        redisTemplate.opsForList().leftPush("DreamFly_user_update", JSON.toJSONString(t));
//        for(int i = 0; i < redisTemplate.opsForList().size("DreamFly_user_update"); i++){
//            System.out.println(redisTemplate.opsForList().index("DreamFly_user_update", i));
//        }
//        while(redisTemplate.opsForList().size("DreamFly_user_update") != 0){
//            redisTemplate.opsForList().leftPop("DreamFly_user_update");
//        }
//        System.out.println("over");
//        System.out.println(redisTemplate.opsForList().size("DreamFly_user_update"));
//        for(int i = 0; i < redisTemplate.opsForList().size("DreamFly_user_update"); i++){
//            System.out.println(redisTemplate.opsForList().index("DreamFly_user_update", i));
//        }
    //}



}
