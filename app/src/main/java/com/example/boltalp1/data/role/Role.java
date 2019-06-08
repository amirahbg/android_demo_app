package com.example.boltalp1.data.role;

import java.util.Date;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = {@Index(value = {"name"}, unique = true)})
public class Role {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long mId;

    @NonNull
    @ColumnInfo(name = "name")
    private String mName;

    @ColumnInfo(name = "updateDate")
    private Date mUpdatedDate;

    public Role(@NonNull String name, Date updatedDate) {
        mName = name;
        mUpdatedDate = updatedDate;
    }

    public void setName(@NonNull String name) {
        mName = name;
    }

    public void setUpdatedDate(Date updatedDate) {
        mUpdatedDate = updatedDate;
    }

    public Date getUpdatedDate() {
        return mUpdatedDate;
    }

    public long getId() {
        return mId;
    }

    @NonNull
    public String getName() {
        return mName;
    }

    public void setId(long id) {
        mId = id;
    }
}
