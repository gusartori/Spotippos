package com.vivareal.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.LinkedTreeMap;
import com.vivareal.model.BuildingVO;
import com.vivareal.model.PropertiesInputDTO;
import com.vivareal.model.ProvinceVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Gustavo on 01/06/17.
 */
@Service
public class FilesReaderService {

    private static final FileSystemResource file_buildings = new FileSystemResource("./input/properties.json");
    private static final FileSystemResource file_provinces = new FileSystemResource("./input/provinces.json");

    @Autowired
    BuildingService buildingService;

    @Autowired
    ProvinceService provinceService;

    @PostConstruct
    public void readSpotipposConfigurationFiles(){
        readProvincesFile();
        readBuildingsFile();
    }

    public void readBuildingsFile() {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file_buildings.getFile()));
            Gson gson = new GsonBuilder().create();
            PropertiesInputDTO propertiesInputDTO = gson.fromJson(reader, PropertiesInputDTO.class);
            ArrayList<BuildingVO> buildings = propertiesInputDTO.getProperties();
            buildings.forEach(building -> buildingService.addBuilding(building));
            Logger.getLogger(FilesReaderService.class.getName()).log(Level.INFO, propertiesInputDTO.getTotalProperties()+" buildings loaded into Spotippos.");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FilesReaderService.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                reader.close();
            } catch (IOException ex) {
                Logger.getLogger(FilesReaderService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void readProvincesFile() {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file_provinces.getFile()));
            Gson gson = new GsonBuilder().create();
            LinkedTreeMap o = (LinkedTreeMap)gson.fromJson(reader, Object.class);
            o.forEach((o1, o2) -> {
                String name = (String) o1;
                int upperLeftX = ((Double) ((LinkedTreeMap) (((LinkedTreeMap) ((LinkedTreeMap) ((LinkedTreeMap) o2).get("boundaries"))).get("upperLeft"))).get("x")).intValue();
                int upperLeftY = ((Double) ((LinkedTreeMap) (((LinkedTreeMap) ((LinkedTreeMap) ((LinkedTreeMap) o2).get("boundaries"))).get("upperLeft"))).get("y")).intValue();
                int bottomRightX = ((Double) ((LinkedTreeMap) (((LinkedTreeMap) ((LinkedTreeMap) ((LinkedTreeMap) o2).get("boundaries"))).get("bottomRight"))).get("x")).intValue();
                int bottomRightY = ((Double) ((LinkedTreeMap) (((LinkedTreeMap) ((LinkedTreeMap) ((LinkedTreeMap) o2).get("boundaries"))).get("bottomRight"))).get("y")).intValue();;
                ProvinceVO provinceVO = new ProvinceVO(upperLeftX, upperLeftY, bottomRightX, bottomRightY, name);
                provinceService.addProvince(provinceVO);
            });
            Logger.getLogger(FilesReaderService.class.getName()).log(Level.INFO, o.size()+" provinces loaded into Spotippos.");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FilesReaderService.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                reader.close();
            } catch (IOException ex) {
                Logger.getLogger(FilesReaderService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}