package com.example.boltalp1.data.user_role.source;

import com.example.boltalp1.data.role.Role;
import com.example.boltalp1.data.user_role.UserRoleJoin;
import com.example.boltalp1.data.user_role.UserRoleJoinDao;

import java.util.List;

import androidx.annotation.NonNull;
import io.reactivex.Flowable;

public class UserRoleRepo implements UserRoleDS {
    private static UserRoleRepo sInstance;
    private final UserRoleJoinDao mUserRoleJoinDao;

    public UserRoleRepo(UserRoleJoinDao userRoleJoinDao) {
        mUserRoleJoinDao = userRoleJoinDao;
    }

    public static UserRoleRepo getInstance(UserRoleJoinDao userRoleJoinDao) {
        if (sInstance == null) {
            sInstance = new UserRoleRepo(userRoleJoinDao);
        }
        return sInstance;
    }

    @Override
    public Flowable<Long> insertUserRole(@NonNull UserRoleJoin userRoleJoin) {
        return mUserRoleJoinDao.insertUserRole(userRoleJoin)
                .toFlowable();
    }

    @Override
    public Flowable<List<UserRoleJoin>> getUserRoles() {
        return mUserRoleJoinDao.getUserRoles()
                .toFlowable();
    }
}
