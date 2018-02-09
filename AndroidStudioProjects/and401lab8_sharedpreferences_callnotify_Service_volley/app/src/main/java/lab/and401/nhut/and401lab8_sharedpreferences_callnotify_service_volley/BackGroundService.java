package lab.and401.nhut.and401lab8_sharedpreferences_callnotify_service_volley;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

/**
 * Created by Nhut on 6/26/2017.
 */
public class BackGroundService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Toast.makeText(this, "BackGroundService : onBind()", Toast.LENGTH_SHORT).show();
        return null;
    }

    @Override
    public void onCreate() {
        Toast.makeText(this, "BackGroundService : onCreate()", Toast.LENGTH_SHORT).show();
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "BackGroundService : onStartCommand()", Toast.LENGTH_SHORT).show();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "BackGroundService : onDestroy()", Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Toast.makeText(this, "BackGroundService : onUnbind()", Toast.LENGTH_SHORT).show();
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        Toast.makeText(this, "BackGroundService : onRebind()", Toast.LENGTH_SHORT).show();
        super.onRebind(intent);
    }
}
