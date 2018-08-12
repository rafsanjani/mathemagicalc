package com.foreverrafs.numericals.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.foreverrafs.numericals.R;

public class About extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);


        getSupportActionBar().setTitle("About");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initControls();
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();

        return true;
    }

    private void initControls() {
    }
/*        ExpandableTextView overview = findViewById(R.id.text_overview);
        ExpandableTextView credits = findViewById(R.id.text_credits);
        overview.setText(Html.fromHtml(getResources().getString(R.string.overview)));

        credits.setText(Html.fromHtml(getResources().getString(R.string.credits)));

        Utilities.setLobsterTypeface(findViewById(R.id.headerText), this);
    }*/

}