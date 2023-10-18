package ru.mirea.solovieva.mireaproject.ui.gallery;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import ru.mirea.solovieva.mireaproject.databinding.FragmentGalleryBinding;

import android.widget.Toast;

import androidx.fragment.app.Fragment;
public class GalleryFragment extends Fragment {
    private static final int REQUEST_CODE_PERMISSION = 123;
    private boolean isWork = false;
    private Uri imageUri;
    private FragmentGalleryBinding binding;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        int cameraPermissionStatus = ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.CAMERA);
        int storagePermissionStatus = ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int galleryPermissionStatus = ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.READ_EXTERNAL_STORAGE);
        if (cameraPermissionStatus == PackageManager.PERMISSION_GRANTED && storagePermissionStatus == PackageManager.PERMISSION_GRANTED && galleryPermissionStatus == PackageManager.PERMISSION_GRANTED) {
            isWork = true;
        } else {
            requestPermissions(new String[]{android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_PERMISSION);
        }
        ActivityResultLauncher<Intent> cameraActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        binding.imageView.setImageURI(imageUri);
                    }
                }
        );

        // устанавливаем обработчик на кнопку для получения изображения на экран
        binding.button.setOnClickListener(v -> {
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            // проверка на наличие разрешений для камеры
            if (cameraIntent.resolveActivity(requireActivity().getPackageManager()) != null && isWork) {
                try {
                    File photoFile = createImageFile();
                    String authorities = requireContext().getPackageName() + ".fileprovider";
                    imageUri = FileProvider.getUriForFile(requireContext(), authorities, photoFile);
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    cameraActivityResultLauncher.launch(cameraIntent);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        // устанавливаем обработчик на кнопку для обрезки полученной фотографии
        binding.cropButton.setOnClickListener(v -> {
            if (imageUri != null) {
                cropImage(imageUri);
            } else {
                Toast.makeText(requireContext(), "No image to crop", Toast.LENGTH_SHORT).show();
            }
        });

        return binding.getRoot();
    }

    // добавляем метод для обрезки фотографии
    private void cropImage(Uri imageUri) {
        Intent cropIntent = new Intent("com.android.camera.action.CROP");
        cropIntent.setDataAndType(imageUri, "image/*");
        cropIntent.putExtra("crop", "true");
        // указываем соотношение сторон для обрезки (например, 1:1)
        cropIntent.putExtra("aspectX", 1);
        cropIntent.putExtra("aspectY", 1);
        // указываем размер выходного изображения (например, 300x300)
        cropIntent.putExtra("outputX", 300);
        cropIntent.putExtra("outputY", 300);
        cropIntent.putExtra("return-data", true);
        cropActivityResultLauncher.launch(cropIntent);
    }
    File photofile1 = null;

    // обновляем код слушателя ActivityResultLauncher для получения обрезанной фотографии
    ActivityResultLauncher<Intent> cameraActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    // обновляем URI изображения
                    imageUri = Uri.fromFile(photofile1);
                    // отображаем изображение в ImageView
                    displayImage(imageUri);
                }
            }
    );

    // добавляем новый слушатель ActivityResultLauncher для получения обрезанной фотографии
    ActivityResultLauncher<Intent> cropActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    // получаем обрезанное изображение
                    Bitmap croppedBitmap = result.getData().getParcelableExtra("data");
                    // отображаем обрезанное изображение в ImageView
                    binding.imageView.setImageBitmap(croppedBitmap);
                }
            }
    );

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ENGLISH).format(new Date());
        String imageFileName = "IMAGE_" + timeStamp + "_";
        File storageDirectory = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(imageFileName, ".jpg", storageDirectory);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSION) {
            isWork = grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED;
        }
    }

    // метод для отображения изображения в ImageView
    private void displayImage(Uri imageUri) {
        try {
            InputStream inputStream = requireContext().getContentResolver().openInputStream(imageUri);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            binding.imageView.setImageBitmap(bitmap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}