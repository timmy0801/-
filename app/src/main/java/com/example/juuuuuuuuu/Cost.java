package com.example.juuuuuuuuu;

public class Cost {
    private String cost;
    private String type;
    private String typeMom;
    private String ininder;
    private String symbol;
    public Cost(String cost,String type,String typeMom,String ininder,String symbol){
        this.cost=cost;
        this.type=type;
        this.typeMom=typeMom;
        this.ininder=ininder;
        this.symbol=symbol;
    }
    public Cost(){

    }
    public String getCost(){
        return cost;
    }
    public String getType(){
        return type;
    }
    public String getTypeMom(){
        return typeMom;
    }
    public String getIninder(){
        return ininder;
    }
    public String getSymbol(){
        return symbol;
    }


    public void setCost(String cost){
        this.cost=cost;
    }
    public void setType(String type){
        this.type=type;
    }
    public void setTypeMom(String typeMom){
        this.typeMom=typeMom;
    }
    public void setIninder(String ininder){
        this.ininder=ininder;
    }
    public void setSymbol(String symbol){
        this.symbol=symbol;
    }
}