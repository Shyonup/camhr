package com.camhr.employer.product.service;

import com.camhr.employer.product.builder.ProductItemQueryBuilder;
import com.camhr.employer.product.entity.EmployerBuyProduct;
import com.camhr.employer.product.entity.EmployerProduct;
import com.camhr.employer.product.entity.EmployerProductItem;
import java.util.List;
import we.util.Page;

/**
 * 企业 - 服务包相关的操作
 *    比如：
 *       企业 购买服务包
 *       查询 自己购买的服务包
 *       使用 已购买的服务包
 *
 * Created by Allen on 2018/10/9.
 */
public interface EmployerProductService {

  Page<EmployerProductItem> queryEmployerProductItems(ProductItemQueryBuilder queryBuilder);

  EmployerProductItem getEmployerProductItem(long employerProductItemId);

  /**
   * 使用服务包（原始代码的方法叫useServiceItem）
   * @param employerId 哪个企业使用
   * @param objectId 外键、可以是jobId
   * @param employerProductId 企业购买的服务，外键
   * @param amount 使用的数量
   * @return 返回更新记录的行数
   */
  int useProductItem(long employerId, long objectId, long employerProductId, int amount);

  /**
   * 企业购买服务
   */
  int employerBuyProduct(EmployerBuyProduct employerBuyProduct);

  int addEmployerProductItems(List<EmployerProductItem> employerProductItemList);

  /**
   * 查询企业购买的服务包
   */
  Page<EmployerProduct> queryEmployerProducts(ProductItemQueryBuilder queryBuilder);
}
