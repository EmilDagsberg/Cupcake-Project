package app.persistence;

import app.entities.OrderDetails;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailsMapper {

    public static OrderDetails getOrderDetailsFromUser(int order_id, ConnectionPool connectionPool) {
        List<OrderDetails> orderDetails = new ArrayList<>();
        String sql = "SELECT id, order_details.order_id, top, bottom, quantity, total_price FROM order_details " +
                "JOIN order_history ON order_details.order_id=order_history.order_id " +
                "WHERE order_details.order_id==?";

        try (Connection conn = connectionPool.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, order_id);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                orderDetails.add(new OrderDetails(
                        rs.getInt("order_id"),
                        rs.getInt("top"),
                        rs.getInt("bottom"),
                        rs.getInt("quantity"),
                        rs.getDouble("total_price"),
                        rs.getInt("id")));

            }
            return (OrderDetails) orderDetails;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
