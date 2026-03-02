package com.fmdj.mis.api.service.impl;

import cn.hutool.core.map.MapUtil;
import com.fmdj.common.exception.GlobalException;
import com.fmdj.common.util.PageUtils;
import com.fmdj.mis.api.db.dao.RoleDao;
import com.fmdj.mis.api.db.pojo.RoleEntity;
import com.fmdj.mis.api.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;

@Service
@Slf4j
public class RoleServiceImpl implements RoleService {
    @Resource
    private RoleDao roleDao;

    @Override
    public PageUtils selectRoleByPage(HashMap param) {
        ArrayList<HashMap> list = null;
        Long count = roleDao.selectRoleCount(param);
        if (count > 0) {
            list = roleDao.selectRoleByPage(param);
        } else {
            list = new ArrayList<>();
        }
        int page = MapUtil.getInt(param, "page");
        int length = MapUtil.getInt(param, "length");
        return new PageUtils(list, count, page, length);
    }

    @Override
    public ArrayList<HashMap> selectAllRole() {
        return roleDao.selectAllRole();
    }

    @Override
    public HashMap selectById(int id) {
        return roleDao.selectById(id);
    }

    @Override
    @Transactional
    public int insert(RoleEntity entity) {
        return roleDao.insert(entity);
    }

    @Override
    @Transactional
    public int deleteDeptByIds(Integer[] ids) {
        if (roleDao.hasAssociatedUsers(ids)) {
            throw new GlobalException("无法删除关联用户的角色");
        } else {
            return roleDao.deleteRoleByIds(ids);
        }
    }

    @Override
    @Transactional
    public int update(RoleEntity entity) {
        return roleDao.update(entity);
    }

    @Override
    public ArrayList<Integer> selectUserIdByRoleId(int roleId) {
        return roleDao.selectUserIdByRoleId(roleId);
    }
}
