package com.camhr.employer.product.service.impl;

import com.camhr.constants.ServiceItem;
import com.camhr.employer.account.constants.TradeType;
import com.camhr.employer.account.entity.AccountTrade;
import com.camhr.employer.account.service.AccountService;
import com.camhr.employer.product.builder.ProductItemFindBuilder;
import com.camhr.employer.product.builder.ProductItemQueryBuilder;
import com.camhr.employer.product.constants.EmployerProductItemStatus;
import com.camhr.employer.product.constants.ProductStatus;
import com.camhr.employer.product.entity.EmployerBuyProduct;
import com.camhr.employer.product.entity.EmployerProduct;
import com.camhr.employer.product.entity.EmployerProductItem;
import com.camhr.employer.product.entity.EmployerProductItemTR;
import com.camhr.employer.product.entity.Product;
import com.camhr.employer.product.entity.ProductItem;
import com.camhr.employer.product.error.ProductErrorCode;
import com.camhr.employer.product.mapper.EmployerProductMapper;
import com.camhr.employer.product.service.EmployerProductService;
import com.camhr.employer.product.service.ProductService;
import com.camhr.employer.service.EmployerService;
import com.camhr.job.config.JobConfiguration;
import com.camhr.job.constants.JobLanguage;
import com.camhr.job.error.JobErrorCodes;
import com.camhr.job.service.JobService;
import com.camhr.job.utils.TimeUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import we.lang.Exceptions;
import we.util.Page;

/**
 * Created by Allen on 2018/10/9.
 */
@Service
public class EmployerProductServiceImpl implements EmployerProductService {

  @Autowired
  private ProductService productService;

  @Autowired
  private EmployerProductMapper employerProductMapper;

  @Autowired
  private AccountService accountService; // 帐号 扣费

  @Autowired
  private EmployerService employerService;

  @Override
  public Page<EmployerProductItem> queryEmployerProductItems(ProductItemQueryBuilder queryBuilder) {
    Page<EmployerProductItem> page = Page.of(queryBuilder);
    page.setResult(employerProductMapper.queryEmployerProductItems(queryBuilder));
    page.setTotalCount(employerProductMapper.countEmployerProductItems(queryBuilder));

    // 旧代码：如果帐号免费发布的话，还强行塞一个值
    if (employerService.isPublishJobFree(queryBuilder.getEmployerId())) {
      EmployerProductItem freePublishProductItem = new EmployerProductItem();
      freePublishProductItem.setId(EmployerProductItem.FREE_PUBLISH_PRODUCT_ITEM_ID);
      freePublishProductItem.setAmount(1); // 数量随便给的，旧版代码逻辑的给的null，我们用的int类型，所以用1
      freePublishProductItem.setDisplay(JobConfiguration.JOB_FREE_DISPLAY_DAYS);
      freePublishProductItem.setItemId(ServiceItem.FREE_BASIC_JOB);
      page.getResult().add(0, freePublishProductItem);
    }
    return page;
  }

  @Override
  public EmployerProductItem getEmployerProductItem(long employerProductItemId) {
    return employerProductMapper.getEmployerProductItem(employerProductItemId);
  }

  @Override
  @Transactional
  public int useProductItem(long employerId, long objectId, long employerProductId, int amount) {
    /* 数据校验 */
    EmployerProductItem serviceItemFromDB = getEmployerProductItem(employerProductId);
    // 这个employer没有购买服务
    if (serviceItemFromDB == null
        || serviceItemFromDB.getEmployerId() != employerId
        || serviceItemFromDB.getStatus() == EmployerProductItemStatus.REMOVED.value()) {
      Exceptions.of(JobService.class).notFound(JobErrorCodes.EMPLOYER_PRODUCT_ITEM_NOT_FOUNT);
    }
    // 余额不足
    int realAmount = serviceItemFromDB.getAmount();
    if (amount > realAmount) {
      Exceptions.of(JobService.class).notFound(JobErrorCodes.AMOUNT_OF_PRODUCT_ITEM_IS_NOT_ENOUGH);
    }

    /* 扣费操作 */
    // 扣费
    int row = employerProductMapper.useProductItem(employerProductId, amount);
    if (row > 0) {
      // 扣费成功后，在 E_SERVICE_ITEM_TR 表 插入一条历史使用记录
      EmployerProductItemTR employerProductItemTR = new EmployerProductItemTR();
      employerProductItemTR.setEmployerId(employerId);
      employerProductItemTR.setObjectid(objectId);
      employerProductItemTR.setServiceItemId(serviceItemFromDB.getId());
      employerProductItemTR.setItemId(serviceItemFromDB.getItemId());
      employerProductItemTR.setAmount(amount);
      employerProductItemTR.setBalance(realAmount - amount);
      employerProductItemTR.setCdate(TimeUtil.getNowDate());
      employerProductItemTR.setStartDate(TimeUtil.getNowDate());
      employerProductItemTR.setEndDate(TimeUtil.getDate(amount * serviceItemFromDB.getDisplay()));

      employerProductMapper.addEmployerProductItemTR(employerProductItemTR);
    }
    return row;
  }

  @Override
  @Transactional
  public int employerBuyProduct(EmployerBuyProduct employerBuyProduct) {
    // 从数据库读取数据
    long employerId = employerBuyProduct.getEmployerId();
    int productId = employerBuyProduct.getProductId();
    int number = employerBuyProduct.getQuantity();
    String cardNo = employerBuyProduct.getCardNo();
    Product productFromDB = productService.getProduct(productId);

    /* 数据校验 */
    // 校验外键数据
    if (productFromDB == null) {
      Exceptions.of(EmployerProductService.class).notFound(ProductErrorCode.PRODUCT_NOT_FOUND);
    }
    // 产品是否已经过期
    boolean productIsExpired = productFromDB.getEndDate() != null
        && productFromDB.getEndDate().before(TimeUtil.getNowDate());
    boolean productIsRemoved = productFromDB.getStatus() == ProductStatus.REMOVED.value().intValue();
    if (productIsExpired || productIsRemoved) {
      Exceptions.of(EmployerProductService.class).notFound(ProductErrorCode.PRODUCT_IS_NOT_AVAILABLE);
    }

    /* 业务逻辑 */
    int row = 0;
    long billNumber = employerProductMapper.generateBillNumber(); // 生成一个唯一的账单编号
    // boolean payIsSuccessful = chargeAccount(employerBuyProduct,  productFromDB, billNumber);
    boolean payIsSuccessful = true; // TODO 临时代码 ：现在充值接口没做好，先不扣费。上面才是正常的代码↑↑↑
    if (payIsSuccessful) { // 扣费成功
      // Step_1 获取 数据：企业购买服务包成功后，要保存的数据
      List<EmployerProductItem> employerProductItemList = Lists.newArrayList();
      for (ProductItem productItem : productFromDB.getProductItems()) {
        EmployerProductItem employerProductItem = new EmployerProductItem();
        employerProductItem.setpBillno(billNumber);
        employerProductItem.setEmployerId(employerId); // 公司Id
        employerProductItem.setpServiceId(productId); // 产品Id
        employerProductItem.setItemId(productItem.getItemId());  // 常量 B_ITEM 表的主键
        employerProductItem.setAmount(number * productItem.getAmount()); // 购买的数量
        employerProductItem.setExpdate(TimeUtil.getMidnightDate(productItem.getExpdate())); /*
          a) employerProductItem.expdate是 过期时间，时间点，date类型
          b) productItem.getExpdate() 表示有效天数，expdate这个名称误导人。按道理应该是expiredDays
          既然旧的数据库表这样写，我也不改了。
        */
        employerProductItem.setDisplay(productItem.getDisplay()); // 允许最大的上线时间
        employerProductItem.setStatus(EmployerProductItemStatus.OK.value());
        employerProductItemList.add(employerProductItem);
      }

      // Step_2 把数据保存进数据库
      row = addEmployerProductItems(employerProductItemList);
    }

    return row;
  }

  /**
   * 购买服务扣费
   */
  private boolean chargeAccount(EmployerBuyProduct employerBuyProduct, Product product, long billNumber) {
    BigDecimal amount = new BigDecimal("-1") // 扣费，负数
        .multiply(product.getPrice()) // 价格
        .multiply(new BigDecimal(new Integer(employerBuyProduct.getQuantity()).toString())); // 数量

    AccountTrade accountTrade = new AccountTrade();// 扣费参数
    accountTrade.setBillno(new Integer(product.getId()).toString()); // productId
    accountTrade.setDescription(new Long(billNumber).toString());  // 交易编号
    accountTrade.setCardno(employerBuyProduct.getCardNo()); // 礼品卡
    accountTrade.setAmount(amount); // 要扣多少钱
    accountTrade.setType(TradeType.PURCHASE_PRODUCTS.value()); // 交易类型
    accountTrade.setEmployerId(employerBuyProduct.getEmployerId()); // 哪个公司

    return accountService.addAccountTrade(accountTrade) > 0;
  }

  @Override
  @Transactional
  public int addEmployerProductItems(List<EmployerProductItem> employerProductItemList) {
    if (employerProductItemList == null || employerProductItemList.isEmpty()) {
      return 0;
    }

    return employerProductMapper.addEmployerProductItems(employerProductItemList);
  }

  @Override
  public Page<EmployerProduct> queryEmployerProducts(ProductItemQueryBuilder queryBuilder) {
    queryBuilder.setQueryEmployerProduct(true);

    Page<EmployerProduct> page = Page.of(queryBuilder);
    List<EmployerProduct> employerProducts = employerProductMapper.queryProducts(queryBuilder);
    fetchEmployerProductItems(employerProducts, queryBuilder.getLanguage()); // 查询Product下面的ProductItemList信息
    page.setResult(employerProducts);
    page.setTotalCount(employerProductMapper.countProducts(queryBuilder));

    return page;
  }

  /**
   * 查询 EmployerProduct下面的 EmployerProductItemList信息
   */
  private void fetchEmployerProductItems(List<EmployerProduct> employerProducts,
      JobLanguage language) {
    if (employerProducts == null || employerProducts.isEmpty()) {
      return;
    }

    List<Long> billNos = Lists.newArrayList();

    for (EmployerProduct employerProduct : employerProducts) {
      billNos.add(employerProduct.getpBillno());
    }

    ProductItemFindBuilder findBuilder = new ProductItemFindBuilder();
    findBuilder.setBillNos(billNos);
    findBuilder.setLanguage(language);
    List<EmployerProductItem> employerProductItemList = employerProductMapper
        .findEmployerProductItemsByBillNos(findBuilder); // 根据账单编号查询，该账单下面所购买的服务项
    Map<Long, List<EmployerProductItem>> employerProductItemMap = toMap(employerProductItemList);

    // 把数据库查出来的数据，塞到employerProduct里面
    for (EmployerProduct employerProduct : employerProducts) {
      List<EmployerProductItem> employerProductItems = employerProductItemMap
          .get(employerProduct.getpBillno());
      employerProduct.setEmployerProductItems(
          employerProductItems == null ? Collections.EMPTY_LIST : employerProductItems
      );
    }
  }

  /**
   * key 为 billNo
   */
  private Map<Long, List<EmployerProductItem>> toMap(
      List<EmployerProductItem> employerProductItemList) {
    Map<Long, List<EmployerProductItem>> employerProductItemMap = Maps.newHashMap();
    for (EmployerProductItem employerProductItem : employerProductItemList) {
      List<EmployerProductItem> tmpProductItems = employerProductItemMap
          .get(employerProductItem.getpBillno());
      if (tmpProductItems == null) {
        tmpProductItems = Lists.newArrayList();
        employerProductItemMap.put(employerProductItem.getpBillno(), tmpProductItems);
      }
      tmpProductItems.add(employerProductItem);
    }
    return employerProductItemMap;
  }

}