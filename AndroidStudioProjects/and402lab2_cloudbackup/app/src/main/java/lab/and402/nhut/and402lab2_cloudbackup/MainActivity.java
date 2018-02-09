package lab.and402.nhut.and402lab2_cloudbackup;

import android.app.Activity;
import android.app.backup.BackupManager;
import android.app.backup.RestoreObserver;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button savebtn, loadbtn, uploadfilebtn, downloadfilebtn;
    private SharedPreferences prefS, prefF;
    private SharedPreferences.Editor editorS;
    private EditText savedata_et;
    private TextView resulttv, loaddata_tv;
    private BackupManager bm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        savebtn = (Button) findViewById(R.id.save);
        loadbtn = (Button) findViewById(R.id.load);
        uploadfilebtn = (Button) findViewById(R.id.uploadfile);
        downloadfilebtn = (Button) findViewById(R.id.downloadfile);
        savedata_et = (EditText) findViewById(R.id.savedata_et);
        loaddata_tv = (TextView) findViewById(R.id.loaddata_tv);
        resulttv = (TextView) findViewById(R.id.resulttv);
        bm = new BackupManager(this);

        savebtn.setOnClickListener(this);
        loadbtn.setOnClickListener(this);
        uploadfilebtn.setOnClickListener(this);
        downloadfilebtn.setOnClickListener(this);
        prefS = getSharedPreferences(BackUpClass.MY_PREF_NAME, MODE_PRIVATE);
        prefF = getSharedPreferences(BackUpClass.BACKUP_FILE, MODE_PRIVATE);
        editorS = prefS.edit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.save:
                backupData("savedString", savedata_et.getText().toString());
                break;
            case R.id.load:
                restoreData("savedString", loaddata_tv);
                break;
            case R.id.uploadfile:
                backupFile(savedata_et.getText().toString(), BackUpClass.FILENAME_ARRAY[0]);
                break;
            case R.id.downloadfile:
                restoreFile(resulttv, BackUpClass.FILENAME_ARRAY[0]);
                break;
        }
    }

    private void restoreFile(final TextView resulttv, final String fileName) {
        resulttv.setText("waiting...");
        bm.requestRestore(new RestoreObserver() {// manually request restore
            @Override
            public void restoreFinished(int error) {
                super.restoreFinished(error);
                try {
                    BufferedReader br = new BufferedReader(new InputStreamReader(
                                                    openFileInput(fileName)));
                    StringBuilder sb = new StringBuilder();
                    String temp;
                    while ((temp=br.readLine()) != null) sb.append(temp);
                    br.close();
                    resulttv.setText(fileName + " : \n" + sb.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                    resulttv.setText(fileName + " not found");
                }
            }
        });
    }

    private void backupFile(String data, String fileName) {
        try {
            File bk = new File(fileName);
            if (bk.exists()) bk.delete();
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                                            openFileOutput(fileName, MODE_PRIVATE)));
            bw.write(data);
            bw.close();
            bm.dataChanged();
            Toast.makeText(this, "Saved and called backup.", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void restoreData(final String key, final TextView loaddata_tv) {
        bm.requestRestore(new RestoreObserver() { // manually request restore
            @Override
            public void restoreFinished(int error) {
                super.restoreFinished(error);
                SharedPreferences pref = getSharedPreferences(BackUpClass.MY_PREF_NAME, MODE_PRIVATE);
                Log.i("BackupManagerService", "MyTag : " + key + " " + pref.getAll().toString());
                loaddata_tv.setText(pref.getString(key, "no data"));
            }
        });
    }

    private void backupData(String key, String data) {
        editorS.putString(key, data);
        editorS.commit();
        bm.dataChanged();
        Toast.makeText(this, "Backup to cloud ok", Toast.LENGTH_SHORT).show();
    }
}
