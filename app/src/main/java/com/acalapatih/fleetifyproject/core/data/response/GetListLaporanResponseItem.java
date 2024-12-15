package com.acalapatih.fleetifyproject.core.data.response;

import com.google.gson.annotations.SerializedName;

public class GetListLaporanResponseItem{

	@SerializedName("note")
	private String note;

	@SerializedName("createdAt")
	private String createdAt;

	@SerializedName("vehicleName")
	private String vehicleName;

	@SerializedName("reportId")
	private String reportId;

	@SerializedName("vehicleLicenseNumber")
	private String vehicleLicenseNumber;

	@SerializedName("createdBy")
	private String createdBy;

	@SerializedName("photo")
	private String photo;

	@SerializedName("reportStatus")
	private String reportStatus;

	@SerializedName("vehicleId")
	private String vehicleId;

	public void setNote(String note){
		this.note = note;
	}

	public String getNote(){
		return note;
	}

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public void setVehicleName(String vehicleName){
		this.vehicleName = vehicleName;
	}

	public String getVehicleName(){
		return vehicleName;
	}

	public void setReportId(String reportId){
		this.reportId = reportId;
	}

	public String getReportId(){
		return reportId;
	}

	public void setVehicleLicenseNumber(String vehicleLicenseNumber){
		this.vehicleLicenseNumber = vehicleLicenseNumber;
	}

	public String getVehicleLicenseNumber(){
		return vehicleLicenseNumber;
	}

	public void setCreatedBy(String createdBy){
		this.createdBy = createdBy;
	}

	public String getCreatedBy(){
		return createdBy;
	}

	public void setPhoto(String photo){
		this.photo = photo;
	}

	public String getPhoto(){
		return photo;
	}

	public void setReportStatus(String reportStatus){
		this.reportStatus = reportStatus;
	}

	public String getReportStatus(){
		return reportStatus;
	}

	public void setVehicleId(String vehicleId){
		this.vehicleId = vehicleId;
	}

	public String getVehicleId(){
		return vehicleId;
	}
}