package com.fmdj.mis.api.db.dao;


import com.fmdj.mis.api.db.pojo.RoleEntity;

import java.util.ArrayList;
import java.util.HashMap;

public interface RoleDao {

    ArrayList<HashMap> selectRoleByPage(HashMap param);

    Long selectRoleCount(HashMap param);

    ArrayList<HashMap> selectAllRole();

    HashMap selectById(int id);

    int insert(RoleEntity entity);

    int update(RoleEntity entity);

    ArrayList<Integer> selectUserIdByRoleId(int roleId);

    boolean hasAssociatedUsers(Integer[] ids);

    int deleteRoleByIds(Integer[] ids);
}




