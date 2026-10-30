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
import android.widget.Toast;

import com.luanya.memento.ui.components.MementoToolbar;

import java.util.List;

public class AlbumFragment extends Fragment {
    private static final String ARG_ALBUM_ID = "albumID";
    private static final String ARG_ALBUM_NAME = "albumName";
    private int albumID;
    private String albumName;
    private MementoToolbar tb;
    private TextView empty;
    private RecyclerView rv;
    private ImageButton add;

    public AlbumFragment() {
        // Required empty public constructor
    }

    public static AlbumFragment newInstance(int albumID, String albumName) {
        AlbumFragment fragment = new AlbumFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_ALBUM_ID, albumID);
        args.putString(ARG_ALBUM_NAME, albumName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            albumID = getArguments().getInt(ARG_ALBUM_ID);
            albumName = getArguments().getString(ARG_ALBUM_NAME);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the view
        View inflatedView = inflater.inflate(R.layout.fragment_album, container, false);

        // Grab resources
        tb = inflatedView.findViewById(R.id.albumDetailToolbar);
        empty = inflatedView.findViewById(R.id.albumDetailEmpty);
        rv = inflatedView.findViewById(R.id.albumDetailRecycler);
        rv.setLayoutManager(new LinearLayoutManager(inflatedView.getContext()));
        add = inflatedView.findViewById(R.id.albumDetailAdd);

        // Update the toolbar
        tb.setToolbarLeftButton(R.drawable.icon_back_arrow);
        tb.setToolbarLeftButtonDesc(R.string.icon_back);
        tb.setToolbarRightButton(R.drawable.icon_menu);
        tb.setToolbarRightButtonDesc(R.string.icon_menu);
        tb.setToolbarTitle(albumName);

        // Set the toolbar's left button listener
        tb.setToolbarLeftButtonOnClick(view -> getParentFragmentManager()
                .popBackStack());

        // Set the toolbar's right button listener
        tb.setToolbarRightButtonOnClick(view -> {
            PopupMenu menu = new PopupMenu(getContext(), view);
            menu.inflate(R.menu.menu_album_options);

            // Set the menu on item selected listener
            // Temporarily toasts, will implement later
            menu.setOnMenuItemClickListener(menuItem -> {
                Toast toast = new Toast(requireContext());

                if (menuItem.getItemId() == R.id.album_edit) {
                    toast.setText("Implement edit album name!");
                } else { // If album_delete
                    toast.setText("Implement delete album!");
                }

                toast.setDuration(Toast.LENGTH_LONG);
                toast.show();

                return true;
            });

            menu.show();
        });

        // Load the album's vignettes into the recycler view
        // Currently set to read sample data, but later will read actual user data
        List<Vignette> vignettes = ReadSampleData.getAlbumVignettes(inflatedView.getContext(), albumID);
        rv.setAdapter(new VignetteAdapter(vignettes));

        // Add button menu and listener
        add.setOnClickListener(view -> {
            PopupMenu menu = new PopupMenu(getContext(), view);
            menu.inflate(R.menu.menu_add_to_album);

            // Set the menu on item selected listener
            menu.setOnMenuItemClickListener(menuItem -> {
                // Will eventually have an alert dialog window with a list of Vignettes to choose from
                // Toast for now
                if (menuItem.getItemId() == R.id.existing_vignette_add) {
                    Toast toast = new Toast(requireContext());
                    toast.setText("Implement choose from existing Vignettes!");
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.show();
                } else { // If new_vignette_to_album, navigates to CreateVignette
                    getParentFragmentManager().beginTransaction()
                            .replace(R.id.fragment, CreateVignette.newInstance(true, albumID), null)
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