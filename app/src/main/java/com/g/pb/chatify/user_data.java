package com.g.pb.chatify;

import java.util.Date;

public class user_data {
    String name;
    String phonenumber;
    String url;
    String userbio;
    userdob userdob;

    public user_data(){

    }
    public user_data(String name, String phonenumber, String url, String userbio,userdob userdob) {
        this.name = name;
        this.phonenumber = phonenumber;
        this.userbio =userbio;
        this.url=url;

        this.userdob=userdob;

    }



    public com.g.pb.chatify.userdob getUserdob() {
        return userdob;
    }

    public void setUserdob(com.g.pb.chatify.userdob userdob) {
        this.userdob = userdob;
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

    public String getUserbio() {
        return userbio;
    }

    public void setUserbio(String userbio) {
        this.userbio = userbio;
    }
}
