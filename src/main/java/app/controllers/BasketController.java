package app.controllers;


import app.entities.CupcakeBot;
import app.entities.CupcakeTop;
import app.entities.OrderDetails;
import app.persistence.BasketMapper;
import app.persistence.ConnectionPool;
import app.persistence.CupcakeBotMapper;
import io.javalin.http.Context;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BasketController {
    ConnectionPool connectionPool;

    public BasketController(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    public void showBasket(Context ctx) throws SQLException {
        List<OrderDetails> orders = ctx.sessionAttribute("orders");
        Map<Integer, String> bottomNames = new HashMap<>();
        Map<Integer, String> toppingNames = new HashMap<>();

        double totalPrice = orders != null ? orders.stream().mapToDouble(OrderDetails::getTotalPrice).sum() : 0.00;

        for (OrderDetails order : orders) {
            if(!bottomNames.containsKey(order.getBotID())) {
                CupcakeBot cakeBot = BasketMapper.getNameFromBotID(order.getBotID(), connectionPool);
                bottomNames.put(order.getBotID(), cakeBot.getBottom());
            }
            if(!toppingNames.containsKey(order.getTopID())) {
                CupcakeTop cakeTop = BasketMapper.getNameFromTopID(order.getTopID(), connectionPool);
                toppingNames.put(order.getTopID(), cakeTop.getTopping());
            }
        }

        
        ctx.render("basket.html", Map.of("orders", orders, "bottomNames", bottomNames, "toppingNames", toppingNames, "totalPrice", totalPrice));
    }
}
