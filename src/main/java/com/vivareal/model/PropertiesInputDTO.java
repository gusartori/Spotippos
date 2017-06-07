package com.vivareal.model;

import java.util.ArrayList;

/**
 * Created by Gustavo on 02/06/17.
 */
public class PropertiesInputDTO {
    private int totalProperties;
    private ArrayList<BuildingVO> properties;

    public PropertiesInputDTO() {
    }

    public int getTotalProperties() {
        return totalProperties;
    }

    public void setTotalProperties(int totalProperties) {
        this.totalProperties = totalProperties;
    }

    public ArrayList<BuildingVO> getProperties() {
        return properties;
    }

    public void setProperties(ArrayList<BuildingVO> properties) {
        this.properties = properties;
    }
}
