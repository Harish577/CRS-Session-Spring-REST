package com.customfusionrestapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Values {
	@JsonProperty("RoleUID")
	public String roleUID;

	public String getRoleUID() {
		return roleUID;
	}

	public void setRoleUID(String roleUID) {
		this.roleUID = roleUID;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	@JsonProperty("Description")
	public String description;
	@JsonProperty("DisplayName")
	public String displayName;
	@JsonProperty("Selected")
	public boolean selected;

	@Override
	public String toString() {
		return roleUID + " " + description + " " + displayName + " " + selected;
	}

}
