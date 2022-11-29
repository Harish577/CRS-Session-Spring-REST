package com.customfusionrestapi.dto;

import java.util.List;

public class BinzBody {

	private List purposes;

	public List getPurposes() {
		return purposes;
	}

	public void setPurposes(List purposes) {
		this.purposes = purposes;
	}

	public boolean isDownloadFile() {
		return downloadFile;
	}

	public void setDownloadFile(boolean downloadFile) {
		this.downloadFile = downloadFile;
	}

	private boolean downloadFile;
}
