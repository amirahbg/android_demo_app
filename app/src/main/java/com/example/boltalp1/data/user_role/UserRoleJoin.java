package com.example.boltalp1.data.user_role;

import com.example.boltalp1.data.role.Role;
import com.example.boltalp1.data.user.User;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(primaryKeys = {"userId", "roleId"},
        foreignKeys = {
                @ForeignKey(entity = User.class,
                        parentColumns = "id",
                        childColumns = "userId"),
                @ForeignKey(entity = Role.class,
                        parentColumns = "id",
                        childColumns = "roleId")
        })
public class UserRoleJoin {

    @ColumnInfo(name = "userId")
    private long mUserId;

    @ColumnInfo(name = "roleId")
    private long mRoleId;

    public UserRoleJoin(long userId, long roleId) {
        mUserId = userId;
        mRoleId = roleId;
    }

    public void setRoleId(int roleId) {
        mRoleId = roleId;
    }

    public void setUserId(int userId) {
        mUserId = userId;
    }

    public long getUserId() {
        return mUserId;
    }

    public long getRoleId() {
        return mRoleId;
    }
}
