package lab.and401.nhut.and401lab8_quotes_provider_application;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Nhut on 6/27/2017.
 */

public class ConnectionDetector {
    private Context context;

    public ConnectionDetector(Context context) {
        this.context = context;
    }

    public boolean isConnectingToInternet() {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
