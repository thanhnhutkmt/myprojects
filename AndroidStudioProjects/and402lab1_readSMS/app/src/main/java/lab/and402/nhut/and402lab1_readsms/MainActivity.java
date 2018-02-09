package lab.and402.nhut.and402lab1_readsms;

import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
    private final String URISTRING = "content://sms";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView list = (ListView) findViewById(R.id.listviewmessage);
        Cursor c = getContentResolver().query(Uri.parse(URISTRING), null, null, null, null);
        list.setAdapter(new MyCursorAdapter(this, c));
    }
}
