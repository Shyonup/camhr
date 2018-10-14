package com.camhr.employer.product.service.impl;

import com.camhr.employer.product.builder.ProductFindBuilder;
import com.camhr.employer.product.entity.Product;
import com.camhr.employer.product.entity.ProductItem;
import com.camhr.employer.product.mapper.ProductMapper;
import com.camhr.employer.product.service.ProductService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Allen on 2018/9/28.
 */
@Service
public class ProductServiceImpl implements ProductService {

  @Autowired
  private ProductMapper productMapper;

  @Override
  public List<Product> findProducts(ProductFindBuilder productFindBuilder) {
    List<Product> products = productMapper.findProducts(productFindBuilder);
    fetchProductItems(products); // 给所有的Product 获取 List<ProductItem> 数据
    return products;
  }

  @Override
  public Product getProduct(int productId) {
    Product product = productMapper.getProduct(productId);
    if (product != null) {
      fetchProductItems(Lists.newArrayList(product));
    }
    return product;
  }

  /**
   * 获取Product对象里面的 List<ProductItem> 数据
   */
  private void fetchProductItems(List<Product> productList) {
    if (productList.isEmpty()) {
      return;
    }

    // 抽取ProductIds
    Set<Integer> productIds = Sets.newHashSet();
    for (Product product : productList) {
      productIds.add(product.getId());
    }

    // 向数据库 获取 productItem数据
    List<ProductItem> productItems = productMapper.findProductItemsByProductIds(productIds);

    // 把productItem数据塞到Products
    Map<Integer, List<ProductItem>> productItemMap = toMapGroupByProductId(productItems);
    for (Product product : productList) {
      List<ProductItem> tmpProductItemList = productItemMap.get(product.getId());
      if (tmpProductItemList != null) {
        product.setProductItems(tmpProductItemList);
      }
    }
  }

  /**
   * List集合转Map集合，方便快速检索List集合里面的数据
   * Key 为 ProductId
   */
  private Map<Integer, List<ProductItem>> toMapGroupByProductId(List<ProductItem> productItemList) {
    Map<Integer, List<ProductItem>> map = Maps.newHashMap();
    for (ProductItem productItem : productItemList) {
      List<ProductItem> tmpProductItems = map
          .get(productItem.getServiceId());// serviceId就是ProductId，就是P_SERVICE表的主键
      if (tmpProductItems == null) {
        tmpProductItems = Lists.newArrayList();
        map.put(productItem.getServiceId(), tmpProductItems);
      }
      tmpProductItems.add(productItem);
    }
    return map;
  }
}
