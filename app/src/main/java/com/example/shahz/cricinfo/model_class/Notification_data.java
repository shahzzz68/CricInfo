package com.example.shahz.cricinfo.model_class;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class Notification_data {

    private String msgTitle;
    private String msgBody;
    @ServerTimestamp
    private Date data;


    public Notification_data() {
    }

    public Date getDate() {
        return data;
    }

    public void setDate(Date date) {
        this.data = date;
    }

    public Notification_data(String msgTitle, String msgBody, Date date) {
        this.msgTitle = msgTitle;
        this.msgBody = msgBody;
        this.data=date;

    }

    public String getMsgTitle() {
        return msgTitle;
    }

    public void setMsgTitle(String msgTitle) {
        this.msgTitle = msgTitle;
    }

    public String getMsgBody() {
        return msgBody;
    }

    public void setMsgBody(String msgBody) {
        this.msgBody = msgBody;
    }
}
