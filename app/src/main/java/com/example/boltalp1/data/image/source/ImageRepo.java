package com.example.boltalp1.data.image.source;

import com.example.boltalp1.data.image.Image;
import com.example.boltalp1.data.image.ImageDao;

import java.util.List;

import io.reactivex.Flowable;

public class ImageRepo implements ImageDS {

    private static ImageRepo sInstance;
    private final ImageDao mImageDao;

    private ImageRepo(ImageDao imageDao) {
        mImageDao = imageDao;
    }

    public static ImageRepo getInstance(ImageDao imageDao) {
        if (sInstance == null) {
            sInstance = new ImageRepo(imageDao);
        }
        return sInstance;
    }

    @Override
    public Flowable<Long> insertImage(Image image) {
        return mImageDao.insertImage(image)
                .toFlowable();
    }

    @Override
    public Flowable<List<Image>> getAdvImages(Long advId) {
        return mImageDao.getAdvImages(advId)
                .toFlowable();
    }
}
