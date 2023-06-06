package opticvyu.Interior;

import static android.os.Environment.DIRECTORY_DOCUMENTS;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.arashivision.sdkcamera.camera.InstaCameraManager;
import com.arashivision.sdkcamera.camera.callback.ICaptureStatusListener;
import com.arashivision.sdkmedia.export.ExportImageParamsBuilder;
import com.arashivision.sdkmedia.export.ExportUtils;
import com.arashivision.sdkmedia.export.IExportCallback;
import com.arashivision.sdkmedia.work.WorkWrapper;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.optic.interior.BuildConfig;
import com.optic.interior.R;
import com.optic.interior.Sensor;
import com.optic.opticvyu.Constants;
import com.optic.opticvyu.LoginScreenActivity;
import com.optic.opticvyu.customviews.Alerdialog;
import com.optic.opticvyu.customviews.CheckNetwork;
import com.optic.opticvyu.customviews.Connection_Alert;
import com.optic.opticvyu.customviews.HRD_option;
import com.optic.opticvyu.customviews.Permissions;
import com.otaliastudios.zoom.ZoomLayout;
import com.ricoh.network.DeviceInfo;
import com.ricoh.network.HttpConnector;
import com.ricoh.network.HttpEventListener;
import com.ricoh.view.ImageRow;
import com.ricoh.view.MJpegInputStream;
import com.ricoh.view.MJpegView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import io.realm.Realm;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import opticvyu.Fragment.Website;
import opticvyu.Model.DrawingImages;
import opticvyu.Model.GetCoordinate.ResponseStItem;
import opticvyu.Model.Project_model;
import opticvyu.Model.Zone;
import opticvyu.Permission;
import opticvyu.Utils.NetworkUtil;
import opticvyu.Utils.RealmController;
import uk.co.senab.photoview.PhotoViewAttacher;

public class InteriorDashboardTimeLapse extends AppCompatActivity {
    Spinner project, Zone, drawing;
    SharedPreferences sharedPreferences, Project_prefrenece, sharedFOlder, cameraModePreferences;
    String const_cameraFromUser, interior_access, const_camera, type, access, ProjectID, zoneId, drawingID, CID, PID, DID1, ProjectIndex = "0";
    ArrayList<Project_model> project_models = new ArrayList<Project_model>();
    ArrayList<Zone> getZone = new ArrayList<Zone>();
    ArrayList<DrawingImages> drawingImages = new ArrayList<>();
    List<ResponseStItem> nearResponses = new ArrayList<>();
    int dHeight, dWidth, mHeight, mWidth, orientation, ActualWidth, ActualHeight;
    float aFloatValue;
    RelativeLayout point;
    ImageView mLandScape;
    Handler handler = new Handler();
    ImageView imgView, menu;
    LinearLayout zoneLinear, drawingSpinner;
    ProgressDialog pd;
    TextView uploadImages, mode, normalImages, plus, hdrMode;
    Connection_Alert connectionAlert;
    int imageCount = 0, count, uploadStatus = 0, status = 0;
    File sdcardPath;
    Alerdialog alertDialog;
    private MJpegView mMv;
    int maxLimit = 0;
    Permissions permissions;
    String timeStampVar, firstName, lastName, mCurrentPhotoPath, coordinateID, timeStamp;
    Switch mSwitch;
    ArrayAdapter<Zone> zoneAdapter;
    ArrayAdapter<DrawingImages> DrawingAdapter;
    boolean cameraStatus = true;
    private final int REQUEST_TAKE_PHOTO = 101;
    Permission permission;
    private boolean mConnectionSwitchEnabled = false;
    private static final int PERMISSION_REQUEST_CODE = 7;
    private ShowLiveViewTask livePreviewTask = null;
    private LoadObjectListTask sampleTask = null;
    private String cameraIpAddress;
    ProgressBar mProgressBar;
    Realm mRealm;
    RealmController realmController;
    boolean netActive;
    String CameraName = "", CameraNameAnother = "",drawingSingleImage = "";
    ZoomLayout mZoomLayout;
    LocationManager mLocationManager;
    private int mCurrentExportId = -1;
    private static String EXPORT_DIR_PATH;
    HRD_option hrd_option;
    boolean hdrOption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interior_dashboardtimelapse);
        mLocationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
        final Window win = getWindow();
        NetworkUtil.getConnectivityStatusString(getApplicationContext());
        win.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON | WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON);
        mRealm = Realm.getDefaultInstance();
        GetCheckOrientationAndDimension();
        getSupportActionBar().hide();
        sharedPreferences = getSharedPreferences(Constants.APP_PREFERENCE, MODE_PRIVATE);
        sharedFOlder = getSharedPreferences(Constants.Time, MODE_PRIVATE);
        cameraModePreferences = getSharedPreferences(Constants.CameraSelectionMode, MODE_PRIVATE);
        timeStampVar = sharedFOlder.getString(Constants.timeStamp, "");
        imgView = new ImageView(getApplicationContext());
        project = findViewById(R.id.projectSpinner);
        mZoomLayout = findViewById(R.id.zoom_image);
        hdrMode = findViewById(R.id.hdrMode);
        Zone = findViewById(R.id.zoneSpinner);
        drawing = findViewById(R.id.drawing);
        uploadImages = findViewById(R.id.uploadImages);
        normalImages = findViewById(R.id.normalImages);
        mProgressBar = findViewById(R.id.loading_object_list_progress_bar);
        mode = findViewById(R.id.mode);
        point = findViewById(R.id.point);
        zoneLinear = findViewById(R.id.zone);
        drawingSpinner = findViewById(R.id.drawingSpinner);
        mLandScape = findViewById(R.id.image_landScape);
        mSwitch = findViewById(R.id.switchButton);
        menu = findViewById(R.id.menu);
        mMv = (MJpegView) findViewById(R.id.live_view);


        checkDevice();

        type = sharedPreferences.getString(Constants.PROJECT_token_type, "");
        access = sharedPreferences.getString(Constants.PROJECT_access_token, "");
        interior_access = sharedPreferences.getString(Constants.interior_access, "");
        const_cameraFromUser = sharedPreferences.getString(Constants.const_camera, "");
        firstName = sharedPreferences.getString(Constants.User_First_Name, "");
        lastName = sharedPreferences.getString(Constants.User_Last_Name, "");

        connectionAlert = new Connection_Alert();
        alertDialog = new Alerdialog();
        Project_prefrenece = getSharedPreferences("ProjectID", MODE_PRIVATE);
        ProjectIndex = Project_prefrenece.getString(Constants.ProjectIndex, "");
        permission = new Permission(this);
        permission.requestPermission();
        mode.setText("360° Image Capturing Mode");
        cameraIpAddress = getResources().getString(R.string.theta_ip_address);
        realmController = new RealmController(this);
        sdcardPath = new File(Environment.getExternalStorageDirectory().getPath() + "/Documents/Opticvyu_images" + timeStampVar);
        EXPORT_DIR_PATH = Environment.getExternalStoragePublicDirectory(DIRECTORY_DOCUMENTS) + "/Opticvyu_images" + timeStampVar + "/";
        //  this lib holds the image and disable zoom . Zoom work from ZoomLayout lib.
        PhotoViewAttacher photoViewAttacher = new PhotoViewAttacher(mLandScape);
        photoViewAttacher.setZoomable(false);
        Constants.Mode = cameraModePreferences.getString("selectionMode", getString(R.string.richo_360));
        hdrMode.setText(!cameraModePreferences.getBoolean("hdrMode", false) ? "HDR- Mode OFF" : "HDR- Mode ON");


        //GetProject();
        OfflineProject();
        project.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Project_model pm = project_models.get(i);
                ProjectID = pm.getProject_id();
                const_camera = pm.getConst_camera();
                SharedPreferences.Editor sed = Project_prefrenece.edit();
                sed.putString(Constants.ProjectIndex, "" + i);
                sed.commit();
                if (pm.getInterior() != "1") {
                    OfflineZone();
                } else {
                    connectionAlert.interiorAccess(getApplicationContext());
                    getZone.clear();
                    drawingImages.clear();

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Zone.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Zone zone = getZone.get(i);
                zoneId = zone.getZoneid();
                offlineDrawing();


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        drawing.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                DrawingImages mImages = drawingImages.get(i);
                drawingID = mImages.getDrawingid();
                drawingSingleImage = mImages.getDrawingpath();
                point.removeAllViews();


                Glide.with(getApplicationContext()).load(drawingSingleImage).into(mLandScape);


                Glide.with(getApplicationContext()).asBitmap().load(drawingSingleImage).into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        dWidth = resource.getWidth();
                        dHeight = resource.getHeight();

                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mLandScape.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        ActualHeight = mLandScape.getMeasuredHeight();
                                        ActualWidth = mLandScape.getMeasuredWidth();
                                        setPoint();
                                    }
                                });
                            }
                        }, 2000);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                    }
                });

                offlineCoordinate();


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        uploadImages.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                permissions = new Permissions(InteriorDashboardTimeLapse.this);
                permissions.requestPermission();
                getProgressShow("Please Wait...");
                try {
                    GetNet(Constants.User_Project);

                } catch (IOException e) {

                }

            }
        });

        normalImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                permissions = new Permissions(InteriorDashboardTimeLapse.this);
                permissions.requestPermission();
                getProgressShow("Please Wait...");
                try {
                    GetNet(Constants.User_Project);

                } catch (IOException e) {

                }
            }
        });

        mSwitch.setOnClickListener(v -> {
            if (mSwitch.isChecked()) {
                uploadStatus = 0;
                cameraStatus = true;
                mode.setText("360° Image Capturing Mode");
                uploadImages.setVisibility(View.VISIBLE);
                normalImages.setVisibility(View.GONE);

            } else {
                cameraStatus = false;
                mode.setText("Normal Image Capturing Mode");
                normalImages.setBackgroundDrawable(getDrawable(R.drawable.buttonroundyellow));
                uploadImages.setVisibility(View.GONE);
                normalImages.setVisibility(View.VISIBLE);


            }
            uploadStatusButton();

        });
    }


    void GetDrawing(String Api) {
        getProgressShow("Loading the drawing...");
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(Api).addHeader(Constants.API_Authorization, type + " " + access).addHeader(Constants.API_Accept, "application/json").build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                OkHttpClient okHttpClient = new OkHttpClient();
                Request request = new Request.Builder().url(Api).addHeader(Constants.API_Authorization, type + " " + access).addHeader(Constants.API_Accept, "application/json").build();
                okHttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {

                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        String getDrawing = response.body().string();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                try {
                                    drawingImages.clear();
                                    JSONObject jsonObject = new JSONObject(getDrawing);
                                    JSONArray drawingArray = jsonObject.getJSONArray("success");

                                    if (drawingArray.length() == 0) {
                                        drawingSpinner.setVisibility(View.INVISIBLE);
                                    } else {
                                        drawingSpinner.setVisibility(View.VISIBLE);
                                    }

                                    for (int i = 0; i < drawingArray.length(); i++) {
                                        JSONObject object = drawingArray.getJSONObject(i);
                                        DrawingImages mDrawingImages = new DrawingImages();
                                        String drawingid = object.getString("drawingid");
                                        String name = object.getString("name");
                                        String drawingpath = object.getString("drawingpath");
                                        String projectid = object.getString("projectid");
                                        String zoneid = object.getString("zoneid");
                                        mDrawingImages.setDrawingid(drawingid);
                                        mDrawingImages.setDrawingpath(drawingpath);
                                        mDrawingImages.setName(name);
                                        mDrawingImages.setProjectid(projectid);
                                        mDrawingImages.setZoneid(zoneid);
                                        drawingImages.add(mDrawingImages);


                                    }
                                    DrawingAdapter = new ArrayAdapter<DrawingImages>(InteriorDashboardTimeLapse.this, R.layout.spinner_text, R.id.spinner_text, drawingImages);
                                    drawing.setAdapter(DrawingAdapter);
                                    realmController.AddDrawing(drawingImages);
                                    ProgressDismiss();
                                } catch (JSONException e) {
                                    Log.e("drawingImagesError", "" + e.toString());
                                }
                            }
                        });
                    }
                });

            }
        });
    }


    private void GetProject() {
        try {
            getProgressShow("Loading the project...");
            if (CheckNetwork.isInternetAvailable(InteriorDashboardTimeLapse.this)) {
                Get_project_Data(Constants.User_Project);
            } else {
                OfflineProject();
            }


        } catch (Exception e) {
            ProgressDismiss();
        }
    }

    private void GetZone(String ProjectID) {
        getProgressShow("Loading the zone...");
        String zone = Constants.InteriorProject + ProjectID + Constants.GetZonE;

        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(zone).addHeader(Constants.API_Authorization, type + " " + access).addHeader(Constants.API_Accept, "application/json").build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String zone = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            JSONObject jsonObject = new JSONObject(zone);
                            JSONArray zoneArray = jsonObject.getJSONArray("success");
                            if (zoneArray.length() == 0) {
                                zoneLinear.setVisibility(View.INVISIBLE);
                                drawingSpinner.setVisibility(View.INVISIBLE);
                            } else {
                                zoneLinear.setVisibility(View.VISIBLE);
                                drawingSpinner.setVisibility(View.VISIBLE);
                            }
                            getZone.clear();

                            for (int i = 0; i < zoneArray.length(); i++) {
                                JSONObject object = zoneArray.getJSONObject(i);
                                Zone mZone1 = new Zone();
                                String projectid = object.getString("projectid");
                                String zoneID = object.getString("zoneid");
                                String zoneName = object.getString("zonename");
                                mZone1.setProjectid(projectid);
                                mZone1.setZoneid(zoneID);
                                mZone1.setZonename(zoneName);
                                getZone.add(mZone1);


                            }

                            zoneAdapter = new ArrayAdapter<Zone>(InteriorDashboardTimeLapse.this, R.layout.spinner_text, R.id.spinner_text, getZone);
                            Zone.setAdapter(zoneAdapter);
                            zoneAdapter.notifyDataSetChanged();
                            realmController.AddZone(getZone);
                            ProgressDismiss();

                        } catch (JSONException e) {
                            Log.e("zoneError", "" + e.toString());
                        }
                    }
                });
            }
        });
    }

    void Get_project_Data(String project_API) throws IOException {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(project_API).addHeader(Constants.API_Authorization, type + " " + access).addHeader(Constants.API_Accept, "application/json").build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                //Toast.makeText(Navigation_drawer.this, "" + e.toString(), Toast.LENGTH_SHORT).show();
            }


            @Override
            public void onResponse(Call call, Response response) throws IOException {

                final String Response = Objects.requireNonNull(response.body()).string();


                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {

                            JSONObject jsonObject = new JSONObject(Response);
                            try {
                                JSONArray project_array = jsonObject.getJSONArray("success");
                                for (int i = 0; i < project_array.length(); i++) {
                                    JSONObject object = project_array.getJSONObject(i);
                                    Project_model projectModel = new Project_model();
                                    String Project_id = object.getString("id");
                                    String Project_name = object.getString("project_name");
                                    String Project_active = object.getString("project_active");
                                    String const_camera = object.getString("const_camera");
                                    String interior = object.getString("interior");
                                    if (interior.equals("1")) {
                                        if (Project_active.equals("1")) {
                                            projectModel.setProject_id(Project_id);
                                            projectModel.setProject_name(Project_name);
                                            projectModel.setProject_active(Project_active);
                                            projectModel.setConst_camera(const_camera);
                                            projectModel.setInterior(interior);
                                            project_models.add(projectModel);

                                        } else {

                                        }
                                    }


                                    final ArrayAdapter<Project_model> arrayAdapter = new ArrayAdapter<Project_model>(InteriorDashboardTimeLapse.this, R.layout.spinner_text, R.id.spinner_text, project_models);
                                    project.setAdapter(arrayAdapter);


                                }
                                realmController.addProjects(project_models);
                                if (!ProjectIndex.equals("0")) {
                                    try {
                                        project.setSelection(Integer.parseInt(ProjectIndex));
                                    } catch (Exception e) {

                                    }

                                }

                            } catch (Exception e) {
                                ProgressDismiss();


                            }
                            try {
                                String project_response = jsonObject.getString("message");
                                Toast.makeText(InteriorDashboardTimeLapse.this, "" + project_response, Toast.LENGTH_SHORT).show();
                            } catch (Exception e) {
                                ProgressDismiss();

                            }
                            ProgressDismiss();
                        } catch (JSONException e) {
                            ProgressDismiss();
                            Log.e("Error3===", e.toString());
                        }
                    }
                });

            }
        });

    }

    void getCoordinates(String drawingID) {

        getProgressShow("Loading strategic points...");
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("https://api.opticvyu.com/api/interior/getcoordinates?did=" + drawingID + "").addHeader(Constants.API_Authorization, type + " " + access).addHeader("Accept", "application/json").get().build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("call", "" + e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                nearResponses.clear();
                final String json = response.body().string();
                Log.e("coordinate", "" + json);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {


                        try {
                            JSONArray jsonObject1 = new JSONArray(json);
                            for (int i = 0; i < jsonObject1.length(); i++) {
                                JSONObject object = jsonObject1.getJSONObject(i);
                                ResponseStItem mResponseStItem = new ResponseStItem();
                                mResponseStItem.setX(object.getInt("x"));
                                mResponseStItem.setY(object.getInt("y"));
                                mResponseStItem.setCoordinateid(object.getInt("coordinateid"));
                                mResponseStItem.setDrawingid(object.getInt("drawingid"));
                                mResponseStItem.setTitle(object.getString("title"));
                                mResponseStItem.setCateone(object.getString("cateone"));
                                nearResponses.add(mResponseStItem);
                            }

                            realmController.SaveCoordinates((ArrayList<ResponseStItem>) nearResponses);
                            //setPoint();
                        } catch (Exception e) {
                            ProgressDismiss();
                            Log.e("Error1===", e.toString());


                        }


                    }
                });


            }
        });

    }

    void setPoint() {


        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        mHeight = displayMetrics.heightPixels;
        mWidth = displayMetrics.widthPixels;
        aFloatValue = ((float) ActualWidth * ((float) 1)) / (float) dWidth;

        for (int i = 0; i < nearResponses.size(); i++) {
            imgView = new ImageView(getApplicationContext());
            imgView.setX((nearResponses.get(i).getX()) * aFloatValue * (float) 0.978);
            imgView.setY((nearResponses.get(i).getY()) * aFloatValue * (float) 0.950);
            imgView.setId(i);

            boolean exist = getHighlighted("" + nearResponses.get(i).getCoordinateid());
            if (exist) {
                imgView.setImageDrawable(getDrawable(R.drawable.red));
                imgView.setTag("red");
            } else {

                imgView.setImageDrawable(getDrawable(R.drawable.yellow));
                imgView.setTag("yellow");
            }
            if (nearResponses.get(i).getCateone() != null && !nearResponses.get(i).getCateone().equalsIgnoreCase("normal")) {
                imgView.setImageDrawable(getDrawable(R.drawable.pink));
                imgView.setTag("pink");
            }

            point.addView(imgView);

            int finalI = i;
            imgView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                        Toast.makeText(InteriorDashboardTimeLapse.this, "Please Enable Location/GPS", Toast.LENGTH_SHORT).show();
                    } else {
                        if (nearResponses.get(finalI).getCateone() != null && !nearResponses.get(finalI).getCateone().equalsIgnoreCase("normal")) {
                            Toast.makeText(InteriorDashboardTimeLapse.this, "" + getResources().getString(R.string.walkThrough_point), Toast.LENGTH_SHORT).show();

                        } else {
                            coordinateID = "" + nearResponses.get(finalI).getCoordinateid();
                            boolean exist = getHighlighted("" + nearResponses.get(finalI).getCoordinateid());
                            // if (!exist) {
                            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                            NetworkInfo info = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                            if (info.isConnected()) {
                                if (cameraStatus) {

                                    if (Constants.Mode.equals(getString(R.string.richo_360))) {
                                        getProgressShow("Please wait while the image is captured.");
                                        forceConnectToWifi();
                                    } else {
                                        openCamera();

                                    }
                                } else {
                                    dispatchCameraIntent();
                                }

                            } else {
                                if (!cameraStatus) {
                                    dispatchCameraIntent();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Please connect to Ricoh Theta Wifi hotspot to capture the images", Toast.LENGTH_LONG).show();
                                }

                            }
                            //  }

                        }
                    }

                }
            });


        }
        ProgressDismiss();
    }

    void captureInsta360() {
        if (hdrOption) {
            InstaCameraManager.getInstance().startHDRCapture(true);
        } else {
            InstaCameraManager.getInstance().startHDRCapture(false);
        }
        //InstaCameraManager.getInstance().startNormalCapture(false);
        InstaCameraManager.getInstance().setCaptureStatusListener(new ICaptureStatusListener() {
            @Override
            public void onCaptureFinish(String[] strings) {

                WorkWrapper mWorkWrapper = new WorkWrapper(strings[0]);

                String _name = coordinateID + "-" + ProjectID + "-" + drawingID + "-" + getString(R.string.INSTA_Image);
                ExportImageParamsBuilder builder = new ExportImageParamsBuilder()
                        .setExportMode(ExportUtils.ExportMode.PANORAMA)
                        .setImageFusion(mWorkWrapper.isPanoramaFile())
                        .setTargetPath(EXPORT_DIR_PATH + _name + ".jpg")
                        ;

                mCurrentExportId = ExportUtils.exportImage(mWorkWrapper, builder, new IExportCallback() {
                    @Override
                    public void onSuccess() {
                        mCurrentExportId = -1;
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                uploadStatusButton();
                                setPoint();
                                ProgressDismiss();
                                deleteFileFromCamera(Arrays.asList(strings));
                            }
                        }, 1500);
                    }

                    @Override
                    public void onFail(int i, String s) {
                        mCurrentExportId = -1;
                        ProgressDismiss();
                    }

                    @Override
                    public void onCancel() {
                        mCurrentExportId = -1;
                        ProgressDismiss();
                    }
                });


            }

            @Override
            public void onCaptureWorking() {
                getProgressShow("Please wait while the image is captured.");

            }

        });
    }

    void deleteFileFromCamera(List fileUrls) {
        InstaCameraManager.getInstance().deleteFileList(fileUrls, null);
    }

    void GetCheckOrientationAndDimension() {
        orientation = getResources().getConfiguration().orientation;
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        mHeight = displayMetrics.heightPixels;
        mWidth = displayMetrics.widthPixels;
    }

    void getProgressShow(String Message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                pd = new ProgressDialog(InteriorDashboardTimeLapse.this);
                pd.setMessage(Message);
                pd.setCancelable(false);
                pd.show();
            }
        });

    }


    void ProgressDismiss() {
        try {
            pd.dismiss();
        } catch (Exception e) {

        }

    }


    void FilePAth() {
        maxLimit++;
        getProgressShow("Uploading the images. Please wait!");
        sdcardPath = new File(Environment.getExternalStorageDirectory().getPath() + "/Documents/Opticvyu_images" + timeStampVar);


        imageCount = sdcardPath.listFiles().length;
        if (imageCount == 0) {
            Toast.makeText(InteriorDashboardTimeLapse.this, "No Image Found", Toast.LENGTH_SHORT).show();
            ProgressDismiss();
        } else {
            for (count = imageCount - 1; count >= 0; count--) {
                String fileName = sdcardPath.listFiles()[count].getName();
                String[] separated = fileName.split("-");
                CID = separated[0];
                PID = separated[1];
                DID1 = separated[2];
                String type = separated[3];
                String[] separated1 = type.split(".jpg");
                String ImageType = separated1[0];
                Bitmap bmp = BitmapFactory.decodeFile(sdcardPath.listFiles()[count].getAbsolutePath());
                Uri uri = getImageUri(getApplicationContext(), bmp, "IMG_" + Calendar.getInstance().getTime());
                File file = new File(getPath(uri));
                String dd = getImageUriCompress(bmp);
                Date date = new Date(sdcardPath.listFiles()[count].lastModified());

                @SuppressLint("SimpleDateFormat") SimpleDateFormat df2 = new SimpleDateFormat("yyyy/MM/dd");


                if (ImageType.equals("R")) {
                    FileBody(file, CID, PID, DID1, dd, count, df2.format(date));
                } else {
                    CameraBody(file, CID, PID, DID1, dd, df2.format(date), count);

                }

                break;
            }

        }

    }

    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor == null) return null;
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String s = cursor.getString(column_index);
        cursor.close();
        return s;
    }

    public Uri getImageUri(Context inContext, Bitmap inImage, String fname) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 85, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "IMG-" + fname, null);
        return Uri.parse(path);
    }

    public String getImageUriCompress(Bitmap inImage) {
        Bitmap reSize = Bitmap.createScaledBitmap(inImage, 200, 100, true);
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        reSize.compress(Bitmap.CompressFormat.JPEG, 85, bytes);
        String encode = android.util.Base64.encodeToString(bytes.toByteArray(), android.util.Base64.DEFAULT);

        return encode;
    }

    void FileBody(File file, String CID, String PID, String DID, String base64, int count, String date) {
        RequestBody body = new MultipartBody.Builder().
                setType(MultipartBody.FORM)
                .addFormDataPart("proj_dir", "PRJCT" + PID)
                .addFormDataPart("project_id", PID)
                .addFormDataPart("thumbnail", "data:image/jpg;base64," + base64)
                .addFormDataPart("coordinateid", CID)
                .addFormDataPart("latitude", "0")
                .addFormDataPart("longitude", "0")
                .addFormDataPart("zone", "0")
                .addFormDataPart("did", DID)
                .addFormDataPart("type", "panaroma")
                .addFormDataPart("uploadedby", firstName + " " + lastName)
                .addFormDataPart("gpsDateStamp", "" + date)
                .addFormDataPart("file", file.getName() + ".jpg", RequestBody.create(MediaType.parse("multipart/form-data"), file)).build();


        OkHttpClient client = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS).writeTimeout(180, TimeUnit.SECONDS).readTimeout(180, TimeUnit.SECONDS).build();
        Request request = new Request.Builder().addHeader(Constants.API_Authorization, type + " " + access).addHeader(Constants.API_Accept, "application/json").post(body).url("https://api.opticvyu.com/api/interior/upload-media").build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                ProgressDismiss();
                if (maxLimit == 3) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Some thing went wrong Correctly connect with network", Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    ProgressDismiss();
                    uploadStatusButton();
                    handler.postDelayed(() -> FilePAth(), 200);

                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        boolean bool = response.isSuccessful();
                        if (bool == true) {
                            sdcardPath.listFiles()[count].delete();
                            int finalLength = sdcardPath.listFiles().length;
                            if (finalLength == 0) {
                                Log.e("true", "3");
                                ProgressDismiss();
                                uploadStatus = 0;
                                uploadStatusButton();
                                setPoint();
                                Toast.makeText(InteriorDashboardTimeLapse.this, "Images successfully uploaded. Check on OpticVyu web-dashboard", Toast.LENGTH_LONG).show();

                            } else {
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        ProgressDismiss();
                                        FilePAth();
                                    }
                                }, 200);
                            }
                        } else {
                            ProgressDismiss();
                            Toast.makeText(InteriorDashboardTimeLapse.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();

                        }


                    }
                });

            }
        });
    }

    boolean getHighlighted(String id) {
        boolean b = false;
        boolean bool = new File(Environment.getExternalStorageDirectory().getPath() + "/Documents/Opticvyu_images" + timeStampVar).exists();
        if (bool) {
            for (int j = 0; j <= sdcardPath.listFiles().length - 1; j++) {
                String fileName = sdcardPath.listFiles()[j].getName();
                String[] separated = fileName.split("-");
                CID = separated[0];
                if (CID.equals(id)) {
                    b = true;
                    break;
                } else {
                    b = false;
                }
            }
        }
        return b;
    }

    void uploadStatusButton() {


        boolean bool = new File(Environment.getExternalStorageDirectory().getPath() + "/Documents/Opticvyu_images" + timeStampVar).exists();

        if (bool) {
            sdcardPath = new File(Environment.getExternalStorageDirectory().getPath() + "/Documents/Opticvyu_images" + timeStampVar);
            imageCount = sdcardPath.listFiles().length;
            Log.e("bool==", "" + imageCount);
            if (imageCount > 0) {
                uploadStatus = 1;
                if (cameraStatus) {
                    uploadImages.setBackgroundDrawable(getDrawable(R.drawable.buttonroundred));
                    uploadImages.setText("Upload Image " + "(" + imageCount + ")");
                    uploadImages.setTextColor(Color.WHITE);
                } else {
                    normalImages.setBackgroundDrawable(getDrawable(R.drawable.buttonroundred));
                    normalImages.setText("Upload Image " + "(" + imageCount + ")");
                    normalImages.setTextColor(Color.WHITE);
                }

            } else if (uploadStatus == 0) {
                uploadButtonConditionStatus();

            } else {
                uploadButtonConditionStatus();
            }

        } else {
            uploadButtonConditionStatus();
        }
    }

    void uploadButtonConditionStatus() {
        if (cameraStatus) {
            uploadImages.setBackgroundDrawable(getDrawable(R.drawable.buttonroundyellow));
            uploadImages.setText("Capture 360 Image");
            uploadImages.setTextColor(Color.BLACK);
        } else {
            normalImages.setBackgroundDrawable(getDrawable(R.drawable.buttonroundyellow));
            normalImages.setText("Normal Image");
            normalImages.setTextColor(Color.BLACK);
        }
    }

    void alert() {
        Dialog deleteDialog = new Dialog(InteriorDashboardTimeLapse.this, R.style.MyAlertDialogTheme);
        deleteDialog.setCancelable(false);
        deleteDialog.setContentView(R.layout.upload_capture_images);
        TextView yes = deleteDialog.findViewById(R.id.yes);
        TextView no = deleteDialog.findViewById(R.id.no);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        deleteDialog.dismiss();
                        Check();
                    }

                }, 500);

            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                deleteDialog.dismiss();
            }
        });

        deleteDialog.show();


    }

    void Instruction() {
        Dialog ins = new Dialog(InteriorDashboardTimeLapse.this, R.style.MyAlertDialogTheme);
        ins.setCancelable(false);
        ins.setContentView(R.layout.instruction_layout);
        TextView yes = ins.findViewById(R.id.yes);
        TextView underline = ins.findViewById(R.id.underline);

        SpannableString content = new SpannableString(underline.getText().toString());
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        underline.setText(content);

        underline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String urlString = "https://support.theta360.com/uk/manual/s/content/prepare/prepare_06.html";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlString));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setPackage("com.android.chrome");
                try {
                    startActivity(intent);
                } catch (ActivityNotFoundException ex) {
                    // Chrome browser presumably not installed so allow user to choose instead
                    intent.setPackage(null);
                    startActivity(intent);
                }
            }
        });
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ins.dismiss();
            }
        });
        ins.show();
    }

    void NormalCamera() {
        Dialog dialog = new Dialog(InteriorDashboardTimeLapse.this, R.style.MyAlertDialogTheme);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.instruction_layout);
        TextView yes = dialog.findViewById(R.id.yes);
        TextView text = dialog.findViewById(R.id.ins);
        text.setText(getString(R.string.normalNotification));
        TextView underline = dialog.findViewById(R.id.underline);
        TextView second = dialog.findViewById(R.id.second);
        underline.setVisibility(View.GONE);
        second.setVisibility(View.GONE);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();

        boolean bb = checkWriteExternalPermission();
        if (bb) {
            uploadStatusButton();
        } else {
            permissions = new Permissions(InteriorDashboardTimeLapse.this);
            permissions.requestPermission();
        }

        if (Constants.PointStatus.equals("1")) {
            setPoint();
            Constants.PointStatus = "0";
        }
        if (cameraStatus) {
            mode.setText("360° Image Capturing Mode");
        } else {
            if (sdcardPath.listFiles() != null && sdcardPath.listFiles().length == 0) {
                mode.setText("Normal Image Capturing Mode");
            }

        }


    }

    private boolean checkWriteExternalPermission() {
        String permission = android.Manifest.permission.READ_EXTERNAL_STORAGE;
        int res = getApplicationContext().checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }




    void Check() {
        if (status == 1) {
            FilePAth();
        }
    }

    private void dispatchCameraIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            try {
                File photoFile = createImageFile();
                // Continue only if the File was successfully created
                if (photoFile != null) {
                    Uri photoURI;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        photoURI = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider", photoFile);
                    } else {
                        photoURI = Uri.fromFile(photoFile);
                    }
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP) {
                        takePictureIntent.setClipData(ClipData.newRawUri("", photoURI));
                        takePictureIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    }

                    startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
                }
            } catch (IOException ex) {
                // Error occurred while creating the File
                ex.printStackTrace();
            }
        } else {

        }
    }


    private File createImageFile() throws IOException {
        // Create an image file name
        timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";

        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName,  // prefix
                ".jpg",         // suffix
                storageDir      // directory
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            if (mCurrentPhotoPath == null) {
                return;
            }

            Uri imageUri = Uri.parse(mCurrentPhotoPath);
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                SaveImage(bitmap, getString(R.string.Normal_Image_Selection));
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        uploadStatusButton();
                        setPoint();
                        ProgressDismiss();
                    }
                }, 1500);
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Some thing went wrong", Toast.LENGTH_SHORT).show();
            }


        }


    }


    void CameraBody(File file, String CID, String PID, String DID, String base64, String date, int count) {
        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM).addFormDataPart("proj_dir", "PRJCT" + PID).addFormDataPart("project_id", PID).addFormDataPart("thumbnail", "data:image/jpg;base64," + base64).addFormDataPart("coordinateid", CID).addFormDataPart("latitude", "0").addFormDataPart("longitude", "0").addFormDataPart("zone", "0").addFormDataPart("did", DID).addFormDataPart("type", "normal").addFormDataPart("uploadedby", firstName + " " + lastName).addFormDataPart("gpsDateStamp", "" + date).addFormDataPart("file", file.getName() + ".jpg", RequestBody.create(MediaType.parse("multipart/form-data"), file)).build();


        OkHttpClient client = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS).writeTimeout(180, TimeUnit.SECONDS).readTimeout(180, TimeUnit.SECONDS).build();
        Request request = new Request.Builder().addHeader(Constants.API_Authorization, type + " " + access).addHeader(Constants.API_Accept, "application/json").post(body).url("https://api.opticvyu.com/api/interior/upload-media").build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                ProgressDismiss();
                if (maxLimit == 3) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Some thing went wrong Correctly connect with network", Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    ProgressDismiss();
                    FilePAth();
                }


            }

            @Override
            public void onResponse(@NonNull Call call, Response response) throws IOException {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        boolean bool = response.isSuccessful();
                        if (bool) {
                            sdcardPath.listFiles()[count].delete();
                            int finalLength = sdcardPath.listFiles().length;
                            if (finalLength == 0) {
                                uploadStatus = 0;
                                ProgressDismiss();
                                uploadStatusButton();
                                setPoint();
                                Toast.makeText(InteriorDashboardTimeLapse.this, "Images successfully uploaded. Check on OpticVyu web-dashboard", Toast.LENGTH_LONG).show();

                            } else {
                                ProgressDismiss();
                                uploadStatusButton();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {

                                        FilePAth();
                                    }
                                }, 200);

                            }

                        } else {
                            ProgressDismiss();
                            Toast.makeText(InteriorDashboardTimeLapse.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();

                        }


                    }
                });

            }
        });
    }


    void checkDevice() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            File dir = new File(Environment.getExternalStoragePublicDirectory(DIRECTORY_DOCUMENTS), "Opticvyu_images" + timeStampVar);
            if (!dir.exists()) {
                dir.mkdirs();
            }

        } else {
            // Do something for phones running an SDK before Android 12
        }
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void forceConnectToWifi() {


        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if ((info != null) && info.isAvailable()) {

                WifiManager wifiManager = (WifiManager) getApplicationContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                try {
                    CameraName = wifiInfo.getSSID().substring(wifiInfo.getSSID().lastIndexOf(".")).replace("\"", "");
                    //CameraName = wifiInfo.getSSID().substring(0,6);
                    //CameraNameAnother = wifiInfo.getSSID().substring(0,4);
                } catch (Exception e) {
                    CameraName = "";
                    CameraNameAnother = "";
                }

                NetworkRequest.Builder builder = new NetworkRequest.Builder();
                builder.addTransportType(NetworkCapabilities.TRANSPORT_WIFI);
                NetworkRequest requestedNetwork = builder.build();


                ConnectivityManager.NetworkCallback callback = new ConnectivityManager.NetworkCallback() {
                    @Override
                    public void onAvailable(Network network) {
                        super.onAvailable(network);

                        ConnectivityManager.setProcessDefaultNetwork(network);
                        mConnectionSwitchEnabled = true;
                        invalidateOptionsMenu();
                        appendLogView("connect to Wi-Fi AP");
                        CheckWifiConnectionTrue();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (CameraName.equals(".OSC")) {
                                    new ShootTask().execute();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Please connect to Ricoh Theta Wifi hotspot to capture the images..", Toast.LENGTH_LONG).show();
                                    ProgressDismiss();
                                }

                            }
                        }, 4000);
                    }

                    @Override
                    public void onLost(Network network) {
                        super.onLost(network);

                        mConnectionSwitchEnabled = false;
                        invalidateOptionsMenu();
                        appendLogView("lost connection to Wi-Fi AP");
                    }
                };

                cm.registerNetworkCallback(requestedNetwork, callback);

            }
        } else {
            mConnectionSwitchEnabled = true;
            invalidateOptionsMenu();
        }
    }

    private void appendLogView(final String log) {
        runOnUiThread(new Runnable() {
            public void run() {

            }
        });
    }

    public void CheckWifiConnectionTrue() {


        if (mConnectionSwitchEnabled) {

            if (livePreviewTask == null) {
                livePreviewTask = new ShowLiveViewTask();
                livePreviewTask.execute(cameraIpAddress);
            }


        } else {

            if (sampleTask != null) {
                sampleTask.cancel(true);
                sampleTask = null;
            }

            if (livePreviewTask != null) {
                livePreviewTask.cancel(true);
                livePreviewTask = null;
            }


            new DisConnectTask().execute();

            mMv.stopPlay();

        }

    }


    private class DisConnectTask extends AsyncTask<Void, String, Boolean> {
        @Override
        protected Boolean doInBackground(Void... params) {

            try {
                publishProgress("disconnected.");
                return true;

            } catch (Throwable throwable) {
                String errorLog = Log.getStackTraceString(throwable);
                publishProgress(errorLog);
                return false;
            }
        }

        @Override
        protected void onProgressUpdate(String... values) {
            for (String log : values) {
                //logViewer.append(log);
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class ShowLiveViewTask extends AsyncTask<String, String, MJpegInputStream> {
        @Override
        protected MJpegInputStream doInBackground(String... ipAddress) {
            MJpegInputStream mjis = null;
            final int MAX_RETRY_COUNT = 20;

            for (int retryCount = 0; retryCount < MAX_RETRY_COUNT; retryCount++) {
                try {
                    publishProgress("start Live view==" + ipAddress[0]);
                    HttpConnector camera = new HttpConnector(ipAddress[0]);
                    InputStream is = camera.getLivePreview();
                    mjis = new MJpegInputStream(is);
                    retryCount = MAX_RETRY_COUNT;
                } catch (IOException e) {
                    try {
                        Thread.sleep(500);

                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                } catch (JSONException e) {
                    try {
                        Thread.sleep(500);

                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }

                }
            }

            return mjis;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            for (String log : values) {
                // logViewer.append(log);
            }
        }

        @Override
        protected void onPostExecute(MJpegInputStream mJpegInputStream) {
            if (mJpegInputStream != null) {
                mMv.setSource(mJpegInputStream);
            } else {
                //logViewer.append("failed to start live view");
            }
        }
    }

    public class LoadObjectListTask extends AsyncTask<Void, String, List<ImageRow>> {

        private ProgressBar progressBar;

        public LoadObjectListTask() {
            progressBar = (ProgressBar) findViewById(R.id.loading_object_list_progress_bar);
        }

        @Override
        protected void onPreExecute() {
            runOnUiThread(() -> progressBar.setVisibility(View.VISIBLE));

        }

        @Override
        protected List<ImageRow> doInBackground(Void... params) {
            try {

                publishProgress("connecting to " + cameraIpAddress + "...");
                HttpConnector camera = new HttpConnector(cameraIpAddress);
                DeviceInfo deviceInfo = camera.getDeviceInfo();

                publishProgress(deviceInfo.getClass().getSimpleName() + ":<" + deviceInfo.getModel() + ", " + deviceInfo.getDeviceVersion() + ", " + deviceInfo.getSerialNumber() + ">");
                if (deviceInfo.getModel().isEmpty()) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(getApplicationContext(), "Please connect to Ricoh Theta Wifi hotspot to capture the images..", Toast.LENGTH_LONG).show();
                        }
                    });

                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setVisibility(View.GONE);
                        }
                    });

                    ProgressDismiss();
                }
                return null;

            } catch (Throwable throwable) {
                String errorLog = Log.getStackTraceString(throwable);
                publishProgress(errorLog);
                return null;
            }
        }

        @Override
        protected void onProgressUpdate(String... values) {
            for (String log : values) {

            }
        }
    }


    public class ShootTask extends AsyncTask<Void, Void, HttpConnector.ShootResult> {

        @Override
        protected void onPreExecute() {
            Log.e("logViewer", "logViewer");
        }

        @SuppressLint("WrongThread")
        @Override
        protected HttpConnector.ShootResult doInBackground(Void... params) {
            mMv.setSource(null);
            CaptureListener postviewListener = new CaptureListener();
            HttpConnector camera = new HttpConnector(getResources().getString(R.string.theta_ip_address));
            HttpConnector.ShootResult result = camera.takePicture(postviewListener);

            return result;
        }

        @Override
        protected void onPostExecute(HttpConnector.ShootResult result) {
            if (result == HttpConnector.ShootResult.FAIL_CAMERA_DISCONNECTED) {
                Log.e("1", "takePicture:FAIL_CAMERA_DISCONNECTED");
            } else if (result == HttpConnector.ShootResult.FAIL_STORE_FULL) {
                Log.e("2", "takePicture:FAIL_STORE_FULL");
            } else if (result == HttpConnector.ShootResult.FAIL_DEVICE_BUSY) {
                Log.e("3", "takePicture:FAIL_DEVICE_BUSY");
            } else if (result == HttpConnector.ShootResult.SUCCESS) {
                Log.e("4", "takePicture:SUCCESS");
            }
        }

        class CaptureListener implements HttpEventListener {
            private String latestCapturedFileId;
            private boolean ImageAdd = false;

            @Override
            public void onCheckStatus(boolean newStatus) {
                if (newStatus) {
                    appendLogView("takePicture:FINISHED");
                } else {
                    appendLogView("takePicture:IN PROGRESS");
                }
            }

            @Override
            public void onObjectChanged(String latestCapturedFileId) {
                this.ImageAdd = true;
                this.latestCapturedFileId = latestCapturedFileId;
                appendLogView("ImageAdd:FileId " + this.latestCapturedFileId);
                DownloadImageFromPath(latestCapturedFileId);
            }

            @Override
            public void onCompleted() {
                appendLogView("CaptureComplete");
                if (ImageAdd) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {


                        }
                    });
                }
            }

            @Override
            public void onError(String errorMessage) {
                appendLogView("CaptureError " + errorMessage);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }


        }
    }

    private void DownloadImageFromPath(String path) {
        InputStream in = null;
        Bitmap bmp = null;

        int responseCode = -1;
        try {

            URL url = new URL(path);//"htt p://192.xx.xx.xx/mypath/img1.jpg
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setDoInput(true);
            con.connect();
            responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                //download
                in = con.getInputStream();
                bmp = BitmapFactory.decodeStream(in);
                SaveImage(bmp, getString(R.string.RICOH_Image));
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        uploadStatusButton();
                        setPoint();
                        ProgressDismiss();
                    }
                }, 1500);


            }

        } catch (Exception ex) {
            Log.e("Exception--", ex.toString());
        }


    }

    public void SaveImage(Bitmap finalBitmap, String type) {
        File myDir;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.R) {
            myDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "Opticvyu_images" + timeStampVar);
        } else {
            String root = Environment.getExternalStorageDirectory().getAbsolutePath();
            myDir = new File(root + "/Documents/Opticvyu_images" + timeStampVar);
        }
        if (!myDir.exists()) {
            myDir.mkdirs();
        }


        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fname = coordinateID + "-" + ProjectID + "-" + drawingID + "-" + type + ".jpg";
        File file = new File(myDir, fname);
        if (file.exists()) file.delete();
        try {

            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 85, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void OfflineProject() {

        if (realmController.getProject().size() > 0) {
            project_models.addAll(realmController.getProject());
            final ArrayAdapter<Project_model> arrayAdapter = new ArrayAdapter<Project_model>(InteriorDashboardTimeLapse.this, R.layout.spinner_text, R.id.spinner_text, project_models);
            project.setAdapter(arrayAdapter);
            if (project_models.size() > Integer.parseInt(ProjectIndex)) {
                project.setSelection(Integer.parseInt(ProjectIndex));
            } else {
                ProjectIndex = "0";
            }

        } else {
            getZone.clear();
            drawingImages.clear();
            nearResponses.clear();
            GetProject();
            handler.postDelayed(() -> pd.dismiss(), 15000);
        }
    }

    void OfflineZone() {
        if (realmController.getZones(ProjectID).size() > 0) {
            getZone.clear();
            getZone.addAll(realmController.getZones(ProjectID));
            zoneAdapter = new ArrayAdapter<Zone>(InteriorDashboardTimeLapse.this, R.layout.spinner_text, R.id.spinner_text, getZone);
            Zone.setAdapter(zoneAdapter);
        } else {
            if (CheckNetwork.isInternetAvailable(InteriorDashboardTimeLapse.this)) {
                NetNotConnectForZone();
                GetZone(ProjectID);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pd.dismiss();
                        // connectionAlert.ActiveNetWork(InteriorDashboard.this);
                    }
                }, 15000);

            } else {
                NetNotConnectForZone();
                connectionAlert.NetLocal(InteriorDashboardTimeLapse.this);
            }

        }
    }

    void offlineDrawing() {
        if (realmController.getDrawings(zoneId).size() > 0) {
            drawingImages.clear();
            drawingImages.addAll(realmController.getDrawings(zoneId));
            DrawingAdapter = new ArrayAdapter<DrawingImages>(InteriorDashboardTimeLapse.this, R.layout.spinner_text, R.id.spinner_text, drawingImages);
            drawing.setAdapter(DrawingAdapter);
        } else {
            if (CheckNetwork.isInternetAvailable(InteriorDashboardTimeLapse.this)) {

                NetNotConnectForDrawing();
                String DrawingApi = Constants.InteriorProject + ProjectID + Constants.GetDrawing + zoneId;
                GetDrawing(DrawingApi);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pd.dismiss();
                        //connectionAlert.ActiveNetWork(InteriorDashboard.this);
                    }
                }, 15000);

            } else {
                NetNotConnectForDrawing();
                connectionAlert.NetLocal(InteriorDashboardTimeLapse.this);
            }
        }


    }

    void offlineCoordinate() {
        if (realmController.getCoordinate(drawingID).size() > 0) {
            nearResponses.clear();
            Log.e("REalm---", "" + realmController.getCoordinate(drawingID));
            nearResponses.addAll(realmController.getCoordinate(drawingID));
        } else {
            if (CheckNetwork.isInternetAvailable(InteriorDashboardTimeLapse.this)) {
                Coordinate();
                getCoordinates(drawingID);
                handler.postDelayed(() -> pd.dismiss(), 15000);
            } else {
                connectionAlert.NetLocal(InteriorDashboardTimeLapse.this);
            }
        }


    }

    void NetNotConnectForZone() {
        getZone.clear();
        drawingImages.clear();
        nearResponses.clear();
        zoneAdapter = new ArrayAdapter<Zone>(this, R.layout.spinner_text, getZone);
        DrawingAdapter = new ArrayAdapter<DrawingImages>(this, R.layout.spinner_text, drawingImages);
        drawing.setAdapter(DrawingAdapter);
        zoneAdapter.notifyDataSetChanged();
        Zone.setAdapter(zoneAdapter);
        DrawingAdapter.notifyDataSetChanged();
        point.removeAllViews();
        Glide.with(getApplicationContext()).load("").into(mLandScape);
    }

    void NetNotConnectForDrawing() {
        drawingImages.clear();
        nearResponses.clear();

        DrawingAdapter = new ArrayAdapter<DrawingImages>(this, R.layout.spinner_text, drawingImages);
        drawing.setAdapter(DrawingAdapter);
        DrawingAdapter.notifyDataSetChanged();
        point.removeAllViews();
        Glide.with(getApplicationContext()).load("").into(mLandScape);

    }

    void Coordinate() {

        nearResponses.clear();
        point.removeAllViews();


    }


    void GetNet(String project_API) throws IOException {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(project_API).addHeader(Constants.API_Authorization, type + " " + access).addHeader(Constants.API_Accept, "application/json").build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                netActive = false;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ProgressDismiss();
                        // Toast.makeText(getApplicationContext(), "==" + netActive, Toast.LENGTH_SHORT).show();
                        ActiveNetWork(InteriorDashboardTimeLapse.this);
                        // Toast.makeText(getApplicationContext(), "Please connect to Ricoh Theta Wifi hotspot to capture the images", Toast.LENGTH_SHORT).show();
                    }
                });


            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                netActive = true;


                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ProgressDismiss();
                        // Toast.makeText(getApplicationContext(), "==" + netActive, Toast.LENGTH_SHORT).show();
                        if (sdcardPath.listFiles() != null && sdcardPath.listFiles().length > 0) {
                            if (netActive) {
                                Toast.makeText(getApplicationContext(), "Online..", Toast.LENGTH_LONG).show();
                                status = 1;
                                uploadImages.setText("Uploading Images" + "(" + imageCount + ")");
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        alert();
                                    }
                                }, 800);


                            } else {
                                Toast.makeText(getApplicationContext(), "Offline..", Toast.LENGTH_LONG).show();
                                connectionAlert.ActiveNetWork(InteriorDashboardTimeLapse.this);
                            }
                        } else {
                            if (cameraStatus) {
                                Instruction();
                            } else {
                                NormalCamera();
                            }

                        }
                    }
                });


            }
        });


    }


    public void ActiveNetWork(Context context) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();

        // Setting Dialog Title
        alertDialog.setTitle("Alert");

        // Setting Dialog Message
        alertDialog.setMessage(context.getString(R.string.ActiveInternet));

        // Setting Icon to Dialog
        alertDialog.setIcon(R.mipmap.ic_launcher);

        // Setting OK Button
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {


                try {
                    GetNet(Constants.User_Project);

                } catch (IOException e) {

                }
                dialog.dismiss();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

    public void CacheCLear(Context context) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();

        // Setting Dialog Title
        alertDialog.setTitle("Alert");

        // Setting Dialog Message
        alertDialog.setMessage(context.getString(R.string.clear));

        // Setting Icon to Dialog
        alertDialog.setIcon(R.mipmap.ic_launcher);

        // Setting OK Button
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {


                int len = sdcardPath.listFiles().length;
                for (int j = 0; j < len; j++) {
                    sdcardPath.listFiles()[count].delete();
                }
                uploadStatus = 0;
                uploadStatusButton();
                setPoint();

            }
        });
        alertDialog.show();
    }

    // Below Code for Insta 360 camera


    private void openCamera() {

        InstaCameraManager.getInstance().openCamera(InstaCameraManager.CONNECT_TYPE_WIFI);



         if(InstaCameraManager.getInstance().getCameraConnectedType() == 2)
         {//Toast.makeText(this, "Insta360 Connected .", Toast.LENGTH_SHORT).show();
             captureInsta360();
         }

        else if (InstaCameraManager.getInstance().getCameraConnectedType() == -1) {

//            if(InstaCameraManager.getInstance().getCameraConnectedType() == 2)
//             {
//                 Toast.makeText(this, "Connected. Click on yellow point to capture.", Toast.LENGTH_SHORT).show();
//
//             }

            Toast.makeText(this, "Connect to Insta360 Wifi for image capture; if already connected, click yellow point again.", Toast.LENGTH_SHORT).show();


        }
//        else {
//             Toast.makeText(InteriorDashboard.this, "Connected", Toast.LENGTH_SHORT).show();
//
//             captureInsta360();
//
//
//        }


    }


}





