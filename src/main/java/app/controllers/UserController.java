package app.controllers;

import app.entities.User;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.UserMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.List;

public class UserController {

    public static void addRoutes(Javalin app, ConnectionPool connectionPool)    {
        app.post("login", ctx -> login(ctx, connectionPool));
    }


    public static void login(Context ctx, ConnectionPool connectionPool)    {
        // Hent form parametre
        String mail = ctx.formParam("mail");
        String password = ctx.formParam("password");

        // Check om bruger findes i databasen med det angivet mail og password
        try {
            User user = UserMapper.login(mail, password, connectionPool);
            ctx.sessionAttribute("currentUser", user);
            // Hvis ja, send videre til index.html
            ctx.render("index.html");

        }catch (DatabaseException e){
            // hvis nej, send tilbage til forside med fejl besked
            ctx.attribute("message", e.getMessage());
            ctx.render("index.html");
        }
    }

}
