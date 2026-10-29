package com.luanya.memento;

import android.view.*;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.luanya.memento.ui.components.VignetteCard;

import java.util.*;

public class VignetteAdapter extends RecyclerView.Adapter<VignetteAdapter.VignetteViewHolder> {

    private List<Vignette> vignettes;

    public VignetteAdapter(List<Vignette> vignettes) {
        this.vignettes = vignettes;
    }

    // Holds a single vignette
    public static class VignetteViewHolder extends RecyclerView.ViewHolder {
        VignetteCard card;

        public VignetteViewHolder(@NonNull View itemView) {
            super(itemView);
            // Grab the vignette card layout
            card = (VignetteCard) itemView;
        }
    }

    @NonNull
    @Override
    public VignetteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the vignette card
        return new VignetteViewHolder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_vignette, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VignetteViewHolder holder, int position) {
        // Grab the vignette's contents
        Vignette v = vignettes.get(position);

        // Create weather string
        String weather = v.getWeather() + " · " + v.getTemp() + "°F";

        // Set the card's views to match the vignette's contents
        holder.card.setVignetteTitle(v.getTitle());
        holder.card.setVignetteDate(v.getFormattedDate());
        holder.card.setVignetteLocation(v.getLocation());
        holder.card.setVignetteWeather(weather);
        holder.card.setVignetteFavorite(v.getIsFavorite());
        holder.card.setVignetteImage(ReadSampleData.loadImage(
                holder.itemView.getContext(), v.getImagePath()));

        // For mood, use the proper resource based on mood value
        switch (v.getMood()) {
            case 1:
                holder.card.setVignetteMood(R.drawable.mood_super_unhappy);
                break;
            case 2:
                holder.card.setVignetteMood(R.drawable.mood_unhappy);
                break;
            case 3:
                holder.card.setVignetteMood(R.drawable.mood_neutral);
                break;
            case 4:
                holder.card.setVignetteMood(R.drawable.mood_happy);
                break;
            case 5:
                holder.card.setVignetteMood(R.drawable.mood_super_happy);
                break;
            default:
                holder.card.setVignetteMood(0);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return vignettes.size();
    }
}
