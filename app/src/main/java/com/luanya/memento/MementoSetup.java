package com.luanya.memento;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class MementoSetup extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private Button next;

    public MementoSetup() {
        // Required empty public constructor
    }

    public static MementoSetup newInstance(String param1, String param2) {
        MementoSetup fragment = new MementoSetup();
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
        View inflatedView = inflater.inflate(R.layout.fragment_memento_setup, container, false);

        // Grab resources
        next = inflatedView.findViewById(R.id.button_notice);

        // Move to the next onboarding screen
        // The switches aren't handled yet, but the logic for it will be here
        next.setOnClickListener(view -> getParentFragmentManager()
                .beginTransaction().replace(R.id.fragment, HomeZoneSetup.class, null)
                .addToBackStack(null).commit());

        return inflatedView;
    }
}