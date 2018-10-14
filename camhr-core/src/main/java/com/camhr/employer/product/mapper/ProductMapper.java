package com.camhr.employer.product.mapper;

import com.camhr.employer.product.builder.ProductFindBuilder;
import com.camhr.employer.product.entity.Product;
import com.camhr.employer.product.entity.ProductItem;
import java.util.List;
import java.util.Set;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Created by Allen on 2018/9/27.
 */
@Mapper
public interface ProductMapper {

  List<Product> findProducts(ProductFindBuilder productFindBuilder);

  List<ProductItem> findProductItemsByProductIds(@Param("productIds") Set<Integer> productIds);

  Product getProduct(@Param("productId") int productId);
}