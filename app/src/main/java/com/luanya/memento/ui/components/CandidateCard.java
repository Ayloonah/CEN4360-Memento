package com.luanya.memento.ui.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.luanya.memento.R;

/* to use add something like this in your xml:

<com.luanya.memento.ui.components.CandidateCard
    android:id="@+id/candidateCard"
    android:layout_width="328dp"
    android:layout_height="wrap_content"
    app:layout_constraintTop_toTopOf="parent"
    app:layout_constraintStart_toStartOf="parent"

    app:candidateTitle="@string/card_candidate_title"
    app:candidateDate="@string/card_candidate_date"
    app:candidateWeather="@string/card_candidate_weather"
    app:candidateReason="@string/reason_new_location"
    app:candidateReasonIcon="@drawable/track_location" />

it needs to be inside a constraint layout
the java methods can also be called on their own */

// reusable vignette candidate card
public class CandidateCard extends ConstraintLayout {

    // candidate card pieces
    private TextView candidateTitle;
    private TextView candidateDate;
    private TextView candidateWeather;
    private TextView candidateReason;
    private ImageView candidateReasonIcon;

    public CandidateCard(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        // inflate the card
        LayoutInflater.from(context).inflate(R.layout.card_candidate, this, true);

        // grab the card pieces
        candidateTitle = findViewById(R.id.candidateTitle);
        candidateDate = findViewById(R.id.candidateDate);
        candidateWeather = findViewById(R.id.candidateWeather);
        candidateReason = findViewById(R.id.candidateReason);
        candidateReasonIcon = findViewById(R.id.candidateReasonIcon);

        // read the xml attributes
        try (TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.CandidateCard)) {

            // grab them
            String title = attributes.getString(R.styleable.CandidateCard_candidateTitle);
            String date = attributes.getString(R.styleable.CandidateCard_candidateDate);
            String weather = attributes.getString(R.styleable.CandidateCard_candidateWeather);
            String reason = attributes.getString(R.styleable.CandidateCard_candidateReason);
            int reasonIcon = attributes.getResourceId(R.styleable.CandidateCard_candidateReasonIcon, 0);

            // apply them
            if (title != null) {
                setCandidateTitle(title);
            }
            if (date != null) {
                setCandidateDate(date);
            }
            if (weather != null) {
                setCandidateWeather(weather);
            }
            if (reason != null) {
                setCandidateReason(reason);
            }
            if (reasonIcon != 0) {
                setCandidateReasonIcon(reasonIcon);
            }
        }
    }

    // set the title (location · time)
    public void setCandidateTitle(String title) {
        candidateTitle.setText(title);
    }

    // set the date
    public void setCandidateDate(String date) {
        candidateDate.setText(date);
    }

    // set the weather
    public void setCandidateWeather(String weather) {
        candidateWeather.setText(weather);
    }

    // set the reason label
    public void setCandidateReason(String reason) {
        candidateReason.setText(reason);
    }

    // set the reason icon (pass in the drawable id)
    public void setCandidateReasonIcon(int drawableId) {
        candidateReasonIcon.setImageResource(drawableId);
    }
}