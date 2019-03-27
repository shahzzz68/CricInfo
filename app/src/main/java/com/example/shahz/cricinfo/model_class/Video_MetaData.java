package com.example.shahz.cricinfo.model_class;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class Video_MetaData {

    String videoName;
    String videoUrl;
    String thumbnail;

    Video_MetaData (){};

    public Video_MetaData(String videoName, String videoUrl, String thumbnail) {
        this.videoName = videoName;
        this.videoUrl = videoUrl;
        this.thumbnail = thumbnail;
    }

    public String getVideoName() {
        return videoName;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public String getThumbnail() {
        return thumbnail;
    }
}
