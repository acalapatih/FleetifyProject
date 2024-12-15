package com.acalapatih.fleetifyproject.core.data.network;

import com.acalapatih.fleetifyproject.core.data.response.GetListLaporanResponseItem;
import com.acalapatih.fleetifyproject.core.data.response.GetListVehicleResponseItem;
import com.acalapatih.fleetifyproject.core.data.response.PostLaporanResponse;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiService {
    @GET("read_all_laporan")
    Call<List<GetListLaporanResponseItem>> getListLaporan(
            @Query("userId") String userId
    );

    @GET("list_vehicle")
    Call<List<GetListVehicleResponseItem>> getListVehicle();

    @Multipart
    @POST("add_laporan")
    Call<PostLaporanResponse> postAddLaporan(
            @Part("vehicleId") RequestBody vehicleId,
            @Part("note") RequestBody note,
            @Part("userId") RequestBody userId,
            @Part MultipartBody.Part photo
    );
}

