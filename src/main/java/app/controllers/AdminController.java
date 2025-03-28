package app.controllers;

import app.entities.OrderDetails;
import app.entities.OrderHistory;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.OrderDetailsMapper;
import app.persistence.OrderHistoryMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.ArrayList;
import java.util.List;

public class AdminController {

    public static void addRoutes(Javalin app, ConnectionPool connectionPool)    {
        app.get("/admin", ctx -> UserController.getAllUsers(ctx, connectionPool));
    }

    public static void getOrders(Context ctx, ConnectionPool connectionPool) throws DatabaseException {
        String mail = ctx.formParam("mail");

        List<OrderHistory> orderHistoryList = OrderHistoryMapper.getAllOrderHistoryByMail(mail, connectionPool);

        List<OrderDetails> orderDetailsList = new ArrayList<>();
        for (OrderHistory orderHistory : orderHistoryList) {
            orderDetailsList.add(OrderDetailsMapper.getOrderDetailsFromUser(orderHistory.getOrderID(), connectionPool));
        }

    }
}


// users: mail -> user_id;   order_history: user-id -> order_id;   order_details: order_id