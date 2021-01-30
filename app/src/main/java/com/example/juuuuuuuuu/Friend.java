package com.example.juuuuuuuuu;

public class Friend {
    private String Username;
    private String imageurl;
    private String content;

    public Friend(){

    }
    public Friend(String Username, String imageurl, String content) {
        this.Username = Username;
        this.imageurl = imageurl;
        this.content = content;
    }

    public String getUsername() {
        return Username;
    }
    public void setUsername(String username) {
        Username = username;
    }
    public String getimageurl() {
        return imageurl;
    }
    public void setimageurl(String imageurl) {
        this.imageurl = imageurl;
    }
    public String getcontent() {
        return content;
    }

    public void setcontent(String imageurl) {
        this.content = content;
    }
}


