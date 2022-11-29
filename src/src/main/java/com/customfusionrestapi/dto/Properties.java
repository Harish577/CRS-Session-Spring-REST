package com.customfusionrestapi.dto;


public class Properties {
	private String featureID;
	private String linkage;

	// ---
	private String name;

	private String nextInspectionDate;
	private String remainingLife;
	private String controllingCorrosionRate;
	private String component;
	private String measurementTakenDate;
	private String readings;
	private String inspectionStartDate;
	private String inspectionHeadline;
	private String inspectionReference;
	private String minimumThickness;
	private String config;
	private String id;
	
	private String measurementPoint;
	private String corrosionComponent;

	public String typeOfEvent;

	private String centerX;

	public String getMeasurementPoint() {
		return measurementPoint;
	}

	public void setMeasurementPoint(String measurementPoint) {
		this.measurementPoint = measurementPoint;
	}

	public String getCorrosionComponent() {
		return corrosionComponent;
	}

	public void setCorrosionComponent(String corrosionComponent) {
		this.corrosionComponent = corrosionComponent;
	}

	public String getCenterX() {
		return centerX;
	}

	public void setCenterX(String centerX) {
		this.centerX = centerX;
	}

	public String getCenterY() {
		return centerY;
	}

	public void setCenterY(String centerY) {
		this.centerY = centerY;
	}

	private String centerY;

	public String getTypeOfEvent() {
		return typeOfEvent;
	}

	public void setTypeOfEvent(String typeOfEvent) {
		this.typeOfEvent = typeOfEvent;
	}

	public Object getEventStartDate() {
		return eventStartDate;
	}

	public void setEventStartDate(Object eventStartDate) {
		this.eventStartDate = eventStartDate;
	}

	public Object getEventEndDate() {
		return eventEndDate;
	}

	public void setEventEndDate(Object eventEndDate) {
		this.eventEndDate = eventEndDate;
	}

	public Object getRemarks() {
		return remarks;
	}

	public void setRemarks(Object remarks) {
		this.remarks = remarks;
	}

	public Object getAuthorizedPersonnel() {
		return authorizedPersonnel;
	}

	public void setAuthorizedPersonnel(Object authorizedPersonnel) {
		this.authorizedPersonnel = authorizedPersonnel;
	}

	public Object getRestrictAccess() {
		return restrictAccess;
	}

	public void setRestrictAccess(Object restrictAccess) {
		this.restrictAccess = restrictAccess;
	}

	public Object eventStartDate;
	public Object eventEndDate;
	public Object remarks;
	public Object authorizedPersonnel;
	public Object restrictAccess;

	public String getFeatureID() {
		return featureID;
	}

	public void setFeatureID(String featureID) {
		this.featureID = featureID;
	}

	public String getLinkage() {
		return linkage;
	}

	public void setLinkage(String linkage) {
		this.linkage = linkage;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNextInspectionDate() {
		return nextInspectionDate;
	}

	public void setNextInspectionDate(String nextInspectionDate) {
		this.nextInspectionDate = nextInspectionDate;
	}

	public String getRemainingLife() {
		return remainingLife;
	}

	public void setRemainingLife(String remainingLife) {
		this.remainingLife = remainingLife;
	}

	public String getControllingCorrosionRate() {
		return controllingCorrosionRate;
	}

	public void setControllingCorrosionRate(String controllingCorrosionRate) {
		this.controllingCorrosionRate = controllingCorrosionRate;
	}

	public String getComponent() {
		return component;
	}

	public void setComponent(String component) {
		this.component = component;
	}

	public String getMeasurementTakenDate() {
		return measurementTakenDate;
	}

	public void setMeasurementTakenDate(String measurementTakenDate) {
		this.measurementTakenDate = measurementTakenDate;
	}

	public String getReadings() {
		return readings;
	}

	public void setReadings(String readings) {
		this.readings = readings;
	}

	public String getInspectionStartDate() {
		return inspectionStartDate;
	}

	public void setInspectionStartDate(String inspectionStartDate) {
		this.inspectionStartDate = inspectionStartDate;
	}

	public String getInspectionHeadline() {
		return inspectionHeadline;
	}

	public void setInspectionHeadline(String inspectionHeadline) {
		this.inspectionHeadline = inspectionHeadline;
	}

	public String getInspectionReference() {
		return inspectionReference;
	}

	public void setInspectionReference(String inspectionReference) {
		this.inspectionReference = inspectionReference;
	}

	public String getMinimumThickness() {
		return minimumThickness;
	}

	public void setMinimumThickness(String minimumThickness) {
		this.minimumThickness = minimumThickness;
	}

	public String getConfig() {
		return config;
	}

	public void setConfig(String config) {
		this.config = config;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}