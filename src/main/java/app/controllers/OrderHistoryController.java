package app.controllers;

import app.persistence.ConnectionPool;
import app.persistence.OrderHistoryMapper;
import io.javalin.http.Context;

public class OrderHistoryController {

private static final ConnectionPool connectionPool = ConnectionPool.getInstance(
        "postgres", "postgres", "jdbc:postgresql://localhost:5432/%s?currentSchema=public", "Cupcake"
);

private static final OrderHistoryMapper orderHistoryMapper = new OrderHistoryMapper(connectionPool);
private static OrderHistoryController controller = new OrderHistoryController();

public static void orderPage(Context ctx){
    ctx.render(""); // Insert HTML for page to see order here
}
}
