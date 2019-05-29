package com.my.schoolshop.dao;

import com.my.schoolshop.model.ProductImg;

import java.util.List;

public interface ProductImgDao {

    int batchInsertProductImg(List<ProductImg> productImgs);

    int deleteProductImgByProductId(long productId);

    List<ProductImg> queryProductImgList(long productId);
}
