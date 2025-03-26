package app.persistence;

import app.entities.CupcakeTop;
import app.entities.OrderHistory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CupcakeTopMapper {

    private final ConnectionPool connectionPool;

    public CupcakeTopMapper(ConnectionPool connectionPool) {this.connectionPool = connectionPool;}


    public CupcakeTop getCupcakeTop(String topping) {
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

    public boolean buyCupcakeTop(String topping, double price, int top_id) {
        String sql = "SELECT price FROM cupcake_top WHERE topping=? AND top_id=?";

        try(Connection conn = connectionPool.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setString(1, topping);
            stmt.setInt(2, top_id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()){
                double actualPrice = rs.getDouble("price");

                if(actualPrice == price){
                    System.out.println("Purchase completed: " + topping + " Price: " + price + " Bot ID: " + top_id);
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
