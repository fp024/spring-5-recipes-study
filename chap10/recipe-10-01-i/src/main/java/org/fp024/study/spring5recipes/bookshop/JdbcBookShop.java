package org.fp024.study.spring5recipes.bookshop;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;
import lombok.Setter;

public class JdbcBookShop implements BookShop {
  @Setter private DataSource dataSource;

  @Override
  public void purchase(String isbn, String username) {
    Connection conn = null;
    try {
      conn = dataSource.getConnection();

      PreparedStatement stmt1 = conn.prepareStatement("SELECT PRICE FROM BOOK WHERE ISBN = ?");
      stmt1.setString(1, isbn);
      ResultSet rs = stmt1.executeQuery();
      rs.next();
      int price = rs.getInt("PRICE");
      stmt1.close();

      PreparedStatement stmt2 =
          conn.prepareStatement("UPDATE BOOK_STOCK SET STOCK = STOCK - 1 " + "WHERE ISBN = ?");
      stmt2.setString(1, isbn);
      stmt2.executeUpdate();
      stmt2.close();

      PreparedStatement stmt3 =
          conn.prepareStatement("UPDATE ACCOUNT SET BALANCE = BALANCE - ? " + "WHERE USERNAME = ?");
      stmt3.setInt(1, price);
      stmt3.setString(2, username);
      stmt3.executeUpdate();
      stmt3.close();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    } finally {
      if (conn != null) {
        try {
          conn.close();
        } catch (SQLException e) {
        }
      }
    }
  }

  /**
   * 재고 조회
   *
   * @param isbn 조회할 도서의 ISBN
   * @return 재고 수
   */
  @Override
  public int getStock(String isbn) {
    Connection conn = null;
    try {
      conn = dataSource.getConnection();

      PreparedStatement stmt1 =
          conn.prepareStatement("SELECT STOCK FROM BOOK_STOCK WHERE ISBN = ?");
      stmt1.setString(1, isbn);
      ResultSet rs = stmt1.executeQuery();
      rs.next();
      int stock = rs.getInt("STOCK");
      stmt1.close();
      return stock;
    } catch (SQLException e) {
      throw new RuntimeException(e);
    } finally {
      if (conn != null) {
        try {
          conn.close();
        } catch (SQLException e) {
        }
      }
    }
  }
}
