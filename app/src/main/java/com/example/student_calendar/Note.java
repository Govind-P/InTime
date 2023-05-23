package com.example.student_calendar;

public class Note {
    String email;
    String topic;
    String desc;
    String sub;
    String downloadUrl;

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    String Key;


    public Note(String email, String topic, String desc, String sub, String downloadUrl) {
        this.email = email;
        this.topic = topic;
        this.desc = desc;
        this.sub = sub;
        this.downloadUrl = downloadUrl;
    }



    public String getDownloadUrl() {
        return downloadUrl;
    }

    public String getEmail() {
        return email;
    }

    public String getTopic() {
        return topic;
    }

    public String getDesc() {
        return desc;
    }

    public String getSub() {
        return sub;
    }

    public Note(){

    }

}
