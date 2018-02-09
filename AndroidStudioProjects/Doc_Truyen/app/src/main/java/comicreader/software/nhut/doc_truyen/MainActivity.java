package comicreader.software.nhut.doc_truyen;

import android.animation.FloatEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.format.DateUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.CharacterStyle;
import android.util.Log;
import android.util.Property;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import comicreader.software.nhut.doc_truyen.DO.ServerList;
import comicreader.software.nhut.doc_truyen.util.MyInAppBilling;

import static comicreader.software.nhut.doc_truyen.util.MyPhone.setPortraitOrient;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tv_selectcomic, tv_savedcomic, tv_exit, tv_author, tv_purchase;
    private Spinner spinner_server;
    private ImageButton imgbtnflag;
    private int countTime = 0;
    private MyInAppBilling purchase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("");
        setPortraitOrient(this);
        showAd();

        Constant.EXTDIR = getExternalFilesDir("").getAbsolutePath();
        Constant.CACHEDIR = getCacheDir().getAbsolutePath();
        spinner_server = (Spinner) findViewById(R.id.spinner_server);
        tv_selectcomic = (TextView) findViewById(R.id.tv_selectcomic);
        tv_savedcomic = (TextView) findViewById(R.id.tv_savedcomic);
        tv_purchase = (TextView) findViewById(R.id.tv_purchase);
        tv_exit = (TextView) findViewById(R.id.tv_exit);
        tv_author = (TextView) findViewById(R.id.tv_author);
        imgbtnflag = (ImageButton) findViewById(R.id.imgbtnflag);

        if (purchase == null) purchase = new MyInAppBilling(this);
        tv_purchase.setEnabled(false);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!purchase.isReady())
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv_purchase.setEnabled(true);
                    }
                });
            }
        }).start();
        tv_selectcomic.setOnClickListener(this);
        tv_savedcomic.setOnClickListener(this);
        tv_purchase.setOnClickListener(this);
        tv_exit.setOnClickListener(this);
        imgbtnflag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeLanguague(v);
            }
        });
        imgbtnflag.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                countTime++;
                if (countTime > 5) {
                    boolean temp = !Constant.TESTING;
                    Constant.TESTING = temp;
                    countTime = 0;
                }
                Log.i("MyTag", "countTime " + countTime);
                return false;
            }
        });

        List<String> listserver = new ArrayList<>();
        for (int sid : ServerList.serverlist) listserver.add(getResources().getString(sid));
        spinner_server.setAdapter(new ArrayAdapter<>
            (this, R.layout.itemspinner, R.id.textview_spinner, listserver));
        decoratetextview();
        animatedRainbow();
        addGradient();

        ImageButton ib = (ImageButton)findViewById(R.id.imgbtnflag);
        if (Locale.getDefault().toString().contains("vi"))
            ib.setBackground(getResources().getDrawable(R.drawable.en_flag));
        else
            ib.setBackground(getResources().getDrawable(R.drawable.vi_flag));
    }

    private void showAd() {
        AdRequest.Builder b = new AdRequest.Builder();
        AdView adv = (AdView) findViewById(R.id.adView);
        if (Constant.TESTING) {
            b.addTestDevice(AdRequest.DEVICE_ID_EMULATOR);
            b.addTestDevice(Constant.myTestID);
        }
        adv.loadAd(b.build());
    }

    private AlertDialog ad;
    private void showSearchDialog(final int searchStringId) {
        AlertDialog.Builder b = new AlertDialog.Builder(MainActivity.this);
        LinearLayout ll = new LinearLayout(MainActivity.this);
        ll.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        ll.setPadding(40, 30, 40, 30);
        final EditText et = new EditText(MainActivity.this);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        et.setLayoutParams(lp);
        et.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                Log.i("MyTag", "KeyCode " + keyCode);
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    if (event.getAction() == KeyEvent.ACTION_UP) {
                        startActivity(
                            new Intent(MainActivity.this, Select_truyen.class)
                                .putExtra(Constant.TRUYENSERVER, getResources()
                                    .getString(searchStringId)
                                    + et.getText().toString().replace(" ", "%20")));
                        ad.dismiss();
                    }
                    return true;
                }
                return false;
            }
        });
        ll.addView(et);
        b.setView(ll)
        .setTitle(R.string.TimTruyen)
        .setPositiveButton(R.string.searchbtn,
            new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startActivity(
                        new Intent(MainActivity.this, Select_truyen.class)
                            .putExtra(Constant.TRUYENSERVER, getResources()
                                .getString(searchStringId)
                                + et.getText().toString().replace(" ", "%20")));
                }
            })
        .setNegativeButton(R.string.cancelbtn,
            new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
        ad = b.create();
        ad.show();
    }

    private void addGradient() {
        Shader s = new LinearGradient(0, 0, 0, tv_author.getTextSize(),
                Color.RED, Color.BLUE, Shader.TileMode.CLAMP);
        tv_author.getPaint().setShader(s);
    }

    private void decoratetextview() {
        String font = getResources().getString(R.string.font);
        tv_exit.setTypeface(Typeface.createFromAsset(getAssets(), font));
        tv_savedcomic.setTypeface(Typeface.createFromAsset(getAssets(), font));
        tv_selectcomic.setTypeface(Typeface.createFromAsset(getAssets(), font));
        tv_purchase.setTypeface(Typeface.createFromAsset(getAssets(), font));
    }

    private void animatedRainbow() {
        final SpannableString ss = new SpannableString(getString(R.string.copyright));
        AnimatedColorSpan span = new AnimatedColorSpan();
        ss.setSpan(span, 0, ss.length(), 0);
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(span, ANIMATED_COLOR_SPAN_FLOAT_PROPERTY, 0, 100);
        objectAnimator.setEvaluator((new FloatEvaluator()));
        objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                tv_author.setText(ss);
            }
        });
        objectAnimator.setInterpolator(new LinearInterpolator());
        objectAnimator.setDuration(DateUtils.MINUTE_IN_MILLIS * 3);
        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
        objectAnimator.start();
    }

    private static final Property<AnimatedColorSpan, Float> ANIMATED_COLOR_SPAN_FLOAT_PROPERTY =
        new Property<AnimatedColorSpan, Float>(Float.class, "ANIMATED_COLOR_SPAN_FLOAT_PROPERTY") {
            public void set(AnimatedColorSpan span, Float value) {
                span.setTranslateXPercentage(value);
            }
            public Float get(AnimatedColorSpan span) {
                return span.getTranslateXPercentage();
            }
        };

    private static class AnimatedColorSpan extends CharacterStyle {
        private float translateXPercentage;
        private int[] colors;
        private Shader shader;
        private Matrix matrix;

        public AnimatedColorSpan() {
            colors = new int[7];
            colors[0] = Color.RED;
            colors[1] = Color.rgb(244, 124, 19); //orange
            colors[2] = Color.rgb(244, 229, 19); //yellow
            colors[3] = Color.rgb(45, 244, 19);  //green
            colors[4] = Color.rgb(19, 173, 244); //blue
            colors[5] = Color.rgb(19, 83, 244);  //indigo
            colors[6] = Color.rgb(132, 19, 244); //purple
            this.matrix = new Matrix();
        }

        public void setTranslateXPercentage(float value) {
            translateXPercentage = value;
        }

        public float getTranslateXPercentage() {
            return translateXPercentage;
        }

        public void updateDrawState(TextPaint paint) {
            paint.setStyle(Paint.Style.FILL);
            float width = paint.getTextSize() * colors.length;
            if (this.shader == null) {
                this.shader = new LinearGradient(0, 0, 0, width, colors, null, Shader.TileMode.MIRROR);
            }
            this.matrix.reset();
            this.matrix.setRotate(90);
            this.matrix.postTranslate(width * translateXPercentage, 0);
            this.shader.setLocalMatrix(this.matrix);
            paint.setShader(this.shader);
        }
    }

    private int checkShowDialog() {
        String item = getResources().getString(
                ServerList.serverlist[spinner_server.getSelectedItemPosition()]);
        String search1 = getResources().getString(R.string.server2);
        String search2 = getResources().getString(R.string.server4_1);
        int searchid = (item == null) ? -1 : item.equals(search1) ? R.string.link2 :
                item.equals(search2) ? R.string.link4_1 : -1;
        return searchid;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        purchase.proceedBuyingProcess(requestCode, resultCode, data);
        //update purchased items in inventory
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.tv_selectcomic:
                int searchid = checkShowDialog();
                if (searchid != -1)
                    showSearchDialog(searchid);
                else
                    startActivity(new Intent(this, Select_truyen.class)
                        .putExtra(Constant.TRUYENSERVER, getResources().getString(
                            ServerList.serverLinkList[spinner_server.getSelectedItemPosition()])));
                break;
            case R.id.tv_savedcomic:
                startActivity(new Intent(this, Truyen_daluu.class));
                break;
            case R.id.tv_exit:
                stopService(new Intent(this, MyGAdService.class));
                finish();//System.exit(0);
                break;
            case R.id.tv_purchase:
                if (purchase.isReady()) purchase.showProductDialog();
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_ngonngu, menu);
        menu.getItem(0).setIcon(R.drawable.en_flag);
        menu.getItem(1).setIcon(R.drawable.vi_flag);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.mnuenglish:
                setLanguage(Locale.US);
                break;
            case R.id.mnuvietnam:
                setLanguage(new Locale("vi"));
                break;
        }
        startActivity(new Intent(this, MainActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
        finish();
        return true;
    }

    public void changeLanguague(View view) {
        ImageButton ib = (ImageButton)view;
        Log.i("MyTag", "Change language : " + Locale.getDefault().toString());
        if (Locale.getDefault().toString().contains("vi")) {
            ib.setBackground(getResources().getDrawable(R.drawable.vi_flag));
            setLanguage(Locale.US);
        } else {
            ib.setBackground(getResources().getDrawable(R.drawable.en_flag));
            setLanguage(new Locale("vi"));
        }
        Log.i("MyTag", "Reverse language : " + Locale.getDefault().toString());
        startActivity(new Intent(this, MainActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
        finish();
    }

    private void setLanguage(Locale language) {
        Locale.setDefault(language);
        Configuration config = new Configuration();
        config.locale = language;
        getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
    }
}