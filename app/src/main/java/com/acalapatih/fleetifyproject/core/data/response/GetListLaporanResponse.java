package com.acalapatih.fleetifyproject.core.data.response;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class GetListLaporanResponse {

    @SerializedName("reportId")
    private List<GetListLaporanResponseItem> laporanItems;

    public List<GetListLaporanResponseItem> getLaporanItems() {
        return laporanItems;
    }

    public void setLaporanItems(List<GetListLaporanResponseItem> laporanItems) {
        this.laporanItems = laporanItems;
    }
}