package com.vivareal.controller;

import com.vivareal.model.BuildingVO;
import com.vivareal.model.PropertiesOutputDTO;
import com.vivareal.service.BuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;

/**
 * Created by Gustavo on 01/06/17.
 */
@RestController
@Validated
public class BuildingController {

    @Autowired
    BuildingService buildingService;

    @RequestMapping(value="/properties", method= RequestMethod.POST)
    public ResponseEntity<BuildingVO> addSpotipposBuilding(@RequestBody @Valid BuildingVO buildingVO, UriComponentsBuilder ucBuilder){
        BuildingVO newBuildingVO = buildingService.addBuilding(buildingVO);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/properties/{id}").buildAndExpand(newBuildingVO.getId()).toUri());
        return new ResponseEntity<>(buildingVO, headers, HttpStatus.CREATED);
    }

    @RequestMapping(value="/properties/{id}", method=RequestMethod.GET)
    public ResponseEntity<BuildingVO> getBuildingById(@PathVariable("id") long id){
        BuildingVO buildingVO = buildingService.getBuildingById(id);
        if (buildingVO == null) {
            return new ResponseEntity<BuildingVO>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<BuildingVO>(buildingVO, HttpStatus.OK);
    }

    @RequestMapping(value="/properties",params = {"ax", "ay", "bx", "by"}, method=RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<PropertiesOutputDTO> getBuildingsByArea(@RequestParam("ax") int ax,
                                                  @RequestParam("ay") int ay,
                                                  @RequestParam("bx") int bx,
                                                  @RequestParam("by") int by) {
        if(ax<0||ax>1400) {
            return new ResponseEntity("Coordinate 'ax' out of bounds.", HttpStatus.BAD_REQUEST);
        }
        if(ay<0||ay>1000) {
            return new ResponseEntity("Coordinate 'ay' out of bounds.", HttpStatus.BAD_REQUEST);
        }
        if(bx<0||bx>1400) {
            return new ResponseEntity("Coordinate 'by' out of bounds.", HttpStatus.BAD_REQUEST);
        }
        if(by<0||by>1000) {
            return new ResponseEntity("Coordinate 'by' out of bounds.", HttpStatus.BAD_REQUEST);
        }
        if(ax>=bx){
            return new ResponseEntity("Coordinate 'bx' must be greater than 'ax'.", HttpStatus.BAD_REQUEST);
        }
        if(ay<=by){
            return new ResponseEntity("Coordinate 'ay' must be greater than 'by'.", HttpStatus.BAD_REQUEST);
        }
        PropertiesOutputDTO buildingsByArea = buildingService.getBuildingsByArea(ax, ay, bx, by);
        return new ResponseEntity<PropertiesOutputDTO>(buildingsByArea, HttpStatus.OK);
    }
}