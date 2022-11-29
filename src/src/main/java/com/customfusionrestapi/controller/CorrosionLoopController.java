package com.customfusionrestapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.customfusionrestapi.service.CorrosionLoopService;

@RestController
@RequestMapping("/luciad")
@ComponentScan({ "com.customfusionrestapi.service" })
public class CorrosionLoopController {

	@Autowired
	private CorrosionLoopService corrosionLoopService;

	@GetMapping(value = "/CorrosionLoop/viewCorrosionLoopBasedOnLoopName")
	public String viewCorrosionLoopBasedOnLoopName(@RequestParam(value = "loop") String loop,
			@RequestHeader("Authorization") String authorization) throws Exception {

		return corrosionLoopService.getCorrosionLoopBasedOnLoopName(authorization, loop);

	}

	@GetMapping(value = "/CorrosionLoop/viewTagBasedOnLinkage")
	public String viewTagBasedOnLinkage(@RequestParam(value = "linkage") String linkage,
			@RequestHeader("Authorization") String authorization) throws Exception {

		return corrosionLoopService.getTagBasedOnLinkage(linkage, authorization);

	}

	@GetMapping(value = "/CorrosionLoop/viewLinkages")
	public String viewLinkages(@RequestHeader("Authorization") String authorization) throws Exception {

		return corrosionLoopService.getLinkages(authorization);

	}

	@GetMapping(value = "/CorrosionLoop/viewLinkagesBasedOnSelectedParam")
	public String viewLinkagesBasedOnSelectedParam(@RequestParam(value = "filter") String filter,
			@RequestHeader("Authorization") String authorization) throws Exception {

		return corrosionLoopService.getLinkagesBasedOnSelectedParam(filter, authorization);

	}

	@GetMapping(value = "/CorrosionLoop/viewCorrosionLoopNames")
	public String viewCorrosionLoopNames(@RequestHeader("Authorization") String authorization) throws Exception {

		return corrosionLoopService.getCorrosionLoopNames(authorization);

	}

}
