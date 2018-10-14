package com.camhr.employer.mapper.handler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import we.lang.JSON;

@MappedJdbcTypes({JdbcType.VARCHAR})
@MappedTypes({List.class})
public class ListToStringHandler extends BaseTypeHandler<List<String>> {

  @Override
  public void setNonNullParameter(PreparedStatement preparedStatement, int i, List<String> list,
      JdbcType jdbcType) throws SQLException {
    preparedStatement.setString(i, JSON.stringify(list));
  }

  @Override
  public List<String> getNullableResult(ResultSet resultSet, String s) throws SQLException {
    String str = resultSet.getString(s);
    if (str != null) {
      return JSON.toList(str, String.class);
    } else {
      return Collections.EMPTY_LIST;
    }
  }

  @Override
  public List<String> getNullableResult(ResultSet resultSet, int i) throws SQLException {
    String str = resultSet.getString(i);
    if (str != null) {
      return JSON.toList(str, String.class);
    } else {
      return Collections.EMPTY_LIST;
    }
  }

  @Override
  public List<String> getNullableResult(CallableStatement callableStatement, int i)
      throws SQLException {
    String str = callableStatement.getString(i);
    if (str != null) {
      return JSON.toList(str, String.class);
    } else {
      return Collections.EMPTY_LIST;
    }
  }
}
