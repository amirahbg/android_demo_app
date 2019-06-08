package com.example.boltalp1.data.user.source;

import com.example.boltalp1.data.user.User;

import androidx.annotation.NonNull;
import io.reactivex.Flowable;

public interface UserDS {
    Flowable<Long> registerUser(@NonNull String username,
                                @NonNull String password,
                                String email,
                                String phoneNumber,
                                @NonNull String role);

    void saveCurrentUser(long userId,
                         @NonNull String role);

    Flowable<User> login(@NonNull String username,
                         @NonNull String password,
                         @NonNull String role);

    boolean logout();
}
