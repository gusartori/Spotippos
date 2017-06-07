package com.vivareal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vivareal.controller.BuildingController;
import com.vivareal.model.BuildingVO;
import com.vivareal.model.PropertiesOutputDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;

import static org.junit.matchers.JUnitMatchers.containsString;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Gustavo on 02/06/17.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SpotipposUnitTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BuildingController buildingControllerMock;

    @Autowired
    ObjectMapper objectMapper;

    //Unit Tests
    @Test
    public void invokingControllerGetBuildingById() throws Exception {
        BuildingVO buildingVO = new BuildingVO();
        buildingVO.setTitle("Unit test for Controller");
        when(buildingControllerMock.getBuildingById(1)).thenReturn(new ResponseEntity<BuildingVO>(buildingVO, HttpStatus.OK));
        this.mockMvc.perform(get("/properties/1")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Unit test for Controller")));
    }

    @Test
    public void invokingControllerGetBuildingByIdNotFound() throws Exception {
        when(buildingControllerMock.getBuildingById(1)).thenReturn(new ResponseEntity<BuildingVO>(HttpStatus.NOT_FOUND));
        this.mockMvc.perform(get("/properties/1")).andDo(print()).andExpect(status().isNotFound());
    }

    @Test
    public void invokingControllerAddSpotipposBuilding() throws Exception {
        BuildingVO buildingVO = new BuildingVO();
        buildingVO.setBeds(2);
        buildingVO.setBaths(2);
        buildingVO.setY(200);
        buildingVO.setX(200);
        buildingVO.setSquareMeters(200);
        when(buildingControllerMock.addSpotipposBuilding(any(), any())).thenReturn(new ResponseEntity<BuildingVO>(buildingVO, HttpStatus.CREATED));
        this.mockMvc.perform(post("/properties")
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(buildingVO))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    public static byte[] convertObjectToJsonBytes(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsBytes(object);
    }

    @Test
    public void invokingControllerGetBuildingsByArea() throws Exception {
        PropertiesOutputDTO propertiesOutputDTO = new PropertiesOutputDTO();
        propertiesOutputDTO.setFoundProperties(423);
        when(buildingControllerMock.getBuildingsByArea(0,100,140,0))
                .thenReturn(new ResponseEntity<PropertiesOutputDTO>(propertiesOutputDTO, HttpStatus.OK));
        this.mockMvc.perform(get("/properties?ax=0&ay=100&bx=140&by=0")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("foundProperties")))
                .andExpect(content().string(containsString("423")))
                .andExpect(content().string(containsString("properties")));
    }

    @Test
    public void invokingControllerGetBuildingsByAreaOutOfBounds() throws Exception {
        PropertiesOutputDTO propertiesOutputDTO = new PropertiesOutputDTO();
        propertiesOutputDTO.setFoundProperties(423);
        when(buildingControllerMock.getBuildingsByArea(-5,100,140,0))
                .thenReturn(new ResponseEntity<PropertiesOutputDTO>(propertiesOutputDTO, HttpStatus.BAD_REQUEST));
        this.mockMvc.perform(get("/properties?ax=-5&ay=100&bx=140&by=0")).andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testExceptionBedsUpBuilding() throws Exception {
        BuildingVO buildingVO = new BuildingVO();
        buildingVO.setBaths(2);
        buildingVO.setBeds(10);
        buildingVO.setSquareMeters(200);
        buildingVO.setX(1300);
        buildingVO.setY(200);
        this.mockMvc.perform(post("/properties")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(buildingVO)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testExceptionBedsDownBuilding() throws Exception {
        BuildingVO buildingVO = new BuildingVO();
        buildingVO.setBaths(2);
        buildingVO.setBeds(0);
        buildingVO.setSquareMeters(200);
        buildingVO.setX(1300);
        buildingVO.setY(200);
        this.mockMvc.perform(post("/properties")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(buildingVO)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testExceptionBathsUpBuilding() throws Exception{
        BuildingVO buildingVO = new BuildingVO();
        buildingVO.setBaths(12);
        buildingVO.setBeds(2);
        buildingVO.setSquareMeters(200);
        buildingVO.setX(1300);
        buildingVO.setY(200);
        this.mockMvc.perform(post("/properties")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(buildingVO)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testExceptionBathsDownBuilding() throws Exception{
        BuildingVO buildingVO = new BuildingVO();
        buildingVO.setBaths(0);
        buildingVO.setBeds(2);
        buildingVO.setSquareMeters(200);
        buildingVO.setX(1300);
        buildingVO.setY(200);
        this.mockMvc.perform(post("/properties")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(buildingVO)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testExceptionCoordinateXUpBuilding()  throws Exception{
        BuildingVO buildingVO = new BuildingVO();
        buildingVO.setBaths(2);
        buildingVO.setBeds(2);
        buildingVO.setSquareMeters(200);
        buildingVO.setX(1500);
        buildingVO.setY(200);
        this.mockMvc.perform(post("/properties")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(buildingVO)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testExceptionCoordinateXDownBuilding() throws Exception{
        BuildingVO buildingVO = new BuildingVO();
        buildingVO.setBaths(2);
        buildingVO.setBeds(2);
        buildingVO.setSquareMeters(200);
        buildingVO.setX(-100);
        buildingVO.setY(200);
        this.mockMvc.perform(post("/properties")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(buildingVO)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testExceptionCoordinateYUpBuilding() throws Exception{
        BuildingVO buildingVO = new BuildingVO();
        buildingVO.setBaths(2);
        buildingVO.setBeds(2);
        buildingVO.setSquareMeters(200);
        buildingVO.setX(1000);
        buildingVO.setY(1220);
        this.mockMvc.perform(post("/properties")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(buildingVO)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
    @Test
    public void testExceptionCoordinateYDownBuilding()  throws Exception{
        BuildingVO buildingVO = new BuildingVO();
        buildingVO.setBaths(2);
        buildingVO.setBeds(2);
        buildingVO.setSquareMeters(200);
        buildingVO.setX(1000);
        buildingVO.setY(-200);
        this.mockMvc.perform(post("/properties")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(buildingVO)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testExceptionSquareMetersUpBuilding() throws Exception{
        BuildingVO buildingVO = new BuildingVO();
        buildingVO.setBaths(2);
        buildingVO.setBeds(2);
        buildingVO.setSquareMeters(1000);
        buildingVO.setX(1000);
        buildingVO.setY(200);
        this.mockMvc.perform(post("/properties")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(buildingVO)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testExceptionSquareMetersDownBuilding() throws Exception{
        BuildingVO buildingVO = new BuildingVO();
        buildingVO.setBaths(2);
        buildingVO.setBeds(2);
        buildingVO.setSquareMeters(10);
        buildingVO.setX(1000);
        buildingVO.setY(200);
        this.mockMvc.perform(post("/properties")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(buildingVO)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

}
