package com.my.schoolshop.service.impl;

import com.my.schoolshop.dao.ShopAuthMapDao;
import com.my.schoolshop.dao.ShopCategoryDao;
import com.my.schoolshop.dao.ShopDao;
import com.my.schoolshop.dto.ShopExecution;
import com.my.schoolshop.enums.ShopStateEnum;
import com.my.schoolshop.exceptions.ShopOperationException;
import com.my.schoolshop.model.Shop;
import com.my.schoolshop.model.ShopAuthMap;
import com.my.schoolshop.model.ShopCategory;
import com.my.schoolshop.service.ShopService;
import com.my.schoolshop.util.FileUtil;
import com.my.schoolshop.util.ImageUtil;
import com.sun.javafx.scene.shape.PathUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.InputStream;
import java.util.Date;

@Service
public class ShopServiceImpl implements ShopService {
    @Autowired
    private ShopDao shopDao;
    @Autowired
    private ShopAuthMapDao shopAuthMapDao;
    @Autowired
    private ShopCategoryDao shopCategoryDao;



    @Override
    @Transactional
    public ShopExecution addShop(Shop shop, InputStream shopImg,String fileName) throws ShopOperationException {
        if(shop == null){
            return new ShopExecution(ShopStateEnum.NULL_SHOP_INFO);
        }
        try {
            //初始值
            shop.setEnableStatus(0);//未上架，待审核
            shop.setCreateTime(new Date());
            shop.setLastEditTime(new Date());
            /*if(shop.getShopCategory() != null){
                Long shopCategoryId = shop.getShopCategory().getShopCategoryId();
                ShopCategory sc = new ShopCategory();
                sc = shopCategoryDao.queryShopCategoryById(shopCategoryId);
                ShopCategory parentCategory = new ShopCategory();
                parentCategory.setShopCategoryId(sc.getParentId());
                shop.setParentCategory(parentCategory);
            }*/
            int effectedNum = shopDao.insertShop(shop);
            if(effectedNum <= 0){
                throw new ShopOperationException("店铺创建失败");
            }else {
                if(shopImg != null){
                    try {//存储图片
                        addShopImg(shop,shopImg,fileName);
                    }catch (Exception e){
                        throw  new ShopOperationException("addShopImg err"+e.getMessage());
                    }
                    //更新店铺图片地址
                    effectedNum = shopDao.updateShop(shop);
                    if(effectedNum <= 0){
                        throw new ShopOperationException("更新图片地址失败");
                    }

                }
            }
        }catch (Exception e){
            throw new ShopOperationException("addShop err"+e.getMessage());
        }


        return new ShopExecution(ShopStateEnum.CHECK,shop);
    }

    private void addShopImg(Shop shop, InputStream shopImg,String fileName) {
        //获取shop图片的相对子路径
        String dest = FileUtil.getShopImagePath(shop.getShopId());
        String shopImgAddr = ImageUtil.generateThumbnail(shopImg,fileName,dest);
        shop.setShopImg(shopImgAddr);
    }
}
