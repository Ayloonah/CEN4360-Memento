package com.luanya.memento;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.luanya.memento.ui.components.MementoToolbar;

public class Settings extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private MementoToolbar tb;
    private TextView home;
    private TextView exclude;
    private TextView reminder;
    private TextView frequency;
    private TextView theme;

    public Settings() {
        // Required empty public constructor
    }

    public static Settings newInstance(String param1, String param2) {
        Settings fragment = new Settings();
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
        View inflatedView = inflater.inflate(R.layout.fragment_settings, container, false);

        // Grab resources
        tb = inflatedView.findViewById(R.id.settingsToolbar);
        home = inflatedView.findViewById(R.id.field_settings_home);
        exclude = inflatedView.findViewById(R.id.field_settings_exclude);
        reminder = inflatedView.findViewById(R.id.field_settings_reminder);
        frequency = inflatedView.findViewById(R.id.field_settings_frequency);
        theme = inflatedView.findViewById(R.id.field_settings_theme);

        // Set the toolbar's left button listener
        tb.setToolbarLeftButtonOnClick(view -> getParentFragmentManager()
                .popBackStack());

        // A lot of listeners, temporarily displaying toasts, will implement later
        home.setOnClickListener(view -> {
            Toast toast = new Toast(requireContext());
            toast.setText("Implement set home location!");
            toast.setDuration(Toast.LENGTH_LONG);
            toast.show();
        });
        exclude.setOnClickListener(view -> {
            Toast toast = new Toast(requireContext());
            toast.setText("Implement choose exclusion radius!");
            toast.setDuration(Toast.LENGTH_LONG);
            toast.show();
        });
        reminder.setOnClickListener(view -> {
            Toast toast = new Toast(requireContext());
            toast.setText("Implement choose reminder time!");
            toast.setDuration(Toast.LENGTH_LONG);
            toast.show();
        });
        frequency.setOnClickListener(view -> {
            Toast toast = new Toast(requireContext());
            toast.setText("Implement choose notification frequency!");
            toast.setDuration(Toast.LENGTH_LONG);
            toast.show();
        });
        theme.setOnClickListener(view -> {
            Toast toast = new Toast(requireContext());
            toast.setText("Implement choose theme!");
            toast.setDuration(Toast.LENGTH_LONG);
            toast.show();
        });

        return inflatedView;
    }
}