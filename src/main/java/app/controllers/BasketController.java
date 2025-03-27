package app.controllers;


import app.entities.OrderDetails;
import app.persistence.ConnectionPool;
import io.javalin.http.Context;

import java.util.List;
import java.util.Map;

public class BasketController {
    ConnectionPool connectionPool;

    public BasketController(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    public void showBasket(Context ctx) {
        List<OrderDetails> orders = ctx.sessionAttribute("orders");
        boolean paymentStatus = ctx.sessionAttribute("paymentStatus") != null ? ctx.sessionAttribute("paymentStatus") : false;

        double totalPrice = orders != null ? orders.stream().mapToDouble(OrderDetails::getTotalPrice).sum() : 0.00;

        
        ctx.render("basket.html", Map.of("orders", orders, "totalPrice", totalPrice, "paymentStatus", paymentStatus));
    }
}
