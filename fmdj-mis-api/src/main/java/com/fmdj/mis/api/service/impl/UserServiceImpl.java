package com.fmdj.mis.api.service.impl;

import cn.hutool.core.map.MapUtil;
import com.fmdj.common.util.PageUtils;
import com.fmdj.mis.api.db.dao.UserDao;
import com.fmdj.mis.api.db.pojo.UserEntity;
import com.fmdj.mis.api.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Resource
    private UserDao userDao;

    @Override
    public Set<String> searchUserPermissions(int userId) {
        return userDao.searchUserPermissions(userId);
    }

    @Override
    public Integer login(Map param) {
        return userDao.login(param);
    }

    @Override
    public HashMap loadUserInfo(int userId) {
        return userDao.loadUserInfo(userId);
    }

    @Override
    public PageUtils selectUserByPage(Map param) {
        List<HashMap> list = null;
        long count = userDao.selectUserCount(param);
        if (count > 0) {
            list = userDao.selectUserByPage(param);
        } else {
            list = new ArrayList<>();
        }
        Integer page = MapUtil.getInt(param, "page");
        Integer length = MapUtil.getInt(param, "length");
        return new PageUtils(list, count, page, length);
    }

    @Override
    public ArrayList<HashMap> selectAllUser() {
        return userDao.selectAllUser();
    }

    @Override
    public HashMap selectNameAndDept(int userId) {
        return userDao.selectNameAndDept(userId);
    }

    @Override
    public HashMap selectById(int userId) {
        return userDao.selectById(userId);
    }

    @Override
    @Transactional
    public int insert(UserEntity userEntity) {
        return userDao.insert(userEntity);
    }

    @Override
    @Transactional
    public int update(UserEntity userEntity) {
        return userDao.update(userEntity);
    }

    @Override
    @Transactional
    public int updatePassword(Map param) {
        return userDao.updatePassword(param);
    }

    @Override
    @Transactional
    public int delete(Integer[] ids) {
        return userDao.delete(ids);
    }

}
