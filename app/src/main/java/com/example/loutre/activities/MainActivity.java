package com.example.loutre.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.loutre.R;
import com.example.loutre.dialog.AuthDialog;
import com.example.loutre.listener.AuthListener;

/**
 *  Login and getting the access token
 */


public class MainActivity extends AppCompatActivity implements AuthListener {
    private AuthDialog auth_dialog;
    private Button btn_get_access_token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_get_access_token = (Button) findViewById(R.id.btn_get_access_token);

        btn_get_access_token.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth_dialog = new AuthDialog(MainActivity.this, MainActivity.this);
                auth_dialog.setCancelable(true);
                auth_dialog.show();
            }
        });
    }

    @Override
    /**
     *  Changing the current activity and passing acces_token to the next activity
     */
    public void onCodeReceived(String access_token) {
        if (access_token == null) {
            auth_dialog.dismiss();
        }

        Intent next = new Intent(MainActivity.this, FeedActivity.class);
        next.putExtra("access_token", access_token);
        startActivity(next);

    }
}
