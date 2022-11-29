package com.customfusionrestapi.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TagResponse {

	@JsonProperty("@odata.context")
	private String odataContext;
	private List<Object> value = new ArrayList<Object>();

	public String getOdataContext() {
		return odataContext;
	}

	public void setOdataContext(String odataContext) {
		this.odataContext = odataContext;
	}

	public List<Object> getValue() {
		return value;
	}

	public void setValue(List<Object> value) {
		this.value = value;
	}
}
