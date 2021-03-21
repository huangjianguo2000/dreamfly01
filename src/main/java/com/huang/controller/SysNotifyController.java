package com.huang.controller;

import com.alibaba.fastjson.JSON;
import com.huang.dao.SysNotifyDao;
import com.huang.pojo.Sysnotify;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@ResponseBody
@CrossOrigin
@Controller
public class SysNotifyController {

    @Autowired
    SysNotifyDao sysNotifyDao;

    @RequestMapping("/getAllSysNotify")
    public String getAllSysNotify(@RequestParam("userId") int userId) {
        return sysNotifyDao.getAllNotify(userId);
    }

    @RequestMapping("/insertOneSysNotify")
    public String insertOneSysNotify(@RequestParam("userId") int userId,
                                     @RequestParam("sendId") int sendId,
                                     @RequestParam("msg") String msg,
                                     @RequestParam("pl") String pl,
                                     @RequestParam("time") String time,
                                     @RequestParam("nickName") String nickName,
                                     @RequestParam("imgUrl") String imgUrl,
                                     @RequestParam("state") int state,
                                     @RequestParam("type") String type,
                                     @RequestParam("id") int id,
                                     @RequestParam("videoImg") String videoImg) {
        // System.out.println(12323);
        Sysnotify sysnotify = new Sysnotify();
        sysnotify.setUserId(userId);
        sysnotify.setSendId(sendId);
        sysnotify.setMsg(msg);
        sysnotify.setPl(pl);
        sysnotify.setTime(time);
        sysnotify.setNickName(nickName);
        sysnotify.setImgUrl(imgUrl);
        sysnotify.setState(state);
        sysnotify.setType(type);
        sysnotify.setId(id);
        sysnotify.setVideoImg(videoImg);
        sysNotifyDao.insertNotify(sysnotify);
        return "OK";
    }


    @RequestMapping("/updateState")
    public String updateState(@RequestParam("userId") int userId) {
        sysNotifyDao.updateState(userId);
        return "OK";
    }
}
