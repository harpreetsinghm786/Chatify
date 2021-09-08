package com.g.pb.chatify;

import java.util.Date;

public class recents_list {



    String phonenumber;
    String url;
    String lastmsg;
    String name;


    public recents_list() {

    }

    public recents_list(String name, String phonenumber, String url, String lastmsg) {
        this.name = name;
        this.phonenumber = phonenumber;
        this.lastmsg = lastmsg;
        this.url = url;



    }




    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLastmsg() {
        return lastmsg;
    }

    public void setLastmsg(String lastmsg) {
        this.lastmsg = lastmsg;
    }

}

