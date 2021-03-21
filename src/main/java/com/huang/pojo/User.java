package com.huang.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private int id;
    private String phoneNumber;
    private String nickName;
    private String passWord;
    private String imgUrl;
    private Integer email;
    private Integer state;
    private String vip;
    private Integer surplus;
    private String sex;
    private String birthday;

}
