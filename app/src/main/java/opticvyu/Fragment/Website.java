package opticvyu.Fragment;

import android.content.Intent;
import android.os.Bundle;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;
import android.webkit.WebView;

import com.optic.interior.R;

public class Website extends AppCompatActivity {
    WebView webView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        webView = findViewById(R.id.webView1);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("http://www.opticvyu.com/");
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
