package com.vivareal.model.repository;

import com.vivareal.model.BuildingVO;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Gustavo on 05/06/17.
 */
@Repository
public class ProvinceRepository {
    private HashMap<String, List<BuildingVO>> provincesBuildings = new HashMap<>();

    public HashMap<String, List<BuildingVO>> getProvincesBuildings() {
        return provincesBuildings;
    }

    public void addBuildingToProvince(String province, BuildingVO buildingVO){
        if(provincesBuildings.containsKey(province)==false){
            provincesBuildings.put(province, new ArrayList<BuildingVO>(){{add(buildingVO);}});
        } else {
            if(!provincesBuildings.containsValue(buildingVO)) {
                provincesBuildings.get(province).add(buildingVO);
            }
        }
    }

    public List<BuildingVO> getBuildingsByProvince(String province){
        return provincesBuildings.get(province);
    }
}

