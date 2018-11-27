package com.xipu.bitcoinconversion;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class ResultScreen extends AppCompatActivity {

    public static final double DOUBLE_DEFAULT_VAL = 0.0;
    TextView priceTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_screen);

        Intent intent = getIntent();
        double price = intent.getDoubleExtra(MainScreen.PRICE_STRING_TAG, DOUBLE_DEFAULT_VAL);
        String cur = intent.getStringExtra(MainScreen.CURRENCY_TAG);

        // display the calculated price
        priceTextView = findViewById(R.id.priceView);
        priceTextView.setText(String.valueOf(price));
        priceTextView.append(" " + cur);
    }

    // direct users to the main screen upon reentering the app
    @Override
    protected void onRestart() {
        super.onRestart();
        goToMainScreen();
    }

    private void goToMainScreen(){
        Intent intent = new Intent(this, MainScreen.class);
        startActivity(intent);
    }
}
