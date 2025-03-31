package app.persistence;

import app.entities.CupcakeBot;
import app.entities.CupcakeTop;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CupcakeBotMapper {
    private final ConnectionPool connectionPool;

    public CupcakeBotMapper(ConnectionPool connectionPool) {this.connectionPool = connectionPool;}


    public static CupcakeBot getCupcakeBot(String bottom, ConnectionPool connectionPool) {
        String sql = "select * from cupcake_bottom where bottom=?";

        try (Connection conn = connectionPool.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {


            stmt.setString(1, (bottom));
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
        return null; // Cupcake bottom not found

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
