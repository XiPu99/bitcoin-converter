package com.xipu.bitcoinconversion;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    SharedPreferences mSharedPreferences;
    FloatingActionButton nextButton;
    TextInputEditText nameInputField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);

        nextButton = findViewById(R.id.nextButton);
        nextButton.hide();
        nameInputField = findViewById(R.id.nameInput);
        nameInputField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                Log.d("Text", charSequence.toString());
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length()==0){
                    nextButton.hide();
                }
                else{
                    nextButton.show();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
//                Log.d("Text", editable.toString());
            }
        });

    }

    // on click method for the floating action button
    public void goToMainScreen(View v){
        Log.d("Text", nameInputField.getText().toString());
        Intent intent = new Intent(this, MainScreen.class);
        startActivity(intent);
    }
}
