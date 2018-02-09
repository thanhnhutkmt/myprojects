package lab.and401.nhut.and401lab8_quotes_provider_application;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import lab.and401.nhut.and401lab8_quotes_provider_application.app.AppController;

public class FortuneActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fortune);
        MyPreferences pref = new MyPreferences(FortuneActivity.this);
        if (pref.isFirstTime()) {
            Toast.makeText(this, "Hi " + pref.getUserName(), Toast.LENGTH_LONG).show();
            pref.setOld(true);
        } else {
            Toast.makeText(this, "Welcome back " + pref.getUserName(), Toast.LENGTH_LONG).show();
        }
        ConnectionDetector cd = new ConnectionDetector(this);
        if (cd.isConnectingToInternet()) {
            getFortuneOnline();
        } else {
            readFortuneFromFile();
        }
    }

    private void getFortuneOnline() {
        final TextView fortunetxt = (TextView) findViewById(R.id.fortune);
        fortunetxt.setText("Loading...");
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
            "http://quotes.rest/qod.json",
            (String)null,
            new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.d("Response", response.toString());
                    String fortune;
                    try {
                        JSONObject contents = response.getJSONObject("contents");
                        JSONArray quotes = contents.getJSONArray("quotes");
                        fortune = quotes.getJSONObject(0).getString("quote");
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(FortuneActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        fortune = "Error";
                    }
                    fortunetxt.setText(fortune);
                    writeToFile(fortune);
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d("Response", "Error: " + error.getMessage());
                    Toast.makeText(FortuneActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        );
        AppController.getInstance().addToRequestQueue(request);
    }

    private void writeToFile(String data) {
        try {
            OutputStreamWriter osw = new OutputStreamWriter(openFileOutput("Fortune.json", Context.MODE_PRIVATE));
            osw.write(data);
            osw.close();
        } catch(IOException e) {
            Log.v("Message:", "File write failed " + e.toString());
        }
    }

    private void readFortuneFromFile() {
        String fortune = "";
        try {
            InputStream is = openFileInput("Fortune.json");
            if (is != null) {
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();
                Log.v("Message:", "reading...");
                while((receiveString = br.readLine()) != null) {
                    stringBuilder.append(receiveString);
                }
                is.close();
                fortune = stringBuilder.toString();
            }
        } catch(FileNotFoundException e) {
            Log.e("Message:", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("Message:", "Can not read file: " + e.toString());
        }
        TextView fortunetxt = (TextView) findViewById(R.id.fortune);
        fortunetxt.setText(fortune);
    }
}
