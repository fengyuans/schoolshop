package com.my.schoolshop.service;

import com.my.schoolshop.dto.ShopExecution;
import com.my.schoolshop.model.Shop;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

public interface ShopService {


    //创建店铺
    ShopExecution addShop(Shop shop, CommonsMultipartFile shopImg) throws RuntimeException;
}
