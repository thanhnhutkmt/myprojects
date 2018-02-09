package and402lab4.nhut.and402.lab.and402lab4_readwritefile_cache_db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.RequiresPermission;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import and402lab4.nhut.and402.lab.and402lab4_readwritefile_cache_db.util.IO_DB;

public class MainActivity extends AppCompatActivity {
    private EditText filenameet, filecontentet;
    private RadioButton Internal, External, Cache, Database;
    private RadioGroup radioGroup;
    private Button Readbtn, Writebtn;
    private String filePath;
    private IO_DB.DBConnection dbconn;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        radioGroup = (RadioGroup) findViewById(R.id.select);
        filenameet = (EditText) findViewById(R.id.filenameet);
        filecontentet = (EditText) findViewById(R.id.filecontentet);
        Readbtn = (Button) findViewById(R.id.Readbtn);
        Writebtn = (Button) findViewById(R.id.Writebtn);
        dbconn = new IO_DB.DBConnection(this);
        db = dbconn.getWritableDatabase();

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId) {
                    case R.id.Internal:
                        filePath = getFilesDir().getAbsolutePath();
                        break;
                    case R.id.External:
                        filePath = (IO_DB.isExtDeviceOk(MainActivity.this)) ?
                                getExternalFilesDir(null).getAbsolutePath() : "";
                        break;
                    case R.id.Cache:
                        filePath = getCacheDir().getAbsolutePath();
                        break;
                    case R.id.Database:
                        filePath = "Database";
                        break;
                }
                Log.i("MyTag", "check item " + checkedId + " " + "file Path : " + filePath);
            }
        });
        radioGroup.check(R.id.Internal);
        Readbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temp = "";
                if (filePath.length() == 0) return;
                else if (filePath.equals("Database")) temp = "DB content : " + IO_DB.readDB(db);
                else temp = IO_DB.read(filePath, filenameet.getText().toString());
                filecontentet.setText("Content :\n" + temp);
            }
        });
        Writebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (filePath.length() == 0) return;
                else if (filePath.equals("Database")) {
                    IO_DB.writeDB(filecontentet.getText().toString(), db);
                    Toast.makeText(MainActivity.this, "DB table " + IO_DB.DBConnection.Table
                        + "'s content: \n" + IO_DB.readDB(db), Toast.LENGTH_SHORT).show();
                } else {
                    IO_DB.write(filePath, filenameet.getText().toString(),
                            filecontentet.getText().toString());
                    Toast.makeText(MainActivity.this, "Written file's content: \n" +
                        IO_DB.read(filePath, filenameet.getText().toString()),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        filecontentet.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ((EditText)v).setText("");
                return false;
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
        dbconn.close();
    }
}
