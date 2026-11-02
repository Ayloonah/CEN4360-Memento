package com.luanya.memento;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class HomeZoneSetup extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private TextView home;
    private TextView exclude;
    private TextView skip;
    private Button start;


    public HomeZoneSetup() {
        // Required empty public constructor
    }

    public static HomeZoneSetup newInstance(String param1, String param2) {
        HomeZoneSetup fragment = new HomeZoneSetup();
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
        // Inflate the view
        View inflatedView = inflater.inflate(R.layout.fragment_home_zone_setup, container, false);

        // Grab resources
        home = inflatedView.findViewById(R.id.field_Home);
        exclude = inflatedView.findViewById(R.id.field_Exclude);
        skip = inflatedView.findViewById(R.id.textView_Skip);
        start = inflatedView.findViewById(R.id.button_home);

        // Set the home on click listener
        // Temporarily toast, will implement later
        home.setOnClickListener(view -> {
            Toast toast = new Toast(requireContext());
            toast.setText("Implement set home location!");
            toast.setDuration(Toast.LENGTH_LONG);
            toast.show();
        });

        // Set the exclusion radius on click listener
        // Temporarily toast, will implement later
        exclude.setOnClickListener(view -> {
            Toast toast = new Toast(requireContext());
            toast.setText("Implement choose exclusion radius!");
            toast.setDuration(Toast.LENGTH_LONG);
            toast.show();
        });

        // Set the skip on click listener
        skip.setOnClickListener(view -> {
            FragmentManager fm = getParentFragmentManager();
            fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            fm.beginTransaction().replace(R.id.fragment, MainPage.class, null).commit();
        });

        //Set the start on click listener
        start.setOnClickListener(view -> {
            FragmentManager fm = getParentFragmentManager();
            fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            fm.beginTransaction().replace(R.id.fragment, MainPage.class, null).commit();
        });

        return inflatedView;
    }
}