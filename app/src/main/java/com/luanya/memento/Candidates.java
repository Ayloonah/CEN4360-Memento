package com.luanya.memento;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.luanya.memento.ui.components.MementoToolbar;

import java.util.List;

// vignette candidates screen (mem-66 / design page 8)
// shows today's sample candidates and tapping one opens create a vignette already filled in
public class Candidates extends Fragment {

    private MementoToolbar tb;
    private RecyclerView rv;
    private TextView empty;

    public Candidates() {
        // android needs an empty constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // inflate the view
        View inflatedView = inflater.inflate(R.layout.fragment_candidates, container, false);

        // grab resources
        tb = inflatedView.findViewById(R.id.candidatesToolbar);
        empty = inflatedView.findViewById(R.id.candidatesEmpty);
        rv = inflatedView.findViewById(R.id.candidatesRecycler);
        rv.setLayoutManager(new LinearLayoutManager(inflatedView.getContext()));

        // back arrow goes back
        tb.setToolbarLeftButtonOnClick(view -> getParentFragmentManager().popBackStack());

        // load the candidates into the list
        // sample data for now and real detection comes later in mem-73
        List<VignetteCandidate> candidates = ReadSampleData.getCandidatesData(inflatedView.getContext());

        // tapping a candidate opens create a vignette with its info filled in
        rv.setAdapter(new CandidateAdapter(candidates, candidate -> getParentFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment, CreateVignette.class, CreateVignette.candidateArgs(
                        candidate.getLocation(),
                        candidate.getDate(),
                        candidate.getWeather(),
                        candidate.getTemp()))
                .addToBackStack(null).commit()));

        // if there are candidates, hide the empty label
        if (!candidates.isEmpty()) {
            empty.setVisibility(View.GONE);
        } else {
            empty.setVisibility(View.VISIBLE);
        }

        return inflatedView;
    }
}