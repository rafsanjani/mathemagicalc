package com.example.azizrafsanjani.numericals.activities;




import android.content.Context;
import android.graphics.Typeface;
import android.renderscript.ScriptGroup;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.azizrafsanjani.numericals.R;
import com.example.azizrafsanjani.numericals.fragments.FragmentMainMenu;
import com.example.azizrafsanjani.numericals.utils.Utilities;

import java.sql.SQLOutput;


public class MainActivity extends AppCompatActivity implements View.OnKeyListener {


    //static Toolbar toolbar;
    static InputMethodManager imm;
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
      //  toolbar  = findViewById(R.id.toolBar);
       // toolbar.setTitle(R.string.app_name);
        //toolbar.setSubtitle(R.string.app_description);
        //toolbar.setLogo(ContextCompat.getDrawable(getApplicationContext(),R.drawable.numericals_icon));
      
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

    }

    public static void setToolBarInfo(String title, String subtitle){
       // toolbar.setTitle(title);
        //toolbar.setSubtitle(subtitle);
    }

    public  static void hideKeyboard(View view){
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    @Override
    public void onBackPressed(){

        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        Fragment f = getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
        Log.i(Utilities.Log,f.toString() +" is the active fragment now");

        if (!(f instanceof FragmentMainMenu)){
           //transition between the open fragment and mainmenu
            Utilities.replaceFragment(f, new FragmentMainMenu(), getSupportFragmentManager(), R.id.fragmentContainer);
            return;
        }


        super.onBackPressed();

    }

    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        System.out.println("key input detected");
        return true;
    }
}
