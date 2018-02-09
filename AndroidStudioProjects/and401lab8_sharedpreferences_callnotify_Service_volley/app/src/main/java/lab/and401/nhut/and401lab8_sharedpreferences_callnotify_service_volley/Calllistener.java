package lab.and401.nhut.and401lab8_sharedpreferences_callnotify_service_volley;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telecom.TelecomManager;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

public class Calllistener extends BroadcastReceiver {
    // must allow Phone permission in setting of app to run broadcastreceiver
    // maybe, this app don't use Application class ?
    public Calllistener() {

    }
    @Override
    public void onReceive(Context context, Intent intent) {
        String phonestate = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
        if (phonestate != null) {
            Log.i(context.getString(R.string.app_name), phonestate);
            if (phonestate.equals(TelephonyManager.EXTRA_STATE_RINGING))
                Toast.makeText(context, "Phone is ringing", Toast.LENGTH_LONG).show();
        }
    }
}
