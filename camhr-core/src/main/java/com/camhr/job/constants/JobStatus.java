package com.camhr.job.constants;


import com.google.common.collect.Lists;
import java.util.List;
import we.annotation.ConstantMapper;
import we.lang.Constant;
import we.lang.Constants;

/**
 * 为了方便信息扩展，代码中只定义需要进行业务判断的常量，也可以定义多个常量<br/> 未定义的常量可能从数据库加载
 */
@ConstantMapper
public enum JobStatus {
  DRAFT(Constants.of("JOB_STATUS", "draft", 0, "草稿")),
  PUBLISHED(Constants.of("JOB_STATUS", "published", 1, "发布")),
  CLOSED(Constants.of("JOB_STATUS", "closed", 2, "关闭")),
  REMOVED(Constants.of("JOB_STATUS", "removed", 9, "移除"));


  private final Constant<Integer> constant;

  JobStatus(Constant<Integer> constant) {
    this.constant = constant;
  }

  public Integer value() {
    return this.constant.getValue();
  }

  public static List<Integer> defaults() {
    List<Integer> list = Lists.newArrayList();

    JobStatus[] values = values();
    for (JobStatus constant : values) {
      if (constant != REMOVED) {
        list.add(constant.value());
      }
    }

    return list;
  }
}
