package com.optic.interior;

import static android.os.Environment.DIRECTORY_DOCUMENTS;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.arashivision.insta360.basemedia.asset.GyroInfo;
import com.arashivision.sdkcamera.camera.InstaCameraManager;
import com.arashivision.sdkcamera.camera.callback.ICaptureStatusListener;
import com.arashivision.sdkcamera.camera.callback.IPreviewStatusListener;
import com.arashivision.sdkcamera.camera.preview.GyroData;
import com.arashivision.sdkcamera.camera.resolution.PreviewStreamResolution;
import com.arashivision.sdkmedia.export.ExportUtils;
import com.arashivision.sdkmedia.export.ExportVideoParamsBuilder;
import com.arashivision.sdkmedia.export.IExportCallback;
import com.arashivision.sdkmedia.work.WorkWrapper;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.model.Response;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import opticvyu.Utils.CsvFile;

public class Sensor extends AppCompatActivity implements IPreviewStatusListener {
    ToggleButton mToggleButton;
    CsvFile mFile;
    TextView connect, text;
    List<CsvFile> mFileList = new ArrayList<>();
    String EXPORT_DIR_PATH;
    int exportId = -1;
    String path[];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sensor);
        mToggleButton = findViewById(R.id.toggle);
        connect = findViewById(R.id.connect);
        text = findViewById(R.id.text);
        InstaCameraManager.getInstance().setTimeLapseInterval(100);
        EXPORT_DIR_PATH = Environment.getExternalStoragePublicDirectory(DIRECTORY_DOCUMENTS) + "/Opticvyu_Vid" + System.currentTimeMillis() + "/";
        InstaCameraManager.getInstance().openCamera(InstaCameraManager.CONNECT_TYPE_WIFI);

        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (InstaCameraManager.getInstance().getCameraConnectedType() == 2) {
                    Toast.makeText(Sensor.this, "Insta360 Connected .", Toast.LENGTH_SHORT).show();

                    Log.e("Camera===", InstaCameraManager.getInstance().getCameraType());


                }
            }
        });

        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Sensor.this, "Click", Toast.LENGTH_SHORT).show();



            }
        });
    }


    public void onToggleClick(View view) {
        if (mToggleButton.isChecked()) {
            mToggleButton.setText("Toggle is ON");
            InstaCameraManager.getInstance().startTimeLapse();
        } else {
            mToggleButton.setText("Toggle is OFF");
            InstaCameraManager.getInstance().stopTimeLapse();
            InstaCameraManager.getInstance().setCaptureStatusListener(new ICaptureStatusListener() {
                @Override
                public void onCaptureFinish(String[] strings) {
                    Toast.makeText(Sensor.this, "Please Wait", Toast.LENGTH_LONG).show();

                    path = strings;

                    extractVideo();
                }
            });


        }
    }


    void extractVideo() {
        String localFolder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/SDK_DEMO_CAPTURE";
        String[] fileNames = new String[path.length];
        String[] localPaths = new String[path.length];
        boolean needDownload = false;
        for (int i = 0; i < localPaths.length; i++) {
            fileNames[i] = path[i].substring(path[i].lastIndexOf("/") + 1);
            localPaths[i] = localFolder + "/" + fileNames[i];

        }

        for (int i = 0; i < localPaths.length; i++) {

            String url = path[i];
            OkGo.<File>get(url).execute(new FileCallback(localFolder, fileNames[i]) {

                @Override
                public void onError(Response<File> response) {
                    super.onError(response);


                }

                @Override
                public void onSuccess(Response<File> response) {

                    WorkWrapper mWorkWrapper = new WorkWrapper(localPaths);
                    Log.e("mWorkWrapper", "" + mWorkWrapper);
                    ExportVideoParamsBuilder builder = new ExportVideoParamsBuilder()
                            .setExportMode(ExportUtils.ExportMode.PANORAMA)
                            .setTargetPath(EXPORT_DIR_PATH + "vid.mp4")
                            .setWidth(5760).setHeight(2880)
                     .setBitrate(100 * 1024 * 1024)
                     .setFps(30);
                    exportId = ExportUtils.exportVideo(mWorkWrapper, builder, new IExportCallback() {
                        @Override
                        public void onSuccess() {
                            Toast.makeText(Sensor.this, "Please Wait", Toast.LENGTH_LONG).show();

                            exportId = -1;
                            ExportUtils.stopExport(exportId);
                            mFileList.clear();

                            for (GyroInfo gyroInfo : mWorkWrapper.getGyroInfo()) {
                                mFile = new CsvFile();
                                mFile.setAx(gyroInfo.getAccelerate_x());
                                mFile.setAy(gyroInfo.getAccelerate_y());
                                mFile.setAz(gyroInfo.getAccelerate_z());
                                mFile.setGx(gyroInfo.getRotation_x());
                                mFile.setGy(gyroInfo.getRotation_y());
                                mFile.setGz(gyroInfo.getRotation_z());
                                mFile.setTimeStamp(gyroInfo.getTimestamp());
                                mFileList.add(mFile);
                            }

                            writeDataToCsvFile(Sensor.this, mFileList);
                            Toast.makeText(Sensor.this, "Export Done", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onFail(int i, String s) {
                            exportId = -1;
                            Log.e("onFail", "==" + s);
                        }


                        @Override
                        public void onCancel() {
                            exportId = -1;
                            Log.e("onCancel", "Cancel");
                        }

                        @Override
                        public void onProgress(float progress) {
                            exportId = -1;
                        }
                    });


                }


            });
        }



    }


    public void writeDataToCsvFile(Context context, List<CsvFile> csv) {


        try {
            FileWriter writer = new FileWriter(new File(EXPORT_DIR_PATH, "sensor.csv"));

            writer.append(" ax, ay, az, gx, gy,gz,timeStamp\n");

            for (CsvFile gyroInfo : csv) {

                writer.write(gyroInfo.toCsvString());

            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}


//            new IPreviewStatusListener() {
//                @Override
//                public void onGyroData(List<GyroData> gyroList) {
//
//                    mFile = new CsvFile();
//
//
//                    handler.postDelayed(new Runnable() {
//                        public void run() {
//                            getSensorData(gyroList);
//                            System.out.println("myHandler: here!"); // Do your work here
//                            handler.postDelayed(this, delay);
//                        }
//                    }, delay);
//
//
//                    IPreviewStatusListener.super.onGyroData(gyroList);
//  }
//  };
