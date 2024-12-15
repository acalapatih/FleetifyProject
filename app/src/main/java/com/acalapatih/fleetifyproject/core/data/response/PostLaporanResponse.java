package com.acalapatih.fleetifyproject.core.data.response;

import com.google.gson.annotations.SerializedName;

public class PostLaporanResponse{

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private boolean status;

	public String getMessage(){
		return message;
	}

	public boolean isStatus(){
		return status;
	}
}