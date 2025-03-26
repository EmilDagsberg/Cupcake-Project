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


    public CupcakeBot getCupcakeBot(String bottom) {
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

    public boolean buyCupcakeBot(String bottom, double price, int bottom_id) {
        String sql = "SELECT price FROM cupcake_bottom WHERE bottom=? AND bottom_id=?";

        try(Connection conn = connectionPool.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setString(1, bottom);
            stmt.setInt(2, bottom_id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()){
                double actualPrice = rs.getDouble("price");

                if(actualPrice == price){
                    System.out.println("Purchase completed: " + bottom + " Price: " + price + " Bot ID: " + bottom_id);
                    return true;
                }else {
                    return false;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);

        }return false;
    }
}
