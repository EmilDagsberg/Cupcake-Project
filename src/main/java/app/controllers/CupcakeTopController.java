package app.controllers;

import app.entities.CupcakeBot;
import app.entities.CupcakeTop;
import app.persistence.CupcakeTopMapper;
import io.javalin.http.Context;

public class CupcakeTopController {

    CupcakeTopMapper cupcakeTopMapper;

    public void getCupcakeTop(Context ctx) {
        try {
            String topping = ctx.queryParam("topping");
            double price = Double.parseDouble(ctx.queryParam("price"));
            int top_id = Integer.parseInt(ctx.queryParam("top_id"));

            CupcakeTop cupcakeTop = cupcakeTopMapper.getCupcakeTop(topping, price, top_id);

            if (cupcakeTop != null) {
                ctx.result("Topping: " + cupcakeTop.getTopping() +
                        ", Price: " + cupcakeTop.getPrice() +
                        ", Topping ID: " + cupcakeTop.getTopID());
            } else {
                ctx.status(404).result("Cupcake topping not found");
            }
        } catch (Exception e) {
            ctx.status(400).result("Invalid request parameters: " + e.getMessage());
        }
    }

    public void buyCupcakeTop(Context ctx) {
        try {
            String topping = ctx.formParam("topping");
            double expectedPrice = Double.parseDouble(ctx.formParam("price"));
            int top_id = Integer.parseInt(ctx.formParam("top_id"));

            boolean success = cupcakeTopMapper.buyCupcakeTop(topping, expectedPrice, top_id);

            if (success) {
                ctx.result("Purchase successful! You bought a " + topping + " for $" + expectedPrice);
            } else {
                ctx.status(400).result("Purchase failed: Invalid price or item not found.");
            }
        } catch (Exception e) {
            ctx.status(400).result("Error processing purchase: " + e.getMessage());
        }
    }
}
