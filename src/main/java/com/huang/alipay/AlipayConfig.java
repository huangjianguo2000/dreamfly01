package com.huang.alipay;

import java.io.FileWriter;
import java.io.IOException;

public class AlipayConfig {
    // 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号,开发时使用沙箱提供的APPID，生产环境改成自己的APPID
    public static String APP_ID = "2016103100781903";

    // 商户私钥，您的PKCS8格式RSA2私钥
    public static String APP_PRIVATE_KEY = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQC77MSRX5kK38SaRPfy927v3N9mKgm19QtVzKDRw2/upbd0k8wEyom123W6lBPioCDBGOVw6LiESWys7WvKClVaTwhu3zd1tIjyA/YUhXTmcl3AZDzZZBITam9jTeiSyzHTxkPD2nTNLk4oE5WXOa7+vW8CyjYJMi8i5LxIFMVkCJOsu2G2DuCOHca8VrsYFvfXLL7p5quQtGvmgBSyETFLnaBzU5Zlx4yKl4FN9Zbm0qvdosMUVXKgJqC+W4xaPMLA5h31NWovy/ronTXUrFZcbgvjFfCEW14whyOjjKfNxCC8Y6yBWupwZ6kjRgBUzTo+Qmgl/e4zsdlSBy1WByXrAgMBAAECggEARBLUccSrb6D3Wtq45q5y5TihRCTeN6d4X7AsYU/mcBBfkD8pnGKx18qTru6M9X8D3m7iTjA/BsyCLXIjpaBworIWX/ND58NqCCEfNvfRDNlI1LR6xlTVp2raxqImKWapugkL6xprTRgE/8d4XLtshKKiX+RYBqTDJmStP38mscE6PVjPSQF2MY8+O9BChUIt1OZ2hyNHsKETejcHOJ5CaxMowHIEGLUEnjApxdirKH3UXqGKgHxckvuLh0Ht4/weuLFposfizqYeqt7RKV8DE4xwEWO3swE/amAKpe44FVltSikdwB+wnZSGE+mvlCRsBdZvgeT90j7VD9yKcrmBIQKBgQDs04YjE2aVLkfCFEq3RuSEJspa1hC84Vke2iZNNMChjsd5LR8WCrgMqqfuzqDPzYHXSSLtGU5FWAIR4B4HD7jwSx9UnEdP1JPwTywIGUdJGmhRs1u7rHPVcBYptXK/P/PxXGmjui2wPoYBIVEQTPdLPqPYpqkKNAgjKmR5NIwCxQKBgQDLI7XoC8RMCCwdpCNTQOgOP3TbwcaAx9k1M+uvmNx/l1YMJbID7ZMWP44tHGYQSOHATVnSmCJW/9lZOO9vkvVSTOJS8U5RUySS5FmKd81NPbbW5Nlhah5Jx7yfSM10DcZ+nc4J4tpm9EYecuJPKhWC4WYrgmOEHsFmSOrtl9dQ7wKBgQCAG0/VC3qTOgyxDH76M0wLvFv71Q4Kzi+CQ+kyHFYTToPSpbjKGJRErc/Xm0hcAEO7LCHLdcPkON4twzkbAsM8oLZa84qTwV8hQAKGgv4PqkxB3a7Cj1+9Vjt25mMi1zpjFmVCnsR2MOwWEmqiUa/3sPO6jzSjg0/fjd/5vJX8LQKBgHYblTANZ1wthDjXrhJBsqpWCsOarqdBivB795Ro9ArbGpSFdjMCdnqBCVEBKfZd6aO9VXQ0MAMVTnGpwMzGTQxE13ISCs8N4D3ntofjj+JQ93WKKTmSUXnkP678zTekqrwDVEA7PXCOphYeBaaxeUBXfOffL60plJi73Ju3pODRAoGBALGVoNdSMhlj5tS2hFIJCPosJcU+WFSyWbL04/gCsKb1E3gc1N1UjbwQu1NbM4j9jzqjGrDgR5Y5W0E3xmpIuMjN48WlVyOl9mJjrHwV5ze6pZUORQomgFkI9nUFKrKMSfwwZlVxCoHzoSn7E1zG8ZBKcJYhk6BOjNsif/TWAYgo";

    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAms03B9QjdMQXQFA7R4Ndq6nOQCcDVCmufEySCM5NWveshYkQvaAK1msDmwNqudwOfeF15UV/zYNSeQ7N6GJ/byM4JIc+7wbtEkioLf6x7HxfCiLcNrUipweNcOzbxESjbx2+k4hR+f1kNXxvbCYR31M6OYtbo69EcbdfmDBn10Q0gbN7nOjsgJ0SVKkM0vwDmEX/WH6XdEoXM4uikTJTvEvOrakMOqDOMeS6w7DUPJqOJAc0Xo8Ew5oVhHBCwln8hpXXDOoA0AlRMIKFzk3VQsyCkRFpIUvUfocIx8u/Qo7PF533uWX3fiwzjHu6rEGqXyYb4fNBSqXoiB2z3x9bCwIDAQAB";

    // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = "http://localhost:83/getReturnPage";


    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问(其实就是支付成功后返回的页面)
    //public static String return_url = "http://localhost:83/getReturnPage";
    public static String return_url = "http://www.handsomehuang.cn:83/getReturnPage";


    // 签名方式
    public static String SIGN_TYPE = "RSA2";

    // 字符编码格式
    public static String CHARSET = "utf-8";

    // 支付宝网关，这是沙箱的网关
    public static String GATEWAYURL = "https://openapi.alipaydev.com/gateway.do";

    // 支付宝网关
    public static String log_path = "C:\\";


//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    /**
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     *
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis() + ".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
