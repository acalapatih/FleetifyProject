package com.acalapatih.fleetifyproject.core.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.acalapatih.fleetifyproject.core.data.network.ApiService;
import com.acalapatih.fleetifyproject.core.data.response.GetListLaporanResponseItem;
import com.acalapatih.fleetifyproject.core.data.response.GetListVehicleResponseItem;
import com.google.gson.Gson;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Singleton
public class ListLaporanRepository {

    private final ApiService apiService;

    @Inject
    public ListLaporanRepository(ApiService apiService) {
        this.apiService = apiService;
    }

    public void getListLaporan(String userId, MutableLiveData<List<GetListLaporanResponseItem>> listLaporanLiveData) {
        Call<List<GetListLaporanResponseItem>> call = apiService.getListLaporan(userId);

        call.enqueue(new Callback<List<GetListLaporanResponseItem>>() {
            @Override
            public void onResponse(Call<List<GetListLaporanResponseItem>> call, Response<List<GetListLaporanResponseItem>> response) {
                if (response.isSuccessful()) {
                    List<GetListLaporanResponseItem> listLaporan = response.body();

                    if (listLaporan != null && !listLaporan.isEmpty()) {
                        Log.d("API Response", "Data received: " + new Gson().toJson(listLaporan));
                        listLaporanLiveData.postValue(listLaporan);
                    } else {
                        Log.d("API Response", "Laporan list is empty");
                        listLaporanLiveData.postValue(null);
                    }
                } else {
                    Log.d("API Response", "Response not successful: " + response.code() + " - " + response.message());
                    listLaporanLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<List<GetListLaporanResponseItem>> call, Throwable t) {
                Log.d("API Response", "Response not successful");
                listLaporanLiveData.postValue(null);
            }
        });
    }

    public void getListVehicle(MutableLiveData<List<GetListVehicleResponseItem>> listVehicleLiveData) {
        Call<List<GetListVehicleResponseItem>> call = apiService.getListVehicle();

        call.enqueue(new Callback<List<GetListVehicleResponseItem>>() {
            @Override
            public void onResponse(Call<List<GetListVehicleResponseItem>> call, Response<List<GetListVehicleResponseItem>> response) {
                if (response.isSuccessful()) {
                    List<GetListVehicleResponseItem> listVehicle = response.body();

                    if (listVehicle != null && !listVehicle.isEmpty()) {
                        Log.d("API Response", "Data received: " + new Gson().toJson(listVehicle));
                        listVehicleLiveData.postValue(listVehicle);
                        ;
                    } else {
                        Log.d("API Response", "Vechicle list is empty");
                        listVehicleLiveData.postValue(null);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<GetListVehicleResponseItem>> call, Throwable t) {
                Log.d("API Response", "Response not successful");
                listVehicleLiveData.postValue(null);
            }
        });
    }

    public interface DataCallback<T> {
        void onSuccess(T data);

        void onError(Exception e);
    }
}
