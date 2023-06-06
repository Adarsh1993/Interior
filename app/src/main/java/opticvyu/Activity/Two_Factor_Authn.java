package opticvyu.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.optic.interior.R;
import com.optic.interior.R;
import com.optic.opticvyu.Constants;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import opticvyu.Interior.InteriorDashboard;

public class Two_Factor_Authn extends AppCompatActivity {

    EditText authentication;
    Button verify;
    TextView send_code_again;
    String OtpValue, User_Email,type, access;
    SharedPreferences sharedPreferences;
    ImageView back;
    ProgressDialog dialog;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tf_authentication);
        sharedPreferences = getSharedPreferences(Constants.APP_PREFERENCE, MODE_PRIVATE);
        User_Email = sharedPreferences.getString(Constants.User_Email, "");
        type = sharedPreferences.getString(Constants.PROJECT_token_type, "");
        access = sharedPreferences.getString(Constants.PROJECT_access_token, "");
        dialog = new ProgressDialog(this);
        authentication = findViewById(R.id.authentication);
        verify = findViewById(R.id.verify);
        back = findViewById(R.id.back);
        send_code_again = findViewById(R.id.send_code_again);

        sendOTP();

        send_code_again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.setMessage("Please wait...");
                dialog.setCancelable(false);
                dialog.show();
                sendOTP();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean bb = validation();
                if (bb) {
                    dialog.setMessage("Please wait...");
                    dialog.setCancelable(false);
                    dialog.show();
                    VerifyOTP();

                } else {


                }

            }
        });
    }


    boolean validation() {
        if (authentication.getText().toString().equals("")) {
            authentication.setError("Value cannot be null");
        } else {
            OtpValue = authentication.getText().toString();
            return true;
        }
        return false;
    }

    void sendOTP() {

        RequestBody body = new FormBody.Builder().build();
        OkHttpClient okHttpClient = new OkHttpClient();

        Request request = new Request.Builder().url("https://api.opticvyu.com/api/user/" + User_Email + "/twofactor_verification").post(body)
                . addHeader(Constants.API_Authorization, type + " " + access).addHeader(Constants.API_Accept, "application/json").build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d("onFailure ==", e.toString());
                Toast.makeText(Two_Factor_Authn.this, "Success", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                assert response.body() != null;
                dialog.dismiss();

            }
        });
    }

    void VerifyOTP() {

        RequestBody body = new FormBody.Builder().add("emailadd", User_Email)
                .add("otp", OtpValue)
                .build();

        OkHttpClient okHttpClient = new OkHttpClient();

        Request request = new Request.Builder().url("https://api.opticvyu.com/api/user/verify_otppwd").
                addHeader(Constants.API_Authorization, type + " " + access).addHeader(Constants.API_Accept, "application/json").post(body).build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d("onFailure ==", e.toString());
                dialog.dismiss();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                assert response.body() != null;
                final String Response = response.body().string();
                Two_Factor_Authn.this.runOnUiThread(() -> {
                    try {
                        dialog.dismiss();
                        JSONObject object = new JSONObject(Response);
                        if(object.getString("success").equals("false"))
                        {
                            Toast.makeText(Two_Factor_Authn.this, "OTP does not matched, try again!", Toast.LENGTH_SHORT).show();
                        }else {
                            final SharedPreferences sharedPreferences1 = getSharedPreferences(Constants.APP_PREFERENCE, Context.MODE_PRIVATE);
                            sharedPreferences1.edit().putBoolean(Constants.LOGIN_PREFERENCE, true).apply();
                            startActivity(new Intent(Two_Factor_Authn.this, InteriorDashboard.class));
                            finish();
                        }



                    } catch (Exception e) {
                        Log.d(" fail ==", e.toString());
                        dialog.dismiss();
                    }
                });

            }
        });
    }
}
