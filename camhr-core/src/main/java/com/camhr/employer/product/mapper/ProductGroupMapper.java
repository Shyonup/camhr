package com.camhr.employer.product.mapper;

import com.camhr.employer.product.builder.ProductGroupQueryBuilder;
import com.camhr.employer.product.entity.ProductGroup;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by Allen on 2018/9/27.
 */
@Mapper
public interface ProductGroupMapper {


  List<ProductGroup> queryProductGroups(ProductGroupQueryBuilder queryBuilder);

  long countProductGroups(ProductGroupQueryBuilder queryBuilder);

}