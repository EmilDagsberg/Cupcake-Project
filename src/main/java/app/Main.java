package app;

import app.config.SessionConfig;
import app.config.ThymeleafConfig;
import app.controllers.BasketController;
import app.controllers.CupcakeController;
import app.controllers.UserController;
import app.persistence.ConnectionPool;
import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinThymeleaf;

import java.util.logging.Logger;

public class Main {

    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";
    private static final String URL = "jdbc:postgresql://localhost:5432/%s?currentSchema=public";
    private static final String DB = "Cupcake";

    private static final ConnectionPool connectionPool = ConnectionPool.getInstance(USER, PASSWORD, URL, DB);

    private static final CupcakeController cupcakeController = new CupcakeController(connectionPool);
    private static final BasketController basketController = new BasketController(connectionPool);


    public static void main(String[] args)
    {

        // Initializing Javalin and Jetty webserver

        Javalin app = Javalin.create(config -> {
            config.staticFiles.add("/public");
            config.jetty.modifyServletContextHandler(handler ->  handler.setSessionHandler(SessionConfig.sessionConfig()));
            config.fileRenderer(new JavalinThymeleaf(ThymeleafConfig.templateEngine()));
        }).start(7070);

        // Routing

        UserController.addRoutes(app, connectionPool);


        app.get("/", ctx -> ctx.render("index.html"));
        app.get("/order", ctx -> ctx.render("order.html"));
        app.post("/order", ctx -> cupcakeController.handleOrder(ctx));
        app.get("/basket", ctx -> basketController.showBasket(ctx));
        app.post("/basket/remove", basketController::removeItem);
        app.post("/basket", ctx -> basketController.handlePayment(ctx));

    }

}