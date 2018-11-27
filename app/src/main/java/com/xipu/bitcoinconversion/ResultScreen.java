package com.xipu.bitcoinconversion;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class ResultScreen extends AppCompatActivity {

    TextView priceTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_screen);

        Intent intent = getIntent();
        double price = intent.getDoubleExtra("Price", 0.0);

        priceTextView = findViewById(R.id.priceView);
        priceTextView.setText(String.valueOf(price));
    }



    @Override
    protected void onStop() {
        super.onStop();
        Intent intent = new Intent(this, MainScreen.class);
        startActivity(intent);
    }
}
