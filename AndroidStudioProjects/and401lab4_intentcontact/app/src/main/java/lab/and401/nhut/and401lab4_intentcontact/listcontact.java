package lab.and401.nhut.and401lab4_intentcontact;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class listcontact extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listcontact);

        final List<ContactObject> list = new ArrayList<ContactObject>();
        list.add(new ContactObject("contact 1", "111-1111", "www.androidatc.com"));
        list.add(new ContactObject("contact 2", "222-2222", "www.5giay.com"));
        list.add(new ContactObject("contact 3", "333-3333", "www.nhatnghe.com"));
        list.add(new ContactObject("contact 4", "444-4444", "www.facebook.com"));
        ListView listContact = (ListView) findViewById(R.id.listcontact);
        listContact.setAdapter(new ArrayAdapter<ContactObject>(this, android.R.layout.simple_list_item_1, list));
        listContact.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(listcontact.this, contactinfo.class);
                i.putExtra(constant.CONTACT, list.get(position));
                startActivityForResult(i, constant.REQUESTCODE);
                return true;
            }
        });
    }

    private String phoneNumber = "";

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == constant.REQUESTCODE) {
            if (resultCode == constant.VALUEPHONE) {
                phoneNumber = data.getStringExtra(constant.VALUE);

                ActivityCompat.requestPermissions(listcontact.this, new String[] {Manifest.permission.CALL_PHONE}, constant.RUNTIME_CODE_PERMISSION);
//                makeCall(data);
            } else if (resultCode == constant.VALUEWEBSITE) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://" + data.getStringExtra(constant.VALUE))));
            }
        }
    }

    private boolean repeat = false;
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED)
            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber)));
        else {
            if (repeat) {
                repeat = false;
                return;
            }
            repeat = true;
            explainReason();
        }
    }

//    private void makeCall(Intent data) {
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
//            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + data.getStringExtra(constant.VALUE))));
//        } else {
//            explainReason();
//        }
//    }
//
    private void explainReason() {
        TextView message = new TextView(this);
        message.setText("Please allow Call permission to make call. If you deny this permission, your call can not proceed");
        message.setPadding(10,10,10,10);
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle("Explain Reason for asking Call permission")
         .setView(message)
         .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
             @Override
             public void onClick(DialogInterface dialog, int which) {
                 dialog.dismiss();
                 ActivityCompat.requestPermissions(listcontact.this, new String[] {Manifest.permission.CALL_PHONE}, constant.RUNTIME_CODE_PERMISSION);
             }
         })
         .create().show();
    }
}
