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
import com.my.schoolshop.util.PageCalculator;
import com.sun.javafx.scene.shape.PathUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.InputStream;
import java.util.Date;
import java.util.List;

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

    @Override
    public Shop getByShopId(long shopId) {
        return shopDao.queryByShopId(shopId);
    }

    @Override
    public ShopExecution modifyShop(Shop shop, InputStream shopImg, String fileName) throws ShopOperationException {
        //1.判断是否需要处理图片
        //2.更新店铺信息
        try {
        if(shop == null || shop.getShopId() == null){
            return new ShopExecution(ShopStateEnum.NULL_SHOP_INFO);
        }else {
             if(shopImg != null && fileName != null && !("").equals(fileName)){
                 Shop tempShop = shopDao.queryByShopId(shop.getShopId());
                 if(tempShop.getShopImg() != null){
                     FileUtil.deleteFile(tempShop.getShopImg());
                 }
                addShopImg(shop,shopImg,fileName);
             }

             shop.setLastEditTime(new Date());
             int k = shopDao.updateShop(shop);
             if(k <= 0){
                 return new ShopExecution(ShopStateEnum.INNER_ERROR);
             }else {
                 shop = shopDao.queryByShopId(shop.getShopId());
                 return new ShopExecution(ShopStateEnum.SUCCESS,shop);
             }
        } }catch (Exception e){
            throw new ShopOperationException("modifyShop err:"+e.getMessage());
        }
    }


    @Override
    public ShopExecution getShopList(Shop shopCondition, int pageIndex, int pageSize) {

        int rowIndex = PageCalculator.calculateRowIndex(pageIndex,pageSize);
        List<Shop> shopList = shopDao.queryShopList(shopCondition,rowIndex,pageSize);
        int count = shopDao.queryShopCount(shopCondition);
        ShopExecution se = new ShopExecution();
        if(shopList != null){
            se.setShopList(shopList);
            se.setCount(count);
        }else {
            se.setState(ShopStateEnum.INNER_ERROR.getState());
        }

        return se;
    }

    private void addShopImg(Shop shop, InputStream shopImg, String fileName) {
        //获取shop图片的相对子路径
        String dest = FileUtil.getShopImagePath(shop.getShopId());
        String shopImgAddr = ImageUtil.generateThumbnail(shopImg,fileName,dest);
        shop.setShopImg(shopImgAddr);
    }
}
