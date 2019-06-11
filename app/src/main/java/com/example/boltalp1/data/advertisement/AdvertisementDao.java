package com.example.boltalp1.data.advertisement;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import io.reactivex.Single;

@Dao
public interface AdvertisementDao {
    @Insert
    Single<Long> insertAdv(Advertisement advertisement);

    @Query("SELECT * FROM advertisement WHERE userId = :userId")
    Single<List<Advertisement>> getUsersAdvertisement(long userId);

    @Query("SELECT * FROM advertisement")
    Single<List<Advertisement>> getAllAdvertisements();

    @Update
    Single<Integer> updateAdvertisement(Advertisement advertisement);
}
