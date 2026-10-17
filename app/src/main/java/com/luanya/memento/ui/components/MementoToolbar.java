package com.luanya.memento.ui.components;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.luanya.memento.R;
import android.content.res.TypedArray;

/* To use, add something like this in your activity's XML:

<com.luanya.memento.ui.components.Toolbar
    android:id="@+id/mementoToolbar"
    android:layout_width="0dp"
    android:layout_height="wrap_content"
    android:minHeight="64dp"
    app:layout_constraintTop_toTopOf="parent"
    app:layout_constraintStart_toStartOf="parent"
    app:layout_constraintEnd_toEndOf="parent"

    app:toolbarTitle="@string/toolbar_title"
    app:toolbarLeftIcon="@drawable/icon_back_arrow"
    app:toolbarLeftDescription="@string/icon_back"
    app:toolbarRightIcon="@drawable/icon_menu"
    app:toolbarRightDescription="@string/more_options" />

It needs to be inside the constraint layout in the XML file
If you do not need the right button, just omit its icon and description
The necessary strings and drawables are already made,
just need to call the right one
Java methods can also be called independently */

// Used to set up the toolbar on each screen
public class MementoToolbar extends ConstraintLayout {

    // Toolbar components
    private TextView toolbarTitle;
    private ImageButton toolbarLeftButton;
    private ImageButton toolbarRightButton;

    public MementoToolbar(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        // Inflate toolbar
        LayoutInflater.from(context).inflate(R.layout.toolbar, this, true);

        // Grab the toolbar components
        toolbarTitle = findViewById(R.id.toolbarTitle);
        toolbarLeftButton = findViewById(R.id.toolbarLeftButton);
        toolbarRightButton = findViewById(R.id.toolbarRightButton);

        // Read the toolbar attributes
        try (TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.MementoToolbar)) {

            // Grab attributes
            String title = attributes.getString(R.styleable.MementoToolbar_toolbarTitle);
            int leftIcon = attributes.getResourceId(R.styleable.MementoToolbar_toolbarLeftIcon, 0);
            int rightIcon = attributes.getResourceId(R.styleable.MementoToolbar_toolbarRightIcon, 0);
            String leftDescription = attributes.getString(R.styleable.MementoToolbar_toolbarLeftDescription);
            String rightDescription = attributes.getString(R.styleable.MementoToolbar_toolbarRightDescription);

            // Apply them
            if (title != null) {
                setToolbarTitle(title);
            }
            if (leftIcon != 0) {
                setToolbarLeftButton(leftIcon);
            }
            if (rightIcon != 0) {
                setToolbarRightButton(rightIcon);
            }
            if (leftDescription != null) {
                setToolbarLeftButtonDesc(leftDescription);
            }
            if (rightDescription != null) {
                setToolbarRightButtonDesc(rightDescription);
            }
        }
    }

    // Set the title to the passed in string
    public void setToolbarTitle(String title) {
        toolbarTitle.setText(title);
    }

    // Set the icon for the toolbar's left button
    // Pass in the id of the icon's drawable
    public void setToolbarLeftButton(int drawableId) {
        toolbarLeftButton.setImageResource(drawableId);
    }

    // Set the icon for the toolbar's right button
    // Pass in the id of the icon's drawable
    public void setToolbarRightButton(int drawableId) {
        toolbarRightButton.setImageResource(drawableId);
        toolbarRightButton.setVisibility(View.VISIBLE);
    }

    // Attach an OnClickListener to the toolbar's left button
    public void setToolbarLeftButtonOnClick(OnClickListener listener) {
        toolbarLeftButton.setOnClickListener(listener);
    }

    // Attach an OnClickListener to the toolbar's right button
    public void setToolbarRightButtonOnClick(OnClickListener listener) {
        toolbarRightButton.setOnClickListener(listener);
    }

    // Update the content description for the toolbar's left button
    public void setToolbarLeftButtonDesc(String description) {
        toolbarLeftButton.setContentDescription(description);
    }

    // Update the content description for the toolbar's right button
    public void setToolbarRightButtonDesc(String description) {
        toolbarRightButton.setContentDescription(description);
    }
}
