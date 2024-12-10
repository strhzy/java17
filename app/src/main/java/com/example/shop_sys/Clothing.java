package com.example.shop_sys;

public class Clothing {
    private String name;
    private String description;
    private String picture;
    public Clothing(String name, String description, String picture) {
        this.name = name;
        this.description = description;
        this.picture = picture;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public void setPicture(String picture){
        this.picture = picture;
    }

    public String getName(){
        return name;
    }

    public String getDescription(){
        return description;
    }

    public String getPicture(){
        return picture;
    }
}
