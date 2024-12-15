package com.acalapatih.fleetifyproject.ui;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.acalapatih.fleetifyproject.R;
import com.acalapatih.fleetifyproject.core.data.response.GetListLaporanResponseItem;
import com.acalapatih.fleetifyproject.databinding.RecyclerViewLaporanBinding;
import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ListLaporanAdapter extends RecyclerView.Adapter<ListLaporanAdapter.LaporanViewHolder> {
    private List<GetListLaporanResponseItem> listLaporan = new ArrayList<>();

    public void setListLaporan(List<GetListLaporanResponseItem> listLaporan) {
        this.listLaporan = listLaporan;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public LaporanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerViewLaporanBinding binding = RecyclerViewLaporanBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new LaporanViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull LaporanViewHolder holder, int position) {
        GetListLaporanResponseItem laporanItem = listLaporan.get(position);
        holder.bind(laporanItem);
    }

    @Override
    public int getItemCount() {
        return listLaporan.size();
    }

    static class LaporanViewHolder extends RecyclerView.ViewHolder {
        private final RecyclerViewLaporanBinding binding;

        public LaporanViewHolder(@NonNull RecyclerViewLaporanBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(GetListLaporanResponseItem laporanItem) {
            binding.tvReportId.setText(laporanItem.getReportId());

            String formattedDate = formatDate(laporanItem.getCreatedAt());
            binding.tvWaktuLaporan.setText(formattedDate);

            binding.tvStatusLaporan.setText(laporanItem.getReportStatus());
            binding.tvVehicleName.setText(laporanItem.getVehicleName());
            binding.tvReportBy.setText(laporanItem.getCreatedBy());
            binding.tvNomorPolisi.setText(laporanItem.getVehicleLicenseNumber());
            binding.tvKeluhan.setText(laporanItem.getNote());

            String imageUrl = laporanItem.getPhoto();
            Glide.with(binding.ivKeluhan.getContext())
                    .load(imageUrl)
                    .placeholder(R.drawable.ic_image)
                    .error(R.drawable.ic_image)
                    .into(binding.ivKeluhan);
        }

        private String formatDate(String dateString) {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            SimpleDateFormat outputFormat = new SimpleDateFormat("EEEE, dd MMM - HH.mm", new Locale("id", "ID"));

            try {
                Date date = inputFormat.parse(dateString);
                return outputFormat.format(date);
            } catch (ParseException e) {
                e.printStackTrace();
                return null;
            }
        }
    }
}