package dhcl.ltdhk9.tuan.quanlybaihat;

/**
 * Created by ANH QUOC on 9/4/2016.
 */
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
/**
 * class nhập thông tin tác giả
 * Mọi thay đổi đều gửi thông tin về MainActivity để xử lý
 * @author drthanh
 *
 */
public class CreateAlbumActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_author);
        final Button btnInsert =(Button) findViewById(R.id.buttonInsert);
        final EditText txtMaAlbum=(EditText) findViewById(R.id.editTextMaAlbum);
        final EditText txtTenAlbum=(EditText) findViewById(R.id.editTextTenAlbum);
        final  Intent intent= getIntent();
        btnInsert.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent=new Intent();
                Bundle bundle=new Bundle();
                bundle.putString("maalbum", txtMaAlbum.getText().toString());
                bundle.putString("tenalbum", txtTenAlbum.getText().toString());
                intent.putExtra("DATA_ALBUM", bundle);
                setResult(MainActivity.SEND_DATA_FROM_AUTHOR_ACTIVITY, intent);
                CreateAlbumActivity.this.finish();

                            }
        });
        final Button btnXoa=(Button) findViewById(R.id.buttonXoa);
        btnXoa.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                txtMaAlbum.setText("");
                txtTenAlbum.setText("");
                txtMaAlbum.requestFocus();
            }
        });

        Bundle bundle= intent.getBundleExtra("DATA");
        if(bundle!=null && bundle.getInt("KEY")==1)
        {
            String f2=bundle.getString("getField2");
            String f3=bundle.getString("getField3");
            txtMaAlbum.setText(f2);
            txtTenAlbum.setText(f3);
            btnInsert.setText("Update");
            this.setTitle("View Detail");
			/*TableRow row=(TableRow) findViewById(R.id.tableRow3);
			row.removeViewAt(0);
			row.setGravity(Gravity.RIGHT);*/
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_create_album, menu);
        return true;
    }
}
