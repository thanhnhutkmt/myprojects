package lab.and401.nhut.and401lab8_sharedpreferences_callnotify_service_volley;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Main2Activity extends AppCompatActivity {
    private TextView content;
    private String path = "";
    private final String filename = "fortunefile.txt";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        path = getApplicationInfo().dataDir + File.separator + filename;
        try {
            new File(path).createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        content = (TextView) findViewById(R.id.fortunetext);
        SharedPreferences sp = getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE);
        String username = sp.getString(MainActivity.USERNAME, null);
        boolean isFirstTime = sp.getBoolean(MainActivity.ISFISTTIME, true);
        if (isFirstTime) {
            Toast.makeText(this, "Hi, " + username, Toast.LENGTH_SHORT).show();
            sp.edit().putBoolean(MainActivity.ISFISTTIME, false).commit();
        } else
            Toast.makeText(this, "Welcome back, " + username, Toast.LENGTH_SHORT).show();
        getdata();
    }

    private void getdata() {
        Volley.newRequestQueue(this).add(new StringRequest(Request.Method.GET,
                "http://quotes.rest/qod.json",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        writeFile(parseResult(response), path);
                        readFromOfflineCache();
                    }
                },
                new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                writeFile(error.getMessage(), path);
                readFromOfflineCache();
            }
        }));
    }

    private void readFromOfflineCache() {
        String result = readFile(path);
        content.setText(result);
    }

    private String readFile(String path) {
        StringBuffer sb = new StringBuffer("");
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(path)));
            String temp;
            while ((temp = br.readLine()) != null) sb.append(temp);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    private boolean writeFile(String content, String path) {
        BufferedWriter bw = null;
        boolean ok = true;
        try {
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path)));
            bw.write(content);
            bw.flush();
        } catch (Exception e) {
            e.printStackTrace();
            ok = false;
        } finally {
            try {
                if (bw != null) bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ok;
    }

    private String parseResult(String Json) {
        String content = "";
        // simple json parser
        String startString = "\"quote\": \"";
        int start = Json.indexOf(startString) + startString.length();
        int end = Json.indexOf("\"", start);
        content = Json.substring(start, end);
        return content;
    }

}
