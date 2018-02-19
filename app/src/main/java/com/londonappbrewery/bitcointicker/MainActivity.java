package com.londonappbrewery.bitcointicker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;


import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import cz.msebera.android.httpclient.Header;


public class MainActivity extends AppCompatActivity {

    // Constants:
    // TODO: Create the base URL
    private final String BASE_URL = "https://apiv2.bitcoinaverage.com/indices/global/ticker/BTC";


    // Member Variables:
    TextView mPriceTextViewask;
    TextView mPriceTextViewbid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPriceTextViewask = (TextView) findViewById(R.id.priceLabel);
        mPriceTextViewbid = (TextView) findViewById(R.id.priceLabel2);
        Spinner spinner = (Spinner) findViewById(R.id.currency_spinner);

        // Create an ArrayAdapter using the String array and a spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.currency_array, R.layout.spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        //the whole chunk of code above is taken from android dev website

        // TODO: Set an OnItemSelected listener on the spinner
        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("CRYPTO",""+parent.getItemAtPosition(position));
                Log.d("CRYPTO","position is: "+position);
                String finalUrl = BASE_URL + parent.getItemAtPosition(position);
                letsDoSomeNetworking(finalUrl);
                //how do you append URL endings?
                //you can just use + !!! what!!!
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.d("CRYTO","nothing selected");
            }
        });

    }



    // TODO: complete the letsDoSomeNetworking() method
    private void letsDoSomeNetworking(String url) {

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new JsonHttpResponseHandler() {
            //the url here is just a placeholder

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // called when response HTTP status is "200 OK"
                Log.d("Crypto", "JSON: " + response.toString());
                CryptoDataModel coinPx = CryptoDataModel.fromJson(response);
                updateUI(coinPx);

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject response) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                Log.d("Clima", "Request fail! Status code: " + statusCode);
                Log.d("Clima", "Fail response: " + response);
                Log.e("ERROR", e.toString());
                Toast.makeText(MainActivity.this, "Request Failed", Toast.LENGTH_SHORT).show();
            }
        });


    }

    // Updates the information shown on screen.
    private void updateUI(CryptoDataModel coin) {//is this weather var a jsonobject?
        mPriceTextViewask.setText(coin.getAskPrice());
        mPriceTextViewbid.setText(coin.getBidPrice());
    }


}
