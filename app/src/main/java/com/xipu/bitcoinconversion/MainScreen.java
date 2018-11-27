package com.xipu.bitcoinconversion;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;


import cz.msebera.android.httpclient.Header;

public class MainScreen extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public static final String DEFAULT_TAG = "DEFAULT";
    public static final String PRICE_STRING_TAG = "Price";
    public static final String CURRENCY_TAG = "Cur";
    public static final String URL = "https://apiv2.bitcoinaverage.com/convert/global?from=BTC&to=%s&amount=%s";
    public static final String PRICE_TO_CONVERT = "1";
    private static boolean hasInit;
    SharedPreferences mSharedPreferences;
    TextView greetingText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen);

        /* set up spinner */
        Spinner spinner = findViewById(R.id.spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.currency_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        // retrieve and display user's name
        greetingText = findViewById(R.id.greeting);
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        greetingText.append(mSharedPreferences.getString(NameActivity.NAME_TAG, DEFAULT_TAG));
        Log.d("Price", "Called again");

        /* set the boolean flag for listener to distinguish between calls by system's
           initialization or by users' selection */
        hasInit = false;
    }

    private void getResultAndGoToResultScreen(final String targetCurrency, String priceToConvert) {

        // build the url for API calls
        String url = String.format(URL, targetCurrency, priceToConvert);

        // make the API call
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("Bitcoin", "JSON: " + response.toString());
                try {
                    double price = response.getDouble("price");
                    goToResultScreen(price, targetCurrency);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.d("Bitcoin", "Request fail! Status code: " + statusCode);
                Log.d("Bitcoin", "Fail response: " + errorResponse);
                Log.e("ERROR", throwable.toString());
            }
        });

    }

    private void goToResultScreen(double price, String cur){
        // pass the calculated converted price along with the intent
        Intent intent = new Intent(this, ResultScreen.class);
        intent.putExtra(PRICE_STRING_TAG, price);
        intent.putExtra(CURRENCY_TAG, cur);
        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
        /*
            use a boolean flag to distinguish between calls by system's
            initialization or by users' selection
         */
        if(!hasInit){
            hasInit = true;
        }
        else{
            String cur = adapterView.getItemAtPosition(pos).toString();
            getResultAndGoToResultScreen(cur, PRICE_TO_CONVERT);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
