package com.camhr.job.utils;

import static org.aspectj.bridge.Version.getTime;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Allen on 2018/6/7.
 */
public class TimeUtil {

  public static Date getNowDate() {
    return Calendar.getInstance().getTime();
  }

  /**
   * <pre>
   * 以此类推：
   *    increment 填 -1 表示获取昨天的时间
   *    increment 填 0  表示获取今天的时间
   *    increment 填 1  表示获取明天的时间
   * </pre>
   */
  public static Date getDate(int increment) {
    Calendar calendar = Calendar.getInstance();
    calendar.add(Calendar.DATE, increment);
    return calendar.getTime();
  }

  /**
   * 计算年龄
   */
  public static int countAges(Date birthday) {
    if (birthday == null) {
      return 0;
    }

    LocalDate birthDayLocalDate = birthday.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    return new Long(ChronoUnit.YEARS.between(birthDayLocalDate, LocalDate.now())).intValue();
  }

  /**
   * 通过年龄计算生日
   */
  public static Date countBirthday(int age) {
    Calendar calendar = Calendar.getInstance();
    calendar.add(Calendar.YEAR, 0 - age);
    return calendar.getTime();
  }

  /**
   * <pre>
   * 以此类推：
   *    increment 填 -1 表示获取昨天0晨0点的时间
   *    increment 填 0  表示获取今天0晨0点的时间
   *    increment 填 1  表示获取明天0晨0点的时间
   * </pre>
   */
  public static Date getMidnightDate(int increment) {
    Calendar c = Calendar.getInstance();
    c.add(Calendar.DATE, increment);
    c.set(Calendar.HOUR_OF_DAY, 0);
    c.set(Calendar.MINUTE, 0);
    c.set(Calendar.SECOND, 0);
    c.set(Calendar.MILLISECOND, 0);
    return c.getTime();
  }

  /**
   * 计算还有多少天过期
   */
  public static int countExpiredDays(Date expiredDate) {
    LocalDate expiredLocalDate = expiredDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    return new Long(ChronoUnit.DAYS.between(LocalDate.now(), expiredLocalDate)).intValue();
  }

}
