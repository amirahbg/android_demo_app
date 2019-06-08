package com.example.boltalp1.viewmodel;

import com.example.boltalp1.BR;

import java.util.Date;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

public class AdvertisementItemViewModel extends BaseObservable {
    private String mTitle;
    private String mVideoUrl;
    private String mBody;
    private int mPrice;
    private double mLat;
    private double mLon;
    private Date mConfDate;

    @Bindable
    public String getTitle() {
        return mTitle;
    }

    @Bindable
    public String getBody() {
        return mBody;
    }

    @Bindable
    public String getVideoUrl() {
        return mVideoUrl;
    }

    @Bindable
    public Date getConfDate() {
        return mConfDate;
    }

    @Bindable
    public int getPrice() {
        return mPrice;
    }


    public void setTitle(String title) {
        mTitle = title;
        notifyPropertyChanged(BR.title);
    }

    public void setPrice(int price) {
        mPrice = price;
        notifyPropertyChanged(BR.price);
    }

    public void setConfDate(Date confDate) {
        mConfDate = confDate;
        notifyPropertyChanged(BR.confDate);
    }

    public void setBody(String body) {
        mBody = body;
        notifyPropertyChanged(BR.body);
    }

    public void setVideoUrl(String videoUrl) {
        mVideoUrl = videoUrl;
        notifyPropertyChanged(BR.videoUrl);
    }
}
