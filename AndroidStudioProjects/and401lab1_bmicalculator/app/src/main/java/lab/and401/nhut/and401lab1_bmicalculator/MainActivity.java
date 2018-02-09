package lab.and401.nhut.and401lab1_bmicalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button button = (Button) findViewById(R.id.button);
        final EditText height = (EditText) findViewById(R.id.editText_height);
        final EditText weight = (EditText) findViewById(R.id.editText_weight);
        final TextView result = (TextView) findViewById(R.id.result);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result.setText(getResult(Float.parseFloat(height.getText().toString())/100, Float.parseFloat(weight.getText().toString())));
            }
        });
    }

    private String getResult(float height, float weight) {
        float bi = weight / (height * height);
        String result = "Body index : " + bi;
        if (bi < 16) {
            result += " (Severely underweight)";
        } else if (bi < 18.5) {
            result += " (underweight)";
        } else if (bi < 25) {
            result += " (normal)";
        } else if (bi < 30) {
            result += " (overweight)";
        } else {
            result += " (obese)";
        }
        return result;
    }
}
