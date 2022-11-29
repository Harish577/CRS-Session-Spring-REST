package com.customfusionrestapi.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.customfusionrestapi.dto.BinzBody;
import com.customfusionrestapi.service.GeoReferenceBinzConvertService;
import org.json.simple.parser.ParseException;

@RestController
@RequestMapping("/modelapi")
public class GeoReferencedBinzConverterController {

	@Autowired
	private GeoReferenceBinzConvertService geoReferenceBinzConvertService;

	@PostMapping("/BinzAndMDB2/Automation1")
	public ResponseEntity<Map<String, String>> geoReferencedBinzConversion(@RequestBody BinzBody body)
			throws Exception {

		return geoReferenceBinzConvertService.geoReferencedBinzConversion(body);
	}

	@GetMapping("/BinzAndMDB2/GetAllServices")
	public String geoReferencedBinzConversionAllServices() throws Exception {

		return geoReferenceBinzConvertService.geoReferencedBinzConversionAllServices();

	}

	@GetMapping("/BinzAndMDB2/processDbTable")
	public ResponseEntity<Map<String, String>> databaseProcessing(@RequestParam("unitname") String unitname)
			throws SQLException, IOException, ClassNotFoundException {

		return geoReferenceBinzConvertService.databaseProcessing(unitname);
	}

//	@PostMapping("/BinzAndMDB2/Mdb2Automation")
//	public ResponseEntity<Map<String, String>> geoReferencedMdb2BinzConversion()
//			throws Exception {
//
//		return geoReferenceBinzConvertService.geoReferencedBinzConversionMdb2();
//	}

	@GetMapping("/BinzAndMDB2/processLinkages")
	public ResponseEntity<Map<String, String>> dmrLinkageProcessing()
			throws SQLException, IOException, ClassNotFoundException {

		return geoReferenceBinzConvertService.dmrLinkageProcessing();
	}
}
