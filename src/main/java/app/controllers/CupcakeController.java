package app.controllers;

import app.entities.CupcakeBot;
import app.entities.CupcakeTop;
import app.entities.OrderDetails;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.CupcakeBotMapper;
import app.persistence.CupcakeTopMapper;
import io.javalin.http.Context;

public class CupcakeController {
    private static ConnectionPool connectionPool;

    public CupcakeController(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }



public void handleOrder(Context ctx) throws DatabaseException {
    String bottom = ctx.formParam("bottom");
    String topping = ctx.formParam("topping");
    int quantity = Integer.parseInt(ctx.formParam("quantity"));

    CupcakeBot cakeBot = CupcakeBotMapper.getCupcakeBot(bottom, connectionPool);
    CupcakeTop cakeTop = CupcakeTopMapper.getCupcakeTop(topping, connectionPool);

    double totalPrice = ((cakeBot.getPrice() + cakeTop.getPrice()) * quantity);

    OrderDetails orderDetails = new OrderDetails(cakeTop.getTopID(), cakeBot.getBotID(), quantity, totalPrice);


    List<OrderDetails> orders = ctx.sessionAttribute("orders");
    if (orders == null) {
        orders = new ArrayList<>();
    }
    // Add the new order to the existing orders list
    orders.add(orderDetails);

    // Store the updated orders list back in the session
    ctx.sessionAttribute("orders", orders);



    ctx.render("order.html");

}


}
