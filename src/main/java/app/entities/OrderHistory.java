package app.entities;

import java.time.LocalDateTime;

public class OrderHistory {
    int orderID;
    int userID;
    LocalDateTime date;

    public OrderHistory(int orderID ,int userID, LocalDateTime date) {
        this.orderID = orderID;
        this.userID = userID;
        this.date = date;
    }

    public OrderHistory(int orderId, int userID) {
        this.orderID = orderId;
        this.userID = userID;
    }


    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
