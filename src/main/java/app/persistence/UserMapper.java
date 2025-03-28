package app.persistence;

import app.entities.User;

import app.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper {
    public static User login(String mail, String password, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "select * from users where mail=? and password=?";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setString(1, mail);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("user_id");
                double amount = rs.getDouble("amount");
                boolean role = rs.getBoolean("role");
                return new User(id, mail, password, amount,role);
            } else {
                throw new DatabaseException("Fejl i login. Prøv igen");
            }
        } catch (SQLException e) {
            throw new DatabaseException("DB fejl", e.getMessage());
        }
    }

    public static void createUser(String mail, String password, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "insert into users (mail, password) values (?,?)";

        try(    Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        )
        {
            ps.setString(1, mail);
            ps.setString(2, password);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected != 1)   {
                throw new DatabaseException("Fejl ved oprettelse af ny bruger");
            }

        } catch (SQLException e)  {
            String msg = "Der er sket en fejl. Prøv igen";
            if (e.getMessage().startsWith("ERROR: duplicate key value "))   {
                msg = "Mail findes allerede.";
            }
            throw new DatabaseException(msg, e.getMessage());
        }
    }

    public static void updateMail(String mail, int userId, ConnectionPool connectionPool) throws DatabaseException {
        String sql ="update users set mail = ? where user_id = ?";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        )
        {
            ps.setString(1, mail);
            ps.setInt(2, userId);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected != 1)
            {
                throw new DatabaseException("Fejl i opdatering af mail");
            }
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Fejl i opdatering mail", e.getMessage());
        }
    }

    public static void updatePassword(String password, int userId, ConnectionPool connectionPool) throws DatabaseException {
        String sql ="update users set password = ? where user_id = ?";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        )
        {
            ps.setString(1, password);
            ps.setInt(2, userId);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected != 1)
            {
                throw new DatabaseException("Fejl i opdatering af password");
            }
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Fejl i opdatering password", e.getMessage());
        }
    }
}
