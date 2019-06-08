package com.example.boltalp1;

import android.content.Context;

import com.example.boltalp1.data.AppDatabase;
import com.example.boltalp1.data.role.Role;
import com.example.boltalp1.data.role.RoleDao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import androidx.room.Room;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;
import io.reactivex.schedulers.Schedulers;

@RunWith(AndroidJUnit4.class)
public class RoleTableTest {
    private RoleDao mRoleDao;
    private AppDatabase mAppDatabase;

    @Before
    public void createDb() {
        Context context = InstrumentationRegistry
                .getInstrumentation()
                .getContext();
        mAppDatabase = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        mRoleDao = mAppDatabase.getRoleDao();

    }

    @After
    public void closeDb() {
        mAppDatabase.close();
    }

    @Test
    public void testInsert() {
        Role role = new Role("Admin", Calendar.getInstance().getTime());
        mRoleDao.insert(role)
                .test()
                .assertSubscribed()
                .assertComplete()
                .assertNoErrors()
                .dispose();
    }

    @Test
    public void testBulkInsert() {
        Role role = new Role("Admin", Calendar.getInstance().getTime());
        Role role1 = new Role("User", Calendar.getInstance().getTime());
        Role role2 = new Role("Visitor", Calendar.getInstance().getTime());
        List<Role> roles = new ArrayList<>();
        roles.add(role);
        roles.add(role1);
        roles.add(role2);


        mRoleDao.bulkInsert(roles)
                .subscribeOn(Schedulers.trampoline())
                .test()
                .assertSubscribed()
                .assertNoErrors()
                .assertComplete()
                .assertValue(l -> l.size() == 3)
                .dispose();


        mRoleDao.getRoles()
                .subscribeOn(Schedulers.trampoline())
                .test()
                .assertSubscribed()
                .assertNoErrors()
                .assertComplete()
                .assertValue(l -> l.size() == 3)
                .dispose();
    }

    @Test
    public void testGetRoleById() {
        Date date = Calendar.getInstance().getTime();
        Role role = new Role("Admin", date);
        mRoleDao.insert(role)
                .subscribeOn(Schedulers.trampoline())
                .test()
                .assertSubscribed()
                .assertNoErrors()
                .assertComplete()
                .dispose();

        mRoleDao.getRoleById(1)
                .test()
                .assertValue(r -> r.getName().equals(role.getName())
                        && r.getUpdatedDate().equals(date))
                .assertNoErrors()
                .assertComplete()
                .assertSubscribed()
                .dispose();

        mRoleDao.getRoles();
    }
}
