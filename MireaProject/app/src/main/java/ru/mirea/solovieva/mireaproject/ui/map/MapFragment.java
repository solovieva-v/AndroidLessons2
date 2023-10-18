package ru.mirea.solovieva.mireaproject.ui.map;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import ru.mirea.solovieva.mireaproject.OpenMapActivity;
import ru.mirea.solovieva.mireaproject.R;
import ru.mirea.solovieva.mireaproject.databinding.FragmentMapBinding;

public class MapFragment extends Fragment implements View.OnClickListener{
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    public MapFragment() {
        // Required empty public constructor
    }

    public static MapFragment newInstance(String param1, String param2) {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    @Override
    public void onStop(){
        super.onStop();
    }
    @Override
    public void onStart(){
        super.onStart();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_map, container, false);
        Button openMapButton = root.findViewById(R.id.button2);
        openMapButton.setOnClickListener(this);
        return root;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getActivity(), OpenMapActivity.class);
        startActivity(intent);
    }
}
