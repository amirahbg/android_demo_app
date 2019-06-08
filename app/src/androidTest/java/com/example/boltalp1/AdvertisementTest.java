package com.example.boltalp1;

import android.content.Context;

import com.example.boltalp1.data.AppDatabase;
import com.example.boltalp1.data.advertisement.Advertisement;
import com.example.boltalp1.data.advertisement.AdvertisementDao;
import com.example.boltalp1.data.user.User;
import com.example.boltalp1.data.user.UserDao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;
import java.util.Date;

import androidx.room.Room;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;
import io.reactivex.schedulers.Schedulers;

@RunWith(AndroidJUnit4.class)
public class AdvertisementTest {

    private AppDatabase mAppDatabase;
    private AdvertisementDao mAdvertisementDao;
    private UserDao mUserDao;
    private User mUser;

    @Before
    public void createDb() {
        Context context = InstrumentationRegistry
                .getInstrumentation()
                .getContext();
        mAppDatabase = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        mAdvertisementDao = mAppDatabase.getAdvertisementDao();
        mUserDao = mAppDatabase.getUserDao();

        mUser = new User("amir",
                "1234",
                "12345",
                "aaaa@gamil.com",
                Calendar.getInstance().getTime(),
                Calendar.getInstance().getTime());

        mUserDao.insertUser(mUser).subscribeOn(Schedulers.trampoline())
                .observeOn(Schedulers.trampoline())
                .subscribe(u -> mUser.setId(u))
                .dispose();
    }

    @After
    public void closeDb() {
        mAppDatabase.close();
    }

    @Test
    public void testInsert() {
        Advertisement advertisement = new Advertisement(mUser.getId(), "title", "url",
                1, 1, "body", new Date(0), 2000);
        mAdvertisementDao.insertAdv(advertisement)
                .subscribeOn(Schedulers.trampoline())
                .test()
                .assertSubscribed()
                .assertComplete()
                .assertValue(l -> l == 1)
                .assertNoErrors()
                .dispose();

        mAdvertisementDao.insertAdv(advertisement)
                .subscribeOn(Schedulers.trampoline())
                .test()
                .assertSubscribed()
                .assertComplete()
                .assertValue(l -> l == 2)
                .assertNoErrors()
                .dispose();

        mAdvertisementDao.getUsersAdvertisement(mUser.getId())
                .subscribeOn(Schedulers.trampoline())
                .test()
                .assertComplete()
                .assertNoErrors()
                .assertSubscribed()
                .assertValue(r -> r.size() == 2)
                .dispose();
    }


}
