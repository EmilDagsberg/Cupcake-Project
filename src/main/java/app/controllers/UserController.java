package app.controllers;

import app.entities.*;
import app.exceptions.DatabaseException;
import app.persistence.*;
import io.javalin.Javalin;
import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;
import java.util.List;

public class UserController {

    // Routes for main
    public static void addRoutes(Javalin app, ConnectionPool connectionPool)    {
        app.post("login", ctx -> login(ctx, connectionPool));
        app.get("login", ctx -> ctx.render("login.html"));
        app.get("logout", ctx -> logout(ctx));
        app.get("createuser", ctx -> ctx.render("createuser.html"));
        app.post("createuser", ctx -> createUser(ctx, connectionPool));
        app.get("profile", ctx -> {
            orderHistory(ctx, connectionPool);
        });
        app.post("updateMail", ctx -> updateMail(ctx, connectionPool));
        app.post("updatePassword", ctx -> updatePassword(ctx, connectionPool));
        app.post("addMoney", ctx -> updateAmount(ctx, connectionPool));
        app.get("/orderDetails", ctx -> orderDetails(ctx, connectionPool));
        app.get("index", ctx -> ctx.render("index.html"));
        app.get("admin", ctx -> getAllUsers(ctx, connectionPool));




    }

    // Updates current sessionUser money amount
    private static void updateAmount(Context ctx, ConnectionPool connectionPool) {
        int amount = Integer.parseInt(ctx.formParam("amount"));

        if (amount < 0) {
            ctx.attribute("message", "Sorry, but you can't add a negative number to your account");
        } else {

            User user = ctx.sessionAttribute("currentUser");

            try {
                UserMapper.updateAmount(user.getAmount() + amount, user.getMail(), connectionPool);
                ctx.attribute("message", "Dit beløb er blevet opdateret");
                user.setAmount(user.getAmount() + amount);
                ctx.render("profile.html");

            } catch (DatabaseException e) {
                ctx.attribute("message", e.getMessage());
                ctx.render("profile.html");
            }


        }



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
            ctx.attribute("message", mail);
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

    private static void createUser(Context ctx, ConnectionPool connectionPool) {
        String mail = ctx.formParam("mail");
        String password1 = ctx.formParam("password1");
        String password2 = ctx.formParam("password2");

        if (password1.equals(password2))    {
            try {
                UserMapper.createUser(mail, password1, connectionPool);
                ctx.attribute("message", "Konto oprettet: " + mail + ". Venligst login");
                ctx.render("login.html");
            } catch (DatabaseException e)   {
                ctx.attribute("message", "Passwords matcher ikke, eller konto eksistere allerede, prøv igen");
                ctx.render("createuser.html");
            }
        }
    }

    private static void updateMail(Context ctx, ConnectionPool connectionPool)  {
        String mail = ctx.formParam("mail");
        User user = ctx.sessionAttribute("currentUser");

        if(user != null) {
            try {
                UserMapper.updateMail(mail, user.getUserID(), connectionPool);

                user.setMail(mail);
                ctx.sessionAttribute("currentUser", user);

                ctx.attribute("message", "Mail er ændret");
                ctx.render("profile.html");


            } catch (DatabaseException e) {
                ctx.attribute("message", e.getMessage());
                ctx.render("index.html");
            }
        }
    }

    private static void updatePassword(Context ctx, ConnectionPool connectionPool)  {
        String password = ctx.formParam("password");
        User user = ctx.sessionAttribute("currentUser");

        if(user != null) {
            try {
                UserMapper.updatePassword(password, user.getUserID(), connectionPool);

                user.setPassword(password);
                ctx.sessionAttribute("currentUser", user);

                ctx.attribute("message", "password er ændret");
                ctx.render("profile.html");


            } catch (DatabaseException e) {
                ctx.attribute("message", e.getMessage());
                ctx.render("index.html");
            }
        }
    }

    public static void getAllUsers(Context ctx, ConnectionPool connectionPool){
        try {
            List<User> users = UserMapper.getAllUsers(connectionPool);
            List<OrderHistory> orders = OrderHistoryMapper.getAllOrders(connectionPool);

            ctx.attribute("users", users);
            ctx.attribute("orders", orders);
            ctx.render("admin.html");
        } catch (DatabaseException e) {
            ctx.attribute(e.getMessage());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    // Gets a list of the order history
    public static void orderHistory(Context ctx, ConnectionPool connectionPool) {
        User user = ctx.sessionAttribute("currentUser");
        if (user != null) {
            try {
                List<OrderHistory> orders = OrderHistoryMapper.getAllOrderHistoryByMail(user.getMail(), connectionPool);
                user.setOrders(orders);
                ctx.sessionAttribute("currentUser", user);


                ctx.render("profile.html");
            } catch (DatabaseException e) {
                ctx.attribute("message", "Error loading orders.");
                ctx.render("profile.html");
            }
        } else {
            ctx.redirect("login");
        }
    }

    // Gets a list of the order details
    public static void orderDetails(Context ctx, ConnectionPool connectionPool) throws SQLException {
        int orderId = Integer.parseInt(ctx.queryParam("orderId"));
        List<OrderDetails> details = OrderDetailsMapper.getOrderDetailsFromUser(orderId, connectionPool);

        for(OrderDetails detail : details) {
            CupcakeBot bot = CupcakeBotMapper.getNameFromBotID(detail.getBotID(), connectionPool);
            CupcakeTop top = CupcakeTopMapper.getNameFromTopID(detail.getTopID(), connectionPool);

            detail.setBotName(bot.getBottom());
            detail.setTopName(top.getTopping());


        }

        ctx.attribute("orderDetails", details);
        ctx.render("orderDetails.html");
    }
}
