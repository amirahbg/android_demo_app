package com.example.boltalp1.data.advertisement;

import com.example.boltalp1.data.user.User;

import java.util.Date;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(foreignKeys = @ForeignKey(entity = User.class,
        parentColumns = "id",
        childColumns = "userId",
        onDelete = CASCADE,
        onUpdate = CASCADE))
public class Advertisement {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long mId;

    @ColumnInfo(name = "userId")
    private long mUserId;

    @ColumnInfo(name = "title")
    private String mTitle;

    @ColumnInfo(name = "video")
    private String mVideoUrl;

    @ColumnInfo(name = "lat")
    private double mLat;

    @ColumnInfo(name = "lon")
    private double mLon;

    @ColumnInfo(name = "body")
    private String mBody;

    @ColumnInfo(name = "confDate")
    private Date mConfDate;

    @ColumnInfo(name = "price")
    private int mPrice;

    public Advertisement() {

    }

    public Advertisement(long userId, String title,
                         String video, double lat, double lon,
                         String body, Date confDate, int price) {
        mUserId = userId;
        mTitle = title;
        mVideoUrl = video;
        mLat = lat;
        mLon = lon;
        mBody = body;
        mConfDate = confDate;
        mPrice = price;
    }


    public void setUserId(long userId) {
        mUserId = userId;
    }

    public void setBody(String body) {
        mBody = body;
    }

    public void setConfDate(Date confDate) {
        mConfDate = confDate;

    }

    public void setId(long id) {
        mId = id;
    }

    public void setLat(double lat) {
        mLat = lat;
    }

    public void setLon(double lon) {
        mLon = lon;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public void setVideoUrl(String videoUrl) {
        mVideoUrl = videoUrl;
    }

    public void setPrice(int price) {
        mPrice = price;
    }

    public long getUserId() {
        return mUserId;
    }

    public Date getConfDate() {
        return mConfDate;
    }

    public double getLat() {
        return mLat;
    }

    public double getLon() {
        return mLon;
    }

    public long getId() {
        return mId;
    }

    public String getBody() {
        return mBody;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getVideoUrl() {
        return mVideoUrl;
    }

    public int getPrice() {
        return mPrice;
    }
}
