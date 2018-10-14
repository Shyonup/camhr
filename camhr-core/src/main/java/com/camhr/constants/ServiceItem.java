package com.camhr.constants;

import com.google.common.collect.Lists;
import java.util.List;

/**
 * <pre>
 * 常量，对应 B_ITEM 表
 *
 * Created by Allen on 2018/9/30.
 * </pre>
 */
public class ServiceItem {

  // ============================== publish job service type ==============================

  public static final int BASIC_JOB = 2;

  public static final int URGENT_JOB = 3;

  public static final int FREE_BASIC_JOB = 5;

  public static final int FREE_URGENT_JOB = 6;

  public static final int STANDARD_JOB = 12;

  public static final int TOP_JOB = 13;

  public static List<Integer> itemIdAboutJob() {
    List<Integer> itemIds = Lists.newArrayList();
    itemIds.add(BASIC_JOB);
    itemIds.add(URGENT_JOB);
    itemIds.add(FREE_BASIC_JOB);
    itemIds.add(FREE_URGENT_JOB);
    itemIds.add(STANDARD_JOB);
    return itemIds;
  }

  /**
   * 与工作有关的ItemId，这个给SQL使用的
   * id之间用逗号分隔
   */
  public static String itemIdsAboutPublishJobForSQL() {
    StringBuilder stringBuilder = new StringBuilder();
    List<Integer> itemIds = itemIdAboutJob();
    itemIds.remove(new Integer(TOP_JOB)); // remove参数一定要Integer，不能是int。int的话就会以为是index
    int size = itemIds.size();
    for (int i = 0; i < size; i++) {
      Integer itemId = itemIds.get(i);
      stringBuilder.append(itemId);
      if (i < size - 1) {
        stringBuilder.append(",");
      }
    }
    return stringBuilder.toString();
  }
}
