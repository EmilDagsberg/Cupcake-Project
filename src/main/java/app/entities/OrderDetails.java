package app.entities;

public class OrderDetails {
    int id;
    int orderID;
    int topID;
    int botID;
    int quantity;
    double totalPrice;

    public OrderDetails(int orderID, int topID, int botID, int quantity, double totalPrice, int id) {
        this.orderID = orderID;
        this.topID = topID;
        this.botID = botID;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.id = id;
    }

    public OrderDetails(int topID, int botID, int quantity, double totalPrice) {
        this.topID = topID;
        this.botID = botID;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getBotID() {
        return botID;
    }

    public void setBotID(int botID) {
        this.botID = botID;
    }

    public int getTopID() {
        return topID;
    }

    public void setTopID(int topID) {
        this.topID = topID;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
