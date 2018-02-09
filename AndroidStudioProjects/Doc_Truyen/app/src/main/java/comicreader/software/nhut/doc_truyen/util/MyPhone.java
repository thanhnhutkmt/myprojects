package comicreader.software.nhut.doc_truyen.util;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.os.Build;

/**
 * Created by Nhut on 7/2/2017.
 */

public class MyPhone {
    public static String getOSVersion() {
        return Integer.toString(Build.VERSION.SDK_INT);
    }

    public static Point getScreenSize(Activity context) {
        Point size = new Point();
        context.getWindowManager().getDefaultDisplay().getSize(size);
        return size;
    }

    public static int getNumberOfCPUCore() {
        return Runtime.getRuntime().availableProcessors();
    }

    public static void setPortraitOrient(Activity context) {
        context.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }
}
