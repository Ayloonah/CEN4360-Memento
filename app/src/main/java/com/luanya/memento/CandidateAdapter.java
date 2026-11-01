package com.luanya.memento;

import android.view.*;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.luanya.memento.ui.components.CandidateCard;

import java.util.*;

public class CandidateAdapter extends RecyclerView.Adapter<CandidateAdapter.CandidateViewHolder> {

    // whoever shows the list decides what tapping a card does
    public interface OnCandidateClickListener {
        void onCandidateClick(VignetteCandidate candidate);
    }

    private List<VignetteCandidate> candidates;
    private OnCandidateClickListener listener;

    public CandidateAdapter(List<VignetteCandidate> candidates, OnCandidateClickListener listener) {
        this.candidates = candidates;
        this.listener = listener;
    }

    // holds a single candidate
    public static class CandidateViewHolder extends RecyclerView.ViewHolder {
        CandidateCard card;

        public CandidateViewHolder(@NonNull View itemView) {
            super(itemView);
            // grab the candidate card layout
            card = (CandidateCard) itemView;
        }
    }

    @NonNull
    @Override
    public CandidateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflate the candidate card
        return new CandidateViewHolder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_candidate, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CandidateViewHolder holder, int position) {
        // grab the candidate's contents
        VignetteCandidate c = candidates.get(position);

        // build the title and weather strings
        String title = c.getLocation() + " · " + c.getFormattedTime();
        String weather = c.getWeather() + " · " + c.getTemp() + "°F";

        // set the card's views to match the candidate
        holder.card.setCandidateTitle(title);
        holder.card.setCandidateDate(c.getFormattedDate());
        holder.card.setCandidateWeather(weather);

        // pick the reason icon and label
        switch (c.getReason()) {
            case "activity_change":
                holder.card.setCandidateReasonIcon(R.drawable.track_exercise);
                holder.card.setCandidateReason(holder.itemView.getContext()
                        .getString(R.string.reason_activity_change));
                break;
            case "weather_change":
                holder.card.setCandidateReasonIcon(R.drawable.track_weather);
                holder.card.setCandidateReason(holder.itemView.getContext()
                        .getString(R.string.reason_weather_change));
                break;
            default:
                holder.card.setCandidateReasonIcon(R.drawable.track_location);
                holder.card.setCandidateReason(holder.itemView.getContext()
                        .getString(R.string.reason_new_location));
                break;
        }

        // tapping the card hands the candidate back to the screen
        holder.card.setOnClickListener(view -> listener.onCandidateClick(c));
    }

    @Override
    public int getItemCount() {
        return candidates.size();
    }
}