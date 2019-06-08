package com.example.boltalp1.data.user.source;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.boltalp1.data.role.RoleDao;
import com.example.boltalp1.data.user.User;
import com.example.boltalp1.data.user.UserDao;
import com.example.boltalp1.data.user_role.UserRoleJoin;
import com.example.boltalp1.data.user_role.UserRoleJoinDao;
import com.example.boltalp1.util.Consts;

import java.util.Calendar;
import java.util.concurrent.atomic.AtomicLong;

import androidx.annotation.NonNull;
import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;

import static android.content.ContentValues.TAG;

public class UserRepo implements UserDS {

    private static UserRepo sInstance;
    private final UserDao mUserDao;
    private final RoleDao mRoleDao;
    private final UserRoleJoinDao mUserRoleJoinDao;
    private final SharedPreferences mSharedPref;

    private UserRepo(Context context, UserDao userDao,
                     RoleDao roleDao,
                     UserRoleJoinDao userRoleJoinDao) {
        mSharedPref = context.getSharedPreferences(context.getPackageName() + Consts.USER,
                Context.MODE_PRIVATE);
        mUserDao = userDao;
        mRoleDao = roleDao;
        mUserRoleJoinDao = userRoleJoinDao;
    }

    public static UserRepo getInstance(Context context,
                                       UserDao userDao,
                                       RoleDao roleDao,
                                       UserRoleJoinDao userRoleJoinDao) {
        if (sInstance == null) {
            sInstance = new UserRepo(context, userDao, roleDao, userRoleJoinDao);
        }
        return sInstance;
    }

    @SuppressLint("CheckResult")
    @Override
    public Flowable<Long> registerUser(@NonNull String username,
                                       @NonNull String password,
                                       String email,
                                       String phoneNumber,
                                       @NonNull String role) {
        // TODO: 6/6/19 password should be encrypted but for the sake of simplicity we ignored it for now
        User user = new User(username, password,
                phoneNumber, email,
                Calendar.getInstance().getTime(),
                Calendar.getInstance().getTime());

        AtomicLong userId = new AtomicLong();

        mUserDao.insertUser(user)
                .subscribeOn(Schedulers.single())
                .observeOn(Schedulers.single())
                .subscribe(userId::set, e -> Log.i(TAG, "registerUser: " + e.getMessage()));

        return mRoleDao.getRoleByName(role)
                .subscribeOn(Schedulers.single())
                .observeOn(Schedulers.single())
                .flatMap(r -> mUserRoleJoinDao.insertUserRole(new UserRoleJoin(userId.get(), r.getId())))
                .toFlowable();
    }

    @Override
    public void saveCurrentUser(long userId,
                                @NonNull String role) {
        SharedPreferences.Editor editor = mSharedPref.edit();
        editor.putLong(Consts.USER_ID, userId);
        editor.putString(Consts.ROLE, role);
        editor.apply();
    }

    @Override
    public Flowable<User> login(@NonNull String username,
                                @NonNull String password,
                                @NonNull String role) {
        // TODO: 6/6/19 password should be encrypted but for the sake of simplicity we ignored it for now

        return mUserDao.getUserByUsernameAndPassword(username, password, role)
                .toFlowable();

    }

    @Override
    public boolean logout() {
        try {
            SharedPreferences.Editor editor = mSharedPref.edit();
            editor.putLong(Consts.USER_ID, -1);
            editor.putString(Consts.ROLE, null);
            editor.apply();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean isLoggedIn() {
        return mSharedPref.getLong(Consts.USER_ID, -1) != -1;
    }

    public String getCurrentRole() {
        return mSharedPref.getString(Consts.ROLE, null);
    }

    public long getUserId() {
        return mSharedPref.getLong(Consts.USER_ID, -1);
    }
}
