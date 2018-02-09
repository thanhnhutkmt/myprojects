package lab.and401.nhut.and401lab8_quotes_provider_application;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Nhut on 6/27/2017.
 */

public class MyPreferences {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;
    int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "DailyFortune";
    private static final String IS_FIRSTTIME = "IsFirstTime";
    public static final String UserName = "name";

    public MyPreferences(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public boolean isFirstTime() {
        return pref.getBoolean(IS_FIRSTTIME, true);
    }

    public void setOld(boolean b) {
        if (b) {
            editor.putBoolean(IS_FIRSTTIME, false);
            editor.commit();
        }
    }

    public String getUserName() {
        return pref.getString(UserName, "");
    }

    public void setUserName(String name) {
        editor.putString(UserName, name);
        editor.commit();
    }
}
