package com.camhr.employer.product.service.impl;

import com.camhr.employer.product.builder.ProductFindBuilder;
import com.camhr.employer.product.builder.ProductGroupQueryBuilder;
import com.camhr.employer.product.constants.ProductStatus;
import com.camhr.employer.product.entity.Product;
import com.camhr.employer.product.entity.ProductGroup;
import com.camhr.employer.product.mapper.ProductGroupMapper;
import com.camhr.employer.product.service.ProductGroupService;
import com.camhr.employer.product.service.ProductService;
import com.camhr.job.constants.JobLanguage;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import we.util.Page;

/**
 * Created by Allen on 2018/9/27.
 */
@Service
public class ProductGroupServiceImpl implements ProductGroupService {

  @Autowired
  private ProductGroupMapper productGroupMapper;

  @Autowired
  private ProductService productService;

  @Override
  public Page<ProductGroup> queryProductGroups(ProductGroupQueryBuilder queryBuilder) {
    Page<ProductGroup> page = Page.of(queryBuilder);

    List<ProductGroup> productGroups = productGroupMapper.queryProductGroups(queryBuilder);
    fetchProducts(productGroups, queryBuilder.getLanguage()); // 获取服务包下面所有的服务

    page.setResult(productGroups);
    page.setTotalCount(productGroupMapper.countProductGroups(queryBuilder));
    return page;
  }

  /**
   * 获取服务包下面 所有的服务
   * @param language 哪种语言，后台从Controller层获取
   */
  private void fetchProducts(List<ProductGroup> productGroups, JobLanguage language) {
    if (productGroups == null || productGroups.isEmpty()) {
      return;
    }

    // 抽取ProductGroupId
    Set<Integer> productGroupIds = Sets.newHashSet();
    for (ProductGroup productGroup : productGroups) {
      productGroupIds.add(productGroup.getId());
    }

    // 根据ProductGroupId 向数据库查询Product信息
    ProductFindBuilder productFindBuilder = new ProductFindBuilder();
    productFindBuilder.setStatus(ProductStatus.notDelete());
    productFindBuilder.setLanguage(language);
    productFindBuilder.setGroupIds(productGroupIds);
    List<Product> productList = productService.findProducts(productFindBuilder);

    // 把productList 中的数据，塞到ProductGroup里面
    Map<Integer, List<Product>> productMap = toMapGroupByProductGroupId(productList);
    for (ProductGroup productGroup : productGroups) {
      List<Product> products = productMap.get(productGroup.getId());
      if (products != null) {
        productGroup.setProducts(products);
      }
    }
  }

  /**
   * Key为ProductGroupId
   */
  private Map<Integer, List<Product>> toMapGroupByProductGroupId(List<Product> products) {
    Map<Integer, List<Product>> map = Maps.newHashMap();
    for (Product product : products) {
      List<Product> tmpProductList = map.get(product.getGroupId());
      if (tmpProductList == null) {
        tmpProductList = Lists.newArrayList();
        map.put(product.getGroupId(), tmpProductList);
      }
      tmpProductList.add(product);
    }
    return map;
  }
}
