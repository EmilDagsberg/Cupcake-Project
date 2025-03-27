package app.entities;

public class CupcakeBot {
    int botID;
    String bottom;
    double price;

    public CupcakeBot(int botID, String bottom, double price) {
        this.botID = botID;
        this.bottom = bottom;
        this.price = price;
    }

    public int getBotID() {
        return botID;
    }

    public void setBotID(int botID) {
        this.botID = botID;
    }

    public String getBottom() {
        return bottom;
    }

    public void setBottom(String bottom) {
        this.bottom = bottom;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
