package lab.and401.nhut.and401lab3_runtimepermission;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.hardware.Camera;
import android.util.Log;
import android.widget.FrameLayout;
import android.Manifest;



import lab.and401.nhut.and401lab3_runtimepermission.util.MyCamera;

public class MainActivity extends AppCompatActivity {
    private Camera mCamera;
    private MyCamera.CameraPreview mPreview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        if (!MyCamera.hasCameraPermission(this)) MyCamera.askCameraPermission(this);
        MyCamera.askCameraPermission(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        int index = -1;
//        for (int i = 0; i < permissions.length; i++)
//            if (permissions[i].equals(Manifest.permission.CAMERA)) {
//                index = i;
//                break;
//            }
//
//        if (requestCode == MyCamera.RUNTIME_PERMISSION_CODE && index > -1) {
//            if (grantResults[index] == PackageManager.PERMISSION_GRANTED) {
//                preview();
//            }
//        }
        if(MyCamera.hasCameraPermission(this)) preview();
    }

    private void preview() {
        // Create an instance of Camera
        if (MyCamera.checkCameraHardware(MainActivity.this)) mCamera = MyCamera.getBackCamera();
        // Create our Preview view and set it as the content of our activity.
        if (mCamera != null) {
            Log.i("check camera", "has camera");
            mPreview = new MyCamera.CameraPreview(this, mCamera);
            FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
            preview.addView(mPreview);
//                    mPreview.surfaceCreated();
        }
    }
}
