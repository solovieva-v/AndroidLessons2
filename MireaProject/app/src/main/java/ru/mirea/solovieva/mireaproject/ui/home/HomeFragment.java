package ru.mirea.solovieva.mireaproject.ui.home;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.security.keystore.KeyGenParameterSpec;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.security.GeneralSecurityException;
import androidx.fragment.app.Fragment;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;

import ru.mirea.solovieva.mireaproject.R;
public class HomeFragment extends Fragment {
    private SharedPreferences secureSharedPreferences;
    private String poetName;
    private ImageView poetPhotoView;
    private TextView poetNameView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initializeSecureSharedPreferences(view);
        saveSecureData();
        loadSecureData(view);
        return view;
    }

    private void initializeSecureSharedPreferences(View view) {
        try {
            KeyGenParameterSpec keyGenParameterSpec = MasterKeys.AES256_GCM_SPEC;
            String mainKeyAlias = MasterKeys.getOrCreate(keyGenParameterSpec);
            secureSharedPreferences = EncryptedSharedPreferences.create(
                    "secret_shared_prefs",
                    mainKeyAlias,
                    requireContext(),
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );
        } catch (GeneralSecurityException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void saveSecureData() {
        secureSharedPreferences.edit().putString("secure", "Добро пожаловать").apply();
    }

    private void loadSecureData(View view) {
        poetName = secureSharedPreferences.getString("secure", "Добро пожаловать");
        poetNameView = view.findViewById(R.id.poet_name_view);
        poetNameView.setText(poetName);
        poetPhotoView = view.findViewById(R.id.poet_photo_view);
    }
}