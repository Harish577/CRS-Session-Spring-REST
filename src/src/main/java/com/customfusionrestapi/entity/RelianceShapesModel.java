package com.customfusionrestapi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "sa_modules_shapesdata_ril")
public class RelianceShapesModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Integer id;

	@Column
	private Double radius;

	@Column
	private String x;

	@Column
	private String y;

	@Column
	private String width;

	@Column
	private String height;

	@Column
	private String coordinates;

	@Column
	private String minimumheight;
	@Column
	private String maximumheight;

	@Column
	private String typeofevent;

	@Column
	private String eventstartdate;

	@Column
	private String eventenddate;

	public RelianceShapesModel() {
	}

	@Override
	public String toString() {
		return "ShapesModel [id=" + id + ", radius=" + radius + ", x=" + x + ", y=" + y + ", width=" + width
				+ ", height=" + height + ", coordinates=" + coordinates + ", minimumHeight=" + minimumheight
				+ ", maximumHeight=" + maximumheight + ", typeOfEvent=" + typeofevent + ", eventStartDate="
				+ eventstartdate + ", eventEndDate=" + eventenddate + "]";
	}

	public RelianceShapesModel(Integer id, Double radius, String x, String y, String width, String height,
			String coordinates, String minimumHeight, String maximumHeight, String typeOfEvent, String eventStartDate,
			String eventEndDate) {
		super();
		this.id = id;
		this.radius = radius;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.coordinates = coordinates;
		this.minimumheight = minimumHeight;
		this.maximumheight = maximumHeight;
		this.typeofevent = typeOfEvent;
		this.eventstartdate = eventStartDate;
		this.eventenddate = eventEndDate;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Double getRadius() {
		return radius;
	}

	public void setRadius(Double radius) {
		this.radius = radius;
	}

	public String getX() {
		return x;
	}

	public void setX(String x) {
		this.x = x;
	}

	public String getY() {
		return y;
	}

	public void setY(String y) {
		this.y = y;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(String coordinates) {
		this.coordinates = coordinates;
	}

	public String getMinimumHeight() {
		return minimumheight;
	}

	public void setMinimumHeight(String minimumHeight) {
		this.minimumheight = minimumHeight;
	}

	public String getMaximumHeight() {
		return maximumheight;
	}

	public void setMaximumHeight(String maximumHeight) {
		this.maximumheight = maximumHeight;
	}

	public String getTypeOfEvent() {
		return typeofevent;
	}

	public void setTypeOfEvent(String typeOfEvent) {
		this.typeofevent = typeOfEvent;
	}

	public String getEventStartDate() {
		return eventstartdate;
	}

	public void setEventStartDate(String eventStartDate) {
		this.eventstartdate = eventStartDate;
	}

	public String getEventEndDate() {
		return eventenddate;
	}

	public void setEventEndDate(String eventEndDate) {
		this.eventenddate = eventEndDate;
	}

}
