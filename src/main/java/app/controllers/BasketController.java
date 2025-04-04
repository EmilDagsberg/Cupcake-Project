package app.controllers;


import app.entities.CupcakeBot;
import app.entities.CupcakeTop;
import app.entities.OrderDetails;
import app.entities.User;
import app.exceptions.DatabaseException;
import app.persistence.*;
import io.javalin.Javalin;
import io.javalin.http.Context;
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

    // Makes a list of Cupcakes consisting of Maps for Topping and Bottom
    public void showBasket(Context ctx) throws SQLException {
        List<OrderDetails> orders = ctx.sessionAttribute("orders");
        Map<Integer, String> bottomNames = new HashMap<>();
        Map<Integer, String> toppingNames = new HashMap<>();

        totalPrice = orders != null ? orders.stream().mapToDouble(OrderDetails::getTotalPrice).sum() : 0.00;

        for (OrderDetails order : orders) {
            if(!bottomNames.containsKey(order.getBotID())) {
                CupcakeBot cakeBot = CupcakeBotMapper.getNameFromBotID(order.getBotID(), connectionPool);
                bottomNames.put(order.getBotID(), cakeBot.getBottom());
            }
            if(!toppingNames.containsKey(order.getTopID())) {
                CupcakeTop cakeTop = CupcakeTopMapper.getNameFromTopID(order.getTopID(), connectionPool);
                toppingNames.put(order.getTopID(), cakeTop.getTopping());
            }
        }

        
        ctx.render("basket.html", Map.of("orders", orders, "bottomNames", bottomNames, "toppingNames", toppingNames, "totalPrice", totalPrice));
    }

    public void handlePayment(Context ctx) throws SQLException, DatabaseException {
        User user = ctx.sessionAttribute("currentUser");

        int userID = user.getUserID();

        double userAmount = UserMapper.getAmountFromUserId(userID, connectionPool);

        if(userAmount < totalPrice) {
            ctx.attribute("message", "Undskyld, men det ligner du ikke har nok på din konto til denne ordre.");
            ctx.render("/basket");
        }
        else
        {
            removeAmount(ctx, userAmount);

            OrderHistoryMapper.addOrderHistory(userID, connectionPool);

            int order_id = OrderHistoryMapper.getOrderHistoryID(userID, connectionPool);

            List<OrderDetails> orders = ctx.sessionAttribute("orders");

            for (OrderDetails order : orders) {
                OrderDetailsMapper.addOrderDetails(order_id, order.getTopID(), order.getBotID(), order.getQuantity(), order.getTotalPrice(), connectionPool);
            }


            boolean paid = true;

            ctx.attribute("paymentStatus", paid);

            ctx.render("/basket");
        }
    }

    public void removeAmount(Context ctx, double userAmount) throws DatabaseException {
        User user = ctx.sessionAttribute("currentUser");

        userAmount = userAmount - totalPrice;

        UserMapper.updateAmount(userAmount, user.getMail(),  connectionPool);
        user.setAmount(userAmount);
    }

    // Removes a cupcake from the basket
    public void removeItem(Context ctx){
        int botID = Integer.parseInt(ctx.formParam("botID"));
        int topID = Integer.parseInt(ctx.formParam("topID"));

        List<OrderDetails> orders = ctx.sessionAttribute("orders");

        if (orders != null){
            orders.removeIf(order -> order.getBotID() == botID && order.getTopID() == topID);
            ctx.sessionAttribute("orders", orders);
        }
        ctx.redirect("/basket");
    }
}
