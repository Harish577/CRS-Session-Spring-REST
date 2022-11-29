package com.customfusionrestapi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "ril_plant_database")
public class ReliancePlantDatabase {

	public ReliancePlantDatabase(Integer id, String linkage, String tag, String properties, String featureid,
			String unitname, String bounds, Double maxx, Double minx, Double maxy, Double miny, Double maxz,
			Double minz) {
		super();

		this.id = id;
		this.linkage = linkage;
		this.tag = tag;
		this.properties = properties;
		this.featureid = featureid;
		this.unitname = unitname;
		this.bounds = bounds;
		this.maxx = maxx;
		this.minx = minx;
		this.maxy = maxy;
		this.miny = miny;
		this.maxz = maxz;
		this.minz = minz;

	}

	public ReliancePlantDatabase() {
		// TODO Auto-generated constructor stub
	}

	@Id
	@Column()
	private Integer id;

	@Column
	private String linkage;

	@Column
	private String tag;

	@Lob
	@Column
	private String properties;

	@Column(length = 100000)
	private String featureid;

	@Column
	private String unitname;

	@Column
	private String bounds;

	@Column
	private Double maxx;

	@Column
	private Double minx;

	@Column
	private Double maxy;

	@Column
	private Double miny;

	@Column
	private Double maxz;

	@Column
	private Double minz;

	public String getLinkeage() {
		return linkage;
	}

	public void setLinkeage(String linkeage) {
		this.linkage = linkeage;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getProperties() {
		return properties;
	}

	public void setProperties(String properties) {
		this.properties = properties;
	}

	public String getFeatureid() {
		return featureid;
	}

	public void setFeatureid(String featureid) {
		this.featureid = featureid;
	}

	public String getUnitname() {
		return unitname;
	}

	public void setUnitname(String unitname) {
		this.unitname = unitname;
	}

	public String getBounds() {
		return bounds;
	}

	public void setBounds(String bounds) {
		this.bounds = bounds;
	}

	public Double getMaxx() {
		return maxx;
	}

	public void setMaxx(Double maxx) {
		this.maxx = maxx;
	}

	public Double getMinx() {
		return minx;
	}

	public void setMinx(Double minx) {
		this.minx = minx;
	}

	public Double getMaxy() {
		return maxy;
	}

	public void setMaxy(Double maxy) {
		this.maxy = maxy;
	}

	public Double getMiny() {
		return miny;
	}

	public void setMiny(Double miny) {
		this.miny = miny;
	}

	public Double getMaxz() {
		return maxz;
	}

	public void setMaxz(Double maxz) {
		this.maxz = maxz;
	}

	public Double getMinz() {
		return minz;
	}

	public void setMinz(Double minz) {
		this.minz = minz;
	}
}