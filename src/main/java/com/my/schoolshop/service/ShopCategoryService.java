package com.my.schoolshop.service;


import com.my.schoolshop.model.ShopCategory;

import java.util.List;

public interface ShopCategoryService {
    List<ShopCategory> getShopCategoryList(ShopCategory shopCategoryConditon);

}
