
package com.christagram.app.data.venues;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Tip {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("createdAt")
    @Expose
    private Integer createdAt;
    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("canonicalUrl")
    @Expose
    private String canonicalUrl;
    @SerializedName("photo")
    @Expose
    private Photo photo;
    @SerializedName("photourl")
    @Expose
    private String photourl;
    @SerializedName("likes")
    @Expose
    private Likes likes;
    @SerializedName("logView")
    @Expose
    private Boolean logView;
    @SerializedName("agreeCount")
    @Expose
    private Integer agreeCount;
    @SerializedName("disagreeCount")
    @Expose
    private Integer disagreeCount;
    @SerializedName("todo")
    @Expose
    private Todo todo;
    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("lastVoteText")
    @Expose
    private String lastVoteText;
    @SerializedName("lastUpvoteTimestamp")
    @Expose
    private Integer lastUpvoteTimestamp;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Integer createdAt) {
        this.createdAt = createdAt;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCanonicalUrl() {
        return canonicalUrl;
    }

    public void setCanonicalUrl(String canonicalUrl) {
        this.canonicalUrl = canonicalUrl;
    }

    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

    public String getPhotourl() {
        return photourl;
    }

    public void setPhotourl(String photourl) {
        this.photourl = photourl;
    }

    public Likes getLikes() {
        return likes;
    }

    public void setLikes(Likes likes) {
        this.likes = likes;
    }

    public Boolean getLogView() {
        return logView;
    }

    public void setLogView(Boolean logView) {
        this.logView = logView;
    }

    public Integer getAgreeCount() {
        return agreeCount;
    }

    public void setAgreeCount(Integer agreeCount) {
        this.agreeCount = agreeCount;
    }

    public Integer getDisagreeCount() {
        return disagreeCount;
    }

    public void setDisagreeCount(Integer disagreeCount) {
        this.disagreeCount = disagreeCount;
    }

    public Todo getTodo() {
        return todo;
    }

    public void setTodo(Todo todo) {
        this.todo = todo;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getLastVoteText() {
        return lastVoteText;
    }

    public void setLastVoteText(String lastVoteText) {
        this.lastVoteText = lastVoteText;
    }

    public Integer getLastUpvoteTimestamp() {
        return lastUpvoteTimestamp;
    }

    public void setLastUpvoteTimestamp(Integer lastUpvoteTimestamp) {
        this.lastUpvoteTimestamp = lastUpvoteTimestamp;
    }

}
