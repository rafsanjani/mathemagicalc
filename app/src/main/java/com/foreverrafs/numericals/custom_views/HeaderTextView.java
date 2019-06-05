package com.foreverrafs.numericals.custom_views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.foreverrafs.numericals.R;

public class HeaderTextView extends AppCompatTextView {


    public HeaderTextView(Context context) {
        super(context);
        init(null);
    }

    public HeaderTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public HeaderTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    private void init(@Nullable AttributeSet attrs) {
        if (attrs == null)
            return;

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.HeaderTextView);
        String fontName = typedArray.getString(R.styleable.HeaderTextView_font_name);
        Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/" + fontName);

        this.setTypeface(typeface);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

}