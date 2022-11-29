package com.custom.elastic.search.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.custom.elastic.search.model.PlantInfo;
import com.custom.elastic.search.repository.PlantInfoRepository;
import com.custom.elastic.search.service.MultiSearchService;


@RestController
public class PlantInfoController {
	private static final Logger log = LoggerFactory.getLogger(PlantInfoController.class);

	@Autowired
	private PlantInfoRepository planInfoRepository;
	@Autowired
	private MultiSearchService multiSearchService;

	@GetMapping("/findAll")
	public List<PlantInfo> getAllPlantInfoData(){

		return 	(List<PlantInfo>) planInfoRepository.findAll();
	}


	@GetMapping("/findByTag/{tag}")
	public int getAllDataByTag(@PathVariable String tag ){
		log.info("searched tag count {}")	;
		List<PlantInfo> plantinfo	=planInfoRepository.findByTag(tag);


		return	plantinfo.size();
	}


	@GetMapping("/findFeatureId/{featureid}")
	public List<PlantInfo> getAllDataByFeatureId(@PathVariable Long featureid ){
		List<PlantInfo> plantinfo	=planInfoRepository.findByFeatureid(featureid);

		log.info("featureId count {}",plantinfo.size())	;	
		return	plantinfo;
	}


	@GetMapping("/moniker/tag/{moniker}")
	public PlantInfo MonikerDataNoAuth(@PathVariable(value = "moniker")  String moniker){

		log.info("moniker {}",moniker)	;
		PlantInfo planInfo = planInfoRepository.findByMoniker(" @a=0027!!20003##296496170373105690");


		log.info("properties {}",planInfo.getProperties())	;

		return	planInfo;
}
	@GetMapping("/muliSearch/tag/{request}")
	  public PlantInfo getNpsResponse(@PathVariable(value = "request")  String request) {
		
	 return multiSearchService.multiMatchQuery(request);
	  }

}
