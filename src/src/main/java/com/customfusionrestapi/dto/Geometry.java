package com.customfusionrestapi.dto;

import java.util.ArrayList;

public class Geometry {

	private ArrayList<Double> coordinates;

	private String type;

	public BaseShape baseShape;

	public Bounds getBounds() {
		return bounds;
	}

	public void setBounds(Bounds bounds) {
		this.bounds = bounds;
	}

	private Bounds bounds;

	public BaseShape getBaseShape() {
		return baseShape;
	}

	public void setBaseShape(BaseShape baseShape) {
		this.baseShape = baseShape;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public ArrayList<Double> getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(ArrayList<Double> coordinates) {
		this.coordinates = coordinates;
	}
}
