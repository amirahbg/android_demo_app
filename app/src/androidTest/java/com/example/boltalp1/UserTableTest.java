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

import androidx.room.EmptyResultSetException;
import androidx.room.Room;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;
import io.reactivex.schedulers.Schedulers;

@RunWith(AndroidJUnit4.class)
public class UserTableTest {
    private AppDatabase mAppDatabase;
    private UserDao mUserDao;
    private RoleDao mRoleDao;
    private UserRoleJoinDao mUserRoleDao;

    @Before
    public void createDb() {
        Context context = InstrumentationRegistry
                .getInstrumentation()
                .getContext();
        mAppDatabase = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        mUserDao = mAppDatabase.getUserDao();
        mRoleDao = mAppDatabase.getRoleDao();
        mUserRoleDao = mAppDatabase.getUserRoleDao();
    }

    @After
    public void closeDb() {
        mAppDatabase.close();
    }

    @Test
    public void testInsertUser() {
        User user = new User(null,
                "1234",
                "0922333333",
                "email@gmail.com",
                Calendar.getInstance().getTime(),
                Calendar.getInstance().getTime());

        mUserDao.insertUser(user)
                .subscribeOn(Schedulers.trampoline())
                .test()
                .assertSubscribed()
                .assertError(e -> e instanceof SQLiteConstraintException)
                .assertNotComplete()
                .dispose();

        user.setUsername("amir");
        mUserDao.insertUser(user)
                .subscribeOn(Schedulers.trampoline())
                .test()
                .assertSubscribed()
                .assertComplete()
                .assertNoErrors()
                .dispose();

        mUserDao.insertUser(user)
                .subscribeOn(Schedulers.trampoline())
                .test()
                .assertSubscribed()
                .assertNotComplete()
                .assertError(e -> e instanceof SQLiteConstraintException)
                .dispose();


    }

    @Test
    public void loginTest() {
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
                .assertValue(v -> v.get(0).getId() == 1)
                .assertNoErrors()
                .dispose();


        UserRoleJoin userRoleJoin = new UserRoleJoin(1, 1);
        mUserRoleDao.insertUserRole(userRoleJoin)
                .subscribeOn(Schedulers.trampoline())
                .test()
                .assertSubscribed()
                .assertComplete()
                .assertNoErrors()
                .dispose();


        mUserRoleDao.insertUserRole(userRoleJoin)
                .subscribeOn(Schedulers.trampoline())
                .test()
                .assertSubscribed()
                .assertNotComplete()
                .assertError(e -> e instanceof SQLiteConstraintException)
                .dispose();

        mUserDao.getUserByUsernameAndPassword("amir", "1234", "Admin")
                .subscribeOn(Schedulers.trampoline())
                .test()
                .assertNoErrors()
                .assertComplete()
                .assertSubscribed()
                .assertValue(u -> u.getUsername().equals("amir") && u.getPassword().equals("1234"))
                .dispose();

        mUserDao.getUserByUsernameAndPassword("amir", "1234", "User")
                .subscribeOn(Schedulers.trampoline())
                .test()
                .assertError(e -> e instanceof EmptyResultSetException)
                .assertNotComplete()
                .assertSubscribed()
                .dispose();

    }
}
