package com.customfusionrestapi.dto;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JwtToken {

	public String iss;
	public String aud;
	public int exp;
	public int nbf;
	public String client_id;
	@JsonProperty("ingr.client_id")
	public String ingrClientId;
	public ArrayList<String> scope;
	public String sub;
	public int auth_time;
	public String idp;
	public String name;
	@JsonProperty("ingr.user_id")
	public String ingrUserId;
	@JsonProperty("ingr.session_id")
	public String ingrSessionId;
	public String email;
	public ArrayList<String> amr;

	public String getIss() {
		return iss;
	}

	public void setIss(String iss) {
		this.iss = iss;
	}

	public String getAud() {
		return aud;
	}

	public void setAud(String aud) {
		this.aud = aud;
	}

	public int getExp() {
		return exp;
	}

	public void setExp(int exp) {
		this.exp = exp;
	}

	public int getNbf() {
		return nbf;
	}

	public void setNbf(int nbf) {
		this.nbf = nbf;
	}

	public String getClient_id() {
		return client_id;
	}

	public void setClient_id(String client_id) {
		this.client_id = client_id;
	}

	public String getIngrClientId() {
		return ingrClientId;
	}

	public void setIngrClientId(String ingrClientId) {
		this.ingrClientId = ingrClientId;
	}

	public ArrayList<String> getScope() {
		return scope;
	}

	public void setScope(ArrayList<String> scope) {
		this.scope = scope;
	}

	public String getSub() {
		return sub;
	}

	public void setSub(String sub) {
		this.sub = sub;
	}

	public int getAuth_time() {
		return auth_time;
	}

	public void setAuth_time(int auth_time) {
		this.auth_time = auth_time;
	}

	public String getIdp() {
		return idp;
	}

	public void setIdp(String idp) {
		this.idp = idp;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIngrUserId() {
		return ingrUserId;
	}

	public void setIngrUserId(String ingrUserId) {
		this.ingrUserId = ingrUserId;
	}

	public String getIngrSessionId() {
		return ingrSessionId;
	}

	public void setIngrSessionId(String ingrSessionId) {
		this.ingrSessionId = ingrSessionId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public ArrayList<String> getAmr() {
		return amr;
	}

	public void setAmr(ArrayList<String> amr) {
		this.amr = amr;
	}

}