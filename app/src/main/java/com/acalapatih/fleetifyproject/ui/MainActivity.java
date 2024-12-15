package com.acalapatih.fleetifyproject.ui;

import static com.acalapatih.fleetifyproject.ui.CameraXUtil.rotateFile;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.Manifest;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.text.SimpleDateFormat;

import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.acalapatih.fleetifyproject.core.data.response.GetListLaporanResponseItem;
import com.acalapatih.fleetifyproject.core.data.response.GetListVehicleResponseItem;
import com.acalapatih.fleetifyproject.databinding.ActivityMainBinding;
import com.acalapatih.fleetifyproject.databinding.DialogLaporanKeluhanBinding;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import dagger.hilt.android.AndroidEntryPoint;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    public DialogLaporanKeluhanBinding dialogBinding;
    public File getFile;
    public static final int CAMERA_X_RESULT = 200;
    private static final String[] REQUIRED_PERMISSION = new String[]{Manifest.permission.CAMERA};
    private static final int REQUEST_CODE_PERMISSION = 10;
    private final String userId = "XCfOTeuO1NlgTT0";

    private ListLaporanAdapter listLaporanAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (allPermissionGranted()){
            ActivityCompat.requestPermissions(
                    this,
                    REQUIRED_PERMISSION,
                    REQUEST_CODE_PERMISSION
            );
        }

        MainViewModel viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        viewModel.makeApiCall();

        initView();
        initViewModel(viewModel);
        initListener(viewModel);
        initSwipeRefreshLayout(viewModel);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSION) {
            if (allPermissionGranted()) {
                Toast.makeText(this, "Semua izin diberikan.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Izin tidak diberikan!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean allPermissionGranted() {
        for (String permission : REQUIRED_PERMISSION) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    protected void initView() {
        listLaporanAdapter = new ListLaporanAdapter();
        RecyclerView recyclerView = binding.rvLaporanKeluhan;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listLaporanAdapter);
    }

    private void initViewModel(MainViewModel viewModel) {
        viewModel.getListLaporanLiveData().observe(this, new Observer<List<GetListLaporanResponseItem>>() {
            @Override
            public void onChanged(List<GetListLaporanResponseItem> getListLaporanResponseItems) {
                Log.d("MainActivity", "Data received: " + (getListLaporanResponseItems != null ? getListLaporanResponseItems.size() : "null"));
                if (getListLaporanResponseItems != null) {
                        listLaporanAdapter.setListLaporan(getListLaporanResponseItems);
                        listLaporanAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(MainActivity.this, "Gagal Memuat Data", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    protected void initListener(MainViewModel viewModel) {
        binding.btnBuatLaporan.setOnClickListener(view -> showDialogFormKeluhan(viewModel));
        binding.btnSearch.setOnClickListener(view -> {
            String searchQuery = binding.etSearch.getText().toString().trim();
            if (!searchQuery.isEmpty()) {
                viewModel.filterLaporanByVehicleName(searchQuery);
            } else {
                Toast.makeText(MainActivity.this, "Masukkan Nama Kendaraan", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initSwipeRefreshLayout(MainViewModel viewModel) {
        binding.swipeRefreshLayout.setOnRefreshListener(() -> {
            viewModel.makeApiCall();
            Toast.makeText(this, "Data Sedang Dimuat Ulang", Toast.LENGTH_SHORT).show();
            binding.swipeRefreshLayout.setRefreshing(false);
        });
    }

    protected void showDialogFormKeluhan(MainViewModel viewModel) {
        Dialog dialog = new Dialog(this);
        dialogBinding = DialogLaporanKeluhanBinding.inflate(getLayoutInflater());
        Window window = dialog.getWindow();

        if (window != null) {
            window.setBackgroundDrawableResource(android.R.color.transparent);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        }
        dialog.setContentView(dialogBinding.getRoot());

        int deviceWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
        int margin = (int) (60 * Resources.getSystem().getDisplayMetrics().density);
        int width = deviceWidth - margin;

        if (window != null) {
            window.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        dialog.setCancelable(false);
        dialog.show();

        dialogBinding.ivClose.setOnClickListener(view -> dialog.dismiss());

        Date currentDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd MMM - HH.mm", new Locale("id", "ID"));
        String formattedDate = sdf.format(currentDate);
        dialogBinding.tvTanggalLaporan.setText(formattedDate);

        viewModel.getListVehicleLiveData().observe(this, new Observer<List<GetListVehicleResponseItem>>() {
            @Override
            public void onChanged(List<GetListVehicleResponseItem> getListVehicleResponseItems) {
                if (getListVehicleResponseItems != null) {
                    List<String> vehicleId = new ArrayList<>();
                    List<String> vehicleTypes = new ArrayList<>();
                    List<String> vehicleData = new ArrayList<>();

                    for (GetListVehicleResponseItem item : getListVehicleResponseItems) {
                        vehicleId.add(item.getVehicleId());
                    }

                    for (GetListVehicleResponseItem item : getListVehicleResponseItems) {
                        vehicleTypes.add(item.getType());
                    }

                    for (int i = 0; i < vehicleId.size(); i++) {
                        String combined = vehicleId.get(i) + " - " + vehicleTypes.get(i);
                        vehicleData.add(combined);
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(
                            MainActivity.this,
                            android.R.layout.simple_dropdown_item_1line,
                            vehicleData
                    );

                    dialogBinding.autoCompleteText.setAdapter(adapter);
                }
            }
        });

        dialogBinding.btnAmbilFoto.setOnClickListener(view -> runCameraX());

        dialogBinding.btnKirimLaporan.setOnClickListener(view -> {
            String selectedVehicle = dialogBinding.autoCompleteText.getText().toString();
            if (selectedVehicle.isEmpty()) {
                Toast.makeText(MainActivity.this, "Pilih kendaraan terlebih dahulu", Toast.LENGTH_SHORT).show();
                return;
            }
            String vehicleId = selectedVehicle.split(" - ")[0];

            String note = dialogBinding.etCatatanKeluhan.getText().toString();
            if (note.isEmpty()) {
                Toast.makeText(MainActivity.this, "Masukkan catatan keluhan", Toast.LENGTH_SHORT).show();
                return;
            }

            if (getFile == null) {
                Toast.makeText(MainActivity.this, "Ambil foto keluhan terlebih dahulu", Toast.LENGTH_SHORT).show();
                return;
            }
            File reducedFile = CameraXUtil.reduceFileImage(getFile);

            MultipartBody.Part photoBody = MultipartBody.Part.createFormData(
                    "photo",
                    reducedFile.getName(),
                    RequestBody.create(MediaType.parse("image/jpeg"), reducedFile)
            );

            viewModel.addLaporan(vehicleId, note, userId, photoBody);
            Toast.makeText(MainActivity.this, "Berhasil Menambahkan Laporan", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        });
    }

    protected void runCameraX() {
        Intent intent = new Intent(this, CameraActivity.class);
        launcherIntentCameraX.launch(intent);
    }

    ActivityResultLauncher<Intent> launcherIntentCameraX = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == CAMERA_X_RESULT) {
                    Intent data = result.getData();

                    File myFile;
                    assert data != null;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        myFile = data.getSerializableExtra("picture", File.class);
                    } else {
                        myFile = (File) data.getSerializableExtra("picture");
                    }

                    boolean isBackCamera = data.getBooleanExtra("isBackCamera", true);

                    if (myFile != null) {
                        rotateFile(myFile, isBackCamera);
                        getFile = myFile;
                        dialogBinding.ivKeluhan.setImageBitmap(BitmapFactory.decodeFile(myFile.getPath()));
                        dialogBinding.btnKirimLaporan.setEnabled(true);
                    }
                }
            }
    );
}