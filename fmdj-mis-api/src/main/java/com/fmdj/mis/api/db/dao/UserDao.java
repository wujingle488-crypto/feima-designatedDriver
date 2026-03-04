package com.fmdj.mis.api.db.dao;

import com.fmdj.mis.api.controller.form.InsertUserForm;
import com.fmdj.mis.api.db.pojo.UserEntity;

import java.util.*;

public interface UserDao {
    Set<String> searchUserPermissions(int userId);

    Integer login(Map param);

    HashMap loadUserInfo(int userId);

    List<HashMap> selectUserByPage(Map param);

    long selectUserCount(Map param);

    ArrayList<HashMap> selectAllUser();

    HashMap selectById(int userId);

    HashMap selectNameAndDept(int userId);

    int insert(UserEntity userEntity);

    int update(UserEntity userEntity);

    int updatePassword(Map param);

    int delete(Integer[] ids);
}




