package com.vivareal;

import com.vivareal.controller.BuildingController;
import com.vivareal.model.BuildingVO;
import com.vivareal.model.PropertiesOutputDTO;
import com.vivareal.model.ProvinceVO;
import com.vivareal.model.repository.BuildingRepository;
import com.vivareal.model.repository.ProvinceRepository;
import com.vivareal.service.ProvinceService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpotipposApplicationTests {

	@Autowired
	BuildingController buildingController;

	@Autowired
	BuildingRepository buildingRepository;

	@Autowired
	ProvinceService provinceService;

	@Autowired
	ProvinceRepository provinceRepository;

	//System Tests
	@Test
	public void testAddBuilding() {
		BuildingVO buildingVO = new BuildingVO();
		buildingVO.setBaths(2);
		buildingVO.setBeds(4);
		buildingVO.setSquareMeters(200);
		buildingVO.setX(1300);
		buildingVO.setY(200);
		UriComponentsBuilder builder = UriComponentsBuilder.newInstance();
		UriComponents result = builder.scheme("http").host("example.com").path("foo").queryParam("bar").fragment("baz").build();
		ResponseEntity<BuildingVO> buildingResponseEntity = buildingController.addSpotipposBuilding(buildingVO, builder);
		assertEquals(buildingResponseEntity.getBody().getId(), buildingRepository.getLastId());
	}

	@Test
	public void testGetBuildingById() {
		BuildingVO buildingVO = new BuildingVO();
		buildingVO.setBaths(2);
		buildingVO.setBeds(4);
		buildingVO.setSquareMeters(200);
		buildingVO.setX(1300);
		buildingVO.setY(200);
		UriComponentsBuilder builder = UriComponentsBuilder.newInstance();
		UriComponents result = builder.scheme("http").host("example.com").path("foo").queryParam("bar").fragment("baz").build();
		ResponseEntity<BuildingVO> buildingResponseEntity = buildingController.addSpotipposBuilding(buildingVO, builder);
		assertNotNull(buildingController.getBuildingById(buildingResponseEntity.getBody().getId()));
	}

	@Test
	public void testProvinceCalculation(){
		ArrayList<String> provinces = provinceService.getProvincesNamesByBuildingCoordinate(1200,867);
		assertEquals(provinces.get(0),"Jaby");
	}

	@Test
	public void testMultipleProvinceCalculation(){
		ArrayList<String> provinces = provinceService.getProvincesNamesByBuildingCoordinate(599,600);
		assertEquals(provinces.size(),2);
	}

	@Test
	public void testAutoSettingProvincesOnBuilding() {
		BuildingVO buildingVO = new BuildingVO();
		buildingVO.setBaths(2);
		buildingVO.setBeds(4);
		buildingVO.setSquareMeters(200);
		buildingVO.setX(1300);
		buildingVO.setY(200);
		UriComponentsBuilder builder = UriComponentsBuilder.newInstance();
		UriComponents result = builder.scheme("http").host("example.com").path("foo").queryParam("bar").fragment("baz").build();
		ResponseEntity<BuildingVO> buildingResponseEntity = buildingController.addSpotipposBuilding(buildingVO, builder);
		assertEquals(buildingResponseEntity.getBody().getProvinces().get(0),"Nova");
	}

	@Test
	public void testBuildingsListInProvinceRepository(){
		HashMap<String, List<BuildingVO>> provincesBuildings = provinceRepository.getProvincesBuildings();
		final int[] size = {0};
		provincesBuildings.forEach((s, buildings1) -> {
			size[0] = size[0] + buildings1.size();
		});
		assertEquals(6, provincesBuildings.keySet().size());
		assertEquals(buildingRepository.getLastId(), size[0]);
	}

	@Test
	public void testSearchBuildingsWithinOneProvinceArea(){
		for(ProvinceVO provinceVO : provinceService.getProvinces()) {
			int ax = provinceVO.getUpperLeftX();
			int ay = provinceVO.getUpperLeftY();
			int bx = provinceVO.getBottomRightX();
			int by = provinceVO.getBottomRightY();
			PropertiesOutputDTO propertiesOutputDTO = buildingController.getBuildingsByArea(ax, ay, bx, by).getBody();
			assertTrue(propertiesOutputDTO.getFoundProperties() > 0);
			assertTrue(propertiesOutputDTO.getProperties().size() > 0);
			propertiesOutputDTO.getProperties().forEach(building -> {
				assertTrue(building.getProvinces().contains(provinceVO.getName()));
			});
		}
	}

	@Test
	public void testSearchBuildingsWithinTwoHorizontaProvinceAreas(){
		int ax = 100;
		int ay = 400;
		int bx = 700;
		int by = 200;
		PropertiesOutputDTO propertiesOutputDTO = buildingController.getBuildingsByArea(ax, ay, bx, by).getBody();
		assertTrue(propertiesOutputDTO.getFoundProperties() > 0);
		assertTrue(propertiesOutputDTO.getProperties().size() > 0);
		final int[] count = {0,0};
		propertiesOutputDTO.getProperties().forEach(building -> {
			assertTrue(building.getProvinces().contains("Scavy")||building.getProvinces().contains("Groola"));
			if(building.getProvinces().contains("Scavy")) {
				count[0]++;
			}
			if(building.getProvinces().contains("Groola")) {
				count[1]++;
			}
		});
		assertTrue(count[0]>0);
		assertTrue(count[1]>0);
	}

	@Test
	public void testSearchBuildingsWithinTwoVerticalProvinceAreas(){
		int ax = 1150;
		int ay = 800;
		int bx = 1300;
		int by = 400;
		PropertiesOutputDTO propertiesOutputDTO = buildingController.getBuildingsByArea(ax, ay, bx, by).getBody();
		assertTrue(propertiesOutputDTO.getFoundProperties() > 0);
		assertTrue(propertiesOutputDTO.getProperties().size() > 0);
		final int[] count = {0,0};
		propertiesOutputDTO.getProperties().forEach(building -> {
			assertTrue(building.getProvinces().contains("Jaby")||building.getProvinces().contains("Nova"));
			if(building.getProvinces().contains("Jaby")) {
				count[0]++;
			}
			if(building.getProvinces().contains("Nova")) {
				count[1]++;
			}
		});
		assertTrue(count[0]>0);
		assertTrue(count[1]>0);
	}

	@Test
	public void testSearchBuildingsWithinThreeHorizontalProvinceAreas(){
		int ax = 100;
		int ay = 400;
		int bx = 1000;
		int by = 100;
		PropertiesOutputDTO propertiesOutputDTO = buildingController.getBuildingsByArea(ax, ay, bx, by).getBody();
		assertTrue(propertiesOutputDTO.getFoundProperties() > 0);
		assertTrue(propertiesOutputDTO.getProperties().size() > 0);
		final int[] count = {0,0,0};
		propertiesOutputDTO.getProperties().forEach(building -> {
			assertTrue(building.getProvinces().contains("Scavy")||building.getProvinces().contains("Groola")||building.getProvinces().contains("Nova"));
			if(building.getProvinces().contains("Scavy")) {
				count[0]++;
			}
			if(building.getProvinces().contains("Groola")) {
				count[1]++;
			}
			if(building.getProvinces().contains("Nova")) {
				count[2]++;
			}
		});
		assertTrue(count[0]>0);
		assertTrue(count[1]>0);
		assertTrue(count[2]>0);
	}


	@Test
	public void testSearchBuildingsWithinShadowProvinceAreas(){
		int ax = 400;
		int ay = 1000;
		int bx = 600;
		int by = 500;
		PropertiesOutputDTO propertiesOutputDTO = buildingController.getBuildingsByArea(ax, ay, bx, by).getBody();
		assertTrue(propertiesOutputDTO.getFoundProperties() > 0);
		assertTrue(propertiesOutputDTO.getProperties().size() > 0);
		propertiesOutputDTO.getProperties().forEach(building -> {
			assertTrue(building.getProvinces().contains("Gode") && building.getProvinces().contains("Ruja"));
		});
	}

	@Test
	public void testSearchBuildingsWithinAllProvinceAreas(){
		int ax = 0;
		int ay = 1000;
		int bx = 1400;
		int by = 0;
		PropertiesOutputDTO propertiesOutputDTO = buildingController.getBuildingsByArea(ax, ay, bx, by).getBody();
		assertTrue(propertiesOutputDTO.getFoundProperties() > 0);
		assertTrue(propertiesOutputDTO.getProperties().size() > 0);
		final int[] total = {0};
		propertiesOutputDTO.getProperties().forEach(building -> {
			total[0]++;
		});
		assertEquals(buildingRepository.getLastId(),total[0]);
	}

	@Test
	public void testSearchBuildingsWithOutOfBoundsInput(){
		ResponseEntity responseEntity = buildingController.getBuildingsByArea(1500, 100, 100, 0);
		assertEquals(HttpStatus.BAD_REQUEST,responseEntity.getStatusCode());
		assertEquals("Coordinate 'ax' out of bounds.",responseEntity.getBody());
	}

}
