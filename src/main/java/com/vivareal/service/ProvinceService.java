package com.vivareal.service;

import com.vivareal.model.BuildingVO;
import com.vivareal.model.ProvinceVO;
import com.vivareal.model.repository.ProvinceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Gustavo on 01/06/17.
 */
@Service
public class ProvinceService {
    private static ArrayList<ProvinceVO> provinces = new ArrayList<ProvinceVO>();

    @Autowired
    ProvinceRepository provinceRepository;

    public ArrayList<String> getProvincesNamesByBuildingCoordinate(int x, int y){
        ArrayList<String> provincesResult = new ArrayList<>();
        provinces.forEach(p->{
            if(containsCoordinate(x,y,p)) {
                provincesResult.add(p.getName());
            }
        });
        return provincesResult;
    }

    private boolean containsCoordinate(int x, int y, ProvinceVO provinceVO){
        if(provinceVO.getBottomRightX()>=x && provinceVO.getUpperLeftX()<= x &&
                provinceVO.getBottomRightY()<=y &&  provinceVO.getUpperLeftY()>= y){
            return true;
        }
        return false;
    }

    public void addProvince(ProvinceVO provinceVO){
        provinces.add(provinceVO);
    }

    public void addBuildingToProvince(BuildingVO buildingVO){
        provinceRepository.addBuildingToProvince(buildingVO.getProvinces().get(0), buildingVO);
    }

    public HashMap<String, List<BuildingVO>> getAllBuildingsFromProvincesInArea(int ax, int ay, int bx, int by) {
        HashMap<String, List<BuildingVO>> provincesBuildings = new HashMap<>();
        provinces.forEach(provinceVO -> {
            if(containsCoordinate(ax,ay, provinceVO) || containsCoordinate(bx, by, provinceVO) ||
                    containsCoordinate(bx, ay, provinceVO) || containsCoordinate(ax, by, provinceVO)) {
                provincesBuildings.put(provinceVO.getName(), provinceRepository.getBuildingsByProvince(provinceVO.getName()));
            } else if(provinceVO.getUpperLeftX()>=ax && provinceVO.getUpperLeftX()<=bx && ay <= provinceVO.getUpperLeftY() && ay >= provinceVO.getBottomRightY()){
                provincesBuildings.put(provinceVO.getName(), provinceRepository.getBuildingsByProvince(provinceVO.getName()));
            } else if(provinceVO.getUpperLeftX()>=ax && provinceVO.getUpperLeftX()<=bx && by>= provinceVO.getBottomRightY() && by<= provinceVO.getUpperLeftY()){
                provincesBuildings.put(provinceVO.getName(), provinceRepository.getBuildingsByProvince(provinceVO.getName()));
            }
        });
        return provincesBuildings;
    }

    public static ArrayList<ProvinceVO> getProvinces() {
        return provinces;
    }
}
