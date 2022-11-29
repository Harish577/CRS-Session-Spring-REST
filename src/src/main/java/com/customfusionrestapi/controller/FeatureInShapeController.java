package com.customfusionrestapi.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.customfusionrestapi.dto.ShapeModel;
import com.customfusionrestapi.service.ShapesModelService;

@RestController
@RequestMapping("/luciad")

public class FeatureInShapeController {
	@Autowired
	private ShapesModelService shapesmodelservice;

	@PostMapping("/persistShapes")
	public ResponseEntity<Map<String, String>> persistShapes(@RequestBody ShapeModel shapeModel,
			@RequestHeader("Authorization") String authorization) throws IOException {

		return shapesmodelservice.persistShapes(shapeModel);
	}

	@GetMapping("/processShapes")
	public List processShapes(@RequestHeader("Authorization") String authorization) throws IOException {

		return shapesmodelservice.processShapes();
	}

}
