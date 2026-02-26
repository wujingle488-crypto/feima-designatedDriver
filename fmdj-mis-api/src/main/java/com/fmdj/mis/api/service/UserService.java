package com.fmdj.mis.api.service;

import com.fmdj.common.util.PageUtils;

import java.util.Map;
import java.util.Set;

public interface UserService {
    Set<String> searchUserPermissions(int userId);

    Integer login(Map param);


}
