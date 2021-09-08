package com.g.pb.chatify;

import java.util.Date;

public class chat_class {
    String key,message,number;

 public chat_class(){

 }
    public chat_class(String key, String message, String number) {
        this.key = key;
        this.message = message;
        this.number=number;



    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

   }
