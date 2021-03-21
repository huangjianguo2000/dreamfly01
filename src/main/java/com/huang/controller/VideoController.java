package com.huang.controller;

import com.alibaba.fastjson.JSON;
import com.huang.dao.VideoDao;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@CrossOrigin
@Controller
@ResponseBody
public class VideoController {

    @Autowired
    VideoDao videoDao;

    @RequestMapping("/getAllVideo")
    public String getAllVideo() {
        return videoDao.getAllVideo();
    }

    @RequestMapping("/getByUserId")
    public String getByUserId(@RequestParam("userId") int userId) {
        return videoDao.getVideoByUserId(userId);
    }
    @RequestMapping("/getVideoById")
    public String getVideoById(@RequestParam("id") int id) {
        return videoDao.getVideoById(id);
    }

    @RequestMapping("/addOneView")
    public String addView(@RequestParam("id") int id) {
       return videoDao.addView(id);
    }

    @RequestMapping("/addOnePoint")
    public String addPoint(@RequestParam("id") int id) {
        videoDao.addPoint(id);
        return "OK";
    }

    @RequestMapping("/addOneCollection")
    public String addCollection(@RequestParam("userId") int userId, @RequestParam("id") int id) {
        videoDao.addCollection(id);
        return "OK";
    }

//    @RequestMapping("/selectSomeVideo")
//    public String selectSome(@RequestParam("keyWord") String keyWord) {
//        SqlSession sqlSession = Mybatis.getSqlSession();
//        videoDao mapper = sqlSession.getMapper(videoDao.class);
//        List<video> ans = mapper.selectSome(keyWord);
//        return JSON.toJSONString(ans);
//    }

    @RequestMapping("/getVideoList")
    public String getVideoList(){
        return videoDao.getVideoList();
    }
}
