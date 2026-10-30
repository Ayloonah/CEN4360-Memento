package com.luanya.memento;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.luanya.memento.ui.components.MementoToolbar;

import java.util.List;

public class MainPage extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    private MementoToolbar tb;
    private TextView empty;
    private RecyclerView rv;
    private ImageButton add;

    public MainPage() {
        // Required empty public constructor
    }

    public static MainPage newInstance(String param1, String param2) {
        MainPage fragment = new MainPage();
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
        View inflatedView = inflater.inflate(R.layout.fragment_main_page, container, false);

        // Grab resources
        tb = inflatedView.findViewById(R.id.mainPageToolbar);
        empty = inflatedView.findViewById(R.id.mainPageEmpty);
        rv = inflatedView.findViewById(R.id.mainPageRecycler);
        rv.setLayoutManager(new LinearLayoutManager(inflatedView.getContext()));
        add = inflatedView.findViewById(R.id.mainPageAdd);

        // Update the toolbar
        tb.setToolbarLeftButton(R.drawable.icon_settings);
        tb.setToolbarLeftButtonDesc(R.string.icon_settings);
        tb.setToolbarRightButton(R.drawable.icon_scrapbook);
        tb.setToolbarRightButtonDesc(R.string.icon_scrapbook);

        // Set the toolbar's left button listener
        tb.setToolbarLeftButtonOnClick(view -> getParentFragmentManager()
                .beginTransaction().replace(R.id.fragment, Settings.class, null)
                .addToBackStack(null).commit());

        // Set the toolbar's right button listener
        tb.setToolbarRightButtonOnClick(view -> getParentFragmentManager()
                .beginTransaction().replace(R.id.fragment, Scrapbook.class,null)
                .addToBackStack(null).commit());

        // Load the vignette data into the recycler view
        // Currently set to read sample data, but later will read actual user data
        List<Vignette> vignettes = ReadSampleData.getVignettesData(inflatedView.getContext());
        rv.setAdapter(new VignetteAdapter(vignettes));

        // Add button menu and listener
        add.setOnClickListener(view -> {
            PopupMenu menu = new PopupMenu(getContext(), view);
            menu.inflate(R.menu.menu_add_new_vignette);

            // Set the menu on item selected listener
            menu.setOnMenuItemClickListener(menuItem -> {
                // Navigates to CreateVignette
                if (menuItem.getItemId() == R.id.new_vignette_add) {
                    getParentFragmentManager().beginTransaction()
                            .replace(R.id.fragment, CreateVignette.newInstance(false, -1), null)
                            .addToBackStack(null).commit();
                } else { // If new_candidate_add, navigates to Candidates
                    getParentFragmentManager().beginTransaction()
                            .replace(R.id.fragment, Candidates.class, null)
                            .addToBackStack(null).commit();
                }

                return true;
            });

            menu.show();
        });

        // If there are vignettes, hide the empty label
        if (!vignettes.isEmpty()) {
            empty.setVisibility(View.GONE);
        } else {
            empty.setVisibility(View.VISIBLE);
        }

        return inflatedView;
    }
}