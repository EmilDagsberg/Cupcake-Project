package app.entities;

import java.util.ArrayList;
import java.util.List;

public class Basket {
    List<OrderDetails> basketList = new ArrayList<>();

    public Basket(List<OrderDetails> cartList){
        this.basketList = cartList;
    }
    public List<OrderDetails> getBasketList() {
        return basketList;
    }
    public void setBasketList(List<OrderDetails> basketList) {
        this.basketList = basketList;
    }
}
