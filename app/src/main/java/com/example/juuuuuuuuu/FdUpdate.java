package com.example.juuuuuuuuu;

import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class FdUpdate {
    private String  friend_img,cost_pic;
    private String post_name,tag_friends,note,cost,postid;
    private ArrayList<String> arrayList;
    public  FdUpdate(){

    }


    public ArrayList<String> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<String> arrayList) {
        this.arrayList = arrayList;
    }

    public FdUpdate(String friend_img, String cost_pic, String post_name, ArrayList<String> arrayList, String note, String cost, String postid){
        this.friend_img=friend_img;
        this.cost_pic=cost_pic;
        this.post_name=post_name;
       // this.tag_friends=tag_friends;
        this.note=note;
        this.cost=cost;
        this.postid=postid;
        this.arrayList=arrayList;
    }

    public String getFriend_img() {
        return friend_img;
    }

    public void setFriend_img(String friend_img) {
        this.friend_img = friend_img;
    }

    public String getCost_pic() {
        return cost_pic;
    }

    public void setCost_pic(String cost_pic) {
        this.cost_pic = cost_pic;
    }

    public String getPost_name() {
        return post_name;
    }

    public void setPost_name(String post_name) {
        this.post_name = post_name;
    }



    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getCost() {
        return cost;
    }

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }

}
