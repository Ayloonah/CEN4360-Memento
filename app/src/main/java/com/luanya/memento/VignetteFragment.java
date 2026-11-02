package com.luanya.memento;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.luanya.memento.ui.components.MementoToolbar;

import java.util.*;

// vignette details screen (mem-67 / design page 7)
// shows everything about one vignette
public class VignetteFragment extends Fragment {
    private static final String ARG_VIGNETTE_ID = "vignetteID";
    private int vignetteID;

    private MementoToolbar tb;
    private ImageView image;
    private ImageView favorite;
    private ImageView mood;
    private TextView date;
    private TextView location;
    private TextView weather;
    private TextView journal;
    private TextView albums;

    public VignetteFragment() {
        // android needs an empty constructor
    }

    // open the details for one vignette
    public static VignetteFragment newInstance(int vignetteID) {
        VignetteFragment fragment = new VignetteFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_VIGNETTE_ID, vignetteID);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            vignetteID = getArguments().getInt(ARG_VIGNETTE_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // inflate the view
        View inflatedView = inflater.inflate(R.layout.fragment_vignette, container, false);

        // grab resources
        tb = inflatedView.findViewById(R.id.vignetteDetailToolbar);
        image = inflatedView.findViewById(R.id.vignetteDetailImage);
        favorite = inflatedView.findViewById(R.id.vignetteDetailFavorite);
        mood = inflatedView.findViewById(R.id.vignetteDetailMood);
        date = inflatedView.findViewById(R.id.vignetteDetailDate);
        location = inflatedView.findViewById(R.id.vignetteDetailLocation);
        weather = inflatedView.findViewById(R.id.vignetteDetailWeather);
        journal = inflatedView.findViewById(R.id.vignetteDetailJournal);
        albums = inflatedView.findViewById(R.id.vignetteDetailAlbums);

        // update the toolbar
        tb.setToolbarLeftButton(R.drawable.icon_back_arrow);
        tb.setToolbarLeftButtonDesc(R.string.icon_back);
        tb.setToolbarRightButton(R.drawable.icon_menu);
        tb.setToolbarRightButtonDesc(R.string.icon_menu);

        // back arrow goes back
        tb.setToolbarLeftButtonOnClick(view -> getParentFragmentManager().popBackStack());

        // menu button (toasts for now like the album page)
        tb.setToolbarRightButtonOnClick(view -> {
            PopupMenu menu = new PopupMenu(getContext(), view);
            menu.inflate(R.menu.menu_vignette_options);

            menu.setOnMenuItemClickListener(menuItem -> {
                Toast toast = new Toast(requireContext());

                if (menuItem.getItemId() == R.id.vignette_edit) {
                    toast.setText("Implement edit vignette!");
                } else { // if vignette_delete
                    toast.setText("Implement delete vignette!");
                }

                toast.setDuration(Toast.LENGTH_LONG);
                toast.show();

                return true;
            });

            menu.show();
        });

        // find the vignette (sample data for now)
        Vignette v = ReadSampleData.getVignette(inflatedView.getContext(), vignetteID);
        if (v == null) {
            return inflatedView;
        }

        // title in the toolbar
        tb.setToolbarTitle(v.getTitle());

        // photo (picture icon if there isn't one)
        Bitmap photo = ReadSampleData.loadImage(inflatedView.getContext(), v.getImagePath());
        if (photo != null) {
            image.setImageBitmap(photo);
        } else {
            image.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            image.setImageResource(R.drawable.placeholder_picture);
        }

        // date, location and weather
        date.setText(v.getFormattedDate());
        location.setText(v.getLocation());
        weather.setText(v.getWeather() + " · " + v.getTemp() + "°F");

        // star
        if (v.getIsFavorite()) {
            favorite.setImageResource(R.drawable.icon_favorite_filled);
        } else {
            favorite.setImageResource(R.drawable.icon_favorite_empty);
        }

        // mood face in the same 1-5 order as the adapter
        switch (v.getMood()) {
            case 1:
                mood.setImageResource(R.drawable.mood_super_unhappy);
                break;
            case 2:
                mood.setImageResource(R.drawable.mood_unhappy);
                break;
            case 3:
                mood.setImageResource(R.drawable.mood_neutral);
                break;
            case 4:
                mood.setImageResource(R.drawable.mood_happy);
                break;
            case 5:
                mood.setImageResource(R.drawable.mood_super_happy);
                break;
            default:
                break;
        }
        if (v.getMood() >= 1 && v.getMood() <= 5) {
            mood.setVisibility(View.VISIBLE);
        }

        // journal (fallback text if it's empty)
        if (v.getJournalEntry().isEmpty()) {
            journal.setText(R.string.label_no_journal);
        } else {
            journal.setText(v.getJournalEntry());
        }

        // albums it's in, separated by commas (fallback text if none)
        List<String> names = new ArrayList<>();
        for (Album a : ReadSampleData.getVignetteAlbums(inflatedView.getContext(), vignetteID)) {
            names.add(a.getName());
        }
        if (names.isEmpty()) {
            albums.setText(R.string.label_no_albums);
        } else {
            albums.setText(String.join(", ", names));
        }

        return inflatedView;
    }
}