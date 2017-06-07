package com.vivareal.model;


import com.google.gson.annotations.SerializedName;
import org.hibernate.validator.constraints.Range;

import java.util.ArrayList;

/**
 * Created by Gustavo on 01/06/17.
 *
 */
public class BuildingVO {

    private long id;

    private String title;

    private long price;

    @Range(min=0,max=1400,message = "Value for X coordinate out of range.")
    @SerializedName("lat")
    private int x;

    @Range(min=0,max=1000,message = "Value for Y coordinate out of range.")
    @SerializedName("long")
    private int y;

    @Range(min=1,max=5,message = "Value for beds out of range.")
    private int beds;

    @Range(min=1,max=4,message = "Value for baths out of range.")
    private int baths;

    @Range(min=20,max=240,message = "Value for square meters out of range.")
    private long squareMeters;

    public BuildingVO() {}

    public BuildingVO(String title, long price, int x, int y, int beds, int baths, long squareMeters) {
        this.title = title;
        this.price = price;
        this.x = x;
        this.y = y;
        this.beds = beds;
        this.baths = baths;
        this.squareMeters = squareMeters;
    }

    private ArrayList<String> provinces;

    public ArrayList<String> getProvinces() {
        return provinces;
    }

    public void setProvinces(ArrayList<String> provinces) {
        this.provinces = provinces;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getBeds() {
        return beds;
    }

    public void setBeds(int beds) {
        this.beds = beds;
    }

    public int getBaths() {
        return baths;
    }

    public void setBaths(int baths) {
        this.baths = baths;
    }

    public long getSquareMeters() {
        return squareMeters;
    }

    public void setSquareMeters(long squareMeters) {
        this.squareMeters = squareMeters;
    }
}
