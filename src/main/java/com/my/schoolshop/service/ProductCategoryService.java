package com.my.schoolshop.service;

import com.my.schoolshop.dto.ProductCategoryExecution;
import com.my.schoolshop.exceptions.ProductCategoryOperationException;
import com.my.schoolshop.model.ProductCategory;

import java.util.List;

public interface ProductCategoryService {
    List<ProductCategory> getProductCategoryList(long shopId);

    ProductCategoryExecution batchAddProductCategory(List<ProductCategory> productCategoryList) throws ProductCategoryOperationException;

    ProductCategoryExecution deleteProductCategory(long productCategoryId, long shopId) throws  ProductCategoryOperationException;
}
