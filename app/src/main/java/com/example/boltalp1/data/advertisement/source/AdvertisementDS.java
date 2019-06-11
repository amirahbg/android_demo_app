package com.example.boltalp1.data.advertisement.source;

import com.example.boltalp1.data.advertisement.Advertisement;
import com.example.boltalp1.data.advertisement.AdvertisementWithImage;

import java.util.List;

import androidx.annotation.NonNull;
import io.reactivex.Flowable;

public interface AdvertisementDS {
    Flowable<Long> insertAdvertisement(@NonNull Advertisement advertisement);

    Flowable<List<Advertisement>> getUsersAdvertisement(long userId);

    Flowable<List<Advertisement>> getAllAdvertisements();

    Flowable<List<AdvertisementWithImage>> getAddAdvertisementWithImages();

    Flowable<Integer> updateAdvertisement(Advertisement advertisement);
}
