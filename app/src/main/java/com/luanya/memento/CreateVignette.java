package com.luanya.memento;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.*;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.luanya.memento.ui.components.MementoToolbar;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

// create a vignette screen (mem-32 / design page 9)
// just the ui for now so nothing actually gets stored
// real saving and live weather/location come later in mem-73
public class CreateVignette extends Fragment {

    // keys for prefilling from a candidate
    private static final String ARG_PREFILLED = "prefilled";
    private static final String ARG_LOCATION = "location";
    private static final String ARG_DATE_TIME = "dateTime";
    private static final String ARG_WEATHER = "weather";
    private static final String ARG_TEMP = "temp";

    // keys for when this gets opened from an album page
    private static final String ARG_IS_IN_ALBUM = "isInAlbum";
    private static final String ARG_ALBUM_ID = "albumID";

    // sample pic from assets until we have a camera
    private static final String SAMPLE_PHOTO = "lake_eola.jpg";

    private static final DateTimeFormatter FIELD_FORMAT =
            DateTimeFormatter.ofPattern("MM/dd/yyyy h:mm a", Locale.US);
    private static final DateTimeFormatter SUMMARY_FORMAT =
            DateTimeFormatter.ofPattern("MMMM d, yyyy 'at' h:mm a", Locale.US);

    private MementoToolbar tb;
    private EditText titleInput;
    private EditText locationInput;
    private EditText journalInput;
    private TextView titleError;
    private TextView dateTimeField;
    private TextView weatherField;
    private TextView photoLabel;
    private ImageView photoIcon;
    private final ImageView[] moodViews = new ImageView[5];

    private LocalDateTime selectedDateTime = LocalDateTime.now().withSecond(0).withNano(0);
    private String selectedWeather = null;
    private int selectedTemp = 0;
    private int selectedMood = 0; // 0 means no mood picked and 1-5 matches the adapter
    private boolean hasPhoto = false;
    private boolean isInAlbum = false;
    private int albumID = -1; // -1 when it's not in an album

    public CreateVignette() {
        // android needs an empty constructor
    }

    // opened from an album page so the new vignette goes in that album
    public static CreateVignette newInstance(boolean isInAlbum, int albumID) {
        CreateVignette fragment = new CreateVignette();
        Bundle args = new Bundle();
        args.putBoolean(ARG_IS_IN_ALBUM, isInAlbum);
        args.putInt(ARG_ALBUM_ID, albumID);
        fragment.setArguments(args);
        return fragment;
    }

    // extras for opening this screen already filled in from a candidate
    // use it like replace(R.id.fragment, CreateVignette.class, CreateVignette.candidateArgs(...))
    public static Bundle candidateArgs(String location, String isoDateTime,
                                       String weather, int temp) {
        Bundle args = new Bundle();
        args.putBoolean(ARG_PREFILLED, true);
        args.putString(ARG_LOCATION, location);
        args.putString(ARG_DATE_TIME, isoDateTime);
        args.putString(ARG_WEATHER, weather);
        args.putInt(ARG_TEMP, temp);
        return args;
    }

    // fake candidate that matches the first card on the candidates design
    public static Bundle sampleCandidateArgs() {
        return candidateArgs("Lake Eola Park", "2026-09-23T16:15:00", "Partly Cloudy", 81);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            isInAlbum = getArguments().getBoolean(ARG_IS_IN_ALBUM);
            albumID = getArguments().getInt(ARG_ALBUM_ID, -1);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // inflate the view
        View inflatedView = inflater.inflate(R.layout.fragment_create_vignette, container, false);

        // grab all the pieces
        tb = inflatedView.findViewById(R.id.createVignetteToolbar);
        titleInput = inflatedView.findViewById(R.id.titleInput);
        titleError = inflatedView.findViewById(R.id.titleError);
        locationInput = inflatedView.findViewById(R.id.locationInput);
        journalInput = inflatedView.findViewById(R.id.journalInput);
        dateTimeField = inflatedView.findViewById(R.id.dateTimeField);
        weatherField = inflatedView.findViewById(R.id.weatherField);
        photoLabel = inflatedView.findViewById(R.id.photoLabel);
        photoIcon = inflatedView.findViewById(R.id.photoIcon);

        // back arrow goes back
        tb.setToolbarLeftButtonOnClick(view -> goBack());

        // mood faces (tap the picked one again to unpick it)
        int[] moodIds = {R.id.mood1, R.id.mood2, R.id.mood3, R.id.mood4, R.id.mood5};
        for (int i = 0; i < moodIds.length; i++) {
            final int value = i + 1;
            moodViews[i] = inflatedView.findViewById(moodIds[i]);
            moodViews[i].setOnClickListener(view -> {
                if (value == selectedMood) {
                    selectMood(0);
                } else {
                    selectMood(value);
                }
            });
        }

        // what happens when stuff gets tapped
        inflatedView.findViewById(R.id.photoBox)
                .setOnClickListener(view -> togglePhoto(view.getContext()));
        dateTimeField.setOnClickListener(view -> pickDateTime(view.getContext()));
        weatherField.setOnClickListener(view -> pickWeather(view.getContext()));
        inflatedView.findViewById(R.id.saveButton)
                .setOnClickListener(view -> save(view.getContext()));

        // get rid of the title error once they start typing
        titleInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().trim().length() > 0) {
                    hideTitleError();
                }
            }
        });

        applyCandidateArguments();
        updateDateTimeField();

        return inflatedView;
    }

    // fill stuff in if we came from a candidate
    private void applyCandidateArguments() {
        Bundle args = getArguments();
        if (args == null || !args.getBoolean(ARG_PREFILLED, false)) {
            return;
        }

        String location = args.getString(ARG_LOCATION);
        if (location != null) {
            locationInput.setText(location);
        }

        String iso = args.getString(ARG_DATE_TIME);
        if (iso != null) {
            selectedDateTime = LocalDateTime.parse(iso, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        }

        String weather = args.getString(ARG_WEATHER);
        if (weather != null) {
            setWeather(weather, args.getInt(ARG_TEMP, 0));
        }

        // the rest is filled in so jump to the title
        titleInput.requestFocus();
    }

    // date picker first and then the time picker
    private void pickDateTime(Context context) {
        new DatePickerDialog(context, (datePicker, year, month, day) ->
                new TimePickerDialog(context, (timePicker, hour, minute) -> {
                    selectedDateTime = LocalDateTime.of(year, month + 1, day, hour, minute);
                    updateDateTimeField();
                }, selectedDateTime.getHour(), selectedDateTime.getMinute(), false).show(),
                selectedDateTime.getYear(),
                selectedDateTime.getMonthValue() - 1,
                selectedDateTime.getDayOfMonth()).show();
    }

    private void updateDateTimeField() {
        dateTimeField.setText(selectedDateTime.format(FIELD_FORMAT));
    }

    // weather choices come from the sample data (one per kind of weather)
    private void pickWeather(Context context) {
        List<Vignette> choices = new ArrayList<>();
        List<String> seen = new ArrayList<>();

        for (Vignette v : ReadSampleData.getVignettesData(context)) {
            if (!seen.contains(v.getWeather())) {
                seen.add(v.getWeather());
                choices.add(v);
            }
        }

        String[] labels = new String[choices.size()];
        for (int i = 0; i < choices.size(); i++) {
            labels[i] = choices.get(i).getWeather() + " · " + choices.get(i).getTemp() + "°F";
        }

        new AlertDialog.Builder(context)
                .setTitle(R.string.fields_weather)
                .setItems(labels, (dialog, which) ->
                        setWeather(choices.get(which).getWeather(), choices.get(which).getTemp()))
                .setNegativeButton(R.string.button_clear, (dialog, which) -> setWeather(null, 0))
                .show();
    }

    private void setWeather(String weather, int temp) {
        selectedWeather = weather;
        selectedTemp = temp;

        // same look as the vignette cards
        if (weather == null) {
            weatherField.setText("");
        } else {
            weatherField.setText(weather + " · " + temp + "°F");
        }
    }

    // make the picked face pop and fade out the rest
    private void selectMood(int mood) {
        selectedMood = mood;
        for (int i = 0; i < moodViews.length; i++) {
            boolean isSelected = (i + 1) == mood;
            float scale = isSelected ? 1.2f : 1f;
            moodViews[i].setAlpha(mood == 0 || isSelected ? 1f : 0.4f);
            moodViews[i].animate().scaleX(scale).scaleY(scale).setDuration(120).start();
            moodViews[i].setSelected(isSelected);
        }
    }

    // name for each mood in the same 1-5 order as the adapter
    private String moodName(int mood) {
        switch (mood) {
            case 1:
                return "Very unhappy";
            case 2:
                return "Unhappy";
            case 3:
                return "Neutral";
            case 4:
                return "Happy";
            case 5:
                return "Very happy";
            default:
                return "—";
        }
    }

    // add or remove the sample pic (no camera yet)
    private void togglePhoto(Context context) {
        Bitmap photo = hasPhoto ? null : ReadSampleData.loadImage(context, SAMPLE_PHOTO);
        hasPhoto = photo != null;

        if (hasPhoto) {
            photoIcon.setScaleType(ImageView.ScaleType.CENTER_CROP);
            photoIcon.setImageBitmap(photo);
            photoLabel.setText(R.string.label_photo_added);
        } else {
            photoIcon.setScaleType(ImageView.ScaleType.FIT_CENTER);
            photoIcon.setImageResource(R.drawable.placeholder_picture);
            photoLabel.setText(R.string.label_add_photo);
        }
    }

    // make sure there's a title and then "save" it and show what got saved
    private void save(Context context) {
        String title = titleInput.getText().toString().trim();
        if (title.isEmpty()) {
            showTitleError();
            return;
        }

        // id is 0 for now since there's no database to hand one out yet
        Vignette vignette = new Vignette(
                0,
                title,
                selectedDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                locationInput.getText().toString().trim(),
                selectedWeather == null ? "" : selectedWeather,
                selectedTemp,
                journalInput.getText().toString().trim(),
                false,
                selectedMood,
                hasPhoto ? SAMPLE_PHOTO : null);

        new AlertDialog.Builder(context)
                .setTitle(R.string.dialog_vignette_saved)
                .setMessage(buildSummary(vignette))
                .setPositiveButton(R.string.button_done, (dialog, which) -> goBack())
                .setCancelable(false)
                .show();
    }

    // text for the saved popup
    private String buildSummary(Vignette v) {
        String journal = v.getJournalEntry();
        if (journal.length() > 80) {
            journal = journal.substring(0, 80) + "…";
        }

        String weather = v.getWeather().isEmpty() ? "—" : v.getWeather() + " · " + v.getTemp() + "°F";
        String photo = v.getImagePath() == null ? "None" : v.getImagePath();

        return "Title: " + v.getTitle()
                + "\nWhen: " + selectedDateTime.format(SUMMARY_FORMAT)
                + "\nLocation: " + orDash(v.getLocation())
                + "\nWeather: " + weather
                + "\nMood: " + moodName(v.getMood())
                + "\nPhoto: " + photo
                + "\nJournal: " + orDash(journal)
                + "\nAlbum: " + (isInAlbum ? "#" + albumID : "—")
                + "\n\nSample save only. Nothing is stored yet.";
    }

    private static String orDash(String s) {
        if (s == null || s.isEmpty()) {
            return "—";
        }
        return s;
    }

    // red outline and message under the title
    private void showTitleError() {
        titleError.setVisibility(View.VISIBLE);
        setBackgroundKeepPadding(titleInput, R.drawable.input_background_error);
        titleInput.requestFocus();
    }

    private void hideTitleError() {
        if (titleError.getVisibility() != View.VISIBLE) {
            return;
        }
        titleError.setVisibility(View.GONE);
        setBackgroundKeepPadding(titleInput, R.drawable.input_background);
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