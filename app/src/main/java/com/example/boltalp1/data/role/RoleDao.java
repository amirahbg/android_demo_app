package com.example.boltalp1.data.role;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import io.reactivex.Flowable;
import io.reactivex.Single;

@Dao
public interface RoleDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Single<Long> insert(Role role);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Single<List<Long>> bulkInsert(List<Role> roles);

    @Update
    Single<Integer> update(Role role);

    @Query("SELECT * FROM role")
    Single<List<Role>> getRoles();

    @Query("SELECT * FROM role WHERE id = :roleId")
    Single<Role> getRoleById(int roleId);

    @Query("SELECT * FROM role WHERE name = :roleName")
    Single<Role> getRoleByName(String roleName);
}
