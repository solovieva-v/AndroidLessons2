package ru.mirea.solovieva.mireaproject;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;


public class SaveFileFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_save_file, container, false);
        filenameEditText = rootView.findViewById(R.id.filenameEditText);
        quoteEditText = rootView.findViewById(R.id.quoteEditText);

        Button saveButton = rootView.findViewById(R.id.saveButton);
        saveButton.setBackgroundColor(Color.WHITE);
        saveButton.setTextColor(Color.BLACK);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDataToFile();
            }
        });

        Button loadButton = rootView.findViewById(R.id.loadButton);
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadDataFromFile();
            }
        });
        return rootView;
    };

    private void saveDataToFile() {
        String filename = filenameEditText.getText().toString();
        String quote = quoteEditText.getText().toString();

        if (!filename.isEmpty() && !quote.isEmpty()) {
            File directory = getActivity().getExternalFilesDir("Directory_Documents");
            if (directory != null) {
                File file = new File(directory, filename);

                try {
                    FileOutputStream fos = new FileOutputStream(file);
                    fos.write(quote.getBytes());
                    fos.close();
                    Toast.makeText(getContext(), "Данные сохранены в файл", Toast.LENGTH_SHORT).show();

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Ошибка при сохранении данных", Toast.LENGTH_SHORT).show();

                }
            }
        } else {
            Toast.makeText(getContext(), "Пожалуйста, введите название файла и цитату из фильма", Toast.LENGTH_SHORT).show();
        }
    }
    private EditText filenameEditText;
    private EditText quoteEditText;
    private void loadDataFromFile() {
        String filename = filenameEditText.getText().toString();

        if (!filename.isEmpty()) {
            File directory = getActivity().getExternalFilesDir("Directory_Documents");
            if (directory != null) {
                File file = new File(directory, filename);

                if (file.exists()) {
                    try {
                        BufferedReader reader = new BufferedReader(new FileReader(file));
                        StringBuilder sb = new StringBuilder();
                        String line;

                        while ((line = reader.readLine()) != null) {
                            sb.append(line);
                            sb.append("\n");
                        }

                        reader.close();
                        quoteEditText.setText(sb.toString().trim());
                        Toast.makeText(getContext(), "Данные загружены из файла", Toast.LENGTH_SHORT).show();

                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(getContext(), "Ошибка при загрузке данных", Toast.LENGTH_SHORT).show();


                    }
                } else {
                    Toast.makeText(getContext(), "Файл не существует", Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            Toast.makeText(getContext(), "Пожалуйста, введите название файла", Toast.LENGTH_SHORT).show();
        }
    }
}