package com.customfusionrestapi.dto;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BinzResponse{
    @JsonProperty("@odata.context") 
    public String odataContext;
    public String getOdataContext() {
		return odataContext;
	}
	public void setOdataContext(String odataContext) {
		this.odataContext = odataContext;
	}
	public ArrayList<Value> getValue() {
		return value;
	}
	public void setValue(ArrayList<Value> value) {
		this.value = value;
	}
	public ArrayList<Value> value;
}


