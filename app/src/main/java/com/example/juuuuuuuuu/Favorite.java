package com.example.juuuuuuuuu;

public class Favorite {
    private String money;
    private String friendimg;
    private String type;
    private String imageurl;
    private String uname;
    private int num;
    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }


    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getFriendimg() {
        return friendimg;
    }

    public void setFriendimg(String friendimg) {
        this.friendimg = friendimg;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public  Favorite(String money, String friendimg, String type, String imageurl, String uname,int num){
        this.money=money;
        this.friendimg=friendimg;
        this.type=type;
        this.imageurl=imageurl;
        this.uname=uname;
        this.num=num;
    }
    public  Favorite(){}

}
