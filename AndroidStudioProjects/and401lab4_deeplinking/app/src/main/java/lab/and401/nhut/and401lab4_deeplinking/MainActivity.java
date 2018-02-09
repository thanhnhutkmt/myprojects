package lab.and401.nhut.and401lab4_deeplinking;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    WebView w;
    EditText webadrress;
    Button reload;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        w = (WebView) findViewById(R.id.webview);
        webadrress = (EditText) findViewById(R.id.link);
        reload = (Button) findViewById(R.id.btn_reload);

        w.getSettings().setJavaScriptEnabled(true);

        Intent i = getIntent();
        if (i.getAction() == Intent.ACTION_VIEW) {
            String url = i. getDataString();
            Log.i("deeplinking", i.getAction() + " : " + url);
            webadrress.setText(url);
            load();
        }

        webadrress.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {
                    load();
                }
                return true;
            }
        });
        webadrress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webadrress.setFocusableInTouchMode(true);
                webadrress.requestFocus();
            }
        });

        reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                load();
            }
        });
    }

    private void load() {
        String url = webadrress.getText().toString();
        if (!url.startsWith("http://") && !url.startsWith("https://"))
            url = "http://" + url;
        w.loadUrl(url);
        ((InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(webadrress.getWindowToken(), 0);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
