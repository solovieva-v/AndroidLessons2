package ru.mirea.sergeevaum.mireaproject;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.mirea.sergeevaum.mireaproject.databinding.FragmentApplicationBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Application#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Application extends Fragment {

    FragmentApplicationBinding binding;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private RecyclerView recyclerView;
    private List<ApplicationInfo> appList;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Context mContext;

    public Application() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Application.
     */
    // TODO: Rename and change types and number of parameters
    public static Application newInstance(String param1, String param2) {
        Application fragment = new Application();
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
        binding = FragmentApplicationBinding.inflate(inflater, container, false);
        View v = binding.getRoot();
        mContext = inflater.getContext();
        PackageManager packageManager = mContext.getPackageManager();
        List<ApplicationInfo> installedApplications = packageManager.getInstalledApplications(0);

        for (ApplicationInfo applicationInfo : installedApplications) {
            String packageName = applicationInfo.packageName;
            Log.d("Package Name", "Package Name: " + packageName);
        }
        //
        PackageManager packageManager2 = mContext.getPackageManager();
        try {
            packageManager2.getPackageInfo("com.anydesk.anydeskandroid", PackageManager.GET_ACTIVITIES);
            Log.d("AnyDesk", "AnyDesk установлен на устройстве");
        } catch (PackageManager.NameNotFoundException e) {
            Log.d("AnyDesk", "AnyDesk не установлен на устройстве");
        }
        return v;
    }


}