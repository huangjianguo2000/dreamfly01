package com.huang.config;

import com.alibaba.fastjson.JSON;
import com.huang.dao.UserDao;
import com.huang.mapper.UserMapper;
import com.huang.pojo.User;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;



@Component
@ServerEndpoint(value = "/connectWebSocket/{userId}")
@Controller
public class WebSocket {

    @Autowired
    UserDao userDao;

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * 在线人数
     */
    public static int onlineNumber = 0;
    /**
     * 以用户的姓名为key，WebSocket为对象保存起来
     */
    private static Map<String, WebSocket> clients = new ConcurrentHashMap<String, WebSocket>();
    /**
     * 会话
     */
    private Session session;
    /**
     * 用户名称
     */
    private String userId;

    /**
     * 建立连接
     *
     * @param session
     */
    @OnOpen
    public void onOpen(@PathParam("userId") String userId, Session session) {
        onlineNumber++;
        logger.info("现在来连接的客户id：" + session.getId() + "用户名：" + userId);
        this.userId = userId;
        this.session = session;
        //  logger.info("有新连接加入！ 当前在线人数" + onlineNumber);
        try {
            //messageType 1代表上线 2代表下线 3代表在线名单 4代表普通消息
            //先给所有人发送通知，说我上线了
            Map<String, Object> map1 = new HashMap<>();
            map1.put("messageType", 1);
            map1.put("userId", userId);
            sendMessageAll(JSON.toJSONString(map1), userId);

            //把自己的信息加入到map当中去
            clients.put(userId, this);
            logger.info("有连接关闭！ 当前在线人数" + clients.size());
            //给自己发一条消息：告诉自己现在都有谁在线
            Map<String, Object> map2 = new HashMap<>();
            map2.put("messageType", 3);
            //移除掉自己
            Set<String> set = clients.keySet();
            map2.put("onlineUsers", set);
            sendMessageTo(JSON.toJSONString(map2), userId);
        } catch (IOException e) {
            logger.info(userId + "上线的时候通知所有人发生了错误");
        }
    }

    @CrossOrigin
    @ResponseBody
    @RequestMapping("/getAllUser")
    public String getAllUser(@RequestParam("id") int id) {

        List<User> ans = userDao.getAllUserNotMe(id);
        for (int i = 0; i < ans.size(); i++) {
            int k = ans.get(i).getId();
            boolean f = true;
            String kk = k + "";
            for (WebSocket item : clients.values()) {
//                System.out.println("在线人员名单  ：" + item.userId.toString());
                 //System.out.println(k);
                if (item.userId.equals(kk)) {
                  //  System.out.println(item.userId + "==========");
                    ans.get(i).setState(2);
                    f = false;
                    break;
                }
            }
            if (f) {
                ans.get(i).setState(1);
            }
        }
        return JSON.toJSONString(ans);
    }

    @OnError
    public void onError(Session session, Throwable error) {
        logger.info("服务端发生了错误" + error.getMessage());
        //error.printStackTrace();
    }

    /**
     * 连接关闭
     */
    @OnClose
    public void onClose() {
        onlineNumber--;
        //webSockets.remove(this);
        clients.remove(userId);
        try {
            //messageType 1代表上线 2代表下线 3代表在线名单  4代表普通消息
            Map<String, Object> map1 = new HashMap<>();
            map1.put("messageType", 2);
            map1.put("onlineUsers", clients.keySet());
            map1.put("userId", userId);
            sendMessageAll(JSON.toJSONString(map1), userId);
        } catch (IOException e) {
            logger.info(userId + "下线的时候通知所有人发生了错误");
        }
        //logger.info("有连接关闭！ 当前在线人数" + onlineNumber);
        logger.info("有连接关闭！ 当前在线人数" + clients.size());
    }

    /**
     * 收到客户端的消息
     *
     * @param message 消息
     * @param session 会话
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        try {
            logger.info("来自客户端消息：" + message + "客户端的id是：" + session.getId());

            System.out.println("------------    :" + message);

            // JSONObject jsonObject = JSON.parseObject(message);
//            String textMessage = jsonObject.getString("message");
//            String fromuserId = jsonObject.getString("userId");
//            String touserId = jsonObject.getString("to");
            String[] ans = message.split("#");
            String textMessage = ans[0];
            String fromuserId = ans[1];
            String touserId = ans[2];
//            System.out.println(textMessage);
//            System.out.println(fromuserId);
//            System.out.println(touserId);
            //如果不是发给所有，那么就发给某一个人
            //messageType 1代表上线 2代表下线 3代表在线名单  4代表普通消息
            Map<String, Object> map1 = new HashMap<>();
            map1.put("messageType", 4);
            map1.put("textMessage", textMessage);
            map1.put("fromuserId", fromuserId);
            if (touserId.equals("All")) {
                map1.put("touserId", "所有人");
                sendMessageAll(JSON.toJSONString(map1), fromuserId);
            } else {
                map1.put("touserId", touserId);
                System.out.println("开始推送消息给" + touserId);
                sendMessageTo(JSON.toJSONString(map1), touserId);
            }
        } catch (Exception e) {

            e.printStackTrace();
            logger.info("发生了错误了");
        }

    }


    public void sendMessageTo(String message, String TouserId) throws IOException {
        for (WebSocket item : clients.values()) {

            //    System.out.println("在线人员名单  ："+item.userId.toString());
            if (item.userId.equals(TouserId)) {
                System.out.println(message);
                item.session.getAsyncRemote().sendText(message);

                break;
            }
        }
    }

    public void sendMessageAll(String message, String FromuserId) throws IOException {
        for (WebSocket item : clients.values()) {
            item.session.getAsyncRemote().sendText(message);
        }
    }

    public static synchronized int getOnlineCount() {
        return onlineNumber;
    }

    @Autowired
    WebSocket webSocket;


    @ResponseBody
    @GetMapping("/sendTo")
    public String sendTo(@RequestParam("userId") String userId, @RequestParam("msg") String msg) throws IOException {

        webSocket.sendMessageTo(msg, userId);

        return "推送成功";
    }

}
