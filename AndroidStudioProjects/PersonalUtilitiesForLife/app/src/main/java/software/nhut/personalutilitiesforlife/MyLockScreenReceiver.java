package software.nhut.personalutilitiesforlife;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import software.nhut.personalutilitiesforlife.constant.AppConstant;

public class MyLockScreenReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Log.i("MyTag", "MyLockScreenReceiver " + action);
//        if (action.equals(Intent.ACTION_SCREEN_ON)
//                || action.equals(Intent.ACTION_BOOT_COMPLETED)
//                || action.equals(Intent.ACTION_SCREEN_OFF) ) {
//        if (action.equals(Intent.ACTION_BOOT_COMPLETED)) {
            Intent i = new Intent(context, XacThucActivity.class);
            i.putExtra(AppConstant.LOCKCODESTRING, AppConstant.LOCKCODE);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
//        }

//        else if (action.equals(Intent.ACTION_SCREEN_ON)) {
//            System.exit(0);
//        }
    }
}
