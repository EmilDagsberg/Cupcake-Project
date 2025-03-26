package app.controllers;

import app.persistence.CupcakeBotMapper;
import app.persistence.CupcakeTopMapper;

public class CupcakeController {
    private final CupcakeBotMapper cupcakeBotMapper;
    private final CupcakeTopMapper cupcakeTopMapper;

    public CupcakeController(CupcakeBotMapper cupcakeBotMapper, CupcakeTopMapper cupcakeTopMapper) {
        this.cupcakeBotMapper = cupcakeBotMapper;
        this.cupcakeTopMapper = cupcakeTopMapper;
    }


}
