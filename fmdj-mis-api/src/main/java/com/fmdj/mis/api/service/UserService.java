package com.fmdj.mis.api.service;

import com.fmdj.common.util.PageUtils;
import com.fmdj.mis.api.db.pojo.UserEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public interface UserService {
    Set<String> searchUserPermissions(int userId);

    Integer login(Map param);

    HashMap loadUserInfo(int userId);

    PageUtils selectUserByPage(Map param);

    ArrayList<HashMap> selectAllUser();

    HashMap selectById(int userId);

    HashMap selectNameAndDept(int userId);

    int insert(UserEntity userEntity);

    int update(UserEntity userEntity);

    int updatePassword(Map param);

    int delete(Integer[] ids);
}
