
package com.optic.opticvyu;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;


import com.optic.interior.R;

public class SplashScreenActivity extends Activity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setStatusBarColor();

        setContentView(R.layout.activity_splash_screen);

        final ImageView imageView = (ImageView) findViewById(R.id.animViewImg);
//        Glide.with(getApplicationContext()).load(R.drawable.opticuview)
//                .asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).crossFade().into(imageView);



        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!isFinishing()) {


                        try {
                            Thread.sleep(1000);

                            imageView.post(new Runnable() {
                                @Override
                                public void run() {
                                    startNextActivity();
                                }
                            });
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                }
            }
        }).start();
    }

    private void setStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            final Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getResources().getColor(R.color.custom_yellow2));
        }
    }



    private void startNextActivity() {
        if (!isFinishing()) {
            startActivity(new Intent(this, LoginScreenActivity.class));//TODO need to change  it.
            finish();
        }
    }
}
