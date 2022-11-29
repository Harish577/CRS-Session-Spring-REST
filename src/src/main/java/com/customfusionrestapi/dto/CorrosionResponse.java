package com.customfusionrestapi.dto;

import java.util.ArrayList;
import java.util.List;

public class CorrosionResponse {

	private List<Feature> features = new ArrayList<Feature>();
	private Crs crs;
	private String type;

	public List<Feature> getFeatures() {
		return features;
	}

	public void setFeatures(List<Feature> features) {
		this.features = features;
	}

	public Crs getCrs() {
		return crs;
	}

	public void setCrs(Crs crs) {
		this.crs = crs;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
