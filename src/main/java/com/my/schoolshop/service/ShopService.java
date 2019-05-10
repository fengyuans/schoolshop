package com.my.schoolshop.service;

import com.my.schoolshop.dto.ShopExecution;
import com.my.schoolshop.model.Shop;

import java.io.InputStream;

public interface ShopService {


    //创建店铺
    ShopExecution addShop(Shop shop, InputStream shopImg,String fileName) throws RuntimeException;
}
