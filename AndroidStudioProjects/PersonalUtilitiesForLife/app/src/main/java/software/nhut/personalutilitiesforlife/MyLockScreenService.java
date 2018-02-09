package software.nhut.personalutilitiesforlife;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

public class MyLockScreenService extends Service {
    public MyLockScreenService() {
    }

    MyLockScreenReceiver lockScreenReceiver;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        IntentFilter filter = new IntentFilter(Intent.ACTION_BOOT_COMPLETED);//Intent.ACTION_SCREEN_ON);
//        filter.addAction(Intent.ACTION_BOOT_COMPLETED);
//        filter.addAction(Intent.ACTION_SCREEN_OFF);
//        filter.addAction(Intent.ACTION_USER_PRESENT);
        lockScreenReceiver = new MyLockScreenReceiver();
        registerReceiver(lockScreenReceiver, filter);
        Log.i("MyTag", "MyLockScreenService starts");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (lockScreenReceiver != null) unregisterReceiver(lockScreenReceiver);
        Log.i("MyTag", "MyLockScreenService stops");
    }
}
