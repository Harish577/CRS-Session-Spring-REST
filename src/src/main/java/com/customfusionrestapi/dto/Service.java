package com.customfusionrestapi.dto;

public class Service {
	public String id;
	public String title;
	public String name;
	public String type;
//	public String abstractText;
//	public ArrayList<String> keywords;
	public String status;
	public String endpointPath;
//	public Date creationTime;
//	public Date updateTime;
//	public Date startedTime;
//	public String isoMetadataXmlContent;
//	public boolean canDelete;
	public String endpointUrl;
	// public String preprocessingOutputPath;
	// public String meshCompression;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getEndpointPath() {
		return endpointPath;
	}

	public void setEndpointPath(String endpointPath) {
		this.endpointPath = endpointPath;
	}

	public String getEndpointUrl() {
		return endpointUrl;
	}

	public void setEndpointUrl(String endpointUrl) {
		this.endpointUrl = endpointUrl;
	}
}
