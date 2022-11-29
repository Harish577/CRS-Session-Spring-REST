package com.customfusionrestapi.controller;

import java.util.List;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.customfusionrestapi.service.PermitSearchService;

@ComponentScan({"com.customfusionrestapi.service"})
@RestController
public class PermitSearchController {

	@Autowired
	private PermitSearchService permitSearchService;
	
	@GetMapping(value = "/Permits/data")
	public String getPermits(@RequestParam(value = "EquipmentNo", required = false) String equipmentNo,
			@RequestParam(value = "PermitID", required = false) String permitID,
			@RequestParam(value = "Plant", required = false) String plant,
			@RequestParam(value = "Site", required = false) String site,
			@RequestParam(value = "WorkType", required = false) String workType) throws Exception {

		List<JSONObject> output = permitSearchService.getPermitData(equipmentNo,permitID,plant,site,workType);
		
		return permitSearchService.getPermitDataWithBounds(output);
		
	}
	

	}

	

