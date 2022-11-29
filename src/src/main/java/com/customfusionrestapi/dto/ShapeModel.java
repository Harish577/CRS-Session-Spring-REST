package com.customfusionrestapi.dto;

import java.util.ArrayList;

public class ShapeModel {

	public String type;
	public Properties properties;
	public String id;
	public Geometry geometry;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Geometry getGeometry() {
		return geometry;
	}

	public void setGeometry(Geometry geometry) {
		this.geometry = geometry;
	}
}

//import com.fasterxml.jackson.databind.ObjectMapper; // version 2.11.1
//import com.fasterxml.jackson.annotation.JsonProperty; // version 2.11.1
/*
 * ObjectMapper om = new ObjectMapper(); Root root = om.readValue(myJsonString,
 * Root.class);
 */




