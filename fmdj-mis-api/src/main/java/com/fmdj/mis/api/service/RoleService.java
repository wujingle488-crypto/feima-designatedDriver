package com.fmdj.mis.api.service;

import com.fmdj.common.util.PageUtils;
import com.fmdj.mis.api.db.pojo.RoleEntity;

import java.util.ArrayList;
import java.util.HashMap;

public interface RoleService {
    PageUtils selectRoleByPage(HashMap param);

    java.util.ArrayList<HashMap> selectAllRole();

    HashMap selectById(int id);

    int insert(RoleEntity entity);

    int update(RoleEntity entity);

    ArrayList<Integer> selectUserIdByRoleId(int roleId);

    int deleteDeptByIds(Integer[] ids);
}
