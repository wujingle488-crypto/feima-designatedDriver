package com.fmdj.mis.api.service.impl;

import cn.hutool.core.map.MapUtil;
import com.fmdj.common.exception.GlobalException;
import com.fmdj.common.util.PageUtils;
import com.fmdj.mis.api.db.dao.DeptDao;
import com.fmdj.mis.api.db.pojo.DeptEntity;
import com.fmdj.mis.api.service.DeptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class DeptServiceImpl implements DeptService {
    @Resource
    private DeptDao deptDao;

    @Override
    public PageUtils selectDeptByPage(Map param) {
        List<HashMap> list = null;
        //先查询是否有满足条件的记录
        long count = deptDao.selectDeptCount(param);
        if (count > 0) {
            list =deptDao.selectDeptByPage(param);
        } else {
            list = new ArrayList<>();
        }
        /*
         * 创建一个分页对象，并且返回
         * */
        int page = MapUtil.getInt(param, "page");
        int length = MapUtil.getInt(param, "length");
        PageUtils pageUtils = new PageUtils(list, count, page, length);
        return pageUtils;
    }

    @Override
    public List<HashMap> selectAllDept() {
        return deptDao.selectAllDept();
    }

    @Override
    public HashMap selectById(int id) {
        return deptDao.selectById(id);
    }

    @Override
    @Transactional
    public int insert(DeptEntity entity) {
        return deptDao.insert(entity);
    }

    @Override
    @Transactional
    public int update(DeptEntity entity) {
        return deptDao.insert(entity);
    }

    @Override
    @Transactional
    public int deleteDeptByIds(Integer[] ids) {
        if (deptDao.hasAssociatedUsers(ids)) {
            throw new GlobalException("无法删除关联用户的部门");
        }
        return deptDao.deleteDeptByIds(ids);
    }
}
