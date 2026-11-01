package com.luanya.memento;

import android.graphics.Bitmap;
import android.view.*;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.*;

public class ChooseVignetteAdapter extends RecyclerView.Adapter<ChooseVignetteAdapter.ChooseViewHolder> {

    private List<Vignette> vignettes;

    // ids of the vignettes that are picked (by id so picks survive showing all)
    private HashSet<Integer> selected = new HashSet<>();

    public ChooseVignetteAdapter(List<Vignette> vignettes) {
        this.vignettes = vignettes;
    }

    // holds a single row
    public static class ChooseViewHolder extends RecyclerView.ViewHolder {
        View row;
        ImageView image;
        TextView title;

        public ChooseViewHolder(@NonNull View itemView) {
            super(itemView);
            // grab the row pieces
            row = itemView.findViewById(R.id.chooseVignetteRow);
            image = itemView.findViewById(R.id.chooseVignetteImage);
            title = itemView.findViewById(R.id.chooseVignetteTitle);
        }
    }

    @NonNull
    @Override
    public ChooseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflate the row
        return new ChooseViewHolder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_choose_vignette, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ChooseViewHolder holder, int position) {
        // grab the vignette's contents
        Vignette v = vignettes.get(position);

        // title and thumbnail (picture icon if there's no photo)
        holder.title.setText(v.getTitle());
        Bitmap photo = ReadSampleData.loadImage(holder.itemView.getContext(), v.getImagePath());
        if (photo == null) {
            holder.image.setImageResource(R.drawable.placeholder_picture);
        } else {
            holder.image.setImageBitmap(photo);
        }

        // dark border if it's picked
        if (selected.contains(v.getId())) {
            holder.row.setBackgroundResource(R.drawable.input_background_selected);
        } else {
            holder.row.setBackgroundResource(R.drawable.input_background);
        }

        // tapping picks or unpicks it
        holder.row.setOnClickListener(view -> {
            if (selected.contains(v.getId())) {
                selected.remove(v.getId());
            } else {
                selected.add(v.getId());
            }
            notifyItemChanged(holder.getBindingAdapterPosition());
        });
    }

    @Override
    public int getItemCount() {
        return vignettes.size();
    }

    // swap in a new list (used by browse all)
    public void setVignettes(List<Vignette> vignettes) {
        this.vignettes = vignettes;
        notifyDataSetChanged();
    }

    // how many are picked (for the created popup)
    public int getSelectedCount() {
        return selected.size();
    }
}