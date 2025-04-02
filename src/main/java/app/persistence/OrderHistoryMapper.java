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

    public static void addOrderHistory(int user_id, ConnectionPool connectionPool) throws SQLException {
        String sql = "INSERT INTO order_history (user_id, date) VALUES (?, ?)";

        try (Connection conn = connectionPool.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, (user_id));
            stmt.setDate(2, new java.sql.Date(new java.util.Date().getTime()));

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int getOrderHistoryID(int user_id, ConnectionPool connectionPool) throws SQLException {
        String sql = "SELECT * FROM order_history WHERE user_id = ? ORDER BY order_id DESC LIMIT 1";

        try (Connection conn = connectionPool.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, (user_id));
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("order_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static List<OrderHistory> getAllOrders(ConnectionPool connectionPool) throws SQLException {
        List<OrderHistory> orderHistoryList = new ArrayList<>();
        String sql = "SELECT order_id, user_id, date FROM order_history";

        try (Connection conn = connectionPool.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()){

            while (rs.next()) {
                orderHistoryList.add(new OrderHistory(
                        rs.getInt("order_id"),
                        rs.getInt("user_id"),
                        rs.getTimestamp("date").toLocalDateTime()
                ));
            }
            return orderHistoryList;
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

