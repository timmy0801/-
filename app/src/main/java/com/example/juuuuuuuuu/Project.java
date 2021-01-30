package com.example.juuuuuuuuu;

import java.util.List;

public class Project {
    private  String name;
    private  String user;
    private List<String> friends;
    private  String project_id;
    private  String description;
    public String getProject_id() {
        return project_id;
    }

    public void setProject_id(String project_id) {
        this.project_id = project_id;
    }

    public Project(String name, String user, List<String> friends, String project_id, String description) {
        this.name = name;
        this.user = user;
        this.friends = friends;
        this.project_id = project_id;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public List<String> getFriends() {
        return friends;
    }

    public void setFriends(List<String> friends) {
        this.friends = friends;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public  Project(){

    }
}
