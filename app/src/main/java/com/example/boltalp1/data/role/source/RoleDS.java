package com.example.boltalp1.data.role.source;

import com.example.boltalp1.data.role.Role;

import java.util.List;

import io.reactivex.Flowable;

public interface RoleDS {
    Flowable<List<Role>> getRoles();

    Flowable<Long> addRole(Role role);

    Flowable<Integer> updateRole(Role role);

    Flowable<Integer> deleteRole(Role role);

    Flowable<List<Long>> initRoleIfEmpty(List<Role> roles);

    Flowable<Role> getRoleByName(String roleName);
}
