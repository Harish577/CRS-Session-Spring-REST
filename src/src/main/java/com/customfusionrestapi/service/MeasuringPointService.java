package com.customfusionrestapi.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.customfusionrestapi.dto.Geometry;
import com.customfusionrestapi.dto.MeasuringPointBody;
import com.customfusionrestapi.dto.Properties;
import com.customfusionrestapi.dto.response.MeasuringPointResponse;
import com.customfusionrestapi.entity.MeasuringPoint;
import com.customfusionrestapi.entity.RelianceMdb2ROGCPlantDatabase;
import com.customfusionrestapi.repos.MeasuringPointRepository;
import com.customfusionrestapi.repos.RelianceMdb2ROGCPlantDatabaseRepository;
import com.customfusionrestapi.util.HttpUtil;
import com.google.common.collect.Lists;
import com.google.gson.Gson;

@Service
public class MeasuringPointService {

	@Autowired
	MeasuringPointRepository measuringPointRepository;

	@Autowired
	RelianceMdb2ROGCPlantDatabaseRepository rogcRepository;

	@Autowired
	private HttpUtil httpUtil;

	@Value("${sdx.measuringpointData}")
	private String measuringpointData;

	@Value("${sdx.postMeasuringPointData}")
	private String postMeasuringPointData;

	@Value("${aim.measuringpointDataFromAim}")
	private String measuringpointDataFromAim;

//	public String getMeasuringData(String corrosionComponent) {
//
//		List<RelianceMdb2ROGCPlantDatabase> measuringPointList = rogcRepository.findByTag(corrosionComponent);
//
//		List<MeasuringPointResponse> measurementResponseList = measuringPointList.stream().map(it -> {
//
////		 new ArrayList<MeasurementResponse>();
////			
//			MeasuringPointResponse measuringPointResponse = new MeasuringPointResponse();
//
//			measuringPointResponse.setId();
//
//			measuringPointResponse.setType(corrosionComponent);
//
//			return measuringPointResponse;
//
//		}).collect(Collectors.toList());
//
//		return new Gson().toJson(measurementResponseList);
//
//	}
//
//	public String getMeasuringPoint(String authorization, String corrosionComponent)
//			throws KeyManagementException, KeyStoreException, NoSuchAlgorithmException {
//
//		HttpHeaders headers = httpUtil.getHttpHeaders();
//
//		headers.add("Authorization", authorization);
//
//		HttpEntity<String> entity = new HttpEntity<String>(headers);
//
//		RestTemplate restTemplate = httpUtil.getRestTemplate();
//
//		ResponseEntity<TagResponse> responseEntity = restTemplate.exchange(measuringpointData, HttpMethod.GET, entity,
//				TagResponse.class);
//
//		return getMeasuringData(corrosionComponent);
//	}

	public String storeMeasuringData(MeasuringPointBody it, String authorization) {

		MeasuringPoint measuringPoint = new MeasuringPoint();

		measuringPoint.setGeometrycoordinates(it.getGeometry().getCoordinates().toString());
		measuringPoint.setType(it.getType());
		measuringPoint.setGeometrytype(it.getGeometry().getType());
		measuringPoint.setMeasurementpoint(it.getProperties().getMeasurementPoint());
		measuringPoint.setCorrosioncomponent(it.getProperties().getCorrosionComponent());

		measuringPoint = measuringPointRepository.save(measuringPoint);

		return generateMeasuringPointResponse(measuringPoint);

	}

	public String updateMeasuringPoint(String authorization, Integer id, MeasuringPointBody it) {

		MeasuringPoint measuringPoint = measuringPointRepository.findByID(id);

		measuringPoint.setGeometrycoordinates(it.getGeometry().getCoordinates().toString());
		measuringPoint.setType(it.getType());
		measuringPoint.setGeometrytype(it.getGeometry().getType());
		measuringPoint.setMeasurementpoint(it.getProperties().getMeasurementPoint());
		measuringPoint.setCorrosioncomponent(it.getProperties().getCorrosionComponent());

		measuringPoint = measuringPointRepository.save(measuringPoint);

		return generateMeasuringPointResponse(measuringPoint);

	}

//	public String getMeasuringDataByFeatureID(String featureID) {
//
//		MeasuringPoint it = measuringPointRepository.findByFeatureID(Integer.parseInt(featureID));
//
//		MeasurementResponse measurementResponse = new MeasurementResponse();
//
//		measurementResponse.setFeatureId(it.getID().toString());
//
//		
//
//		String value = it.getGeometrycoordinates().substring(2, it.getGeometrycoordinates().length() - 2);
//
//		System.out.println(value);
//
//		String[] keyValuePairs = value.replaceAll("\\],", "\\] ").split(" ");
//
//		System.out.println(keyValuePairs);
//
//		Map<Double, Double> doubleMap = new HashMap<>();
//
//		for (String entry : keyValuePairs) {
//
//			String[] coordinates = entry.replaceAll("\\[", " ").replaceAll("\\]", " ").split(",");
//
//			doubleMap.put(Double.parseDouble(coordinates[0]), Double.parseDouble(coordinates[1]));
//
//		}
//
//		measurementResponse.setCenterX(doubleMap.keySet().stream().mapToDouble(a -> a).average().getAsDouble());
//
//		measurementResponse.setCenterY(doubleMap.values().stream().mapToDouble(a -> a).average().getAsDouble());
//
//		measurementResponse.setMaxZ(it.getMaxZ());
//
//		return new Gson().toJson(measurementResponse);
//
//	}

	public String removeMeasuringData(String authorization, Integer id) {

		measuringPointRepository.deleteById(id);
		// TODO Auto-generated method stub
		return "Successful deleted the record";
	}

	public String generateMeasuringPointResponse(MeasuringPoint it) {

		ArrayList<Map<String, ?>> features = new ArrayList<Map<String, ?>>();

		Map<String, Object> map = new HashMap<>();

		map.put("id", it.getID());

		Map<String, Object> properties = new HashMap<>();

		properties.put("corrosioncomponent", it.getCorrosioncomponent());
		properties.put("measuringpoint", it.getMeasurementpoint());

		map.put("properties", properties);

		Map<String, Object> geometry = new HashMap<>();

		geometry.put("type", "Point");

		String bounds = it.getGeometrycoordinates().replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\"", "");

		List<String> strlist = Arrays.asList(bounds.split(","));
		List<Double> lists = new ArrayList<Double>(Lists.transform(strlist, Double::parseDouble));

		geometry.put("coordinates", lists);

		map.put("geometry", geometry);

		map.put("type", "Feature");

		features.add(map);

		Map<String, Object> response = new HashMap<>();
		response.put("type", "FeatureCollection");
		response.put("features", features);
		return new Gson().toJson(response);

	}

	public String getMeasuringPoint(String authorization) {

		List<MeasuringPoint> measuringPointList = measuringPointRepository.findAll();

		ArrayList<Map<String, ?>> features = new ArrayList<Map<String, ?>>();

		for (MeasuringPoint it : measuringPointList) {

			Map<String, Object> map = new HashMap<>();

			map.put("id", it.getID());

			Map<String, Object> properties = new HashMap<>();

			properties.put("corrosioncomponent", it.getCorrosioncomponent());
			properties.put("measuringpoint", it.getMeasurementpoint());

			map.put("properties", properties);

			Map<String, Object> geometry = new HashMap<>();

			geometry.put("type", "Point");

			String bounds = it.getGeometrycoordinates().replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\"",
					"");

			List<String> strlist = Arrays.asList(bounds.split(","));
			List<Double> lists = new ArrayList<Double>(Lists.transform(strlist, Double::parseDouble));

			geometry.put("coordinates", lists);

			map.put("geometry", geometry);

			map.put("type", "Feature");

			features.add(map);

		}

		Map<String, Object> response = new HashMap<>();
		response.put("type", "FeatureCollection");
		response.put("features", features);
		return new Gson().toJson(response);

	}

	public String getComponentInfo(String authorization, String corrosionComponent) {

		MeasuringPoint measuringPoint = measuringPointRepository.findByCorrosionComponent(corrosionComponent);

		List<RelianceMdb2ROGCPlantDatabase> plantList = rogcRepository.findByTag(corrosionComponent);

		ArrayList<Map<?, ?>> features = new ArrayList<Map<?, ?>>();

		for (RelianceMdb2ROGCPlantDatabase it : plantList) {

			Map<String, Object> map = new HashMap<>();

			map.put("id", measuringPoint.getID());
			map.put("corrosioncomponent", measuringPoint.getCorrosioncomponent());
			map.put("linkage", it.getLinkeage());
			map.put("featureId", it.getFeatureid());

			Map<String, Object> properties = new HashMap<>();

			properties.put("minx", it.getMinx());
			properties.put("maxx", it.getMaxx());
			properties.put("miny", it.getMiny());
			properties.put("maxy", it.getMaxy());
			properties.put("minz", it.getMinz());
			properties.put("maxz", it.getMaxy());

			map.put("properties", properties);

			Map<String, Object> geometry = new HashMap<>();

			Map<String, Object> coordinates = new HashMap<>();

			coordinates.put("coordinates", it.getCenterBounds());

			geometry.put("coordinates", coordinates);

			map.put("geometry", geometry);

			features.add(map);
		}

		Map<String, Object> response = new HashMap<>();
		response.put("type", "FeatureCollection");
		Map<String, Object> insidecrs = new HashMap<>();
		insidecrs.put("name", "urn:ogc:def:crs:OGC:1.3:CRS84");
		Map<String, Object> crs = new HashMap<>();
		crs.put("properties", insidecrs);
		crs.put("type", "name");
		response.put("crs", crs);
		response.put("features", features);
		return new Gson().toJson(response);
	}
}
