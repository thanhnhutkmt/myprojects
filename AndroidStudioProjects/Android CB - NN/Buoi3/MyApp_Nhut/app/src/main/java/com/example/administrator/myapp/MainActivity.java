package com.example.administrator.myapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private String Can[] =
            {"Canh", "Tan", "Nham", "Quy", "Giap", "At",
                "Binh", "Dinh", "Mau", "Ky"};
    private String Chi[] =
            {"Than", "Dau", "Tuat", "Hoi", "Ty", "Suu",
                "Dan", "Meo", "Thin", "Ti", "Ngo", "Mui"};
    private EditText nam;
    private Button bt;
    private TextView kq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nam = (EditText)findViewById(R.id.editText);
        bt = (Button)findViewById(R.id.button);
        kq = (TextView)findViewById(R.id.textView2);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int n = Integer.parseInt(nam.getText().toString());
                kq.setText("Nam " + n + " la " + Can[n%10] + " " + Chi[n%12]);
            }
        });
    }
}
