package com.fmdj.mis.api.service.impl;

import com.fmdj.mis.api.db.dao.UserDao;
import com.fmdj.mis.api.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Set;

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
}
