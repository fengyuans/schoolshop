package com.my.schoolshop.service;

import com.my.schoolshop.dao.ImageHolder;
import com.my.schoolshop.dto.ShopExecution;
import com.my.schoolshop.exceptions.ShopOperationException;
import com.my.schoolshop.model.Shop;

import java.io.InputStream;

public interface ShopService {


    //创建店铺
    ShopExecution addShop(Shop shop,ImageHolder thumbnail) throws ShopOperationException;

    Shop getByShopId(long shopId);

    ShopExecution modifyShop(Shop shop,ImageHolder thumbnail) throws ShopOperationException;

    ShopExecution getShopList(Shop shopCondition,int pageIndex,int pageSize);
}
