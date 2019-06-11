package com.example.boltalp1.viewmodel;

import android.app.Application;

import com.example.boltalp1.R;
import com.example.boltalp1.data.advertisement.AdvertisementWithImage;
import com.example.boltalp1.data.advertisement.source.AdvertisementRepo;
import com.example.boltalp1.data.user.source.UserRepo;
import com.example.boltalp1.util.Injection;
import com.example.boltalp1.util.ObservableViewModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.Bindable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.EmptyResultSetException;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class AdvertisementsViewModel extends ObservableViewModel {
    private AdvertisementRepo mAdvertisementRepo;
    private UserRepo mUserRepo;
    private MutableLiveData<List<AdvertisementWithImage>> mAdvertisementsLive;
    private MutableLiveData<String> mMessageLive;

    public AdvertisementsViewModel(@NonNull Application application) {
        super(application);

        mAdvertisementRepo = Injection.provideAdvertisementRepo(application);
        mAdvertisementsLive = new MutableLiveData<>();
        mUserRepo = Injection.provideUserRepo(getApplication());
        mMessageLive = new MutableLiveData<>();
    }

    public void requestAdvertisements() {
        getCompositeDisposable().add(mAdvertisementRepo.getAddAdvertisementWithImages()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(advertisements -> {
                            if (advertisements.size() == 0) {
                                setMessage(getApplication().getString(R.string.err_no_adv_found));
                                return;
                            }
                            if (mUserRepo.getCurrentRole().equals("Admin")) {
                                mAdvertisementsLive.setValue(advertisements);
                            } else {
                                mAdvertisementsLive.setValue(extractConfAdv(advertisements));
                            }
                        },
                        e -> {
                            if (e instanceof EmptyResultSetException) {
                                setMessage(getApplication().getString(R.string.err_no_adv_found));
                            }
                        }));
    }

    private List<AdvertisementWithImage> extractConfAdv(List<AdvertisementWithImage> advertisements) {
        List<AdvertisementWithImage> res = new ArrayList<>();
        for (int i = 0; i < advertisements.size(); i++) {
            Date date = advertisements.get(i).mAdvertisement.getConfDate();
            if (date == null || date.equals(new Date(0)))
                continue;
            res.add(advertisements.get(i));
        }
        return res;
    }

    public boolean isAdmin() {
        return mUserRepo.getCurrentRole().equals("Admin");
    }

    public void setMessage(String s) {
        mMessageLive.setValue(s);
    }

    @Bindable
    public LiveData<List<AdvertisementWithImage>> getAdvertisementsLive() {
        return mAdvertisementsLive;
    }

    @Bindable
    public MutableLiveData<String> getMessageLive() {
        return mMessageLive;
    }
}
