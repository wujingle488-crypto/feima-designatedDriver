package com.fmdj.mis.api.db.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface UserDao {
    Set<String> searchUserPermissions(int userId);

    Integer login(Map param);
}




