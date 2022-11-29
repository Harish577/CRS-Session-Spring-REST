package com.customfusionrestapi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "measuring_point_rilance")
//@JsonIgnoreProperties(ignoreUnknown = true)
public class MeasuringPoint {

	@Column
	private String type;

	@Column
	private String geometrytype;

	@Column
	private String geometrycoordinates;

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column
	private String measurementpoint;

	@Column
	private String corrosioncomponent;

	public MeasuringPoint() {

	}

	public MeasuringPoint(String type, String geometrytype, String geometrycoordinates, Integer id,
			String measurementpoint, String corrosioncomponent) {
		super();
		this.type = type;
		this.geometrytype = geometrytype;
		this.geometrycoordinates = geometrycoordinates;
		this.id = id;
		this.measurementpoint = measurementpoint;
		this.corrosioncomponent = corrosioncomponent;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getGeometrytype() {
		return geometrytype;
	}

	public void setGeometrytype(String geometrytype) {
		this.geometrytype = geometrytype;
	}

	public String getGeometrycoordinates() {
		return geometrycoordinates;
	}

	public void setGeometrycoordinates(String geometrycoordinates) {
		this.geometrycoordinates = geometrycoordinates;
	}

	public Integer getID() {
		return id;
	}

	public void setID(Integer id) {
		this.id = id;
	}

	public String getCorrosioncomponent() {
		return corrosioncomponent;
	}

	public void setCorrosioncomponent(String corrosioncomponent) {
		this.corrosioncomponent = corrosioncomponent;
	}

	public String getMeasurementpoint() {
		return measurementpoint;
	}

	public void setMeasurementpoint(String measurementpoint) {
		this.measurementpoint = measurementpoint;
	}

}
