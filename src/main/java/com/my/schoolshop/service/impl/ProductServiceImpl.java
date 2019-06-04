package com.my.schoolshop.service.impl;

import com.my.schoolshop.dto.ImageHolder;
import com.my.schoolshop.dao.ProductDao;
import com.my.schoolshop.dao.ProductImgDao;
import com.my.schoolshop.dto.ProductExecution;
import com.my.schoolshop.enums.ProductStateEnum;
import com.my.schoolshop.exceptions.ProductOperationException;
import com.my.schoolshop.model.Product;
import com.my.schoolshop.model.ProductImg;
import com.my.schoolshop.service.ProductService;
import com.my.schoolshop.util.FileUtil;
import com.my.schoolshop.util.ImageUtil;
import com.my.schoolshop.util.PageCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductDao productDao;
    @Autowired
    private ProductImgDao productImgDao;


    @Override
    @Transactional
    public ProductExecution addProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgList) throws ProductOperationException {
        if(product != null && product.getShop() != null && product.getShop().getShopId() != null){
            product.setCreateTime(new Date());
            product.setLastEditTime(new Date());

            product.setEnableStatus(1);
            if(thumbnail != null){
                addThumnail(product,thumbnail);
            }
            try {
                int effectedNum = productDao.insertProduct(product);
                if (effectedNum <= 0){
                    throw  new ProductOperationException("创建商品失败");
                }
            }catch (Exception e){
                throw  new ProductOperationException("创建商品失败"+e.getMessage());
            }

            if(productImgList != null && productImgList.size() > 0){
                addProductImgList(product,productImgList);
            }
            return new ProductExecution(ProductStateEnum.SUCCESS,product);
        }else {
            return new ProductExecution(ProductStateEnum.EMPTY);
        }
    }

    @Override
    @Transactional
    public ProductExecution modifyProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgList) throws ProductOperationException {
        if(product != null && product.getShop() != null && product.getShop().getShopId() != null){
            product.setLastEditTime(new Date());
            if (thumbnail != null){
                Product tempProduct = productDao.queryProductByProductId(product.getProductId());
                if(tempProduct != null){
                    FileUtil.deleteFile(tempProduct.getImgAddr());
                }
                addThumnail(product,thumbnail);
            }
            if (productImgList != null && productImgList.size() > 0){
                deleteProductImgList(product.getProductId());
                addProductImgList(product,productImgList);
            }
            try {
                int effectedNum = productDao.updateProduct(product);
                if(effectedNum <= 0){
                    throw new ProductOperationException("更新商品信息失败");
                }
                return new ProductExecution(ProductStateEnum.SUCCESS,product);
            }catch (Exception e){
                throw new ProductOperationException("更新商品信息失败"+e.toString());
            }
        }else {
            return new ProductExecution(ProductStateEnum.EMPTY);
        }
    }

    @Override
    public Product queryProductByProductId(long productId) {
        return productDao.queryProductByProductId(productId);
    }

    @Override
    public ProductExecution getProductList(Product product, int pageIndex, int pageSize) {
        int rowIndex = PageCalculator.calculateRowIndex(pageIndex,pageSize);
        List<Product> productList = productDao.queryProductList(product,rowIndex,pageSize);
        int count = productDao.queryProductCount(product);
        ProductExecution pe = new ProductExecution();
        pe.setProductList(productList);
        pe.setCount(count);
        return  pe;
    }


    private void addThumnail(Product product,ImageHolder thumbnail){
        String dest = FileUtil.getShopImagePath(product.getShop().getShopId());
        String thumbnailAddr = ImageUtil.generateThumbnail(thumbnail,dest);
        product.setImgAddr(thumbnailAddr);
    }

    private void addProductImgList(Product product,List<ImageHolder> productImgList){
        String dest = FileUtil.getShopImagePath(product.getShop().getShopId());
        List<ProductImg> productImgs = new ArrayList<>();
        for(ImageHolder productImagHolder : productImgList){
            String imagAddr = ImageUtil.generateThumbnail(productImagHolder,dest);
            ProductImg productImg = new ProductImg();
            productImg.setImgAddr(imagAddr);
            productImg.setProductId(product.getProductId());
            productImg.setCreateTime(new Date());
            productImgs.add(productImg);
        }

        if(productImgs.size() > 0){
            try {
                int effectedNum = productImgDao.batchInsertProductImg(productImgs);
                if(effectedNum <= 0){
                    throw  new ProductOperationException("创建商品详情图片失败");
                }
            }catch (Exception e){
                throw  new ProductOperationException("创建商品详情图片失败"+e.getMessage());
            }
        }
    }

    private void deleteProductImgList(long productId){
        List<ProductImg> productImgs = productImgDao.queryProductImgList(productId);
        for (ProductImg productImg : productImgs){
            FileUtil.deleteFile(productImg.getImgAddr());
        }
        productImgDao.deleteProductImgByProductId(productId);
    }

}
