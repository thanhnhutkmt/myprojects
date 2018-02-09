package lab.and402.nhut.and402lab1_readsms;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.provider.Telephony;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import java.util.Date;

/**
 * Created by Nhut on 7/1/2017.
 */

public class MyCursorAdapter extends CursorAdapter {
    private final String COLNAME_SMS_ID = "_id";
    private final String COLNAME_SMS_ADDRESS = "address";
    private final String COLNAME_SMS_DATE = "date";
    private final String COLNAME_SMS_BODY = "body";
    public MyCursorAdapter(Activity activity, Cursor c) {
        super(activity, c);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return ((Activity)context).getLayoutInflater().inflate(android.R.layout.simple_list_item_1, null);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView textview = (TextView)view.findViewById(android.R.id.text1);

        textview.setText(
            new Date(Long.parseLong(cursor.getString(cursor.getColumnIndexOrThrow(COLNAME_SMS_DATE)))).toLocaleString() + "\n" +
            cursor.getString(cursor.getColumnIndex(COLNAME_SMS_ADDRESS)) + "\n" +
            cursor.getString(cursor.getColumnIndex(COLNAME_SMS_BODY)));
    }
}
