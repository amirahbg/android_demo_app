package com.example.boltalp1.viewmodel;

import android.app.Application;
import android.database.sqlite.SQLiteConstraintException;
import android.util.Log;

import com.example.boltalp1.R;
import com.example.boltalp1.data.role.Role;
import com.example.boltalp1.data.role.source.RoleRepo;
import com.example.boltalp1.data.user.source.UserRepo;
import com.example.boltalp1.util.Injection;
import com.example.boltalp1.util.ObservableViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.Bindable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static android.content.ContentValues.TAG;

public class RegisterViewModel extends ObservableViewModel {

    private RoleRepo mRoleRepo;
    private UserRepo mUserRepo;

    private MutableLiveData<Boolean> mWaitingProgressLive;
    private MutableLiveData<String> mMessageLive;
    private MutableLiveData<Boolean> mIsSuccessfulRegister;


    public RegisterViewModel(@NonNull Application application) {
        super(application);

        mRoleRepo = Injection.provideRoleRepo(application);
        mUserRepo = Injection.provideUserRepo(application);
        mWaitingProgressLive = new MutableLiveData<>();
        mMessageLive = new MutableLiveData<>();
        mIsSuccessfulRegister = new MutableLiveData<>();

        getCompositeDisposable().add(mRoleRepo.initRoleIfEmpty(getRoles(application))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(r -> {
                    Log.i(TAG, "RegisterViewModel: " + r.size());
                }, e -> {
                    Log.i(TAG, "RegisterViewModel: " + e.getMessage());
                }));
    }

    public void requestRegisterUser(@NonNull String username,
                                    @NonNull String password,
                                    String email,
                                    String phoneNumber,
                                    @NonNull String role) {
        getCompositeDisposable().add(mUserRepo.registerUser(username,
                password,
                email,
                phoneNumber,
                role)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(l -> {
                    setMessage("Successfully Registered");
                    saveCurrentUser(l, role);
                    setWaitingProgress(false);
                    mIsSuccessfulRegister.setValue(true);
                }, e -> {
                    Log.i(TAG, "requestRegisterUser: " + e.getMessage());
                    setWaitingProgress(false);
                    if (e instanceof SQLiteConstraintException) {
                        setMessage(getApplication().getString(R.string.err_repeated_username));
                    }
                }));
    }

    private void saveCurrentUser(long userId, @NonNull String role) {
        // save registered user in sharedPref for auto requestLogin
        mUserRepo.saveCurrentUser(userId, role);
    }

    private List<Role> getRoles(@NonNull Application application) {
        String[] rolesString =
                application.getResources().getStringArray(R.array.roles);
        List<Role> roles = new ArrayList<>();
        for (String role : rolesString) {
            roles.add(new Role(role, Calendar.getInstance().getTime()));
        }
        return roles;
    }

    public void setWaitingProgress(boolean b) {
        mWaitingProgressLive.setValue(b);
    }

    public void setMessage(String error) {
        mMessageLive.setValue(error);
    }

    @Bindable
    public LiveData<Boolean> getWaitingProgressLive() {
        return mWaitingProgressLive;
    }

    @Bindable
    public LiveData<String> getMessageLive() {
        return mMessageLive;
    }

    public LiveData<Boolean> getIsSuccessfulRegister() {
        return mIsSuccessfulRegister;
    }
}
