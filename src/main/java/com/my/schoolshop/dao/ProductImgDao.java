package com.my.schoolshop.dao;

import com.my.schoolshop.model.ProductImg;

import java.util.List;

public interface ProductImgDao {

    int batchInsertProductImg(List<ProductImg> productImgs);
}
