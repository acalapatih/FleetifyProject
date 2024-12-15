package com.acalapatih.fleetifyproject.core.data.response;

import com.google.gson.annotations.SerializedName;

public class GetListVehicleResponseItem {

    @SerializedName("licenseNumber")
    private String licenseNumber;

    @SerializedName("vehicleId")
    private String vehicleId;

    @SerializedName("type")
    private String type;

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public String getType() {
        return type;
    }
}