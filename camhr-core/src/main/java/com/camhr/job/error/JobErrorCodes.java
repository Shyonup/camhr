package com.camhr.job.error;

/**
 * Created by Allen on 2018/9/14.
 */
public class JobErrorCodes {

  public static final String JOB_NOT_EXIST = "{error.job.not.exist}";

  public static final String CLOSE_A_UNPUBLISHED_JOB = "{error.close.a.unpublished.job}";

  /**
   * 发布时间太长了，没法通过后台数据校验
   */
  public static final String PUBLISHED_DAYS_IS_TOO_LONG = "{error.published.days.is.too.long}";

  public static final String JOB_HAD_PUBLISHED = "{error.job.had.published}";

  public static final String INTERVIEW_STATUS_IS_NOT_EXIST = "{error.interview.status.is.not.exist}";


  public static final String EMPLOYER_STATUS_IS_NOT_OK = "{error.employer.status.is.not.ok}";

  public static final String EMPLOYER_PRODUCT_ITEM_NOT_FOUNT = "{error.employer.product.item.not.fount}";

  /**
   * productId（service）不是与发布职位相关的
   */
  public static final String PRODUCT_IS_NOT_ABOUT_PUBLISHED_JOB = "{error.product.is.not.about.published.job}";

  /**
   * productId（service）不是与置顶职位相关的
   */
  public static final String PRODUCT_IS_NOT_ABOUT_TOP_JOB = "{error.product.is.not.about.top.job}";

  /**
   * 企业购买的服务包，余额不足
   */
  public static final String AMOUNT_OF_PRODUCT_ITEM_IS_NOT_ENOUGH = "{error.amount.of.product.item.is.not.enough}";


  public static final String JOB_APPLY_STATUS_IS_NOT_EXIST = "{error.job.apply.status.is.not.exist}";

  /**
   * 职位尚未发布，置顶的职位必须处于发布状态
   */
  public static final String TOB_JOB_AND_JOB_IS_UNPUBLISHED = "{error.tob.job.and.job.is.unpublished}";

  /**
   * 紧急的职位无需置顶
   */
  public static final String TOB_JOB_AND_JOB_IS_URGENT = "{error.tob.job.and.job.is.urgent}";

  public static final String JOB_IS_TOP = "{error.job.is.top}";
}
