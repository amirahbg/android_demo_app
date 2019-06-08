package com.example.boltalp1.viewmodel;

import android.app.Application;

import com.example.boltalp1.R;
import com.example.boltalp1.data.user.source.UserRepo;
import com.example.boltalp1.util.Injection;
import com.example.boltalp1.util.ObservableViewModel;

import androidx.annotation.NonNull;
import androidx.databinding.Bindable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.EmptyResultSetException;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class LoginViewModel extends ObservableViewModel {

    private UserRepo mUserRepo;
    private MutableLiveData<Boolean> mWaitingProgressLive;
    private MutableLiveData<Boolean> mIsLoggedIn;
    private MutableLiveData<Boolean> mIsSuccessfulLogin;
    private MutableLiveData<String> mMessageLive;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        mUserRepo = Injection.provideUserRepo(getApplication());
        mWaitingProgressLive = new MutableLiveData<>();
        mIsLoggedIn = new MutableLiveData<>();
        mIsSuccessfulLogin = new MutableLiveData<>();
        mMessageLive = new MutableLiveData<>();
    }

    public void requestIsLoggedIn() {
        mIsLoggedIn.setValue(mUserRepo.isLoggedIn());
    }

    public void requestLogin(@NonNull String username,
                             @NonNull String password,
                             @NonNull String role) {
        getCompositeDisposable().add(mUserRepo.login(username, password, role)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(u -> {
                    mUserRepo.saveCurrentUser(u.getId(), role);
                    mIsSuccessfulLogin.setValue(true);
                }, e -> {
                    mIsSuccessfulLogin.setValue(false);
                    if (e instanceof EmptyResultSetException) {
                        setMessage(getApplication().getString(R.string.err_wrong_username_or_password));
                    }
                }));
    }

    public void setMessage(String message) {
        mMessageLive.setValue(message);
    }

    public void setWaitingProgress(Boolean b) {
        mWaitingProgressLive.setValue(b);
    }

    @Bindable
    public LiveData<Boolean> getWaitingProgressLive() {
        return mWaitingProgressLive;
    }

    public LiveData<Boolean> getIsSuccessfulLogin() {
        return mIsSuccessfulLogin;
    }

    public LiveData<Boolean> getIsLoggedIn() {
        return mIsLoggedIn;
    }

    @Bindable
    public LiveData<String> getMessageLive() {
        return mMessageLive;
    }
}
