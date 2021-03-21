package com.huang.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Chatmsg {
    private String connection;
    private String msg;
    private String time;
    private Integer sendId;
    private Integer state;
    private Integer sendToId;

}
