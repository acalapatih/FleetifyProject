package com.acalapatih.fleetifyproject.ui;

import static com.acalapatih.fleetifyproject.ui.CameraXUtil.createCustomTempFile;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.core.content.ContextCompat;

import com.acalapatih.fleetifyproject.databinding.ActivityCameraBinding;
import com.google.common.util.concurrent.ListenableFuture;

import java.io.File;
import java.util.concurrent.Executor;

public class CameraActivity extends AppCompatActivity {

    private ActivityCameraBinding binding;
    private CameraSelector cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA;
    private ImageCapture imageCapture = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCameraBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.captureImage.setOnClickListener(view -> takePhoto());
    }

    private void takePhoto() {
        if (imageCapture == null) {
            return;
        }

        File photoFile = createCustomTempFile(this);

        assert photoFile != null;
        ImageCapture.OutputFileOptions outputOptions =
                new ImageCapture.OutputFileOptions.Builder(photoFile).build();

        imageCapture.takePicture(
                outputOptions,
                ContextCompat.getMainExecutor(this),
                new ImageCapture.OnImageSavedCallback() {
                    @Override
                    public void onError(@NonNull ImageCaptureException exc) {
                        Toast.makeText(
                                CameraActivity.this,
                                "Gagal mengambil gambar.",
                                Toast.LENGTH_SHORT
                        ).show();
                    }

                    @Override
                    public void onCaptureProcessProgressed(int progress) {
                        ImageCapture.OnImageSavedCallback.super.onCaptureProcessProgressed(progress);
                    }

                    @Override
                    public void onPostviewBitmapAvailable(@NonNull Bitmap bitmap) {
                        ImageCapture.OnImageSavedCallback.super.onPostviewBitmapAvailable(bitmap);
                    }

                    @Override
                    public void onCaptureStarted() {
                        ImageCapture.OnImageSavedCallback.super.onCaptureStarted();
                    }

                    @Override
                    public void onImageSaved(@NonNull ImageCapture.OutputFileResults output) {
                        Intent intent = new Intent();
                        intent.putExtra("picture", photoFile);
                        intent.putExtra(
                                "isBackCamera",
                                cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA
                        );
                        setResult(MainActivity.CAMERA_X_RESULT, intent);
                        finish();
                    }
                }
        );
    }

    private void startCamera() {
        ListenableFuture<ProcessCameraProvider> cameraProviderFuture = ProcessCameraProvider.getInstance(this);
        Executor executor = ContextCompat.getMainExecutor(this);

        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();

                Preview preview = new Preview.Builder().build();
                preview.setSurfaceProvider(binding.viewFinder.getSurfaceProvider());

                imageCapture = new ImageCapture.Builder().build();

                cameraProvider.unbindAll();
                cameraProvider.bindToLifecycle(
                        CameraActivity.this,
                        cameraSelector,
                        preview,
                        imageCapture
                );

            } catch (Exception exc) {
                Toast.makeText(
                        CameraActivity.this,
                        "Gagal Menampilkan Kamera",
                        Toast.LENGTH_SHORT
                ).show();
            }
        }, executor);
    }

    private void uiSetting() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            WindowInsetsController insetsController = getWindow().getInsetsController();
            if (insetsController != null) {
                insetsController.hide(WindowInsets.Type.statusBars());
            }
        } else {
            getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN
            );
        }
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        uiSetting();
        startCamera();
    }
}