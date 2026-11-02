package com.luanya.memento;

import android.graphics.Bitmap;
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

        // load the photos from the album's first 4 vignettes
        // Currently uses sample data, will use actual user data later
        List<Vignette> vignettes = ReadSampleData.getAlbumVignettes(
                holder.itemView.getContext(), a.getId());
        List<Bitmap> photos = new ArrayList<>();
        for (int i = 0; i < Math.min(4, vignettes.size()); i++) {
            photos.add(ReadSampleData.loadImage(
                    holder.itemView.getContext(), vignettes.get(i).getImagePath()));
        }
        holder.card.setAlbumPhotos(photos);

        // tapping the star switches it between filled and empty
        // this is a display only change, will actually implement later
        holder.card.setAlbumFavOnClickListener(view -> {
            a.setIsFavorite(!a.getIsFavorite());
            holder.card.setAlbumFavorite(a.getIsFavorite());
        });

        // tapping the card hands the album back to the screen
        holder.card.setOnClickListener(view -> listener.onAlbumClick(a));
    }

    @Override
    public int getItemCount() {
        return albums.size();
    }
}