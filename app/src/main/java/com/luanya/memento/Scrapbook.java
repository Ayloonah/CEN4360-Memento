package com.luanya.memento;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.luanya.memento.ui.components.MementoToolbar;

import java.util.List;

// scrapbook screen (mem-33 / design page 5)
// shows the albums in a two column grid and tapping one opens that album
public class Scrapbook extends Fragment {

    private MementoToolbar tb;
    private RecyclerView rv;
    private ImageButton add;
    private TextView empty;

    public Scrapbook() {
        // android needs an empty constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // inflate the view
        View inflatedView = inflater.inflate(R.layout.fragment_scrapbook, container, false);

        // grab resources
        tb = inflatedView.findViewById(R.id.scrapbookToolbar);
        rv = inflatedView.findViewById(R.id.scrapbookRecycler);
        rv.setLayoutManager(new GridLayoutManager(inflatedView.getContext(), 2));
        add = inflatedView.findViewById(R.id.scrapbookAdd);
        empty = inflatedView.findViewById(R.id.scrapbookEmpty);

        // back arrow goes back
        tb.setToolbarLeftButtonOnClick(view -> getParentFragmentManager().popBackStack());

        // load the albums into the grid
        // sample data for now and real user data comes later in mem-73
        List<Album> albums = ReadSampleData.getAlbumsData(inflatedView.getContext());

        // tapping an album opens its page
        rv.setAdapter(new AlbumAdapter(albums, album -> getParentFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment, AlbumFragment.newInstance(album.getId(), album.getName()), null)
                .addToBackStack(null).commit()));

        // + opens create an album
        add.setOnClickListener(view -> getParentFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment, CreateAlbum.class, null)
                .addToBackStack(null).commit());

        // if there are albums, hide the empty label
        if (!albums.isEmpty()) {
            empty.setVisibility(View.GONE);
        } else {
            empty.setVisibility(View.VISIBLE);
        }

        return inflatedView;
    }
}