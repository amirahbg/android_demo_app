package com.example.boltalp1.data.permission;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Permission {
    @PrimaryKey
    public int mPermissionId;

    @ColumnInfo(name = "mId")
    public int mRoleId;

    @ColumnInfo(name = "name")
    public String mName;

    @ColumnInfo(name = "updateDate")
    public String mUpdateDate;
}
