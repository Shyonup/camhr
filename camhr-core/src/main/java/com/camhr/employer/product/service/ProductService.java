package com.camhr.employer.product.service;

import com.camhr.employer.product.builder.ProductFindBuilder;
import com.camhr.employer.product.entity.Product;
import java.util.List;

/**
 * Created by Allen on 2018/9/28.
 */
public interface ProductService {

  List<Product> findProducts(ProductFindBuilder productFindBuilder);

  Product getProduct(int productId);
}
