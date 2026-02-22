package com.fmdj.vhr.db.dao;


import com.fmdj.vhr.db.pojo.VoucherEntity;

import java.util.ArrayList;

public interface VoucherDao {
    public int insert(VoucherEntity entity);

    public ArrayList<String> searchIdByUUID(ArrayList<String> list);
}




