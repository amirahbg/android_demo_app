package com.example.boltalp1.data;

import com.example.boltalp1.data.advertisement.AdvertiseWithImagesDao;
import com.example.boltalp1.data.advertisement.Advertisement;
import com.example.boltalp1.data.advertisement.AdvertisementDao;
import com.example.boltalp1.data.image.Image;
import com.example.boltalp1.data.image.ImageDao;
import com.example.boltalp1.data.permission.Permission;
import com.example.boltalp1.data.role.Role;
import com.example.boltalp1.data.role.RoleDao;
import com.example.boltalp1.data.user.User;
import com.example.boltalp1.data.user.UserDao;
import com.example.boltalp1.data.user_role.UserRoleJoin;
import com.example.boltalp1.data.user_role.UserRoleJoinDao;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {User.class, Image.class, Permission.class, Role.class,
        Advertisement.class, UserRoleJoin.class}, version = 1)
@TypeConverters(Converters.class)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao getUserDao();

    public abstract RoleDao getRoleDao();

    public abstract UserRoleJoinDao getUserRoleDao();

    public abstract AdvertisementDao getAdvertisementDao();

    public abstract AdvertiseWithImagesDao getAdvertisementWithImagesDao();

    public abstract ImageDao getImageDao();
}
