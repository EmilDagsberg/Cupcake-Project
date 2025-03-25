package app.persistence;

import app.entities.OrderHistory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
}

