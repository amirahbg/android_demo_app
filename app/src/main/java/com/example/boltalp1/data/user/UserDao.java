package com.example.boltalp1.data.user;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import io.reactivex.Single;

@Dao
public interface UserDao {
    @Query("SELECT * FROM User WHERE Id = :userId")
    Single<User> getUser(int userId);

    @Query("SELECT * FROM user")
    Single<List<User>> getUsers();

    @Insert
    Single<Long> insertUser(User user);

    @Update
    Single<Integer> updateUser(User user);

    @Query("SELECT * FROM user " +
            "INNER JOIN UserRoleJoin ON user.id = UserRoleJoin.userId " +
            "INNER JOIN role ON UserRoleJoin.roleId = role.id " +
            "WHERE  role.name = :role " +
            "AND username = :username AND password = :password")
    Single<User> getUserByUsernameAndPassword(String username,
                                              String password,
                                              String role);
}
