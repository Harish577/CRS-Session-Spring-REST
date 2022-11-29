package com.customfusionrestapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.customfusionrestapi.dto.MeasuringPointBody;
import com.customfusionrestapi.dto.response.MeasuringPointResponse;
import com.customfusionrestapi.service.MeasuringPointService;

@RestController
@RequestMapping("/luciad")
public class MeasuringPointController {

	@Autowired
	private MeasuringPointService measuringPointService;

	@PostMapping(value = "/measuringpoint/pointdata")
	public String storMeasuringPoint(@RequestHeader("Authorization") String authorization,
			@RequestBody MeasuringPointBody measuringPointBody) throws Exception {

		return measuringPointService.storeMeasuringData(measuringPointBody, authorization);

	}

	@GetMapping(value = "/measuringpoint/pointdata")
	public String getMeasuringPoint(@RequestHeader("Authorization") String authorization) throws Exception {

		return measuringPointService.getMeasuringPoint(authorization);

	}

	@PutMapping(value = "/measuringpoint/pointdata/{id}")
	public String updateMeasuringPoint(@RequestHeader("Authorization") String authorization,
			@PathVariable Integer id, @RequestBody MeasuringPointBody measuringPointBody) throws Exception {

		return measuringPointService.updateMeasuringPoint(authorization, id, measuringPointBody);

	}

//
	@DeleteMapping(value = "/measuringpoint/pointdata/{id}")
	public String removeMeasuringPoint(@RequestHeader("Authorization") String authorization, @PathVariable Integer id)
			throws Exception {

		return measuringPointService.removeMeasuringData(authorization, id);

	}

	@GetMapping(value = "/measuringpoint/zoomInComponent")
	public String getComponentInfo(@RequestHeader("Authorization") String authorization,
			@RequestParam String corrosionComponent) throws Exception {

		return measuringPointService.getComponentInfo(authorization, corrosionComponent);

	}

}
