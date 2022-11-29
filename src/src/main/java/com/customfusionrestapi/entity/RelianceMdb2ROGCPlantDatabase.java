package com.customfusionrestapi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "rilance_plant_rogc")
public class RelianceMdb2ROGCPlantDatabase {

	public RelianceMdb2ROGCPlantDatabase(Integer id, String linkage, String tag, String equipment, String properties, String featureid,
			String originalbounds, String centerbounds, String boundarybounds, Double maxx, Double minx, Double maxy,
			Double miny, Double maxz, Double minz) {
		super();

		this.id = id;
		this.linkage = linkage;
		this.tag = tag;
		this.equipment = equipment;
		this.properties = properties;
		this.featureid = featureid;
		this.originalbounds = originalbounds;
		this.centerbounds = centerbounds;
		this.boundarybounds = boundarybounds;
		this.maxx = maxx;
		this.minx = minx;
		this.maxy = maxy;
		this.miny = miny;
		this.maxz = maxz;
		this.minz = minz;

	}

	public RelianceMdb2ROGCPlantDatabase() {
		// TODO Auto-generated constructor stub
	}

	@Id
	@Column
	private Integer id;

	@Column
	private String linkage;

	@Lob
	@Column
	private String tag;

	@Lob
	@Column
	private String properties;

	@Lob
	@Column
	private String equipment;

	@Column(length = 100000)
	private String featureid;

	@Column
	private String originalbounds;

	@Column
	private String centerbounds;

	@Column
	private String boundarybounds;

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

	public String getEquipment() {
		return equipment;
	}

	public void setEquipment(String equipment) {
		this.equipment = equipment;
	}

	public String getFeatureid() {
		return featureid;
	}

	public void setFeatureid(String featureid) {
		this.featureid = featureid;
	}

	public String getOriginalBounds() {
		return originalbounds;
	}

	public void setOriginalBounds(String originalbounds) {
		this.originalbounds = originalbounds;
	}

	public String getCenterBounds() {
		return centerbounds;
	}

	public void setCenterBounds(String centerbounds) {
		this.centerbounds = centerbounds;
	}

	public String getBoundaryBounds() {
		return boundarybounds;
	}

	public void setBoundaryBounds(String boundarybounds) {
		this.boundarybounds = boundarybounds;
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