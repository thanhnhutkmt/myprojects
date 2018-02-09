package lab.and401.nhut.and401lab8_quotes_provider_application;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.widget.Toast;

public class CallListener extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
            if(state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
                Toast.makeText(context, "Phone is ringing", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
