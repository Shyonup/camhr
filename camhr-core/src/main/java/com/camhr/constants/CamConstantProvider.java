package com.camhr.constants;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.lang.Nullable;
import we.business.constant.JdbcConstantProvider;
import we.lang.ConstantSet;
import we.lang.Constants;

public class CamConstantProvider extends JdbcConstantProvider {

  private final static int OK = 0;
  private final static String[] tables = new String[]{
      "B_CARD_TYPE",
      "B_CATEGORY",
      "B_COMMENT",
      "B_DEPARTMENT",
      "B_EMPLOYER_SCALE",
      "B_ETYPE",
      "B_INDUSTRIAL",
      "B_ITEM",
      "B_JOB_LEVEL",
      "B_JOBTERM",
      "B_LANGUAGE",
      "B_LANGUAGE_LEVEL",
      "B_LOCATION",
      "B_MAJOR",
      "B_MARITAL",
      "B_OPERATION_TYPE",
      "B_ORGANIZATION_TYPE",
      "B_POSITION",
      "B_PUBLISH_TYPE",
      "B_QUALIFICATION",
      "B_SALARY",
      "B_SEX",
      "B_TRADE_TYPE",
      "B_TRAINING_FUNCTION",
      "B_TRAINING_MONTH",
      "B_TRAINING_PRICE",
      "B_WORK_STATUS"};


  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Override
  public void afterPropertiesSet() throws Exception {
    Constants.addLanguage(Locale.SIMPLIFIED_CHINESE);
    Constants.addLanguage(Locale.US);
    super.afterPropertiesSet();
    /*加载原始代码的常量值*/
    loadCamConstant();
  }


  private void loadCamConstant() {
    for (int i = 0; i < tables.length; i++) {
      String table = tables[i];
      String name = table.substring("B_".length());
      jdbcTemplate
          .query("select ID,NAME_EN,NAME_ZH,NAME_KH from " + table + " WHERE  status=  " + OK,
              new ResultSetExtractor() {

                @Nullable
                @Override
                public Object extractData(ResultSet rs)
                    throws SQLException, DataAccessException {
                  ConstantSet kh = Constants.of(name);
                  ConstantSet zh = Constants.of(Locale.SIMPLIFIED_CHINESE, name);
                  ConstantSet en = Constants.of(Locale.US, name);
                  while (rs.next()) {
                    try {
                      kh.add(name + "_" + rs.getString("ID"), rs.getInt("ID"),
                          rs.getString("NAME_KH"));
                      zh.add(name + "_" + rs.getString("ID"), rs.getInt("ID"),
                          rs.getString("NAME_ZH"));
                      en.add(name + "_" + rs.getString("ID"), rs.getInt("ID"),
                          rs.getString("NAME_EN"));
                    } catch (Exception e) {
                      // 数据库可能存在重复数据
                      //e.printStackTrace();
                    }
                  }
                  return null;
                }
              });

    }
  }
}
