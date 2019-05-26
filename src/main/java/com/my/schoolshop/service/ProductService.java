package com.my.schoolshop.service;

import com.my.schoolshop.dto.ProductExecution;
import com.my.schoolshop.exceptions.ProductOperationException;

public interface ProductService {
    ProductExecution addProduct() throws ProductOperationException;
}
