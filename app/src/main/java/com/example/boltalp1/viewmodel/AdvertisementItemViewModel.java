package com.example.boltalp1.viewmodel;

import android.app.Application;
import android.content.Context;

import com.example.boltalp1.BR;
import com.example.boltalp1.data.advertisement.Advertisement;
import com.example.boltalp1.data.advertisement.source.AdvertisementRepo;
import com.example.boltalp1.util.Injection;

import java.util.Calendar;
import java.util.Date;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class AdvertisementItemViewModel extends BaseObservable {
    //    private String mTitle;
//    private String mVideoUrl;
//    private String mBody;
//    private String mPrice;
//    private String mPhoneNumber;
//    private double mLat;
//    private double mLon;
//    private Date mConfDate;
    private Advertisement mAdvertisement;
    private AdvertisementRepo mAdvertisementRepo;
    private boolean mIsAdmin;
    private boolean mIsChecked;
    private String mPrice;
    private String mPhoneNumber;

    public AdvertisementItemViewModel(Application application) {
        mAdvertisementRepo = Injection.provideAdvertisementRepo(application);
    }

    public void setAdvertisement(Advertisement advertisement) {
        mAdvertisement = advertisement;
        if (mAdvertisement.getConfDate() != null &&
                !mAdvertisement.getConfDate().equals(new Date(0))) {
            setChecked(true);
        } else {
            setChecked(false);
        }
    }

    public void setAdmin(boolean admin) {
        mIsAdmin = admin;
        notifyPropertyChanged(BR.admin);
    }

    @Bindable
    public boolean isAdmin() {
        return mIsAdmin;
    }

    @Bindable
    public String getTitle() {
        return mAdvertisement.getTitle();
    }

    @Bindable
    public String getBody() {
        return mAdvertisement.getBody();
    }

    @Bindable
    public String getVideoUrl() {
        return mAdvertisement.getVideoUrl();
    }

    @Bindable
    public Date getConfDate() {
        return mAdvertisement.getConfDate();
    }

    @Bindable
    public String getPrice() {
        return mPrice;
    }

    @Bindable
    public String getPhoneNumber() {
        return "PhoneNumber: " + mPhoneNumber;
    }

    public void setTitle(String title) {
        mAdvertisement.setTitle(title);
        notifyPropertyChanged(BR.title);
    }

    public void setPrice(int price) {
        mPrice = String.format("%d toman", price);
        mAdvertisement.setPrice(price);
        notifyPropertyChanged(BR.price);
    }

    public void setConfDate(Date confDate) {
        mAdvertisement.setConfDate(confDate);
        notifyPropertyChanged(BR.confDate);
    }

    public void setBody(String body) {
        mAdvertisement.setBody(body);
        notifyPropertyChanged(BR.body);
    }

    public void setVideoUrl(String videoUrl) {
        mAdvertisement.setVideoUrl(videoUrl);
        notifyPropertyChanged(BR.videoUrl);
    }

    public void setPhoneNumber(String phoneNumber) {
        mPhoneNumber = phoneNumber;
        notifyPropertyChanged(BR.phoneNumber);
    }

    @Bindable
    public boolean isChecked() {
        return mIsChecked;
    }

    public void setChecked(boolean checked) {
        mIsChecked = checked;
        notifyPropertyChanged(BR.checked);

        if (mIsChecked) {
            setConfDate(Calendar.getInstance().getTime());
            mAdvertisementRepo.updateAdvertisement(mAdvertisement)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe();
        } else {
            setConfDate(new Date(0));
            mAdvertisementRepo.updateAdvertisement(mAdvertisement)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe();
        }
    }


}
