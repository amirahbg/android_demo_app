package com.example.boltalp1.data.advertisement.source;

import com.example.boltalp1.data.advertisement.AdvertiseWithImagesDao;
import com.example.boltalp1.data.advertisement.Advertisement;
import com.example.boltalp1.data.advertisement.AdvertisementDao;
import com.example.boltalp1.data.advertisement.AdvertisementWithImage;

import java.util.List;

import androidx.annotation.NonNull;
import io.reactivex.Flowable;

public class AdvertisementRepo implements AdvertisementDS {
    private static AdvertisementRepo sInstance;
    private AdvertisementDao mAdvertisementDao;
    private AdvertiseWithImagesDao mAdvertiseWithImagesDao;

    private AdvertisementRepo(AdvertisementDao advertisementDao,
                              AdvertiseWithImagesDao advertiseWithImagesDao) {
        mAdvertisementDao = advertisementDao;
        mAdvertiseWithImagesDao = advertiseWithImagesDao;
    }

    public static AdvertisementRepo getInstance(AdvertisementDao advertisementDao,
                                                AdvertiseWithImagesDao advertiseWithImagesDao) {
        if (sInstance == null) {
            sInstance = new AdvertisementRepo(advertisementDao, advertiseWithImagesDao);
        }
        return sInstance;
    }

    @Override
    public Flowable<Long> insertAdvertisement(@NonNull Advertisement advertisement) {
        return mAdvertisementDao.insertAdv(advertisement)
                .toFlowable();
    }

    @Override
    public Flowable<List<Advertisement>> getUsersAdvertisement(long userId) {
        return mAdvertisementDao.getUsersAdvertisement(userId)
                .toFlowable();
    }

    @Override
    public Flowable<List<Advertisement>> getAllAdvertisements() {
        return mAdvertisementDao.getAllAdvertisements()
                .toFlowable();
    }

    @Override
    public Flowable<List<AdvertisementWithImage>> getAddAdvertisementWithImages() {
        return mAdvertiseWithImagesDao.getAdvWithImages()
                .toFlowable();
    }
}
