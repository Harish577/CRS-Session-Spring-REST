package com.customfusionrestapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Value{
    @JsonProperty("FileId") 
    public String fileId;
    public String getFileId() {
		return fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getItemType() {
		return itemType;
	}
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	public String getContentLength() {
		return contentLength;
	}
	public void setContentLength(String contentLength) {
		this.contentLength = contentLength;
	}
	public Object getParentFileOBID() {
		return parentFileOBID;
	}
	public void setParentFileOBID(Object parentFileOBID) {
		this.parentFileOBID = parentFileOBID;
	}
	@JsonProperty("Id") 
    public String id;
    @JsonProperty("ItemType") 
    public String itemType;
    @JsonProperty("Purpose") 
    public String purpose;
    @JsonProperty("Uri") 
    public String uri;
    @JsonProperty("ContentLength") 
    public String contentLength;
    @JsonProperty("ParentFileOBID") 
    public Object parentFileOBID;
}