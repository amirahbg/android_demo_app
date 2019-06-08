package com.example.boltalp1.data.advertisement;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Query;
import io.reactivex.Single;

@Dao
public interface AdvertiseWithImagesDao {

    @Query("SELECT * FROM advertisement")
    Single<List<AdvertisementWithImage>> getAdvWithImages();
}
