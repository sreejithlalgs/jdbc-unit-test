package com.lal.examples.jdbc.test;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest(JDBCExample.class)
public class JDBCExampleTest {
  private JDBCExample example;

  @Before
  public void setUp() throws Exception {
    PowerMockito.mockStatic(DriverManager.class);
    PreparedStatement pstmt = PowerMockito.mock(PreparedStatement.class);
    ResultSet rs = PowerMockito.mock(ResultSet.class);
    Connection connection = PowerMockito.mock(Connection.class);
    when(DriverManager.getConnection(anyString(), anyString(), anyString())).thenReturn(connection);
    when(connection.prepareStatement(anyString())).thenReturn(pstmt);
    when(pstmt.executeQuery()).thenReturn(rs);
    when(rs.next()).thenReturn(true).thenReturn(true).thenReturn(false);
    when(rs.getString("some-col")).thenReturn("abc").thenReturn("bcd");
    example = JDBCExample.getInstance();
  }

  @Test
  public void testMock() {
    try {
      List<String> result = example.getColumnValues("some-col");
      Assert.assertEquals(2, result.size());
      Assert.assertEquals("abc", result.get(0));
      Assert.assertEquals("bcd", result.get(1));
    } catch (SQLException e) {
      e.printStackTrace();
      Assert.assertTrue(false);
    }
  }
}
