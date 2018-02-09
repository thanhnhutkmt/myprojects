package and402lab3.nhut.and402.lab.and402lab3_exposedcontentprovider;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Uri dataUri = Uri.parse("content://" + MyContentProvider.authorities);
    private Uri secureDataUri = Uri.parse("content://" + MySecuredContentProvider.authorities);
    private String colmsgName = MyContentProvider.DBconnection.DBColmsgname;
    private String colmsgNameS = MySecuredContentProvider.DBconnection.DBColmsgname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ContentValues cv = new ContentValues();
        cv.put(colmsgName, "Hello message here");
        ContentValues cvS = new ContentValues();
        cvS.put(colmsgNameS, "Hello secured message here");

        getContentResolver().insert(dataUri, cv);
        getContentResolver().insert(secureDataUri, cvS);

        TextView content = (TextView) findViewById(R.id.content);

        Cursor c = getContentResolver().query(dataUri, null, null, null, null);
        if (c.moveToFirst())
            content.setText(c.getString(1));
        Cursor cS = getContentResolver().query(secureDataUri, null, null, null, null);
        if (cS.moveToFirst())
            content.append("\n" + cS.getString(1));
    }
}
