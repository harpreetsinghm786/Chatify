package com.g.pb.chatify;

public class group_chat_class {
    String key,message,number,sendername,url;

 public group_chat_class(){

 }

    public group_chat_class(String key, String message, String number,String sendername,String url) {
        this.key = key;
        this.message = message;
        this.number=number;
        this.sendername=sendername;
        this.url=url;



    }

    public String getSendername() {
        return sendername;
    }

    public void setSendername(String sendername) {
        this.sendername = sendername;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
