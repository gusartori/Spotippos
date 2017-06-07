package com.vivareal.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Created by Gustavo on 01/06/17.
 */
public class ProvinceVO {

    private int upperLeftX;
    private int upperLeftY;
    private int bottomRightX;
    private int bottomRightY;
    private String name;

    public ProvinceVO(int upperLeftX, int upperLeftY, int bottomRightX, int bottomRightY, String name) {
        this.upperLeftX = upperLeftX;
        this.upperLeftY = upperLeftY;
        this.bottomRightX = bottomRightX;
        this.bottomRightY = bottomRightY;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @JsonIgnore
    public int getUpperLeftX() {
        return upperLeftX;
    }

    @JsonIgnore
    public int getUpperLeftY() {
        return upperLeftY;
    }

    @JsonIgnore
    public int getBottomRightX() {
        return bottomRightX;
    }

    @JsonIgnore
    public int getBottomRightY() {
        return bottomRightY;
    }
}
