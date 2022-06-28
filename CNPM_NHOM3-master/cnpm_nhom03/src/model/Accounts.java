package model;

public class Accounts {
    private int id;
    private String userName;
    private String password;
    private String email;
    private String address;
    private String numberPhone;
    private int active;

    public Accounts(int id, String userName, String password, String email, String numberPhone, String address, int active) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.numberPhone = numberPhone;
        this.address = address;
        this.password = password;
        this.active = active;
    }

    public Accounts() {
    }

    public Accounts(String userName, String email, String numberPhone, String address, String password, int active) {
        this.userName = userName;
        this.email = email;
        this.numberPhone = numberPhone;
        this.address = address;
        this.password = password;
        this.active = active;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNumberPhone() {
        return numberPhone;
    }

    public void setNumberPhone(String numberPhone) {
        this.numberPhone = numberPhone;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }
}
