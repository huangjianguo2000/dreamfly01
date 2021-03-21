package com.huang.controller;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.huang.dao.UserDao;
import com.huang.mapper.UserMapper;
import com.huang.pojo.User;
import com.huang.service.UserService;
import com.huang.utils.Constant;
import com.huang.utils.SendSms;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@ResponseBody
@Controller
public class UserController {


    @Autowired
    UserService userService;
    @Autowired
    UserDao userDao;

    @Autowired
    SendSms sendSms;



    //用户登录账号密码判断
    @RequestMapping("/checkUser")
    public String checkUser(@RequestParam("phoneNumber") String phoneNumber,
                          @RequestParam("passWord") String passWord) {
        return userService.loginUser(passWord,userDao.selectOneByPhone(phoneNumber));
    }

    //判断手机号是不是已经被注册
    @RequestMapping("/checkPhone")
    public String checkPhone(@RequestParam("phoneNumber") String phoneNumber) {
        return userService.checkPhone(phoneNumber);
    }

    //用户注册
    @RequestMapping("/insertUser")
    public String insertUser(@RequestParam("phoneNumber") String phoneNumber,
                             @RequestParam("nickName") String nickName,
                             @RequestParam("passWord") String passWord,
                             @RequestParam("birthday") String birthday,
                             @RequestParam("sex") String sex) {
        User user = new User();
        user.setBirthday(birthday);
        user.setPhoneNumber(phoneNumber);
        user.setNickName(nickName);
        user.setSex(sex);
        user.setPassWord(passWord);
        user.setVip("normal");
        user.setImgUrl( "http://www.handsomehuang.cn:8081/img2/userHead/80055ef5eb0ae91cca60de6f23aad7e2.jpg");

        userDao.insertUser(user);
        return "OK";
    }

    //获得用户头像
    @RequestMapping("/getImgUrl")
    public String toIndex2(@RequestParam("userId") int userId) {
        return userDao.selectOneById(userId).getImgUrl();
    }

    //更新用户的昵称
    @RequestMapping("/updateNickName")
    public String updateNickName(@RequestParam("phoneNumber") String phoneNumber,
                                 @RequestParam("nickName") String nickName) {

        userDao.updateNickName(userDao.selectOneByPhone(phoneNumber),phoneNumber,nickName);
        return "OK";
    }

    //发短信
    @RequestMapping("/sendcode")
    public String Register(@RequestParam("phone") String phoneNumber,
                           @RequestParam("code") String code) {
        System.out.println("ye");
        sendSms.sendCode(phoneNumber, code);
        return "OK";
    }
    @RequestMapping("/sendEmailCode")
    public String sendEmailCode() {
        sendSms.sendEmail("306119715@qq.com", "564798");
        return "OK";
    }
}
