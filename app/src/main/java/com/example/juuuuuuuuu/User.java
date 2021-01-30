package com.example.juuuuuuuuu;

public class User {
    private String account;
    private String Username;
    private String Password;
    private String Password_check;
    private String Ris_Username;
    private String Email;
    private String imageurl;
    public  User(){

    }
    public User(String account, String Username, String password, String ris_Username, String email,String imageurl) {
        this.account = account;
        this.Username = Username;
        this.Password = password;
        this.Ris_Username = ris_Username;
        this.Email = email;
        this.imageurl=imageurl;
    }



    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getPassword_check() {
        return Password_check;
    }

    public void setPassword_check(String password_check) {
        Password_check = password_check;
    }

    public String getRis_Username() {
        return Ris_Username;
    }

    public void setRis_Username(String ris_Username) {
        Ris_Username = ris_Username;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }
    public String getimageurl() {
        return imageurl;
    }

    public void setimageurl(String imageurl) {
        this.imageurl = imageurl;
    }

}
