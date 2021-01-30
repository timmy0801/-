package com.example.juuuuuuuuu;

public class Cost2 {
    private String cost;
    private String type;
    public Cost2(String cost,String type){
        this.cost=cost;
        this.type=type;
    }
    public Cost2(){

    }
    public String getCost(){
        return cost;
    }
    public String getType(){
        return type;
    }
    public void setCost(String cost){
        this.cost=cost;
    }
    public void setType(String type){
        this.type=type;
    }
}