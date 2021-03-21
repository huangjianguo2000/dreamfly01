package com.huang.controller;

import com.alibaba.fastjson.JSON;
import com.huang.dao.AlgorithmDao;
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
@RequestMapping(value = "/question")
public class AlgorithmController {
    @Autowired
    AlgorithmDao algorithmDao;
    @RequestMapping("/getAllAlgorithmQuestion")
    public String getAll(@RequestParam("pageIndex") int pageIndex) {

        return algorithmDao.getAll(pageIndex);
    }

    @RequestMapping("/getPageSize")
    public String getPageSize() {

        return algorithmDao.getPageSize();
    }

    @RequestMapping("/getAlgorithmQuestionById")
    public String getQuestion(@RequestParam("id") int id) {

        return algorithmDao.getQuestion(id);
    }
}
