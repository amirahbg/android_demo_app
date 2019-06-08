package com.example.boltalp1.viewmodel;

import android.app.Application;

import com.example.boltalp1.R;
import com.example.boltalp1.data.advertisement.AdvertisementWithImage;
import com.example.boltalp1.data.advertisement.source.AdvertisementRepo;
import com.example.boltalp1.util.Injection;
import com.example.boltalp1.util.ObservableViewModel;

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
    private MutableLiveData<List<AdvertisementWithImage>> mAdvertisementsLive;
    private MutableLiveData<String> mMessageLive;

    public AdvertisementsViewModel(@NonNull Application application) {
        super(application);

        mAdvertisementRepo = Injection.provideAdvertisementRepo(application);
        mAdvertisementsLive = new MutableLiveData<>();
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
                            mAdvertisementsLive.setValue(advertisements);
                        },
                        e -> {
                            if (e instanceof EmptyResultSetException) {
                                setMessage(getApplication().getString(R.string.err_no_adv_found));
                            }
                        }));
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
