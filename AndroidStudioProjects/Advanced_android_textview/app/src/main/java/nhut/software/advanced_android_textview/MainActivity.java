package nhut.software.advanced_android_textview;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.MaskFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.CharacterStyle;
import android.text.style.ForegroundColorSpan;
import android.text.style.MaskFilterSpan;
import android.text.style.MetricAffectingSpan;
import android.text.style.TextAppearanceSpan;
import android.text.style.UpdateAppearance;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;
import org.xml.sax.XMLReader;

public class MainActivity extends AppCompatActivity {
    private TextView textView;
    private TextView customfontView;
    private TextView gradientTView;
    private TextView cheetahTView;
    private TextView htmlTView;
    private TextView multicolorTextView;
    private SpannableString multicolor_SpannableString;
    private TextView fractionTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.animated_text);
        customfontView = (TextView) findViewById(R.id.customfont);
        gradientTView = (TextView) findViewById(R.id.gradientText);
        cheetahTView = (TextView) findViewById(R.id.cheetahText);
        htmlTView = (TextView) findViewById(R.id.htmlText);
        multicolorTextView = (TextView) findViewById(R.id.multicolorText);
        fractionTextView = (TextView) findViewById(R.id.fractiontext);

        startAnimation();
        customFont();
        setGradient();
        setCheetah();
        sethtmlText();
        setmulticolor();
        setFractionText();


        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, Main2Activity.class);
                startActivity(i);
            }
        });
    }

    private void setFractionText() {
        fractionTextView.setTypeface(Typeface.createFromAsset(getAssets(), "Nutso2.otf"));
        fractionTextView.setText(Html.fromHtml(getString(R.string.fractiontext) + "<br>" + "<br>" + getString(R.string.fractiontext1), null, new Html.TagHandler() {
            @Override
            public void handleTag(boolean opening, String tag, Editable output, XMLReader xmlReader) {
                if (!"afrc".equalsIgnoreCase(tag)) return;
                int len = output.length();
                if (opening) {
                    output.setSpan(new FractionSpan(), len, len, Spannable.SPAN_MARK_MARK);
                } else {
                    Object obj = getLast(output, FractionSpan.class);
                    int where = output.getSpanStart(obj);
                    output.removeSpan(obj);
                    if (where != len) {
                        output.setSpan(new FractionSpan(), where, len, 0);
                    }
                }
            }
            private Object getLast(Editable text, Class kind) {
                Object[] objs = text.getSpans(0, text.length(), kind);
                if (objs.length == 0) return null;
                for (int i = objs.length - 1; i >= 0; --i) {
                    if (text.getSpanFlags(objs[i]) == Spannable.SPAN_MARK_MARK) {
                        return objs[i];
                    }
                }
                return null;
            }
            class FractionSpan extends MetricAffectingSpan {
                @Override
                public void updateMeasureState(TextPaint textPaint) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                        textPaint.setFontFeatureSettings("afrc");
                }

                @Override
                public void updateDrawState(TextPaint textPaint) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                        textPaint.setFontFeatureSettings("afrc");
                }
            }
        }
        ));
    }

    private void setmulticolor() {
        String multicolorText = getString(R.string.multicolorText);
        multicolor_SpannableString = new SpannableString(multicolorText);
        Object span = new ForegroundColorSpan(Color.RED);
        multicolor_SpannableString.setSpan(span, 0, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        multicolorTextView.setText(multicolor_SpannableString);
    }

    private void sethtmlText() {
        String html = getString(R.string.from_html_text);
        htmlTView.setMovementMethod(LinkMovementMethod.getInstance());
//        Html.ImageGetter ig = new Html.ImageGetter() {
//            @Override
//            public Drawable getDrawable(String s) {
//                int path = MainActivity.this.getResources().getIdentifier(s, "drawable", BuildConfig.APPLICATION_ID);
//                Drawable drawable = MainActivity.this.getResources().getDrawable(path);
//                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
//                return drawable;
////                return null;
//            }
//        };
        htmlTView.setText(Html.fromHtml(html, new ResourceImageGetter(this), null));
    }

    private static class ResourceImageGetter implements Html.ImageGetter {
        private Context context;
        ResourceImageGetter(Context context) {
            this.context = context;
        }
        public Drawable getDrawable(String source) {
            int path = context.getResources().getIdentifier(source, "drawable", BuildConfig.APPLICATION_ID);
            Drawable drawable = context.getResources().getDrawable(path);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            return drawable;
        }
    }

    private void setCheetah() {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.cheetah_tile);
        Shader s = new BitmapShader(
                bitmap,
                Shader.TileMode.REPEAT,
                Shader.TileMode.REPEAT
        );
        cheetahTView.getPaint().setShader(s);
    }

    private void setGradient() {
        Shader s = new LinearGradient(0, 0, 0, gradientTView.getTextSize(), Color.RED, Color.BLUE, Shader.TileMode.CLAMP);
        gradientTView.getPaint().setShader(s);
    }

    private void customFont() {
        customfontView.setTypeface(Typeface.createFromAsset(getAssets(), "Ruthie.ttf"));
    }

    private void startAnimation() {
        Drawable[] drawables = textView.getCompoundDrawables();
        for (Drawable drawable : drawables) {
            if (drawable != null && drawable instanceof Animatable) {
                ((Animatable) drawable).start();
            }
        }
    }
}
