package com.example.juuuuuuuuu;

public class Inla {
    private String cost;
    private String num;
    private String date;
    private String gain;
    private String ass;
    public Inla(String cost,String num,String date ,String gain,String ass){
        this.cost=cost;
        this.num=num;
        this.date=date;
        this.gain=gain;
        this.ass=ass;
    }
    public Inla(){

    }
    public String getCost(){
        return cost;
    }
    public String getNum(){
        return num;
    }
    public String getDate(){
        return date;
    }
    public String getGain(){return gain;}
    public String getAss(){return ass;}


    public void setCost(String cost){
        this.cost=cost;
    }
    public void setNum(String num){
        this.num=num;
    }
    public void setDate(String date){
        this.date=date;
    }
    public void setGain(String gain){
        this.gain=gain;
    }
    public void setAss(String ass){
        this.ass=ass;
    }




}