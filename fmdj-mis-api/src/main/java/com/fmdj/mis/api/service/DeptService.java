package com.fmdj.mis.api.service;

import com.fmdj.common.util.PageUtils;
import com.fmdj.mis.api.db.pojo.DeptEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface DeptService {
    PageUtils selectDeptByPage(Map param);

    List<HashMap> selectAllDept();

    HashMap selectById(int id);

    int insert(DeptEntity entity);

    int update(DeptEntity entity);

    int deleteDeptByIds(Integer[] ids);
}
