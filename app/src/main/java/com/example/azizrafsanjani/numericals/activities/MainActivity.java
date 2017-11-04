package com.example.azizrafsanjani.numericals.activities;




import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Window;
import android.widget.TextView;

import com.example.azizrafsanjani.numericals.R;
import com.example.azizrafsanjani.numericals.fragments.FragmentMainMenu;
import com.example.azizrafsanjani.numericals.utils.Utilities;


public class MainActivity extends AppCompatActivity {


    static Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
       setContentView(R.layout.activity_main);

        loadMenuFragment();
        initControls();
    }

    private void loadMenuFragment() {
        FragmentMainMenu fragment = new FragmentMainMenu();
        Utilities.loadFragment(fragment, getSupportFragmentManager(), R.id.fragmentContainer);

    }


    private void initControls(){
        toolbar  = (Toolbar) findViewById(R.id.toolBar);
        toolbar.setTitle(R.string.app_name);
        toolbar.setSubtitle(R.string.app_description);
        toolbar.setLogo(ContextCompat.getDrawable(getApplicationContext(),R.drawable.numericals_icon));

    }

    public static void setToolBarInfo(String title, String subtitle){
        toolbar.setTitle(title);
        toolbar.setSubtitle(subtitle);
    }

    @Override
    public void onBackPressed(){
        Fragment f = getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
        Log.i(Utilities.Log,f.toString() +" is the active fragment now");

        if (!(f instanceof FragmentMainMenu)){
           //transition between the open fragment and mainmenu
            Utilities.replaceFragment(f, new FragmentMainMenu(), getSupportFragmentManager(), R.id.fragmentContainer);
            return;
        }


        super.onBackPressed();

    }

}
