package com.example.juuuuuuuuu;

import java.util.ArrayList;
import java.util.List;

public class Post {
    private  String cost;
    private  String cost_pic;
    private  String cost_type;
    private  String date;
    private  String originalcost;
    private  String postid;
    private  String project;
    private  String type;
    private  String typeMom;
    private  String user;
    private  String location;
    private  String other;
    private List<String> friends;

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public List<String> getFriends() {
        return friends;
    }

    public void setFriends(List<String> friends) {
        this.friends = friends;
    }





    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getCost_pic() {
        return cost_pic;
    }

    public void setCost_pic(String cost_pic) {
        this.cost_pic = cost_pic;
    }

    public String getCost_type() {
        return cost_type;
    }

    public void setCost_type(String cost_type) {
        this.cost_type = cost_type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getOriginalcost() {
        return originalcost;
    }

    public void setOriginalcost(String originalcost) {
        this.originalcost = originalcost;
    }

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeMom() {
        return typeMom;
    }

    public void setTypeMom(String typeMom) {
        this.typeMom = typeMom;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Post(String cost, String cost_pic, String cost_type, String date, String originalcost, String postid, String project, String type, String typeMom, String user, String location, String other, List<String> friends) {
        this.cost = cost;
        this.cost_pic = cost_pic;
        this.cost_type = cost_type;
        this.date = date;
        this.originalcost = originalcost;
        this.postid = postid;
        this.project = project;
        this.type = type;
        this.typeMom = typeMom;
        this.user = user;
        this.location = location;
        this.other = other;
        this.friends = friends;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public  Post(){

    }

}

