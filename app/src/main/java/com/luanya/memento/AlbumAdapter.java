package com.luanya.memento;

import android.view.*;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.luanya.memento.ui.components.AlbumCard;

import java.util.*;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder> {

    // whoever shows the grid decides what tapping an album does
    public interface OnAlbumClickListener {
        void onAlbumClick(Album album);
    }

    private List<Album> albums;
    private OnAlbumClickListener listener;

    public AlbumAdapter(List<Album> albums, OnAlbumClickListener listener) {
        this.albums = albums;
        this.listener = listener;
    }

    // holds a single album
    public static class AlbumViewHolder extends RecyclerView.ViewHolder {
        AlbumCard card;

        public AlbumViewHolder(@NonNull View itemView) {
            super(itemView);
            // grab the album card inside the cell
            card = itemView.findViewById(R.id.albumCard);
        }
    }

    @NonNull
    @Override
    public AlbumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflate the album cell
        return new AlbumViewHolder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_album, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumViewHolder holder, int position) {
        // grab the album's contents
        Album a = albums.get(position);

        // set the title and star
        holder.card.setAlbumTitle(a.getName());
        holder.card.setAlbumFavorite(a.getIsFavorite());

        // one picture icon per vignette in the album (up to 4) and empty tiles for the rest
        int count = ReadSampleData.getAlbumVignettes(holder.itemView.getContext(), a.getId()).size();
        holder.card.setAlbumImage1(imageFor(0, count));
        holder.card.setAlbumImage2(imageFor(1, count));
        holder.card.setAlbumImage3(imageFor(2, count));
        holder.card.setAlbumImage4(imageFor(3, count));

        // tapping the card hands the album back to the screen
        holder.card.setOnClickListener(view -> listener.onAlbumClick(a));
    }

    // picture icon if this slot has a vignette, nothing (0) if it's empty
    private int imageFor(int slot, int count) {
        if (slot < count) {
            return R.drawable.placeholder_picture;
        }
        return 0;
    }

    @Override
    public int getItemCount() {
        return albums.size();
    }
}