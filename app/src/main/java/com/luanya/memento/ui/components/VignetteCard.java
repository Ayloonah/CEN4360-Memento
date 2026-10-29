package com.luanya.memento.ui.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.luanya.memento.R;

/* To use, add something like this in your activity's XML:

<com.luanya.memento.ui.components.VignetteCard
    android:id="@+id/vignetteCard"
    android:layout_width="328dp"
    android:layout_height="112dp"
    app:layout_constraintTop_toTopOf="parent"
    app:layout_constraintStart_toStartOf="parent"

    app:vignetteTitle="@string/card_vignette_title"
    app:vignetteDate="@string/card_vignette_date"
    app:vignetteLocation="@string/card_vignette_location"
    app:vignetteWeather="@string/card_vignette_weather"
    app:vignetteFavorite="false"
    app:vignetteMood="@drawable/mood_super_happy" />

It needs to be inside the constraint layout in the XML file
The necessary strings and drawables are already made,
just need to call the right one
Java methods can also be called independently */

// Reusable vignette card layout
public class VignetteCard extends ConstraintLayout {

    // Vignette components
    private TextView vignetteTitle;
    private TextView vignetteDate;
    private TextView vignetteLocation;
    private TextView vignetteWeather;
    private ImageButton vignetteFavorite;
    private ImageView vignetteMood;
    private ImageView vignetteImage;

    public VignetteCard(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        // Inflate vignette
        LayoutInflater.from(context).inflate(R.layout.card_vignette, this, true);

        // Grab the vignette components
        vignetteTitle = findViewById(R.id.vignetteTitle);
        vignetteDate = findViewById(R.id.vignetteDate);
        vignetteLocation = findViewById(R.id.vignetteLocation);
        vignetteWeather = findViewById(R.id.vignetteWeather);
        vignetteFavorite = findViewById(R.id.vignetteFavoriteButton);
        vignetteMood = findViewById(R.id.vignetteMoodIcon);
        vignetteImage = findViewById(R.id.vignetteImage);

        // Read the vignette attributes
        try (TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.VignetteCard)) {

            // Grab attributes
            String title = attributes.getString(R.styleable.VignetteCard_vignetteTitle);
            String dateTime = attributes.getString(R.styleable.VignetteCard_vignetteDate);
            String location = attributes.getString(R.styleable.VignetteCard_vignetteLocation);
            String weather = attributes.getString(R.styleable.VignetteCard_vignetteWeather);
            boolean favorite = attributes.getBoolean(R.styleable.VignetteCard_vignetteFavorite, false);
            int mood = attributes.getResourceId(R.styleable.VignetteCard_vignetteMood, 0);

            // Apply them
            if (title != null) {
                setVignetteTitle(title);
            }
            if (dateTime != null) {
                setVignetteDate(dateTime);
            }
            if (location != null) {
                setVignetteLocation(location);
            }
            if (weather != null) {
                setVignetteWeather(weather);
            }
            if (mood != 0) {
                setVignetteMood(mood);
            }
            setVignetteImage(null);
            setVignetteFavorite(favorite);
        }
    }

    // Set vignette title
    public void setVignetteTitle(String title)  {
        vignetteTitle.setText(title);
    }

    // Set vignette date/time
    public void setVignetteDate(String date) {
        vignetteDate.setText(date);
    }

    // Set vignette location
    public void setVignetteLocation(String location) {
        vignetteLocation.setText(location);
        vignetteLocation.setVisibility(View.VISIBLE);
    }

    // Set vignette weather
    public void setVignetteWeather(String weather) {
        vignetteWeather.setText(weather);
        vignetteWeather.setVisibility(View.VISIBLE);
    }

    // Set favorite status
    public void setVignetteFavorite(boolean isFavorite) {
        if (isFavorite) {
            vignetteFavorite.setImageResource(R.drawable.icon_favorite_filled);
        } else {
            vignetteFavorite.setImageResource(R.drawable.icon_favorite_empty);
        }
    }

    // Set mood icon
    public void setVignetteMood(int drawableId) {
        if (drawableId == 0) {
            vignetteMood.setVisibility(View.INVISIBLE);
        } else {
            vignetteMood.setImageResource(drawableId);
            vignetteMood.setVisibility(View.VISIBLE);
        }
    }

    // Set image
    public void setVignetteImage(Bitmap image) {
        if (image == null) {
            vignetteImage.setImageResource(R.drawable.placeholder_picture);
        } else {
            vignetteImage.setImageBitmap(image);
        }
    }

    // Attach an OnClickListener to the favorite button
    public void setVignetteFavOnClickListener(OnClickListener listener) {
        vignetteFavorite.setOnClickListener(listener);
    }
}