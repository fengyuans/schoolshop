package com.my.schoolshop.service;

import com.my.schoolshop.dto.ImageHolder;
import com.my.schoolshop.dto.ProductExecution;
import com.my.schoolshop.exceptions.ProductOperationException;
import com.my.schoolshop.model.Product;

import java.util.List;

public interface ProductService {
    //thumbnail 缩略图
    //productImgList 详情图
    ProductExecution addProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgList) throws ProductOperationException;

    ProductExecution modifyProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgList) throws ProductOperationException;

    Product queryProductByProductId(long productId);

    ProductExecution getProductList(Product product,int pageIndex,int pageSize);

}
