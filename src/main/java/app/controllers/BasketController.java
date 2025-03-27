package app.controllers;


import app.entities.CupcakeBot;
import app.entities.CupcakeTop;
import app.entities.OrderDetails;
import app.entities.User;
import app.persistence.BasketMapper;
import app.persistence.ConnectionPool;
import app.persistence.CupcakeBotMapper;
import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BasketController {
    ConnectionPool connectionPool;
    double totalPrice;

    public BasketController(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    public void showBasket(Context ctx) throws SQLException {
        List<OrderDetails> orders = ctx.sessionAttribute("orders");
        Map<Integer, String> bottomNames = new HashMap<>();
        Map<Integer, String> toppingNames = new HashMap<>();

        totalPrice = orders != null ? orders.stream().mapToDouble(OrderDetails::getTotalPrice).sum() : 0.00;

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

    public void handlePayment(Context ctx) throws SQLException {
        User user = ctx.sessionAttribute("currentUser");

        int userID = user.getUserID();

        double userAmount = BasketMapper.getAmountFromUserId(userID, connectionPool);

        if(userAmount < totalPrice) {
            ctx.attribute("message", "Undskyld, men det ligner du ikke har nok på din konto til denne ordre.");
            ctx.render("/basket");
        }
        else
        {
            removeAmount(ctx, userAmount);

            boolean paid = true;

            ctx.attribute("paymentStatus", paid);

            ctx.render("/basket");
        }









    }

    public void removeAmount(Context ctx, double userAmount) throws SQLException {
        User user = ctx.sessionAttribute("currentUser");

        userAmount = userAmount - totalPrice;

        BasketMapper.setAmountFromUserId(user.getUserID(), userAmount, connectionPool);
    }
}
