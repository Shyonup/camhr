package com.camhr.job.config;

/**
 * <pre>
 * 旧的项目，camhr项目src目录下，有个conf.properties文件，里面配置的很多全局常量，
 * 这里，把上面这个文件配置的部分常量给抽取过来
 *
 * Created by Allen on 2018/9/13.
 * </pre>
 */
public class JobConfiguration {

  /**
   * 最长发布时长
   */
  public static final int JOB_FREE_DISPLAY_DAYS = 30;

  /**
   * 预发布职位。发布职位，可以定义多少天 后才自动发布。
   * 但最大值不能超过下面这个值
   */
  public static final int JOB_PUBLISH_PRE_DAY = 30;

  /**
   * publish job need pay
   */
  public static final boolean JOB_PUBLISH_PAY = true;

  /**
   * featured publish job need pay
   */
  public static final boolean JOB_FEATURED_PAY = false;
}
