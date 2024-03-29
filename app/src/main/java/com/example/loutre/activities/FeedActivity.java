package com.example.loutre.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.loutre.POJO.*;
import com.example.loutre.R;
import com.example.loutre.adapter.ListViewAdapter;
import com.example.loutre.rest.RestClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 *  Actual feed
 */

public class FeedActivity extends AppCompatActivity {
    private EditText etSearch; // For the search field
    private ListView lvFeed;
    private ListViewAdapter lvAdapter;
    private ArrayList<Data> data = new ArrayList<>();
    private String access_token = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        // Get the access_token from the IntentExtra
        Intent i = this.getIntent();
        access_token = i.getStringExtra("access_token");

        lvFeed = (ListView) findViewById(R.id.lv_feed);
        etSearch = (EditText) findViewById(R.id.et_search);

        // Set the listview adapter
        lvAdapter = new ListViewAdapter(this, 0, data);
        lvFeed.setAdapter(lvAdapter);

        // Set the listener for the "Done" button of the soft keyboard
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                // Don't search if the etSearch is emtpy when pressing the done button
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    if(etSearch.getText().length() <= 0){
                        Toast.makeText(getApplicationContext(), "Enter a search tag", Toast.LENGTH_SHORT).show();
                    } else {
                        lvAdapter.clearListView();
                        fetchData(etSearch.getText().toString());
                        etSearch.setText("");
                        etSearch.clearFocus();
                    }
                    // Close the soft keyboard
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });

    }

    public void fetchData(String tag) {
        Call<InstagramResponse> call = RestClient.getRetrofitService().getTagPhotos(tag, access_token); // Getting the Retrofit Service and making the request
        call.enqueue(new Callback<InstagramResponse>() {
            @Override
            public void onResponse(Call<InstagramResponse> call, Response<InstagramResponse> response) {
                if (response.body() != null) {
                    for(int i = 0; i < response.body().getData().length; i++){ // Getting the JSON data
                        data.add(response.body().getData()[i]); // Clear and update it
                    }
                    lvAdapter.notifyDataSetChanged(); // Notifying the data has changed
                }
            }

            @Override
            public void onFailure(Call<InstagramResponse> call, Throwable t) {
                //Handle failure
                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
