package com.g.pb.chatify;

import java.util.List;

public class new_group_list {
    List<user_data> members_list;
    String groupname,discription,url;
    List<chat_class>chat;
    String key;

    public new_group_list(){

    }

    public new_group_list(List<user_data> members_lists, String groupname, List<chat_class> chat, String discription, String url, String key) {
        this.members_list = members_lists;
        this.groupname = groupname;
        this.chat = chat;
        this.discription=discription;
        this.url=url;
        this.key=key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<user_data> getMembers_list() {
        return members_list;
    }

    public void setMembers_list(List<user_data> members_list) {
        this.members_list = members_list;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public List<chat_class> getChat() {
        return chat;
    }

    public void setChat(List<chat_class> chat) {
        this.chat = chat;
    }
}
