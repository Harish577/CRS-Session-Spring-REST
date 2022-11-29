/*
 * package com.custom.elastic.search.model;
 * 
 * import org.springframework.data.annotation.Id; import
 * org.springframework.data.elasticsearch.annotations.Document; import
 * org.springframework.http.ResponseEntity;
 * 
 * 
 * @Document(indexName ="test1",shards = 1) public class PlantInfo {
 * 
 * @Id private Integer id; private String moniker;
 * 
 * private String tag;
 * 
 * private String properties; private Long featureid; private String unitname;
 * private String bounds; private Double maxx; private Double minx;
 * 
 * @Override public String toString() { return "PlantInfo [id=" + id +
 * ", moniker=" + moniker + ", tag=" + tag + ", properties=" + properties +
 * ", featureid=" + featureid + ", unitname=" + unitname + ", bounds=" + bounds
 * + ", maxx=" + maxx + ", minx=" + minx + ", maxy=" + maxy + ", miny=" + miny +
 * ", maxz=" + maxz + ", minz=" + minz + "]"; } public Long getFeatureid() {
 * return featureid; } public void setFeatureid(Long featureid) { this.featureid
 * = featureid; } private Double maxy; private Double miny; private Double maxz;
 * private Double minz; public Integer getId() { return id; } public void
 * setId(Integer id) { this.id = id; } public String getMoniker() { return
 * moniker; } public void setMoniker(String moniker) { this.moniker = moniker; }
 * public String getTag() { return tag; } public void setTag(String tag) {
 * this.tag = tag; } public String getProperties() { return properties; } public
 * void setProperties(String properties) { this.properties = properties; }
 * 
 * 
 * public String getUnitname() { return unitname; } public void
 * setUnitname(String unitname) { this.unitname = unitname; } public String
 * getBounds() { return bounds; } public void setBounds(String bounds) {
 * this.bounds = bounds; } public Double getMaxx() { return maxx; } public void
 * setMaxx(Double maxx) { this.maxx = maxx; } public Double getMinx() { return
 * minx; } public void setMinx(Double minx) { this.minx = minx; } public Double
 * getMaxy() { return maxy; } public void setMaxy(Double maxy) { this.maxy =
 * maxy; } public Double getMiny() { return miny; } public void setMiny(Double
 * miny) { this.miny = miny; } public Double getMaxz() { return maxz; } public
 * void setMaxz(Double maxz) { this.maxz = maxz; } public Double getMinz() {
 * return minz; } public void setMinz(Double minz) { this.minz = minz; }
 * 
 * 
 * 
 * 
 * 
 * }
 */





package com.custom.elastic.search.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName ="test1",shards = 1) public class PlantInfo{



	// define fields
	@Id 
	private Integer id;


	private String moniker;

	@Override
	public String toString() {
		return "PlantInfoModel [id=" + id + ", moniker=" + moniker + ", tag=" + tag + ", properties=" + properties
				+ ", featureid=" + featureid + ", unitname=" + unitname + ", bounds=" + bounds + ", maxx=" + maxx
				+ ", minx=" + minx + ", maxy=" + maxy + ", miny=" + miny + ", maxz=" + maxz + ", minz=" + minz + "]";
	}






	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getMoniker() {
		return moniker;
	}


	public void setMoniker(String moniker) {
		this.moniker = moniker;
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


	public Long getFeatureid() {
		return featureid;
	}


	public void setFeatureid(Long featureid) {
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


	public PlantInfo(Integer id, String moniker, String tag, String properties, long featureid, String unitname,
			String bounds, Double maxx, Double minx, Double maxy, Double miny, Double maxz, Double minz) {
		super();
		this.id = id;
		this.moniker = moniker;
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


	public PlantInfo() {

	}



	private String tag;


	private String properties;


	private Long featureid;


	private String unitname;


	private String bounds;


	private Double maxx;


	private Double minx;


	private Double maxy;


	private Double miny;


	private Double maxz;



	private Double minz;








}

