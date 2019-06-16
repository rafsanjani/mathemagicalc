package com.foreverrafs.numericals.custom_views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.foreverrafs.numericals.R;

/**
 * Custom TextView which gives the user the ability to specify a custom font_name in xml
 * Usage:
 * <p>
 * <com.foreverrafs.numericals.custom_views.HeaderTextView
 * android:id="@+id/text_header"
 * font_name="font_file_name.ttf"
 * android:text="My Custom Text" />
 * <p>
 *
 * font_file_name.ttf must be the name of a font asset stored in assets/fonts directory else an exception will be thrown and a
 * fallback font will be used instead
 */
public class RafsTextView extends AppCompatTextView {


    public RafsTextView(Context context) {
        super(context);
        init(null);
    }

    public RafsTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public RafsTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    private void init(@Nullable AttributeSet attrs) {
        if (attrs == null)
            return;

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.RafsTextView);
        String fontName = typedArray.getString(R.styleable.RafsTextView_font_name);
        typedArray.recycle();

        Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/" + fontName);


        this.setTypeface(typeface);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

}