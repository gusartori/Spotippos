package com.vivareal.service;

import com.vivareal.model.BuildingVO;
import com.vivareal.model.PropertiesOutputDTO;
import com.vivareal.model.repository.BuildingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Gustavo on 01/06/17.
 */
@Service
public class BuildingService {

    @Autowired
    BuildingRepository buildingRepository;

    @Autowired
    ProvinceService provinceService;

    public BuildingVO addBuilding(BuildingVO buildingVO) {
        buildingVO.setId(buildingRepository.getLastId()+1);
        buildingVO.setProvinces(provinceService.getProvincesNamesByBuildingCoordinate(buildingVO.getX(), buildingVO.getY()));
        buildingRepository.addBuilding(buildingVO);
        provinceService.addBuildingToProvince(buildingVO);
        return buildingVO;
    }

    public BuildingVO getBuildingById(long id){
        return buildingRepository.getBuildingById(id);
    }

    public PropertiesOutputDTO getBuildingsByArea(int ax, int ay, int bx, int by) {
        HashMap<String, List<BuildingVO>> buildingsInProvinces = provinceService.getAllBuildingsFromProvincesInArea(ax, ay, bx, by);
        ArrayList<BuildingVO> buildings = new ArrayList<>();
        buildingsInProvinces.forEach((province, buildingsList) -> {
            for(BuildingVO b: buildingsList){
                if(b.getX()>=ax && b.getX()<=bx && b.getY()<=ay && b.getY() >= by){
                    buildings.add(b);
                }
            }
        });
        PropertiesOutputDTO propertiesOutputDTO = new PropertiesOutputDTO();
        propertiesOutputDTO.setProperties(buildings);
        propertiesOutputDTO.setFoundProperties(buildings.size());
        return propertiesOutputDTO;
    }

}
