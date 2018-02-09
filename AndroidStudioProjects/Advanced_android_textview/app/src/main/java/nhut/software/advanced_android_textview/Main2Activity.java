package nhut.software.advanced_android_textview;

import android.animation.FloatEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BlurMaskFilter;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.MaskFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.format.DateUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.AlignmentSpan;
import android.text.style.CharacterStyle;
import android.text.style.ClickableSpan;
import android.text.style.MaskFilterSpan;
import android.text.style.TextAppearanceSpan;
import android.text.style.UpdateAppearance;
import android.util.Property;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import static android.R.attr.button;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener{
    private TextView stringBuilderTView;
    private TextView alignTextView, anirainbowTextView, clickableTView;
    private Button left, right;
    private EditText edittext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        stringBuilderTView = (TextView) findViewById(R.id.spanstringbuilder);
        alignTextView = (TextView) findViewById(R.id.textView);
        anirainbowTextView = (TextView) findViewById(R.id.textView2);
        clickableTView = (TextView) findViewById(R.id.clickableText);
        left = (Button) findViewById(R.id.btnL);
        right = (Button) findViewById(R.id.btnR);
        edittext = (EditText) findViewById(R.id.editText);
        left.setOnClickListener(this);
        right.setOnClickListener(this);
        setSpanStringBuilder();
        animatedRainbow();
        clickableText();
    }

    private void appendText(String text, Layout.Alignment align) {
        AlignmentSpan span = new AlignmentSpan.Standard(align);
        SpannableString ss = new SpannableString(text);
        ss.setSpan(span, 0, text.length(), 0);
        if (alignTextView.length() > 0) {
            alignTextView.append("\n\n");
        }
        alignTextView.append(ss);
    }

    private void setSpanStringBuilder() {
        float radius = getResources().getDisplayMetrics().density*2;
        SpannableStringBuilder b = new SpannableStringBuilder()
            .append(formatString(this, R.string.version, new MaskFilterSpan(
                    new BlurMaskFilter(radius, BlurMaskFilter.Blur.NORMAL))
                    , Spanned.SPAN_EXCLUSIVE_EXCLUSIVE))
            .append("\n")
            .append(formatString(this, R.string.author, new RainbowSpan(this), 0, 9))
            .append("\n")
            .append(formatString(this, R.string.copyright, new TextAppearanceSpan(this, R.style.BigRedTextAppearance)));
        stringBuilderTView.setText(b.subSequence(0, b.length()));
    }

    @Override
    public void onClick(View button) {
        String text = edittext.getText().toString();
        Layout.Alignment align = button.getId() == R.id.btnR ? Layout.Alignment.ALIGN_OPPOSITE : Layout.Alignment.ALIGN_NORMAL;
        appendText(text, align);
        edittext.setText(null);
    }

    private static class RainbowSpan extends CharacterStyle implements UpdateAppearance {
        private int[] colors;
        public RainbowSpan(Context context) {
            colors = new int[7];//context.getResources().getIntArray(R.array.rainbow);
//            for (int i = 0; i < colors.length; i++) colors[i] = i;
            colors[0] = Color.RED;
            colors[1] = Color.rgb(244, 124, 19); //orange
            colors[2] = Color.rgb(244, 229, 19); //yellow
            colors[3] = Color.rgb(45, 244, 19);  //green
            colors[4] = Color.rgb(19, 173, 244); //blue
            colors[5] = Color.rgb(19, 83, 244);  //indigo
            colors[6] = Color.rgb(132, 19, 244); //purple
        }

        @Override
        public void updateDrawState(TextPaint textPaint) {
            textPaint.setStyle(Paint.Style.FILL);
            Shader shader = new LinearGradient(0, 0, 0, textPaint.getTextSize() * colors.length, colors, null, Shader.TileMode.MIRROR);
            Matrix m = new Matrix();
            m.setRotate(90);
            shader.setLocalMatrix(m);
            textPaint.setShader(shader);
        }
    }

    private SpannableString formatString(Context context, int textId, Object span, int flag, int start) {
        String text = context.getString(textId);
        return formatString(context, textId, span, flag, start, text.length());
    }

    private SpannableString formatString(Context context, int textId, Object span, int flag) {
        return formatString(context, textId, span, flag, 0);
    }

    private SpannableString formatString(Context context, int textId, Object span) {
        return formatString(context, textId, span, 0);
    }

    private SpannableString formatString(Context context, int textId, Object span, int flag, int start, int end) {
        String text = context.getString(textId);
        SpannableString ss = new SpannableString(text);
        ss.setSpan(span, start, end, flag);
        return ss;
    }

    private void animatedRainbow() {
        final SpannableString ss = new SpannableString(getString(R.string.animated));
        AnimatedColorSpan span = new AnimatedColorSpan();
        ss.setSpan(span, 11, 32, 0);
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(span, ANIMATED_COLOR_SPAN_FLOAT_PROPERTY, 0, 100);
        objectAnimator.setEvaluator((new FloatEvaluator()));
        objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                anirainbowTextView.setText(ss);
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

    private void clickableText() {
        String text = clickableTView.getText().toString();
        SpannableString ss = new SpannableString(text);
        String goToSettings = getString(R.string.gtsetting);
        int start = text.indexOf(goToSettings);
        int end = start + goToSettings.length();
        ss.setSpan(new ClickableSpan() {
            @Override public void onClick(View view) {
                view.getContext().startActivity(new Intent(android.provider.Settings.ACTION_SETTINGS));
            }
        }, start, end, 0);
        clickableTView.setText(ss);
        clickableTView.setMovementMethod(new LinkMovementMethod());
    }
}
