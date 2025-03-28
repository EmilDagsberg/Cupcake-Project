package app.persistence;

import app.entities.CupcakeBot;
import app.entities.CupcakeTop;
import app.entities.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BasketMapper {

    public BasketMapper(){

    }

    public static CupcakeTop getNameFromTopID(int top_id, ConnectionPool connectionPool) throws SQLException {
        String sql = "SELECT * FROM cupcake_top WHERE top_id = ?";

        try (Connection conn = connectionPool.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {


            stmt.setInt(1, (top_id));
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new CupcakeTop(
                        rs.getInt("top_id"),
                        rs.getString("topping"),
                        rs.getDouble("price"));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static CupcakeBot getNameFromBotID(int bot_id, ConnectionPool connectionPool) throws SQLException {
        String sql = "SELECT * FROM cupcake_bottom WHERE bottom_id = ?";

        try (Connection conn = connectionPool.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {


            stmt.setInt(1, (bot_id));
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new CupcakeBot(
                        rs.getInt("bottom_id"),
                        rs.getString("bottom"),
                        rs.getDouble("price"));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static double getAmountFromUserId(int user_id, ConnectionPool connectionPool) throws SQLException {
        String sql = "SELECT * FROM users WHERE user_id = ?";
        try (Connection conn = connectionPool.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, (user_id));
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getDouble("amount");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.00;
    }

    public static void setAmountFromUserId(int user_id, double amount, ConnectionPool connectionPool) throws SQLException {
        String sql = "UPDATE users SET amount = ? WHERE user_id = ?";
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(2, user_id);
            stmt.setDouble(1, amount);

            stmt.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
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

    public static void addOrderDetails(int order_id, int top, int bottom, int quantity, double totalPrice, ConnectionPool connectionPool) throws SQLException {
        String sql = "INSERT INTO order_details (order_id, top, bottom, quantity, total_price) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = connectionPool.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, order_id);
            stmt.setInt(2, top);
            stmt.setInt(3, bottom);
            stmt.setInt(4, quantity);
            stmt.setDouble(5, totalPrice);

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}


