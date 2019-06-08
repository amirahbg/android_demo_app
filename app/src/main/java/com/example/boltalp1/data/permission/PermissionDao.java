package com.example.boltalp1.data.permission;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface PermissionDao {
    @Insert
    long insertPermission(Permission permission);

    @Query("SELECT * FROM permission WHERE mPermissionId = :permId")
    Permission getPermissionById(int permId);

    @Query("SELECT * FROM permission WHERE name = :name")
    Permission getPermissionByName(String name);
}
