package com.xipu.bitcoinconversion;

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
    public static final String URL = "https://apiv2.bitcoinaverage.com/convert/global?from=BTC&to=%s&amount=%s";
    public static final String PRICE_TO_CONVERT = "1";
    private static boolean isInit = false;
    SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Spinner spinner = findViewById(R.id.spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.currency_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        TextView textView = findViewById(R.id.greeting);
        textView.append(mSharedPreferences.getString(NameActivity.NAME_TAG, DEFAULT_TAG));

        Log.d("Price", String.format(URL, "USD", PRICE_TO_CONVERT));

        letsDoSomeNetworking("https://apiv2.bitcoinaverage.com/convert/global?from=BTC&to=USD&amount=2");
    }

    private void letsDoSomeNetworking(String url) {

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                //super.onSuccess(statusCode, headers, response);
                Log.d("Bitcoin", "JSON: " + response.toString());
                try {
                    double price = response.getDouble("price");
                    Log.d("Price", String.valueOf(price));
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

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
        /*
            use a boolean flag to distinguish between calls by system's
            initialization or by users' selection
         */
        if(!isInit){
            isInit = true;
        }
        else{
            Log.d("Price", adapterView.getItemAtPosition(pos).toString());
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
