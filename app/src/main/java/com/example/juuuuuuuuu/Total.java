package com.example.juuuuuuuuu;

public class Total {
    private String cost1;
    private String type1;
    private String date1;
    public Total(String cost1,String type1,String date1){
        this.cost1=cost1;
        this.type1=type1;
        this.date1=date1;
    }
    public Total(){

    }
    public String getCost1(){
        return cost1;
    }
    public String getType1(){
        return type1;
    }
    public void setCost1(String cost1){
        this.cost1=cost1;
    }
    public void setType1(String type1){
        this.type1=type1;
    }

    public String getDate1(){
        return date1;
    }
    public void setDate1(String date1){
        this.date1=date1;
    }
}