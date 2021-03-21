package com.huang.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.huang.dao.BuyedDao;
import com.huang.mapper.BuyedMapper;
import com.huang.pojo.Buyed;
import com.huang.utils.Constant;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.concurrent.TimeUnit;

@CrossOrigin
@Controller
@ResponseBody
public class BuyedController {

    @Autowired
    BuyedDao buyedDao;
    @RequestMapping("/getBuyedVideo")
    public String getBuyedVideo(@RequestParam("userId") int userId, HttpServletRequest request, HttpServletResponse response) {
        Cookie cookie[] = request.getCookies();
        System.out.println(cookie);
        if (cookie == null){
            System.out.println("这是第一次访问本网站");
        }else{
            for (Cookie cookie1 : cookie){
                System.out.println(cookie1);
            }
        }
        response.addCookie(new Cookie("time", "hhhhh"));
        return buyedDao.getAllBuyed(userId);
    }
}
