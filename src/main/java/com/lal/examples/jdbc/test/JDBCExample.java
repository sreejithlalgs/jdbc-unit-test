package com.lal.examples.jdbc.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JDBCExample {

  private static Connection connection;
  private static JDBCExample INSTANCE;
  static {
    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
    } catch (ClassNotFoundException e) {
      throw new RuntimeException(e.getMessage(), e.getCause());
    }
  }

  private JDBCExample() throws SQLException {
    connection = DriverManager.getConnection("<con-str>", "<user>", "<pwd>");

  }

  public static JDBCExample getInstance() throws SQLException {
    if (INSTANCE == null) {
      synchronized (JDBCExample.class) {
        if (INSTANCE == null)
          INSTANCE = new JDBCExample();
      }

    }
    return INSTANCE;
  }


  public List<String> getColumnValues(String colName) throws SQLException {
    try (PreparedStatement pstmt = connection.prepareStatement("<qry>");
        ResultSet rs = pstmt.executeQuery()) {
      List<String> colValues = new ArrayList<>();
      while (rs.next()) {
        colValues.add(rs.getString(colName));
      }
      return colValues;
    }
  }

  @Override
  protected void finalize() throws Throwable {
    connection.close();
    super.finalize();

  }



}
