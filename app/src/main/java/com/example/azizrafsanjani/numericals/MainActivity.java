package com.example.azizrafsanjani.numericals;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.azizrafsanjani.numericals.NumericalMethods.Numericals;


import static android.R.attr.duration;
import static com.example.azizrafsanjani.numericals.NumericalMethods.Numericals.BinaryOperationType.DecimalFraction;
import static com.example.azizrafsanjani.numericals.NumericalMethods.Numericals.BinaryOperationType.DecimalInteger;
import static com.example.azizrafsanjani.numericals.NumericalMethods.Numericals.BinaryOperationType.Mixed;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnKeyListener{

    Button btnCalculate_int, btnCalculate_fraction, btnCalculate_mixed;
    EditText txtInput_int, txtInput_fraction, txtInput_mixed;
    TextView integerAnswer, fractionAnswer, mixedAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCalculate_int =  (Button) findViewById(R.id.button_calculate_int);
        btnCalculate_fraction =  (Button) findViewById(R.id.button_calculate_fraction);
        btnCalculate_mixed =  (Button) findViewById(R.id.button_calculate_mixed);


        txtInput_int = (EditText) findViewById(R.id.user_input_int);
        txtInput_fraction = (EditText) findViewById(R.id.user_input_fraction);
        txtInput_mixed= (EditText) findViewById(R.id.user_input_mixed);


        integerAnswer = (TextView)findViewById(R.id.text_binary_int);
        fractionAnswer = (TextView)findViewById(R.id.text_binary_fraction);
        mixedAnswer = (TextView)findViewById(R.id.text_binary_mixed);

        txtInput_int.setOnKeyListener(this);
        txtInput_fraction.setOnKeyListener(this);
        txtInput_mixed.setOnKeyListener(this);


        btnCalculate_int.setOnClickListener(this);
        btnCalculate_fraction.setOnClickListener(this);
        btnCalculate_mixed.setOnClickListener(this);

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults){
        if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            vibrateDevice();
        } else{
           Toast.makeText(this,"This permission is required to continue, app will close now",Toast.LENGTH_LONG );
            finish();
        }

    }

    public void vibrateDevice(){
        Vibrator v = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(500);
    }
    @Override
    public void onClick(View view){
        String input = "0000";
        switch(view.getId()){
            case R.id.button_calculate_int:
                input = txtInput_int.getText().toString();
                calculateAndDisplay(input, Numericals.BinaryOperationType.DecimalInteger, integerAnswer);
                break;
            case R.id.button_calculate_fraction:
                input = txtInput_fraction.getText().toString();
                calculateAndDisplay(input, Numericals.BinaryOperationType.DecimalFraction, fractionAnswer);
                break;
            case R.id.button_calculate_mixed:
                input = txtInput_mixed.getText().toString();
                calculateAndDisplay(input, Numericals.BinaryOperationType.Mixed , mixedAnswer);
                break;
        }

    }

    void calculateAndDisplay(String n, Numericals.BinaryOperationType op, TextView resultViewer){
        if(n.isEmpty()){
            if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.VIBRATE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.VIBRATE}, 1000);
            } else {
                vibrateDevice();
            }
            vibrateDevice();
            return;
        }

        String result = "";
        switch(op)
        {
            case DecimalInteger:
                result = Numericals.DecimalIntToBinary(Integer.parseInt(n));

                if(result.length() > 10) {
                    result = result.substring(0, 10);
                    Snackbar sb = Snackbar.make(findViewById(R.id.constraint_layout), R.string.answer_truncated, Snackbar.LENGTH_LONG);
                    sb.show();
                }

                resultViewer.setText(result);
                YoYo.with(Techniques.BounceIn )
                        .duration(700)
                        .repeat(0)
                        .playOn(resultViewer);
                break;
            case DecimalFraction:
                double input = Double.parseDouble(n);
                if(input > 0.99)
                {
                    vibrateDevice();
                    txtInput_fraction.setText("");
                    break;
                }
                result = Numericals.DecimalFractionToBinary(input);

                if(result.length() > 10) {
                    result = result.substring(0, 11);

                    Snackbar sb = Snackbar.make(findViewById(R.id.constraint_layout), R.string.answer_truncated, Snackbar.LENGTH_LONG);
                            sb.show();
                }

                resultViewer.setText(result);
                YoYo.with(Techniques.BounceIn )
                        .duration(700)
                        .repeat(0)
                        .playOn((TextView)findViewById(R.id.text_binary_fraction));
                break;
            case Mixed:
                result = Numericals.DecimalToBinary(Double.parseDouble(n));

                if(result.length() > 10) {
                    result = result.substring(0, 10);

                    Snackbar sb = Snackbar.make(findViewById(R.id.constraint_layout), R.string.answer_truncated, Snackbar.LENGTH_LONG);
                    sb.show();
                }

                resultViewer.setText(result);
                YoYo.with(Techniques.BounceIn )
                        .duration(700)
                        .repeat(0)
                        .playOn(resultViewer);
                break;
            default:
                //do nothing default
        }

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(resultViewer.getWindowToken(), 0);
    }



    @Override
    public boolean onKey(View view, int keyCode, KeyEvent e){
        switch(view.getId()){
            case R.id.user_input_int:
                if(e.getKeyCode() == KeyEvent.KEYCODE_ENTER){
                    calculateAndDisplay(txtInput_int.getText().toString(), DecimalInteger, integerAnswer);
                    return true;

                }
            return false;

            case R.id.user_input_fraction:
                if(e.getKeyCode() == KeyEvent.KEYCODE_ENTER){
                    calculateAndDisplay(txtInput_fraction.getText().toString(), DecimalFraction, fractionAnswer);
                    return true;

                }
                return false;

            case R.id.user_input_mixed:
                if(e.getKeyCode() == KeyEvent.KEYCODE_ENTER){
                    calculateAndDisplay(txtInput_mixed.getText().toString(), Mixed, mixedAnswer);
                    return true;

                }
                return false;
        }
      return false;
    }
}
