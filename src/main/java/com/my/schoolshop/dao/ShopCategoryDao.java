package com.my.schoolshop.dao;

import com.my.schoolshop.model.ShopCategory;

public interface ShopCategoryDao {


    int insertShopCategory(ShopCategory shopCategory);

    ShopCategory queryShopCategoryById(long shopCategoryId);
}
