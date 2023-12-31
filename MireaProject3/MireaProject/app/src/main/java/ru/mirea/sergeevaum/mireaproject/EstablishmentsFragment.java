package ru.mirea.sergeevaum.mireaproject;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import ru.mirea.sergeevaum.mireaproject.databinding.FragmentEstablishmentsBinding;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EstablishmentsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EstablishmentsFragment extends Fragment {
    private FragmentEstablishmentsBinding binding;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EstablishmentsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EstablishmentsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EstablishmentsFragment newInstance(String param1, String param2) {
        EstablishmentsFragment fragment = new EstablishmentsFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FragmentEstablishmentsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        Intent intent = new Intent(getContext(), PlaceMap.class);
        startActivity(intent);
        return view;
    }
}