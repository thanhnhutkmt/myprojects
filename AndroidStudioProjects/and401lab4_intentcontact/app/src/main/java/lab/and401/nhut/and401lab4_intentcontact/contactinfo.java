package lab.and401.nhut.and401lab4_intentcontact;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class contactinfo extends AppCompatActivity implements View.OnClickListener {
    TextView name, phone, website;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactinfo);

        name = (TextView) findViewById(R.id.txt_name);
        phone = (TextView) findViewById(R.id.txt_phone);
        website = (TextView) findViewById(R.id.txt_website);

        ContactObject c = (ContactObject) getIntent().getSerializableExtra(constant.CONTACT);
        if (c != null) {
            name.setText(c.getName());
            phone.setText(c.getPhone());
            website.setText(c.getWebsite());
        }

        phone.setOnClickListener(this);
        website.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent();
        switch(v.getId()) {
            case R.id.txt_phone:
                i.putExtra(constant.VALUE, phone.getText().toString());
                setResult(constant.VALUEPHONE, i);
                finish();
                break;
            case R.id.txt_website:
                i.putExtra(constant.VALUE, website.getText().toString());
                setResult(constant.VALUEWEBSITE, i);
                finish();
                break;
        }
    }
}
