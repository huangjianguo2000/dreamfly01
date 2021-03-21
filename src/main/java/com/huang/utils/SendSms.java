package com.huang.utils;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

/*
pom.xml
<dependency>
  <groupId>com.aliyun</groupId>
  <artifactId>aliyun-java-sdk-core</artifactId>
  <version>4.5.3</version>
</dependency>
*/

@Service
public class SendSms {

    @Autowired
    JavaMailSenderImpl mailSender;

    public  void sendCode(String number, String code) {
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAI4GE7Yxx16tyNyNVtYvuK", "5XTNWUFWQ2LzXprdDrNBqQHnFpDD4n");
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", number);
        request.putQueryParameter("SignName", "纳兹商城");
        request.putQueryParameter("TemplateCode", "SMS_205442216");
        request.putQueryParameter("TemplateParam", "{code:" + code + "}");
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }

    public void sendEmail(String email, String code){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setSubject("验证码");
        mailMessage.setText("您的验证码为：" + code);
        mailMessage.setTo(email);
        mailMessage.setFrom("306119715@qq.com");
        mailSender.send(mailMessage);
    }
    public void sendEmailNotify(String email){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setSubject("DreamFly");
        mailMessage.setText("你已经很多天没有来DreamFly学习了， 是不是不想学习了，不学习以后怎么找工作， 怎么挣钱！！！");
        mailMessage.setTo(email);
        mailMessage.setFrom("306119715@qq.com");
        mailSender.send(mailMessage);
    }
}
