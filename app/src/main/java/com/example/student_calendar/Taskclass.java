package com.example.student_calendar;

public class Taskclass {

    String email;
    String topic;
    String desc;
    String sub;
    String prio;
    String dueDate;
    String dueTime;
    String notify;
    String status;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    String key;

    public Taskclass(String email, String topic, String desc, String sub, String prio, String dueDate, String dueTime, String notify, String imageurl,String status) {
        this.email = email;
        this.topic = topic;
        this.desc = desc;
        this.sub = sub;
        this.prio = prio;
        this.dueDate = dueDate;
        this.dueTime = dueTime;
        this.notify = notify;
        this.imageurl = imageurl;
        this.status = status;
    }

    String imageurl;



    public String getStatus() {
        return status;
    }


    public String getImageurl() {
        return imageurl;
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

    public String getPrio() {
        return prio;
    }

    public String getDueDate() {
        return dueDate;
    }

    public String getDueTime() {
        return dueTime;
    }

    public String getNotify() {
        return notify;
    }

    public Taskclass(){

    }

}
