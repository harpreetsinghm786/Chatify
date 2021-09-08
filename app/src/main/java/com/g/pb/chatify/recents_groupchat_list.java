package com.g.pb.chatify;

public class recents_groupchat_list {


    String groupname;
    String key;
    String url;
    String sendername;
    String lastmsg;



    public recents_groupchat_list() {

    }

    public recents_groupchat_list(String groupname, String key, String url, String lastmsg,String sendername) {
        this.groupname = groupname;
        this.key = key;
        this.lastmsg = lastmsg;
        this.url = url;
        this.sendername=sendername;


    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSendername() {
        return sendername;
    }

    public void setSendername(String sendername) {
        this.sendername = sendername;
    }

    public String getName() {
        return groupname;
    }

    public void setName(String name) {
        this.groupname = name;
    }

    public String getPhonenumber() {
        return key;
    }

    public void setPhonenumber(String phonenumber) {
        this.key = phonenumber;
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

