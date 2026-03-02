package com.fmdj.mis.api.service.impl;

import com.fmdj.mis.api.db.dao.PermissionDao;
import com.fmdj.mis.api.service.PermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;

@Service
@Slf4j
public class PermissionServiceImpl implements PermissionService {
    @Resource
    private PermissionDao permissionDao;

    @Override
    public ArrayList<HashMap> searchAllPermission() {
        return permissionDao.searchAllPermission();
    }
}
