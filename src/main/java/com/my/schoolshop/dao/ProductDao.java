package com.my.schoolshop.dao;

import com.my.schoolshop.model.Product;

public interface ProductDao {

    int updateProductCategoryToNull(long productCategoryId);

    int insertProduct(Product product);
}
