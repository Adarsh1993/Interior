package com.optic.opticvyu;

import android.os.Bundle;

import android.view.View;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

import com.optic.interior.R;

public class PrivacyPolicyActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.terms_conditions_activity);

        WebView webView = (WebView) findViewById(R.id.webView);
        webView.loadUrl("file:///android_res/raw/privacy_policy.htm");
    }

    private void startNextActivity() {
    }

    @Override
    public void onClick(View view) {
    }
}
