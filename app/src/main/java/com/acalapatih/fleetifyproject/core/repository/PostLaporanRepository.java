package com.acalapatih.fleetifyproject.core.repository;

import androidx.lifecycle.MutableLiveData;

import com.acalapatih.fleetifyproject.core.data.network.ApiService;
import com.acalapatih.fleetifyproject.core.data.response.PostLaporanResponse;

import java.io.File;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Singleton
public class PostLaporanRepository {
    private final ApiService apiService;

    @Inject
    public PostLaporanRepository(ApiService apiService) {
        this.apiService = apiService;
    }

    public void postAddLaporan(String vehicleId, String note, String userId, MultipartBody.Part photo, MutableLiveData<PostLaporanResponse> postLaporanResponseLiveData) {
        RequestBody vehicleIdRequest = RequestBody.create(MediaType.parse("text/plain"), vehicleId);
        RequestBody noteRequest = RequestBody.create(MediaType.parse("text/plain"), note);
        RequestBody userIdRequest = RequestBody.create(MediaType.parse("text/plain"), userId);

        Call<PostLaporanResponse> call = apiService.postAddLaporan(vehicleIdRequest, noteRequest, userIdRequest, photo);
        call.enqueue(new Callback<PostLaporanResponse>() {
            @Override
            public void onResponse(Call<PostLaporanResponse> call, Response<PostLaporanResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    postLaporanResponseLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<PostLaporanResponse> call, Throwable t) {
                // Handle error jika request gagal
                postLaporanResponseLiveData.setValue(null);
            }
        });
    }
}
