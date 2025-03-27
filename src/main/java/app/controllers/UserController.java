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
        app.get("login", ctx -> ctx.render("login.html"));
        app.get("logout", ctx -> logout(ctx));
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
            ctx.attribute("message", "Velkommen: " + mail);
            ctx.render("index.html");

        }catch (DatabaseException e){
            // hvis nej, send tilbage til login med fejl besked
            ctx.attribute("message", e.getMessage());
            ctx.render("login.html");
        }
    }

    public static void logout(Context ctx)  {
        ctx.req().getSession().invalidate();
        ctx.redirect("/");
    }

    private static void createAccount(Context ctx, ConnectionPool connectionPool) {
        String mail = ctx.formParam("mail");
        String password1 = ctx.formParam("password1");
        String password2 = ctx.formParam("password2");

        if (password1.equals(password2))    {
            try {
                UserMapper.createAccount(mail, password1, connectionPool);
                ctx.attribute("message", "Konto oprettet: " + mail + ". Venligst login");
                ctx.render("login.html");
            } catch (DatabaseException e)   {
                ctx.attribute("message", "Passwords matcher ikke. prøv igen");
                ctx.render("createuser.html");
            }
        }
    }


}
