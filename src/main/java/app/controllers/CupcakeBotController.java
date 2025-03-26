package app.controllers;

import app.entities.CupcakeBot;
import app.persistence.CupcakeBotMapper;
import app.entities.CupcakeTop;
import io.javalin.http.Context;

public class CupcakeBotController {
    CupcakeBotMapper cupcakeBotMapper;

    public void getCupcakeBottom(Context ctx) {
        try {
            String bottom = ctx.queryParam("bottom");
            double price = Double.parseDouble(ctx.queryParam("price"));
            int bottom_id = Integer.parseInt(ctx.queryParam("bottom_id"));

            CupcakeBot cupcakeBottom = cupcakeBotMapper.getCupcakeBot(bottom, price, bottom_id);

            if (cupcakeBottom != null) {
                ctx.result("Bottom: " + cupcakeBottom.getBottom() +
                        ", Price: " + cupcakeBottom.getPrice() +
                        ", Bottom ID: " + cupcakeBottom.getBotID());
            } else {
                ctx.status(404).result("Cupcake bottom not found");
            }
        } catch (Exception e) {
            ctx.status(400).result("Invalid request parameters: " + e.getMessage());
        }
    }

    public void buyCupcakeBottom(Context ctx) {
        try {
            String bottom = ctx.formParam("bottom");
            double expectedPrice = Double.parseDouble(ctx.formParam("price"));
            int bottom_id = Integer.parseInt(ctx.formParam("bottom_id"));

            boolean success = cupcakeBotMapper.buyCupcakeBot(bottom, expectedPrice, bottom_id);

            if (success) {
                ctx.result("Purchase successful! You bought a " + bottom + " for " + expectedPrice);
            } else {
                ctx.status(400).result("Purchase failed: Invalid price or item not found.");
            }
        } catch (Exception e) {
            ctx.status(400).result("Error processing purchase: " + e.getMessage());
        }
    }
}