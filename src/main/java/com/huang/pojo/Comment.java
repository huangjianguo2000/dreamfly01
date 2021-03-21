package com.huang.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    private Integer userId;
    private String msg;
    private Integer videoId;
    private String nickName;
    private String imgUrl;
    private String time;

}
