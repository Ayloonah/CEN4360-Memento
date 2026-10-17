package com.luanya.memento.ui.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.luanya.memento.R;

/* To use, add something like this in your activity's XML:

<com.luanya.memento.ui.components.AlbumCard
    android:id="@+id/albumCard"
    android:layout_width="156dp"
    android:layout_height="160dp"
    app:layout_constraintTop_toTopOf="parent"
    app:layout_constraintStart_toStartOf="parent"

    app:albumTitle="@string/card_album_title"
    app:albumFavorite="false"
    app:albumImage1="@drawable/placeholder_picture"
    app:albumImage2="@drawable/placeholder_picture"
    app:albumImage3="@drawable/placeholder_picture"
    app:albumImage4="@drawable/placeholder_picture" />

It needs to be inside the constraint layout in the XML file
The necessary strings and drawables are already made,
just need to call the right one
Omit the picture drawables when there aren't vignettes in the album
Java methods can also be called independently */

// Reusable album card layout
public class AlbumCard extends ConstraintLayout {

    // Album card components
    private TextView albumTitle;
    private ImageButton albumFavorite;
    private ImageView albumImage1;
    private ImageView albumImage2;
    private ImageView albumImage3;
    private ImageView albumImage4;

    public AlbumCard(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        // Inflate album
        LayoutInflater.from(context).inflate(R.layout.card_album, this, true);

        // Grab the album components
        albumTitle = findViewById(R.id.albumTitle);
        albumFavorite = findViewById(R.id.albumFavoriteButton);
        albumImage1 = findViewById(R.id.albumImage1);
        albumImage2 = findViewById(R.id.albumImage2);
        albumImage3 = findViewById(R.id.albumImage3);
        albumImage4 = findViewById(R.id.albumImage4);

        // Read the album attributes
        try (TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.AlbumCard)) {

            // Grab attributes
            String title = attributes.getString(R.styleable.AlbumCard_albumTitle);
            boolean favorite = attributes.getBoolean(R.styleable.AlbumCard_albumFavorite, false);
            int image1 = attributes.getResourceId(R.styleable.AlbumCard_albumImage1, 0);
            int image2 = attributes.getResourceId(R.styleable.AlbumCard_albumImage2, 0);
            int image3 = attributes.getResourceId(R.styleable.AlbumCard_albumImage3, 0);
            int image4 = attributes.getResourceId(R.styleable.AlbumCard_albumImage4, 0);

            // Apply them
            if (title != null) {
                setAlbumTitle(title);
            }
            if (image1 != 0) {
                setAlbumImage1(image1);
            }
            if (image2 != 0) {
                setAlbumImage2(image2);
            }
            if (image3 != 0) {
                setAlbumImage3(image3);
            }
            if (image4 != 0) {
                setAlbumImage4(image4);
            }
            setAlbumFavorite(favorite);
        }
    }

    // Set album title
    public void setAlbumTitle(String title)  {
        albumTitle.setText(title);
    }

    // Set favorite status
    public void setAlbumFavorite(boolean isFavorite) {
        if (isFavorite) {
            albumFavorite.setImageResource(R.drawable.icon_favorite_filled);
        } else {
            albumFavorite.setImageResource(R.drawable.icon_favorite_empty);
        }
    }

    // Set image1
    public void setAlbumImage1(int drawableId) {
        albumImage1.setImageResource(drawableId);
    }

    // Set image2
    public void setAlbumImage2(int drawableId) {
        albumImage2.setImageResource(drawableId);
    }

    // Set image3
    public void setAlbumImage3(int drawableId) {
        albumImage3.setImageResource(drawableId);
    }

    // Set image4
    public void setAlbumImage4(int drawableId) {
        albumImage4.setImageResource(drawableId);
    }

    // Attach an OnClickListener to the favorite button
    public void setAlbumFavOnClickListener(OnClickListener listener) {
        albumFavorite.setOnClickListener(listener);
    }
}
