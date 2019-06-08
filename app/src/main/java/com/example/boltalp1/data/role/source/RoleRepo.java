package com.example.boltalp1.data.role.source;

import android.util.Log;

import com.example.boltalp1.data.role.Role;
import com.example.boltalp1.data.role.RoleDao;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static android.content.ContentValues.TAG;

public class RoleRepo implements RoleDS {
    private static RoleRepo sInstance;
    private final RoleDao mRoleDao;

    public RoleRepo(RoleDao roleDao) {
        mRoleDao = roleDao;
    }

    public static RoleRepo getInstance(RoleDao roleDao) {
        if (sInstance == null) {
            sInstance = new RoleRepo(roleDao);
        }
        return sInstance;
    }

    @Override
    public Flowable<List<Role>> getRoles() {
        return mRoleDao.getRoles().toFlowable();
    }

    @Override
    public Flowable<Long> addRole(Role role) {
        return mRoleDao.insert(role).toFlowable();
    }

    @Override
    public Flowable<Integer> updateRole(Role role) {
        return mRoleDao.update(role).toFlowable();
    }

    @Override
    public Flowable<Integer> deleteRole(Role role) {
        // FIXME: 6/3/19 handle delete Role
        return mRoleDao.update(role).toFlowable();
    }

    @Override
    public Flowable<List<Long>> initRoleIfEmpty(List<Role> roles) {
        return mRoleDao.bulkInsert(roles)
                .toFlowable();
    }

    @Override
    public Flowable<Role> getRoleByName(String roleName) {
        return mRoleDao.getRoleByName(roleName)
                .toFlowable();
    }
}
