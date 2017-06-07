package com.vivareal.model.repository;

import com.vivareal.model.BuildingVO;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

/**
 * Created by Gustavo on 01/06/17.
 */

@Repository
public class BuildingRepository {

    private HashMap<Long,BuildingVO> spotipposBuildings = new HashMap<>();

    public BuildingVO getBuildingById(long id){
        return spotipposBuildings.get(id);
    }

    public void addBuilding(BuildingVO buildingVO){
        spotipposBuildings.put(buildingVO.getId(), buildingVO);
    }

    public int getLastId(){
        return spotipposBuildings.size();
    }
}
