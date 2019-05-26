package com.my.schoolshop.dao;

import com.my.schoolshop.model.ShopCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShopCategoryDao {


    int insertShopCategory(ShopCategory shopCategory);

    ShopCategory queryShopCategoryById(long shopCategoryId);

    List<ShopCategory> queryShopCategory(@Param("shopCategoryCondition") ShopCategory shopCategoryCondition);
}
