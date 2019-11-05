package com.example.loutre.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.example.loutre.Constants;
import com.example.loutre.R;
import com.example.loutre.listener.AuthListener;


public class AuthDialog extends Dialog {

    private final AuthListener listener;
    private Context context;
    private WebView web_view;

    // Creating the custom URL thanks to the constants, to get the access_token
    private final String url = Constants.BASE_URL
            + "oauth/authorize/?app-id="
            + Constants.CLIENT_ID
            + "&redirect_uri="
            + Constants.REDIRECT_URI
            + "&scope=user_profile,user_media"
            + "&response_type=code";

    private final String url2 =
            //"https://google.com"; Chargement : ok
            "https://api.instagram.com/oauth/authorize/?app_id=575568853188767&redirect_uri=https://instagram.com/&scope=user_profile,user_media&response_type=code";

    public AuthDialog(@NonNull Context context, AuthListener listener) {
        super(context);
        this.context = context;
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.auth_dialog);
        initializeWebView();
    }

    private void initializeWebView() {
        web_view = (WebView) findViewById(R.id.web_view);
        web_view.loadUrl(url);
        web_view.setWebViewClient(new WebViewClient() {

            boolean authComplete = false;
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            String access_token;

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                // Testing if the resquest is successful and getting the access_token
                if (url.contains("#access_token=") && !authComplete) {
                    Uri uri = Uri.parse(url);
                    access_token = uri.getEncodedFragment();
                    access_token = access_token.substring(access_token.lastIndexOf("=")+1); // get the whole token after the '=' sign
                    Log.i("", "CODE : " + access_token);
                    authComplete = true;
                    listener.onCodeReceived(access_token);
                    dismiss(); // removing the dialog from the screen, activity switching

                } else if (url.contains("?error")) {
                    Toast.makeText(context, "Error Occured", Toast.LENGTH_SHORT).show();
                    dismiss();
                }
            }
        });
    }
}
