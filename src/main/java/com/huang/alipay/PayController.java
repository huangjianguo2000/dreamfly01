package com.huang.alipay;


import com.huang.dao.BuyedDao;
import com.huang.dao.UserDao;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;


@Controller
public class PayController {
    @Autowired
    @Qualifier("alipayService")
    private AlipayService alipayService;

    @Autowired
    UserDao userDao;
    @Autowired
    BuyedDao buyedDao;

    @ResponseBody
    @CrossOrigin
    @RequestMapping("/pay")
    public String payController(@RequestParam("goodNo") String id,
                                @RequestParam("goodName") String name,
                                @RequestParam("goodPrice") String amount,
                                @RequestParam("userId") int userId,
                                HttpSession session
    ) throws Exception {
        System.out.println("接收到了");
        Integer pay = Integer.valueOf(amount);
        String pays = alipayService.webPagePay(id, pay, name);
        session.setAttribute("name", name);
        session.setAttribute("userId", userId);
        return pays;
    }

    @ResponseBody
    @CrossOrigin
    @RequestMapping("/payVideo")
    public String payVideo(@RequestParam("goodNo") String id,
                           @RequestParam("goodName") String name,
                           @RequestParam("goodPrice") String amount,
                           @RequestParam("userId") String userId,
                           @RequestParam("videoId") String videoId,
                           HttpSession session) throws Exception {
        Integer pay = Integer.valueOf(amount);
        Integer userId2 = Integer.valueOf(userId);
        Integer videoId2 = Integer.valueOf(videoId);
        String pays = alipayService.webPagePay(id, pay, name);
        session.setAttribute("videoId", videoId);
        session.setAttribute("name", name);
        session.setAttribute("userId", userId);
        System.out.println(name);


        return pays;
    }


    @RequestMapping("/getReturnPage")
    public String returnPage(HttpServletResponse servletResponse, HttpServletRequest request,
                             HttpSession session) throws UnsupportedEncodingException {
        System.out.println("支付成功--");
        //商户订单号
        String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("UTF-8"), "UTF-8");
        //支付宝交易号
        String trade_no = new String(request.getParameter("trade_no").getBytes("UTF-8"), "UTF-8");
        //付款金额
        String total_amount = new String(request.getParameter("total_amount").getBytes("UTF-8"), "UTF-8");
        String name = session.getAttribute("name").toString();
        int userId = Integer.parseInt(session.getAttribute("userId").toString());

        String vip = "";
        String num = "";
        if (name.equals("buyVideo")) {
            int videoId = Integer.parseInt(session.getAttribute("videoId").toString());
            buyedDao.insertBuyed(userId, videoId);
        } else {
            for (int i = 0; i < name.length(); i++) {
                char t = name.charAt(i);
                if (t >= 'a' && t <= 'z') {
                    vip += t;
                } else {
                    num += t;
                }
            }
            int sum = Integer.parseInt(num);
            userDao.updateVip(userId, vip, sum);
        }

        // return "http://handsomehuang.cn:81/home";
        return "returnUrl";
    }


}
