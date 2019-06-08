package com.example.boltalp1;

import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;

import com.example.boltalp1.data.AppDatabase;
import com.example.boltalp1.data.role.Role;
import com.example.boltalp1.data.role.RoleDao;
import com.example.boltalp1.data.user.User;
import com.example.boltalp1.data.user.UserDao;
import com.example.boltalp1.data.user_role.UserRoleJoin;
import com.example.boltalp1.data.user_role.UserRoleJoinDao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;

import androidx.room.Room;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;
import io.reactivex.schedulers.Schedulers;

@RunWith(AndroidJUnit4.class)
public class UserRoleJoinTest {
    private UserRoleJoinDao mUserRoleJoinDao;
    private UserDao mUserDao;
    private RoleDao mRoleDao;
    private AppDatabase mAppDatabase;

    @Before
    public void createDb() {
        Context context = InstrumentationRegistry
                .getInstrumentation()
                .getContext();
        mAppDatabase = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        mUserRoleJoinDao = mAppDatabase.getUserRoleDao();
        mRoleDao = mAppDatabase.getRoleDao();
        mUserDao = mAppDatabase.getUserDao();
    }

    @After
    public void closeDb() {
        mAppDatabase.close();
    }

    @Test
    public void testInsertUserRole() {
        User user = new User("amir",
                "1234",
                "0922333333",
                "email@gmail.com",
                Calendar.getInstance().getTime(),
                Calendar.getInstance().getTime());

        Role role = new Role("Admin",
                Calendar.getInstance().getTime());

        mUserDao.insertUser(user)
                .subscribeOn(Schedulers.trampoline())
                .test()
                .assertSubscribed()
                .assertComplete()
                .assertNoErrors()
                .dispose();

        mRoleDao.insert(role)
                .subscribeOn(Schedulers.trampoline())
                .test()
                .assertNoErrors()
                .assertComplete()
                .assertSubscribed()
                .dispose();

        mRoleDao.getRoles()
                .subscribeOn(Schedulers.trampoline())
                .test()
                .assertValue(v -> v.get(0).getId() == 1)
                .dispose();

        mUserDao.getUsers()
                .subscribeOn(Schedulers.trampoline())
                .test()
                .assertValue(v -> {
                    return v.get(0).getId() == 1;
                })
                .assertNoErrors()
                .dispose();


        UserRoleJoin userRoleJoin = new UserRoleJoin(1, 1);
        mUserRoleJoinDao.insertUserRole(userRoleJoin)
                .subscribeOn(Schedulers.trampoline())
                .test()
                .assertSubscribed()
                .assertComplete()
                .assertNoErrors()
                .dispose();


        mUserRoleJoinDao.insertUserRole(userRoleJoin)
                .subscribeOn(Schedulers.trampoline())
                .test()
                .assertSubscribed()
                .assertNotComplete()
                .assertError(e -> e instanceof SQLiteConstraintException)
                .dispose();
    }

}
