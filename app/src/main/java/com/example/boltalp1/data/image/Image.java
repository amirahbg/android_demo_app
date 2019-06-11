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

    @ColumnInfo(name = "imagePath")
    private String mImagePath;

    @ColumnInfo(name = "updateDate")
    private Date mUpdateDate;


    public Image(long advertisementId, String imagePath, Date updateDate) {
        mAdvertisementId = advertisementId;
        mImagePath = imagePath;
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

    public String getImagePath() {
        return mImagePath;
    }

    public void setImagePath(String imagePath) {
        mImagePath = imagePath;
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }
}
