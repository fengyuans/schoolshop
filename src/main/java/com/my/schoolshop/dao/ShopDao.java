package com.my.schoolshop.dao;

import com.my.schoolshop.model.Shop;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShopDao {
    //新增店铺
    int insertShop(Shop shop);

    //更新店铺信息
    int updateShop(Shop shop);

    //通过shopId查询店铺信息
    Shop queryByShopId(long shopId);

    //分页查询店铺
    List<Shop> queryShopList(@Param("shopCondition") Shop shopCondition,@Param("rowIndex") int rowIndex,@Param("pageSize") int pageSize);

    int queryShopCount(@Param("shopCondition") Shop shopCondition);




}
