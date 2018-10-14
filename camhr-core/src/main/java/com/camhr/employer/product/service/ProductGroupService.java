package com.camhr.employer.product.service;

import com.camhr.employer.product.builder.ProductGroupQueryBuilder;
import com.camhr.employer.product.entity.ProductGroup;
import we.util.Page;

/**
 * <pre>
 * 服务包
 *    发布职位时，要选一个服务包。
 *    数据存在 P_SERVICE、P_SERVICE_GROUP、P_SERVICE_ITEM 表
 *
 * Created by Allen on 2018/9/27.
 * </pre>
 */
public interface ProductGroupService {

  /**
   * 查询服务包
   */
  Page<ProductGroup> queryProductGroups(ProductGroupQueryBuilder queryBuilder);

}
