package com.example.boltalp1.data.image;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import io.reactivex.Single;

@Dao
public interface ImageDao {

    @Insert
    Single<Long> insertImage(Image image);

    @Query("SELECT * FROM image WHERE advertisementId = :advId")
    Single<List<Image>> getAdvImages(long advId);
}
