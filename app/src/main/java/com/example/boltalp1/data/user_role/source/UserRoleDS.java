package com.example.boltalp1.data.user_role.source;

import com.example.boltalp1.data.user_role.UserRoleJoin;

import java.util.List;

import androidx.annotation.NonNull;
import io.reactivex.Flowable;

public interface UserRoleDS {
    Flowable<Long> insertUserRole(@NonNull UserRoleJoin userRoleJoin);

    Flowable<List<UserRoleJoin>> getUserRoles();
}
