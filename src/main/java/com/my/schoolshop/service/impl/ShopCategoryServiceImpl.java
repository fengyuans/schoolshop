package com.my.schoolshop.service.impl;

import com.my.schoolshop.dao.ShopCategoryDao;
import com.my.schoolshop.model.ShopCategory;
import com.my.schoolshop.service.ShopCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShopCategoryServiceImpl implements ShopCategoryService {
    @Autowired
    private ShopCategoryDao dao;


    @Override
    public List<ShopCategory> getShopCategoryList(ShopCategory shopCategoryConditon) {
        return dao.queryShopCategory(shopCategoryConditon);
    }
}
