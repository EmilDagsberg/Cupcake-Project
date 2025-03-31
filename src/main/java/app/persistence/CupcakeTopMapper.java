package app.persistence;

import app.entities.CupcakeTop;
import app.entities.OrderHistory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CupcakeTopMapper {


    public static CupcakeTop getCupcakeTop(String topping, ConnectionPool connectionPool) {
        String sql = "select * from cupcake_top where topping=?";

        try (Connection conn = connectionPool.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {


            stmt.setString(1, (topping));
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
        return null; // Cupcake topping not found
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
}
