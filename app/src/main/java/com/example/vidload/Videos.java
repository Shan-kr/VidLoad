package com.example.vidload;

public class Videos {

    private String title;
    private  String description;
    private  String thumbnail;
    private  String videoId;

    public  Videos(){};
    public  Videos(String title,String description ,String thumbnail,String videoId){
        this.title=title;
        this.description=description;
        this.thumbnail=thumbnail;
        this.videoId=videoId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
