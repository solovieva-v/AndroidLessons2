package ru.mirea.solovieva.mireaproject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.IOException;

public class FilesFragment extends Fragment {
    private static final int REQUEST_CODE_PICK_JPG_FILE = 1;
    private static final String TAG = "FilesFragment";

    private Button mBtnSelectJpg;
    private Button mBtnConvertJpgToPng;
    private String mJpgFilePath;
    private String mPngFilePath;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_files, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mBtnSelectJpg = view.findViewById(R.id.btn_select_jpg);
        mBtnConvertJpgToPng = view.findViewById(R.id.btn_convert_jpg);
        mBtnSelectJpg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/jpeg");
                startActivityForResult(intent, REQUEST_CODE_PICK_JPG_FILE);
            }
        });

        mBtnConvertJpgToPng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mJpgFilePath == null) {
                    Toast.makeText(getActivity(), "Выберите  JPG файл  ", Toast.LENGTH_SHORT).show();
                    return;
                }
                boolean success = convertJpgToPng(mJpgFilePath, mPngFilePath);
                if (success) {
                    Toast.makeText(getActivity(), " Конвертация удалась", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), " Конвертация не удалась", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PICK_JPG_FILE && resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();
            if (uri != null) {
                mJpgFilePath = uri.getPath();
                Log.d(TAG, "Выбор JPG : " + mJpgFilePath);
            }
        }
    }

    public boolean convertJpgToPng(String jpgFilePath, String pngFilePath) {
        // Создаем Bitmap из файла JPG
        Bitmap bitmap = BitmapFactory.decodeFile(jpgFilePath);
        if (bitmap == null) {
            return false;
        }

        // Сохраняем Bitmap в файл PNG
        try (FileOutputStream outputStream = new FileOutputStream(pngFilePath)) {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            outputStream.flush();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
