package com.camhr.employer.product.mapper;

import com.camhr.employer.product.builder.ProductItemFindBuilder;
import com.camhr.employer.product.builder.ProductItemQueryBuilder;
import com.camhr.employer.product.entity.EmployerProduct;
import com.camhr.employer.product.entity.EmployerProductItem;
import com.camhr.employer.product.entity.EmployerProductItemTR;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Created by Allen on 2018/9/27.
 */
@Mapper
public interface EmployerProductMapper {

  int addEmployerProductItems(@Param("employerProductItemList") List<EmployerProductItem> employerProductItemList);

  /**
   * 生成账单编号，相当于IdWorker自动生成Id
   */
  long generateBillNumber();

  List<EmployerProduct> queryProducts(ProductItemQueryBuilder queryBuilder);

  long countProducts(ProductItemQueryBuilder queryBuilder);

  EmployerProductItem getEmployerProductItem(@Param("employerProductItemId") long employerProductItemId);

  List<EmployerProductItem> queryEmployerProductItems(ProductItemQueryBuilder queryBuilder);

  long countEmployerProductItems(ProductItemQueryBuilder queryBuilder);

  int useProductItem(@Param("employerProductItemId") long employerProductItemId,
      @Param("amount") int amount);

  int addEmployerProductItemTR(EmployerProductItemTR employerProductItemTR);

  List<EmployerProductItem> findEmployerProductItemsByBillNos(
      ProductItemFindBuilder productItemFindBuilder);

}