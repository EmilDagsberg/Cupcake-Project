package app.persistence;

import app.entities.OrderHistory;
import app.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderHistoryMapper {

    private final ConnectionPool connectionPool;

    public OrderHistoryMapper(ConnectionPool connectionPool) {  this.connectionPool = connectionPool; }


    public OrderHistory getOrderHistory(int orderID) {
        String sql = "select * from Order_history where order_id=?";

        try (Connection conn = connectionPool.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {


            stmt.setString(1, String.valueOf(orderID));
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                System.out.println("Order ID: " + orderID);
                return new OrderHistory(
                        rs.getInt("order_id"),
                        rs.getInt("user_id"));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Order not found
    }

    public static List<OrderHistory> getAllOrderHistoryByMail(String mail, ConnectionPool connectionPool) throws DatabaseException {
        List<OrderHistory> orderHistoryList = new ArrayList<>();
        String sql = "SELECT order_id, order_history.user_id, date FROM order_history JOIN users ON order_history.user_id=users.user_id WHERE mail=?";

        try (Connection conn = connectionPool.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, mail);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                orderHistoryList.add(new OrderHistory(
                        rs.getInt("order_id"),
                        rs.getInt("user_id"),
                        rs.getTimestamp("date").toLocalDateTime()));

            }
            return orderHistoryList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}

