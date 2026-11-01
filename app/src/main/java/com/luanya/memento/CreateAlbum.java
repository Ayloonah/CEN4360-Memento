package com.luanya.memento;

import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.*;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.luanya.memento.ui.components.MementoToolbar;

import java.util.*;

// create an album screen (mem-97 / design page 11)
// just the ui for now so nothing actually gets stored
// real saving comes later in mem-73
public class CreateAlbum extends Fragment {

    // how many vignettes show before browse all
    private static final int PREVIEW_COUNT = 5;

    private MementoToolbar tb;
    private EditText nameInput;
    private TextView nameError;
    private TextView browseAll;
    private RecyclerView rv;
    private ChooseVignetteAdapter adapter;

    public CreateAlbum() {
        // android needs an empty constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // inflate the view
        View inflatedView = inflater.inflate(R.layout.fragment_create_album, container, false);

        // grab all the pieces
        tb = inflatedView.findViewById(R.id.createAlbumToolbar);
        nameInput = inflatedView.findViewById(R.id.albumNameInput);
        nameError = inflatedView.findViewById(R.id.albumNameError);
        browseAll = inflatedView.findViewById(R.id.browseAllLink);
        rv = inflatedView.findViewById(R.id.chooseVignettesRecycler);
        rv.setLayoutManager(new LinearLayoutManager(inflatedView.getContext()));

        // back arrow goes back
        tb.setToolbarLeftButtonOnClick(view -> goBack());

        // load the vignettes and show the first few
        // sample data for now and real user data comes later in mem-73
        List<Vignette> all = ReadSampleData.getVignettesData(inflatedView.getContext());
        List<Vignette> preview = new ArrayList<>(all.subList(0, Math.min(PREVIEW_COUNT, all.size())));
        adapter = new ChooseVignetteAdapter(preview);
        rv.setAdapter(adapter);

        // underline the browse all link like the design
        browseAll.setPaintFlags(browseAll.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        // browse all shows every vignette and then hides itself
        browseAll.setOnClickListener(view -> {
            adapter.setVignettes(all);
            browseAll.setVisibility(View.GONE);
        });

        // hide the link if there's nothing more to show
        if (all.size() <= PREVIEW_COUNT) {
            browseAll.setVisibility(View.GONE);
        }

        // get rid of the name error once they start typing
        nameInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().trim().length() > 0) {
                    hideNameError();
                }
            }
        });

        // create button
        inflatedView.findViewById(R.id.createButton)
                .setOnClickListener(view -> create(view.getContext()));

        return inflatedView;
    }

    // make sure there's a name and then "create" it and show what got made
    private void create(Context context) {
        String name = nameInput.getText().toString().trim();
        if (name.isEmpty()) {
            showNameError();
            return;
        }

        String summary = "Name: " + name
                + "\nVignettes: " + adapter.getSelectedCount()
                + "\n\nSample save only. Nothing is stored yet.";

        new AlertDialog.Builder(context)
                .setTitle(R.string.dialog_album_created)
                .setMessage(summary)
                .setPositiveButton(R.string.button_done, (dialog, which) -> goBack())
                .setCancelable(false)
                .show();
    }

    // red outline and message under the name
    private void showNameError() {
        nameError.setVisibility(View.VISIBLE);
        setBackgroundKeepPadding(nameInput, R.drawable.input_background_error);
        nameInput.requestFocus();
    }

    private void hideNameError() {
        if (nameError.getVisibility() != View.VISIBLE) {
            return;
        }
        nameError.setVisibility(View.GONE);
        setBackgroundKeepPadding(nameInput, R.drawable.input_background);
    }

    // swapping the background can wipe the padding so put it back after
    private void setBackgroundKeepPadding(View view, int drawableRes) {
        int start = view.getPaddingStart();
        int top = view.getPaddingTop();
        int end = view.getPaddingEnd();
        int bottom = view.getPaddingBottom();
        view.setBackground(ContextCompat.getDrawable(view.getContext(), drawableRes));
        view.setPaddingRelative(start, top, end, bottom);
    }

    // back to whatever screen opened this one
    private void goBack() {
        if (getParentFragmentManager().getBackStackEntryCount() > 0) {
            getParentFragmentManager().popBackStack();
        }
    }
}