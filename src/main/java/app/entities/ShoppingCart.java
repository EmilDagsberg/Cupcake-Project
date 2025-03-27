package app.entities;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
    List<OrderDetails> cartList = new ArrayList<>();

    public ShoppingCart(List<OrderDetails> cartList){
        this.cartList = cartList;
    }
    public List<OrderDetails> getCartList() {
        return cartList;
    }
    public void setCartList(List<OrderDetails> cartList) {
        this.cartList = cartList;
    }
}
