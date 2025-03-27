package app.entities;

import java.time.LocalDateTime;

public class OrderHistory {
    int orderID;
    int userID;
    LocalDateTime date;

    public OrderHistory(int orderID ,int userID){
        this.orderID = orderID;
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
