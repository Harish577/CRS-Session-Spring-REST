package com.customfusionrestapi.dto;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RolesResponse {

	@JsonProperty("@odata.context")
	public String odataContext;

	public String getOdataContext() {
		return odataContext;
	}

	public void setOdataContext(String odataContext) {
		this.odataContext = odataContext;
	}

	public ArrayList<Values> getValue() {
		return values;
	}

	public void setValue(ArrayList<Values> values) {
		this.values = values;
	}

	public ArrayList<Values> values;
}