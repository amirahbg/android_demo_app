package com.example.boltalp1.viewmodel;

import android.app.Application;

import com.example.boltalp1.data.user.source.UserRepo;
import com.example.boltalp1.util.Injection;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class MainViewModel extends AndroidViewModel {
    private MutableLiveData<Boolean> mIsLogoutSuccessful;
    private UserRepo mUserRepo;

    public MainViewModel(@NonNull Application application) {
        super(application);
        mIsLogoutSuccessful = new MutableLiveData<>();
        mUserRepo = Injection.provideUserRepo(getApplication());
    }

    public void requestLogout() {
        mIsLogoutSuccessful.setValue(mUserRepo.logout());
    }

    public LiveData<Boolean> getIsLogoutSuccessful() {
        return mIsLogoutSuccessful;
    }
}
