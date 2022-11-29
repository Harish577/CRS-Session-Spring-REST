package com.customfusionrestapi.dto;

import java.util.ArrayList;

public class Center{
	
	 public ArrayList<Double> getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(ArrayList<Double> coordinates) {
		this.coordinates = coordinates;
	}

	public ArrayList<Double> coordinates;
	 public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String type;
	}
