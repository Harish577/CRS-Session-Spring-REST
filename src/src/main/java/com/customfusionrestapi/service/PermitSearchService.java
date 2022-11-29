package com.customfusionrestapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.customfusionrestapi.entity.RelianceMdb2ROGCPlantDatabase;
import com.customfusionrestapi.repos.RelianceMdb2ROGCPlantDatabaseRepository;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.netflix.discovery.converters.Converters;

@Service
@EntityScan({"com.customfusionrestapi.entity"})
public class PermitSearchService {
	
	@Autowired
	private RelianceMdb2ROGCPlantDatabaseRepository relianceMdb2ROGCPlantDatabaseRepository;
		
	public List<JSONObject> getPermitData(String equipmentNo, String permitID, String plant, String site, String workType) throws ParseException {

		RestTemplate restTemplate1=new RestTemplate();
		String url = "http://sidcaimsdxintrf/aim_nc_rcowservice/api/v1/permits";

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		HttpEntity<String> entity = new HttpEntity<String>(headers);

		ResponseEntity<String> responseEntity = restTemplate1.exchange(url, HttpMethod.GET, entity,
				String.class);
		
		String response=responseEntity.getBody();
		JSONParser jP=new JSONParser();		
		JSONObject obj  = (JSONObject) jP.parse(response);
		JSONArray jA = new JSONArray();
		jA=(JSONArray) obj.get("value");
		
		ArrayList<JSONObject> list=jA;
				
		List<JSONObject> temp=new ArrayList<JSONObject>();
	    List<JSONObject> output = new ArrayList<JSONObject>();
	    
	    for(JSONObject permit:list) {
	    	
	    	JSONObject k=(JSONObject) permit.get("WorkOrder");
	    	if((permit.get("Permit_ID")!=null) && (k.get("Equipment_No")!=null)) {
	    		temp.add(permit);
	    		
	    	}
	    }
	    
	    if(equipmentNo!=null) {
	    	JSONObject p=new JSONObject();
		    for(JSONObject permit : temp ) {
		    	
		    	p=(JSONObject) permit.get("WorkOrder");
		    	
		       if((p.get("Equipment_No")!=null)&&((String) p.get("Equipment_No")).equals(equipmentNo)) {
		    	   if(!(output.contains(permit))) {
		    		   output.add(permit);
		    	   }
		       }
		    }
		    temp=new ArrayList<JSONObject>(output);
		    }
	    
	    if(permitID!=null) {
	    	
		    for(JSONObject permit : temp ) {
		    	
		       if((permit.get("Permit_ID")!=null)&&((String) permit.get("Permit_ID")).equals(permitID)) {
		    	   if(!(output.contains(permit))) {
		    		   output.add(permit);
		    	   }
		       }
		    }
		    temp=new ArrayList<JSONObject>(output);
		    }
		
	    if(plant!=null) {
	    	JSONObject p=new JSONObject();
		    for(JSONObject permit : temp ) {
		    	
		    	p=(JSONObject) permit.get("WorkOrder");
		    	
		       if((p.get("PlantName")!=null)&&((String) p.get("PlantName")).equals(plant)) {
		    	   if(!(output.contains(permit))) { 
		    		   output.add(permit);
		    	   }
		       }
		    }
		    temp=new ArrayList<JSONObject>(output);
		    }
	    
	    if(site!=null) {
	    	JSONObject p=new JSONObject();
		    for(JSONObject permit : temp ) {
		    	
		    	p=(JSONObject) permit.get("WorkOrder");
		    	
		       if((p.get("SiteName")!=null)&&((String) p.get("SiteName")).equals(site)) {
		    	   if(!(output.contains(permit))) {
		    		   output.add(permit);
		    	   }
		       }
		    }
		    temp=new ArrayList<JSONObject>(output);
		    }
	    
	    if(workType!=null) {
	    	JSONObject p=new JSONObject();
		    for(JSONObject permit : temp ) {
		    	
		    	p=(JSONObject) permit.get("Mst_PermitType");
		    	
		       if((p.get("Name")!=null)&&((String) p.get("Name")).equals(workType)) {
		    	   
		    	   if(!(output.contains(permit))) {
		    		   output.add(permit);
		    	   }
		       }
		    }
		    temp=new ArrayList<JSONObject>(output);
		    }
		
		return output;
	}

	
	
	public String getPermitDataWithBounds(List<JSONObject> output) {

		Map<String, List<JSONObject>> dictionary = StreamSupport.stream(output.spliterator(), false)
				.collect(Collectors.groupingBy(f -> (((JSONObject) f.get("WorkOrder")).get("Equipment_No")).toString()));
		
		ArrayList<JSONObject> objects = new ArrayList<JSONObject>();
		
		for (Map.Entry<String, List<JSONObject>> entry : dictionary.entrySet()) {
			
			List<RelianceMdb2ROGCPlantDatabase> pm=(List<RelianceMdb2ROGCPlantDatabase>) relianceMdb2ROGCPlantDatabaseRepository.findByEquipment(entry.getKey());

			// Iterate the list to append the permits data to GeoInfo for the Tag
                 if(pm.size()>0) {
			
					for (JSONObject ob : entry.getValue()) {
						
						JSONObject jsonobj = new JSONObject();
						jsonobj.put("linkage", pm.get(0).getLinkeage());
						jsonobj.put("OriginalBounds", pm.get(0).getOriginalBounds());
						jsonobj.put("minx", pm.get(0).getMinx());
						jsonobj.put("maxx", pm.get(0).getMaxx());
						jsonobj.put("miny", pm.get(0).getMiny());
						jsonobj.put("maxy", pm.get(0).getMaxy());
						jsonobj.put("minz", pm.get(0).getMinz());
						jsonobj.put("maxz", pm.get(0).getMaxz());
						jsonobj.put("label_value", pm.get(0).getTag());
						jsonobj.put("EquipmentNumber", pm.get(0).getEquipment());
						jsonobj.put("id", pm.get(0).getFeatureid());
					jsonobj.put("permit_data", ob);
					objects.add(jsonobj); // store the properties of json objects into 'objects' list
				}
                 }
                 
			}
		
		String bounds;
		int i = 0;
		ArrayList<JSONObject> feature_store = new ArrayList<JSONObject>();
				
		for (JSONObject ob : objects) {

			bounds = (String) ob.get("OriginalBounds");
			bounds = bounds.replace("[", "");
			bounds = bounds.replace("]", "");
			List<String> strlist = Arrays.asList(bounds.split(","));
			List<Double> lists = new ArrayList(Lists.transform(strlist, Double::parseDouble));
			JSONObject feature = new JSONObject();
			JSONObject insidegeometry = new JSONObject();
			List<List<Double>> listOfLists = Lists.partition(lists,2);
			List<List> newlist=new ArrayList<List>();
			newlist.add(listOfLists);
			JSONObject properties = new JSONObject();
			properties.put("permit_data", ob.get("permit_data"));
			properties.put("feature_id", ob.get("id"));
			properties.put("maxz", ob.get("maxz"));
			properties.put("minz", ob.get("minz"));
			properties.put("maxy", ob.get("maxy"));
			properties.put("miny", ob.get("miny"));
			properties.put("maxx", ob.get("maxx"));
			properties.put("minx", ob.get("minx"));
			properties.put("linkage", ob.get("linkage"));
			properties.put("label_value", ob.get("label_value"));
			feature.put("properties", properties);
			insidegeometry.put("coordinates", newlist);
			insidegeometry.put("type", "Polygon");
			feature.put("geometry", insidegeometry);
			feature.put("id", i + 1);
			feature.put("type", "Feature");
			i = i + 1;
			feature_store.add(feature);
		}
		JSONObject dic = new JSONObject();
		dic.put("type", "FeatureCollection");
		JSONObject insidecrs = new JSONObject();
		insidecrs.put("name", "urn:ogc:def:crs:OGC:1.3:CRS84");
		JSONObject crs = new JSONObject();
		crs.put("properties", insidecrs);
		crs.put("type", "name");
		dic.put("crs", crs);
		dic.put("features", feature_store);
		return new Gson().toJson(dic);
	}

}


