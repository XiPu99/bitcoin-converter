package com.xipu.bitcoinconversion;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

public class NameActivity extends AppCompatActivity {

    public static final String NAME_TAG = "Name";
    SharedPreferences mSharedPreferences;
    FloatingActionButton nextButton;
    TextInputEditText nameInputField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        // check to see if users enter the app for the first time
        if(!mSharedPreferences.contains(NAME_TAG)){
            setContentView(R.layout.name_activity);
            setUpUI();
        }
        // if not, go directly to the main screen
        else{
            Intent intent = new Intent(this, MainScreen.class);
            startActivity(intent);
        }
    }

    // on click method for the floating action button
    public void goToMainScreen(View v){
        // store user's name locally for future use
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(NAME_TAG, nameInputField.getText().toString());
        editor.apply();
        // go to the main screen
        Intent intent = new Intent(this, MainScreen.class);
        startActivity(intent);
    }

    // initialize button, text input and UI effect
    private void setUpUI(){
        nextButton = findViewById(R.id.nextButton);
        nextButton.hide();
        nameInputField = findViewById(R.id.nameInput);
        nameInputField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Log.d("Text", charSequence.toString());
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // hide the button if the input field is empty
                if(charSequence.length()==0){
                    nextButton.hide();
                }
                else{
                    nextButton.show();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Log.d("Text", editable.toString());
            }
        });
    }



}
