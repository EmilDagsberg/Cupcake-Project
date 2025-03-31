package app.controllers;

import app.entities.User;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.UserMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
class UserControllerTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }


    // Checks the database to see if expected result is = to something in the database
    @Test
    void login() throws DatabaseException {

        // Expected Result
        ConnectionPool connectionPool = ConnectionPool.getInstance(
                "postgres",
                "postgres",
                "jdbc:postgresql://localhost:5432/%s?currentSchema=public",
                "Cupcake");
        String mail = "admin@admin.dk";
        String password = "admin";

        // Actual Result
        User user = UserMapper.login(mail,password,connectionPool);

        assertNotNull(user);
        assertEquals(mail, user.getMail());
        assertEquals(password, user.getPassword());
    }


    // Checks through the user DB and checks if index 0 is taken. If it is taken that means it works.
    @Test
    void getAllUsers() throws DatabaseException {

        ConnectionPool connectionPool = ConnectionPool.getInstance(
                "postgres",
                "postgres",
                "jdbc:postgresql://localhost:5432/%s?currentSchema=public",
                "Cupcake");

        List<User> users = UserMapper.getAllUsers(connectionPool);

        assertNotNull(users, "User list is null, it shouldn't be null");
        assertFalse(users.isEmpty(), "User list is empty, it shouldn't be empty");

        User firstUser = users.get(0);
        assertNotNull(firstUser.getMail());
    }



}