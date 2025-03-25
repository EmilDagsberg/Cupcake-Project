package app.entities;

public class CupcakeTop {
    int topID;
    String topping;
    double price;

    public CupcakeTop(int topID, String topping, double price) {
        this.topID = topID;
        this.topping = topping;
        this.price = price;
    }

    public int getTopID() {
        return topID;
    }

    public void setTopID(int topID) {
        this.topID = topID;
    }

    public String getTopping() {
        return topping;
    }

    public void setTopping(String topping) {
        this.topping = topping;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
