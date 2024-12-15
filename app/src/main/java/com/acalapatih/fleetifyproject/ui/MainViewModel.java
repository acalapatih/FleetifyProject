package com.acalapatih.fleetifyproject.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.acalapatih.fleetifyproject.core.data.network.ApiService;
import com.acalapatih.fleetifyproject.core.data.response.GetListLaporanResponseItem;
import com.acalapatih.fleetifyproject.core.data.response.GetListVehicleResponseItem;
import com.acalapatih.fleetifyproject.core.data.response.PostLaporanResponse;
import com.acalapatih.fleetifyproject.core.repository.ListLaporanRepository;
import com.acalapatih.fleetifyproject.core.repository.PostLaporanRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import okhttp3.MultipartBody;

@HiltViewModel
public class MainViewModel extends ViewModel {
    private final PostLaporanRepository postLaporanRepository;
    private final ListLaporanRepository listLaporanRepository;

    private final MutableLiveData<List<GetListLaporanResponseItem>> listLaporanLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<GetListVehicleResponseItem>> listVehicleLiveData = new MutableLiveData<>();
    private final MutableLiveData<PostLaporanResponse> postLaporanResponseLiveData = new MutableLiveData<>();

    @Inject
    public MainViewModel(PostLaporanRepository postLaporanRepository, ListLaporanRepository listLaporanRepository) {
        this.postLaporanRepository = postLaporanRepository;
        this.listLaporanRepository = listLaporanRepository;
    }

    public MutableLiveData<List<GetListLaporanResponseItem>> getListLaporanLiveData() {
        return listLaporanLiveData;
    }

    public MutableLiveData<List<GetListVehicleResponseItem>> getListVehicleLiveData() {
        return listVehicleLiveData;
    }

    public MutableLiveData<PostLaporanResponse> getPostLaporanResponse() {
        return postLaporanResponseLiveData;
    }

    public void makeApiCall() {
        listLaporanRepository.getListLaporan("XCfOTeuO1NlgTT0", listLaporanLiveData);
        listLaporanRepository.getListVehicle(listVehicleLiveData);
    }

    public void filterLaporanByVehicleName(String vehicleName) {
        if (listVehicleLiveData.getValue() != null) {
            List<GetListLaporanResponseItem> filteredList = new ArrayList<>();
            for (GetListLaporanResponseItem item : Objects.requireNonNull(listLaporanLiveData.getValue())) {
                if (item.getVehicleName().toLowerCase().contains(vehicleName.toLowerCase())) {
                    filteredList.add(item);
                }
            }
            listLaporanLiveData.setValue(filteredList);
        }
    }

    public void addLaporan(String vehicleId, String note, String userId, MultipartBody.Part photo) {
        postLaporanRepository.postAddLaporan(vehicleId, note, userId, photo, postLaporanResponseLiveData);
    }
}