package com.huanglong.login.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    private Integer code;
    private String message;
    Map<String,Object> map=new HashMap<>();

    public static Message success(){
        Message message=new Message();
        message.setCode(100);
        message.setMessage("处理成功");
        return message;
    }
    public static Message fail(){
        Message message=new Message();
        message.setCode(200);
        message.setMessage("处理失败");
        return message;
    }
    public  Message add(String key,Object value){
        this.getMap().put(key,value);
        return this;
    }

}

