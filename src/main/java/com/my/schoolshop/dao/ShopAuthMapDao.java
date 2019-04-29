package com.my.schoolshop.dao;

import com.my.schoolshop.model.ShopAuthMap;

public interface ShopAuthMapDao {

    //新增一条店铺与店员的授权关系
    int insertShopAuthMap(ShopAuthMap shopAuthMap);
}
