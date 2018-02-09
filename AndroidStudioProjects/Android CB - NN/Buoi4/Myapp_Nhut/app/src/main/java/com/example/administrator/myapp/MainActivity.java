package com.example.administrator.myapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText input, inputb;
    private TextView result;
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        input = (EditText)findViewById(R.id.input);
        inputb = (EditText)findViewById(R.id.inputb);
        result = (TextView)findViewById(R.id.result);
        button = (Button)findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int a = Integer.parseInt(input.getText().toString());
                int b = Integer.parseInt(inputb.getText().toString());
                result.setText("Bai 1\n");
                result.append(tinhNguyenDuong(a));
                result.append("\nBai 2\n");
                result.append(tinhUSCLNBSCNNCUS(a, b));
                result.append("\nBai 3\n");
                result.append(inBangCuuChuong(a));
                result.append("\nBai 4\n");
                result.append(tinhTongSoTuNhien(a));
                result.append("\nBai 5\n");
                result.append(tinhGiaiThua(a));
                result.append("\nBai 6\n");
                result.append(tinhChuoiNguyenDuongLe(a));
                result.append("\nBai 7\n");
                result.append(tinhChuoi(a));
            }
        });

        result.setMovementMethod(new ScrollingMovementMethod());
    }

    private String tinhNguyenDuong(int a) {
        StringBuilder sb = new StringBuilder("S=1");
        int temp = 1;
        for (int i = 2; i <= a; i++) {
            sb.append("+" + i);
            temp+=i;
        }
        return sb.append("\nS=" + temp + "\n").toString();
    }

    private int max(int a, int b) {
        return (a>b)? a : b;
    }

    private int min(int a, int b) {
        return (a<b)? a : b;
    }
    private String tinhUSCLNBSCNNCUS(int a, int b) {
        int min = min(a,b);
        int max = max(a,b);
        StringBuilder sb = new StringBuilder();
        sb.append("Cac uoc so chung : 1");
        for (int i = 2; i <= min; i++) {
            if (((max % i) == 0) && ((min % i) == 0)) {
                sb.append(" " + i);
            }
        }
        sb.append("\nUSCLN : ");
        for (int i = min; i >= 1; i--) {
            if (((max % i) == 0) && ((min % i) == 0)) {
                sb.append(i);
                break;
            }
        }
        sb.append("\nBSCNN : ");
        int multiplication = min * max;
        for (int i = max; i <= multiplication; i++) {
            if (((i % max) == 0) && ((i % min) == 0)) {
                sb.append(i);
                break;
            }
        }
        sb.append("\n");
        return sb.toString();
    }
    private String inBangCuuChuong(int a) {
        StringBuilder sb = new StringBuilder("");
        for (int i = 1; i < 10; i++) {
            sb.append(a + "x" + i + "=" + a*i + "\n");
        }
        return sb.toString();
    }
    private String tinhTongSoTuNhien(int a) {
        StringBuilder sb = new StringBuilder("S=1");
        int s = 1;
        for (int i = 2; (s + i) < a; i++) {
            sb.append("+" + i);
            s += i;
        }
        return sb.append(" < " + a + "\n").toString();
    }
    private String tinhGiaiThua(int a) {
        int s = 1;
        for (int i = 2; i <= a; i++) s *= i;
        return a + "!=" + s + "\n";
    }
    private String tinhChuoiNguyenDuongLe(int a) {
        int s = 1;
        StringBuilder sb = new StringBuilder("S=1");
        for (int i = 3; i <= a; i+=2) {
            s += i;
            sb.append("+" + i);
        }
        return sb.toString() + "=" + s + "\n";
    }
    private String tinhChuoi(int a) {
        int s = 1;
        int d = -1;
        StringBuilder sb = new StringBuilder("S=1");
        for (int i = 3; i <= a; i+=2, d = -d) {
            int temp = i * d;
            s += temp;
            sb.append((d>0) ? "+" + temp : temp);
        }
        return sb.toString() + "=" + s;
    }
}
