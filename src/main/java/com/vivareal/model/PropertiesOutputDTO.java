package com.vivareal.model;

import java.util.ArrayList;

/**
 * Created by Gustavo on 02/06/17.
 */
public class PropertiesOutputDTO {
    private int foundProperties;
    private ArrayList<BuildingVO> properties;

    public PropertiesOutputDTO() {
    }

    public int getFoundProperties() {
        return foundProperties;
    }

    public void setFoundProperties(int foundProperties) {
        this.foundProperties = foundProperties;
    }

    public ArrayList<BuildingVO> getProperties() {
        return properties;
    }

    public void setProperties(ArrayList<BuildingVO> properties) {
        this.properties = properties;
    }
}
