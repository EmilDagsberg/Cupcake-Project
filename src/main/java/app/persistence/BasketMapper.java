package app.persistence;

import app.entities.CupcakeBot;
import app.entities.CupcakeTop;

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

}
