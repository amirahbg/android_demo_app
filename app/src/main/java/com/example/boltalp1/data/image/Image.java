package com.example.boltalp1.data.image;

import com.example.boltalp1.data.advertisement.Advertisement;

import java.util.Date;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity
        (foreignKeys = @ForeignKey(entity = Advertisement.class,
                parentColumns = "id",
                childColumns = "advertisementId",
                onDelete = CASCADE,
                onUpdate = CASCADE))
public class Image {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long mId;

    @ColumnInfo(name = "advertisementId")
    private long mAdvertisementId;

    @ColumnInfo(name = "ssp")
    private String mSsp;

    @ColumnInfo(name = "fragment")
    private String mFragment;

    @ColumnInfo(name = "scheme")
    private String mScheme;

    @ColumnInfo(name = "updateDate")
    private Date mUpdateDate;


    public Image(long advertisementId, String ssp, String fragment, String scheme, Date updateDate) {
        mAdvertisementId = advertisementId;
        mSsp = ssp;
        mFragment = fragment;
        mScheme = scheme;
        mUpdateDate = updateDate;
    }


    public Date getUpdateDate() {
        return mUpdateDate;
    }

    public void setUpdateDate(Date updateDate) {
        mUpdateDate = updateDate;
    }

    public long getAdvertisementId() {
        return mAdvertisementId;
    }

    public void setAdvertisementId(long advertisementId) {
        mAdvertisementId = advertisementId;
    }

    public String getSsp() {
        return mSsp;
    }

    public void setSsp(String ssp) {
        mSsp = ssp;
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public String getScheme() {
        return mScheme;
    }

    public void setScheme(String scheme) {
        mScheme = scheme;
    }

    public String getFragment() {
        return mFragment;
    }

    public void setFragment(String fragment) {
        mFragment = fragment;
    }
}
