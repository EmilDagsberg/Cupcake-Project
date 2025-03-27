package app.entities;

public class User {
    int userID;
    String mail;
    String password;
    double amount;
    boolean role;

    public User(int userID, String mail, String password, double amount, boolean role) {
        this.userID = userID;
        this.mail = mail;
        this.password = password;
        this.amount = amount;
        this.role = role;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public boolean isRole() {
        return role;
    }

    public void setRole(boolean role) {
        this.role = role;
    }
}
