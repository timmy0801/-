package com.example.juuuuuuuuu;

import java.util.Locale;

public class Share {
    private String people;
    private String category;
    private String money;
    private String friendnum;
    private String time;
    public Share(String people,String category,String money,String friendnum,String time){
        this.people=people;
        this.category=category;
        this.money=money;
        this.friendnum=friendnum;
        this.time=time;
    }
    public Share(){

    }
    public String getPeople(){
        return people;
    }
    public String getCategory(){
        return category;
    }
    public String getMoney(){return money;}
    public String getFriendnum(){return friendnum;}
    public String getTime(){return time;}
    public void setPeople(String people){
        this.people=people;
    }
    public void setCategory(String category){
        this.category=category;
    }
    public void setMoney(String money){this.money=money;}
    public void setFriendnum(String friendnum){this.friendnum=friendnum;}
    public void setTime(String time){this.time=time;}
}