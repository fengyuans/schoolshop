package com.my.schoolshop.dao;

import com.my.schoolshop.model.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductDao {

    int updateProductCategoryToNull(long productCategoryId);

    int insertProduct(Product product);

    int updateProduct(Product product);

    Product queryProductByProductId(long productId);

    List<Product> queryProductList(@Param("productCondition") Product productCondition,@Param("rowIndex") int rowIndex, @Param("pageSize") int pageSize);

    int queryProductCount(@Param("productCondition") Product productCondition);
}
