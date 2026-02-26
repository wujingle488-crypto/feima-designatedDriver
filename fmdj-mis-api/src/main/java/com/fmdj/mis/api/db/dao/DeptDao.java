package com.fmdj.mis.api.db.dao;


import com.fmdj.mis.api.db.pojo.DeptEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface DeptDao {
    List<HashMap> selectDeptByPage(Map param);

    long selectDeptCount(Map param);

    List<HashMap> selectAllDept();

    HashMap selectById(int id);

    int insert(DeptEntity entity);

    int update(DeptEntity entity);

    boolean hasAssociatedUsers(Integer[] ids);

    int deleteDeptByIds(Integer[] ids);
}




