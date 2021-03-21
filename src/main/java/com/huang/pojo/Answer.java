package com.huang.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Answer {
    private int discussId;
    private String answer;
    private int userId;
    private String nickName;
    private int agree; //赞同这个回答的数量
    private String time;
    private String headImg;

}
