package com.example.boltalp1.data.user;

import java.util.Date;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = {@Index(value = {"username"}, unique = true)})
public class User {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long mId;

    @NonNull
    @ColumnInfo(name = "username")
    private String mUsername;

    @NonNull
    @ColumnInfo(name = "password")
    private String mPassword;

    @ColumnInfo(name = "mobile")
    private String mMobile;

    @ColumnInfo(name = "email")
    private String mEmail;

    @ColumnInfo(name = "updateDate")
    private Date mUpdateDate;

    @ColumnInfo(name = "deletedDate")
    private Date mDeletedDate;

    public User(@NonNull String username,
                @NonNull String password,
                String mobile,
                String email,
                Date updateDate,
                Date deletedDate) {
        mUsername = username;
        mPassword = password;
        mMobile = mobile;
        mEmail = email;
        mUpdateDate = updateDate;
        mDeletedDate = deletedDate;
    }

    public void setId(long id) {
        mId = id;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public void setDeletedDate(Date deletedDate) {
        mDeletedDate = deletedDate;
    }

    public void setMobile(String mobile) {
        mMobile = mobile;
    }

    public void setUpdateDate(Date updateDate) {
        mUpdateDate = updateDate;
    }

    public void setUsername(@NonNull String username) {
        mUsername = username;
    }

    @NonNull
    public String getPassword() {
        return mPassword;
    }

    public void setPassword(@NonNull String password) {
        mPassword = password;
    }

    public Date getDeletedDate() {
        return mDeletedDate;
    }

    public Date getUpdateDate() {
        return mUpdateDate;
    }

    public long getId() {
        return mId;
    }

    public String getEmail() {
        return mEmail;
    }

    public String getMobile() {
        return mMobile;
    }

    @NonNull
    public String getUsername() {
        return mUsername;
    }
}
