package com.example.boltalp1.data.user_role;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import io.reactivex.Single;

@Dao
public interface UserRoleJoinDao {
    @Insert
    Single<Long> insertUserRole(UserRoleJoin userRoleJoin);

    @Query("SELECT * FROM userrolejoin")
    Single<List<UserRoleJoin>> getUserRoles();
}
