package lab.and401.nhut.and401lab7_contextmenu_optionmenu_webview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

public class Main3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        WebView wv = (WebView) findViewById(R.id.webview);
        wv.setWebChromeClient(new WebChromeClient());
        wv.getSettings().setJavaScriptEnabled(true);
        wv.loadUrl("http://androidatc.com");
    }
}
