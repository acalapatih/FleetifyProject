package com.acalapatih.fleetifyproject.core.data.response;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class GetListVehicleResponse{

	@SerializedName("GetListVehicleResponse")
	private List<GetListVehicleResponseItem> getListVehicleResponse;

	public List<GetListVehicleResponseItem> getGetListVehicleResponse(){
		return getListVehicleResponse;
	}
}