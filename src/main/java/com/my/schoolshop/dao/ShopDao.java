package com.my.schoolshop.dao;

import com.my.schoolshop.model.Shop;

public interface ShopDao {
    //新增店铺
    int insertShop(Shop shop);

    //更新店铺信息
    int updateShop(Shop shop);

    //通过shopId查询店铺信息
    Shop queryByShopId(long shopId);

}
