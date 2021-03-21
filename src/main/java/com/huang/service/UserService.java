package com.huang.service;

import com.alibaba.fastjson.JSON;
import com.huang.dao.UserDao;
import com.huang.mapper.UserMapper;
import com.huang.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public class UserService {

    @Autowired
    UserMapper userMapper;
    @Autowired
    UserDao userDao;

    //用户登录判断
    public String loginUser(String passWord, User user){
        if(user == null)
            return "false";
        if(user.getPassWord().equals(passWord))
            return JSON.toJSONString(user);
        return "false";
    }

    //判断用户号码是不是已经存在了
    public String checkPhone(String phoneNumber) {
        User user = userDao.selectOneByPhone(phoneNumber);
        if(user == null)
            return "true";
        return "false";
    }
}
