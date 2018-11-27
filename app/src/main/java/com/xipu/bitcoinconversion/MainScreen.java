package com.xipu.bitcoinconversion;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MainScreen extends AppCompatActivity {

    public static final String DEFAULT_TAG = "DEFAULT";
    SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Spinner spinner = findViewById(R.id.spinner);
//        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
//                R.array.currency_array, R.layout.spinner_item);

        // Specify the layout to use when the list of choices appears
//        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        // Apply the adapter to the spinner
//        spinner.setAdapter(adapter);

        TextView textView = findViewById(R.id.greeting);
        textView.append(mSharedPreferences.getString(NameActivity.NAME_TAG, DEFAULT_TAG));

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
}
