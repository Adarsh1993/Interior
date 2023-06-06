package com.optic.opticvyu;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import com.optic.interior.R;
import com.optic.opticvyu.customviews.Alerdialog;
import com.optic.opticvyu.customviews.Alert;
import com.optic.opticvyu.customviews.Permissions;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import opticvyu.Activity.Two_Factor_Authn;
import opticvyu.Interior.InteriorDashboard;

public class LoginScreenActivity extends AppCompatActivity implements View.OnClickListener, LoginAsyncTask.LoginAsyncTaskCallback {


    private EditText userIdEditTxt;
    private EditText passwordEditTxt;
    private android.widget.Button loginViewBt;
    private android.widget.TextView privacyPolicyViewTxt;
    private android.widget.TextView termsOfUseViewTxt;
    private android.widget.TextView forgorpassword;
    private ProgressDialog progressDialog;
    String acceess, token;
    SharedPreferences sharedPreferences, shareFolder;
    Permissions permissions;
    ImageView mshow_pass_btn;
    Alert alerdialog;
    int passwordNotVisible = 0;
    Alerdialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        sharedPreferences = getSharedPreferences(Constants.APP_PREFERENCE, Context.MODE_PRIVATE);
        final boolean isAlreadyLoggedIn = sharedPreferences.getBoolean(Constants.LOGIN_PREFERENCE, false);
         shareFolder = getSharedPreferences(Constants.Time, MODE_PRIVATE);
        SharedPreferences.Editor sedFolder = shareFolder.edit();


        alerdialog = new Alert();


        if (isAlreadyLoggedIn) {
            if (sharedPreferences.getString(Constants.interior_access, "").equals("1") && sharedPreferences.getString(Constants.const_camera, "").equals("0")) {
//
                Intent in = new Intent(LoginScreenActivity.this, InteriorDashboard.class);
                startActivity(in);
            } else {
                startHomeScreenActivity();
            }

            return;
        }
        setStatusBarColor();

        setContentView(R.layout.activity_login_screen);

        permissions = new Permissions(LoginScreenActivity.this);
        permissions.requestPermission();

        termsOfUseViewTxt = (TextView) findViewById(R.id.termsOfUseViewTxt);

        if (termsOfUseViewTxt != null) {
            termsOfUseViewTxt.setOnClickListener(this);
        }

        privacyPolicyViewTxt = (TextView) findViewById(R.id.privacyPolicyViewTxt);
        if (privacyPolicyViewTxt != null) {
            privacyPolicyViewTxt.setOnClickListener(this);
        }
        forgorpassword = (TextView) findViewById(R.id.forgorpassword);
        if (forgorpassword != null) {
            forgorpassword.setOnClickListener(this);
        }
        loginViewBt = (Button) findViewById(R.id.loginViewBt);
        if (loginViewBt != null) {
            loginViewBt.setOnClickListener(this);
        }
        passwordEditTxt = (EditText) findViewById(R.id.passwordEditTxt);
        passwordEditTxt.setOnFocusChangeListener(new LoginEditFocusChangeListener());

        userIdEditTxt = (EditText) findViewById(R.id.userIdEditTxt);
        mshow_pass_btn = (ImageView) findViewById(R.id.show_pass_btn);
        userIdEditTxt.setOnFocusChangeListener(new LoginEditFocusChangeListener());


        SharedPreferences sharedPreferences = getSharedPreferences(Constants.APP_Remember, MODE_PRIVATE);
        try {
            userIdEditTxt.setText(sharedPreferences.getString(Constants.User_Email, ""));
        } catch (Exception e) {

        }
        try {
            passwordEditTxt.setText(sharedPreferences.getString("Password", ""));
        } catch (Exception e) {

        }

        mshow_pass_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (passwordNotVisible == 1) {
                    mshow_pass_btn.setBackground(getDrawable(R.drawable.closed_eye));

                    passwordEditTxt.setInputType(InputType.TYPE_CLASS_TEXT);
                    passwordNotVisible = 0;
                } else {
                    mshow_pass_btn.setBackground(getDrawable(R.drawable.eye));
                    passwordEditTxt.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    passwordNotVisible = 1;
                }

            }
        });


       if(shareFolder.getString(Constants.timeStamp,"").equals(""))
       {
           Log.e("Hai====",new SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date()));
           sedFolder.putString(Constants.timeStamp, "" + new SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date()));
           sedFolder.commit();
       }
       else {
           Log.e("Hai=========","Haiiiii"+new SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date()));
       }



    }


    private void setStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            final Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getResources().getColor(R.color.custom_yellow2));
        }
    }

    private void loginAction() {

        Log.e("Hai=========","Haiiiii"+shareFolder.getString(Constants.timeStamp,""));
        if (!Util.isNetworkAvailable(this)) {
            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(LoginScreenActivity.this);

            alertDialogBuilder.setTitle("Error");
            alertDialogBuilder.setMessage(getString(R.string.connectivity_error_message));
            alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            final AlertDialog show = alertDialogBuilder.show();

            return;
        } else if (userIdEditTxt.getText().toString().trim().length() == 0) {
            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(LoginScreenActivity.this);

            alertDialogBuilder.setTitle("Error");
            alertDialogBuilder.setMessage("Please enter valid user name.");
            alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            final AlertDialog show = alertDialogBuilder.show();
            return;
        } else if (passwordEditTxt.getText().toString().trim().length() == 0) {
            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(LoginScreenActivity.this);

            alertDialogBuilder.setTitle("Error");
            alertDialogBuilder.setMessage("Please enter valid user password.");
            alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            final AlertDialog show = alertDialogBuilder.show();
            return;
        }


        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.processing_message));
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(true);
        progressDialog.show();
        progressDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                LoginAsyncTask.clearInstance();
            }
        });


        LoginAsyncTask.clearInstance();

        final LoginAsyncTask loginAsyncTask = LoginAsyncTask.getInstance();
        loginAsyncTask.setLoginAsyncTaskCallback(this);
        loginAsyncTask.setUserName(userIdEditTxt.getText().toString().trim());
        loginAsyncTask.setUserPassword(passwordEditTxt.getText().toString().trim());

        loginAsyncTask.execute();

        progressDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {

                LoginAsyncTask.clearInstance();
            }
        });

//        startActivity(new Intent(this, HomeScreenActivity.class));
//        finish();
    }


    @Override
    public void onClick(View view) {
        if (view == loginViewBt) {

            loginAction();
        } else if (view == termsOfUseViewTxt) {
            startActivity(new Intent(this, TermsConditionsActivity.class));
        } else if (view == privacyPolicyViewTxt) {
            startActivity(new Intent(this, PrivacyPolicyActivity.class));
        } else if (view == forgorpassword) {
            // Forgor password open in chrome
            String urlString = "https://secure.opticvyu.com/login";
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlString));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setPackage("com.android.chrome");
            try {
                getApplicationContext().startActivity(intent);
            } catch (ActivityNotFoundException ex) {
                // Chrome browser presumably not installed and open Kindle Browser
                intent.setPackage("com.amazon.cloud9");
                getApplicationContext().startActivity(intent);
            }

        }
    }


    @Override
    public void onLoginSuccess(String token_type, String expires_in, String access_token, String refresh_token) {
        if (!isFinishing()) {
            if (progressDialog != null) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();

                    final SharedPreferences sharedPreferences = getSharedPreferences(Constants.APP_PREFERENCE, Context.MODE_PRIVATE);
                    sharedPreferences.edit().
                            putString(Constants.PROJECT_token_type, token_type).
                            putString(Constants.PROJECT_expires_in, expires_in).
                            putString(Constants.PROJECT_access_token, access_token).
                            putString(Constants.PROJECT_refresh_token, refresh_token).apply();


                    SharedPreferences sharedPreferences1 = getSharedPreferences(Constants.APP_Remember, MODE_PRIVATE);
                    SharedPreferences.Editor sed = sharedPreferences1.edit();
                    sed.putString(Constants.User_Email, "" + userIdEditTxt.getText().toString());
                    sed.putString("Password", "" + passwordEditTxt.getText().toString());
                    sed.commit();

                    //startHomeScreenActivity();
                    GET_USER_DATA();
                }
            }
        }
    }

    private void GET_USER_DATA() {
        //   making folder  for storing camera and normal image every login create new folder


        acceess = sharedPreferences.getString(Constants.PROJECT_access_token, "");
        token = sharedPreferences.getString(Constants.PROJECT_token_type, "");

        try {
            UserData(Constants.Get_User_Details);
        } catch (Exception e) {

        }

    }


    void UserData(String post_url) throws IOException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder().url(post_url).addHeader("Authorization", token + " " + acceess).addHeader("Accept", "application/json").build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String Response = response.body().string();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject jsonObject = new JSONObject(Response);
                            Log.e("jsonObject==", "" + jsonObject);

                            try {
                                String data = jsonObject.getString("message");
                                Toast.makeText(LoginScreenActivity.this, data, Toast.LENGTH_SHORT).show();

                            } catch (Exception e) {

                            }
                            try {
                                JSONObject data = jsonObject.getJSONObject("success");
                                final String Email = data.getString("email");
                                final String First_name = data.getString("firstname");
                                final String Last_name = data.getString("lastname");
                                final String User_code = data.getString("user_code");
                                final String User_id = data.getString("id");
                                final String contactNo = data.getString("contactNo");
                                final String free_access = data.getString("free_access");
                                final String cctvtimelapse_user = data.getString("cctvtimelapse_user");
                                final String User2F_auth = data.getString("User2F_auth");
                                final String opticvyu_user = data.getString("opticvyu_user");
                                final String interior_access = data.getString("interior_access");
                                final String const_camera = data.getString("cont_camera_aceess");


                                if (!User_id.isEmpty() && !Email.isEmpty()) {

                                    final SharedPreferences sharedPreferences = getSharedPreferences(Constants.APP_PREFERENCE, Context.MODE_PRIVATE);
                                    sharedPreferences.edit().putBoolean(Constants.LOGIN_PREFERENCE, false).
                                            putString(Constants.User_Email, Email).
                                            putString(Constants.User_First_Name, First_name).
                                            putString(Constants.User_Last_Name, Last_name).
                                            putString(Constants.User_code, User_code).
                                            putString(Constants.opticvyu_user, opticvyu_user).
                                            putString(Constants.User_contact, contactNo).
                                            putString(Constants.User_ID, User_id).
                                            putString(Constants.User2F_auth, User2F_auth).
                                            putString(Constants.cctvtimelapse_user, cctvtimelapse_user).
                                            putString(Constants.Free, free_access).
                                            putString(Constants.const_camera, const_camera).
                                            putString(Constants.interior_access, interior_access).apply();


                                    if (cctvtimelapse_user.equals("1")) {
                                        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(LoginScreenActivity.this);

                                        alertDialogBuilder.setTitle("Error");
                                        alertDialogBuilder.setMessage("Access Not Allowed");
                                        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
//                                    show.dismiss();
                                            }
                                        });
                                        final AlertDialog show = alertDialogBuilder.show();
                                    } else {
                                        if (User2F_auth.equals("1")) {
                                            startActivity(new Intent(LoginScreenActivity.this, Two_Factor_Authn.class));

                                        } else {
                                            final SharedPreferences sharedPreferences1 = getSharedPreferences(Constants.APP_PREFERENCE, Context.MODE_PRIVATE);
                                            sharedPreferences1.edit().putBoolean(Constants.LOGIN_PREFERENCE, true).
                                                    apply();
                                            startHomeScreenActivity();


                                        }

                                        if (interior_access.equals("1") && const_camera.equals("0")) {
                                            startActivity(new Intent(LoginScreenActivity.this, InteriorDashboard.class));
                                            finish();
                                        }


                                    }


                                } else {
                                    Toast.makeText(LoginScreenActivity.this, "Somthing Went Wrong", Toast.LENGTH_SHORT).show();
                                }


                            } catch (Exception e) {

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });


            }
        });

    }


    private void startHomeScreenActivity() {

        startActivity(new Intent(LoginScreenActivity.this, InteriorDashboard.class));
        finish();
    }

    @Override
    public void onLoginFailed(final String errorMessage) {
        if (!isFinishing()) {
            if (progressDialog != null) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(LoginScreenActivity.this);

                            alertDialogBuilder.setTitle("Error");
                            alertDialogBuilder.setMessage(errorMessage);
                            alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
//                                    show.dismiss();
                                }
                            });
                            final AlertDialog show = alertDialogBuilder.show();
                        }
                    });
                }
            }
        }
    }

    private class LoginEditFocusChangeListener implements View.OnFocusChangeListener {

        @Override
        public void onFocusChange(View view, boolean hasFocus) {
            if (hasFocus)
                ((EditText) view).setTextColor(getResources().getColor(R.color.custom_yellow));
            else
                ((EditText) view).setTextColor(getResources().getColor(R.color.custom_grey));
        }
    }
}

class LoginAsyncTask extends AsyncTask {

    private static LoginAsyncTask loginAsyncTask;

    private String userName;
    private String userPassword;
    private LoginAsyncTaskCallback loginAsyncTaskCallback;
    private boolean isInterrupted;
    String token_type, access_token;

    private LoginAsyncTask() {
    }

    public synchronized static LoginAsyncTask getInstance() {
        if (loginAsyncTask == null) {
            loginAsyncTask = new LoginAsyncTask();
        }
        return loginAsyncTask;
    }

    public synchronized static void clearInstance() {
        if (loginAsyncTask != null)
            loginAsyncTask.isInterrupted = true;
        loginAsyncTask = null;
    }

    @Override

    protected Object doInBackground(Object[] params) {
        try {
            final URL urlObj = new URL(Constants.Get_Oath_Details);

            final HttpURLConnection conn = (HttpURLConnection) urlObj.openConnection();


            conn.setRequestMethod("GET");

            conn.setConnectTimeout(20000);

            conn.setDoInput(true);
            conn.setDoOutput(true);

            Uri.Builder builder = new Uri.Builder()
                    .appendQueryParameter("grant_type", "password")
                    .appendQueryParameter("client_id", "2")
                    .appendQueryParameter("client_secret", Constants.Secret_ID)
                    .appendQueryParameter("username", userName)
                    .appendQueryParameter("password", userPassword)
                    .appendQueryParameter("scope", "");

            String query = builder.build().getEncodedQuery();
            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(query);
            writer.flush();
            writer.close();
            os.close();


            conn.connect();
            //Receive the response from the server
            final InputStream in = new BufferedInputStream(conn.getInputStream());
            final BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            final StringBuilder result = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
            if (isInterrupted) {
                return null;
            }
            final JSONObject jsonObject = new JSONObject(result.toString());
            Log.e("Data", "" + jsonObject);
            final String error;

            if (jsonObject.has("error")) {
                error = jsonObject.getString("error");
                Log.e("error", "" + error);
            } else {
                Log.e("Data==2", "" + jsonObject);
                error = null;
            }

            if (error != null && loginAsyncTaskCallback != null) {
                loginAsyncTaskCallback.onLoginFailed(error);
            } else {
                token_type = jsonObject.getString("token_type");
                final String expires_in = jsonObject.getString("expires_in");
                access_token = jsonObject.getString("access_token");
                final String refresh_token = jsonObject.getString("refresh_token");


                Log.e("token_type", " " + token_type);
                Log.e("expires_in", " " + expires_in);
                Log.e("access_token", " " + access_token);
                Log.e("refresh_token", " " + refresh_token);


                if (loginAsyncTaskCallback != null)
                    loginAsyncTaskCallback.onLoginSuccess(token_type, expires_in, access_token, refresh_token);
            }

            Log.e("JSON Parser", "result: " + result.toString());
            conn.disconnect();

        } catch (Exception ex) {
            if (loginAsyncTaskCallback != null) {

                Log.e("JSON Parser", "result: " + ex.toString());
                loginAsyncTaskCallback.onLoginFailed("Incorrect login ID or password");
            }
        }

        return null;
    }


    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public void setLoginAsyncTaskCallback(LoginAsyncTaskCallback loginAsyncTaskCallback) {
        this.loginAsyncTaskCallback = loginAsyncTaskCallback;
    }

    public interface LoginAsyncTaskCallback {

        void onLoginSuccess(String token_type, String expires_in, String access_token, String refresh_token);

        void onLoginFailed(final String errorMessage);
    }
    //..........Get Access user data............


}
