package com.customfusionrestapi.dto;

public class MeasurementResponse {

	public MeasurementResponse(String featureId, String linkeages, double centerX, double centerY, double maxZ) {
		super();
		this.featureId = featureId;
		this.linkeages = linkeages;
		this.centerX = centerX;
		this.centerY = centerY;
		this.maxZ = maxZ;
	}

	public MeasurementResponse() {
	}

	private String featureId;

	private String linkeages;

	private double centerX;

	private double centerY;

	private double maxZ;

	public String getFeatureId() {
		return featureId;
	}

	public void setFeatureId(String featureId) {
		this.featureId = featureId;
	}

	public String getLinkeages() {
		return linkeages;
	}

	public void setLinkeages(String linkeages) {
		this.linkeages = linkeages;
	}

	public double getCenterX() {
		return centerX;
	}

	public void setCenterX(double centerX) {
		this.centerX = centerX;
	}

	public double getCenterY() {
		return centerY;
	}

	public void setCenterY(double centerY) {
		this.centerY = centerY;
	}

	public double getMaxZ() {
		return maxZ;
	}

	public void setMaxZ(double maxZ) {
		this.maxZ = maxZ;
	}
}
