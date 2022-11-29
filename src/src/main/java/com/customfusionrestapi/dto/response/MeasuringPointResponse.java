package com.customfusionrestapi.dto.response;

import com.customfusionrestapi.dto.Geometry;
import com.customfusionrestapi.dto.Properties;

public class MeasuringPointResponse {

	private Geometry geometry;
	private String id;
	private String type;
	private Properties properties;

	public Geometry getGeometry() {
		return geometry;
	}

	public void setGeometry(Geometry geometry) {
		this.geometry = geometry;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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

}
