package opticvyu;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CALL_PHONE;
import static android.Manifest.permission.CAMERA;

public class Permission extends Activity {
    public static final int RequestPermissionCode = 1;


    private Context context;

    public Permission(Context context) {
        this.context = context;
    }

    public void requestPermission() {


        ActivityCompat.requestPermissions((Activity) context, new String[]
                {

                        CAMERA


                }, RequestPermissionCode);
    }


    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {

            case RequestPermissionCode:

                if (grantResults.length > 0) {


                    boolean Camera = grantResults[1] == PackageManager.PERMISSION_GRANTED;


                    if (Camera) {

                        //Toast.makeText(getApplicationContext(), "Permission Granted", Toast.LENGTH_LONG).show();
                    } else {
                        // Toast.makeText(getApplicationContext(), "Permission Denied", Toast.LENGTH_LONG).show();
                    }
                }
                break;
        }
    }


}
