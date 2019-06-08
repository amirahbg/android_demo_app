package com.example.boltalp1.util;

import android.app.Application;

import com.example.boltalp1.data.AppDatabase;
import com.example.boltalp1.data.advertisement.AdvertiseWithImagesDao;
import com.example.boltalp1.data.advertisement.AdvertisementDao;
import com.example.boltalp1.data.advertisement.source.AdvertisementRepo;
import com.example.boltalp1.data.image.ImageDao;
import com.example.boltalp1.data.image.source.ImageRepo;
import com.example.boltalp1.data.role.RoleDao;
import com.example.boltalp1.data.role.source.RoleRepo;
import com.example.boltalp1.data.user.UserDao;
import com.example.boltalp1.data.user.source.UserRepo;
import com.example.boltalp1.data.user_role.UserRoleJoinDao;
import com.example.boltalp1.data.user_role.source.UserRoleRepo;

import androidx.annotation.NonNull;
import androidx.room.Room;

public class Injection {
    private Injection() {
    }

    @NonNull
    private static AppDatabase provideAppDatabase(Application application) {
        return Room.databaseBuilder(application,
                AppDatabase.class, Consts.DATABASE_NAME).build();
    }

    private static RoleDao provideRoleDao(Application application) {
        return provideAppDatabase(application).getRoleDao();
    }


    private static UserRoleJoinDao provideUserRoleDao(Application application) {
        return provideAppDatabase(application).getUserRoleDao();
    }

    private static UserDao provideUserDao(Application application) {
        return provideAppDatabase(application).getUserDao();
    }

    private static AdvertisementDao provideAdvertisementDao(Application application) {
        return provideAppDatabase(application).getAdvertisementDao();
    }

    public static UserRepo provideUserRepo(Application application) {
        return UserRepo.getInstance(application,
                provideUserDao(application),
                provideRoleDao(application),
                provideUserRoleDao(application));
    }

    public static RoleRepo provideRoleRepo(Application application) {
        return RoleRepo.getInstance(provideRoleDao(application));
    }

    public static UserRoleRepo provideUserRoleRepo(Application application) {
        return UserRoleRepo.getInstance(provideUserRoleDao(application));
    }

    public static AdvertisementRepo provideAdvertisementRepo(Application application) {
        return AdvertisementRepo.getInstance(provideAdvertisementDao(application),
                provideAdvertiseWithImagesDao(application));
    }

    private static AdvertiseWithImagesDao provideAdvertiseWithImagesDao(Application application) {
        return provideAppDatabase(application).getAdvertisementWithImagesDao();
    }

    public static ImageRepo provideImageRepo(Application application) {
        return ImageRepo.getInstance(provideImageDao(application));
    }

    private static ImageDao provideImageDao(Application application) {
        return provideAppDatabase(application).getImageDao();
    }
}
