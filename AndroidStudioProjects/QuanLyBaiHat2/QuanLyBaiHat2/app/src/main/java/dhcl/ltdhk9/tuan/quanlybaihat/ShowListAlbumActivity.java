package dhcl.ltdhk9.tuan.quanlybaihat;

/**
 * Created by ANH QUOC on 9/4/2016.
 */
import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
/**
 * class xem danh sách tác giả
 * @author drthanh
 *
 */
public class ShowListAlbumActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_show_data);
        updateUI();
        Button btn=(Button) findViewById(R.id.buttonBack);
        btn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                ShowListAlbumActivity.this.finish();
            }
        });
    }
    List<InforData>list=new ArrayList<InforData>();
    InforData dataClick=null;
    SQLiteDatabase database=null;
    MySimpleArrayAdapter adapter=null;
    public void updateUI()
    {
        database=openOrCreateDatabase("mydata.db", SQLiteDatabase.CREATE_IF_NECESSARY, null);
        if(database!=null)
        {

            Cursor cursor=database.query("tblAlbum", null, null, null, null, null, null);
            startManagingCursor(cursor);
            InforData header=new InforData();
            header.setField1("STT");
            header.setField2("Mã Album");
            header.setField3("Tên Album");
            list.add(header);
            cursor.moveToFirst();
            while(!cursor.isAfterLast())
            {
                InforData data=new InforData();
                data.setField1(cursor.getInt(0));
                data.setField2(cursor.getString(1));
                data.setField3(cursor.getString(2));
                list.add(data);
                cursor.moveToNext();
            }
            cursor.close();
            adapter=new MySimpleArrayAdapter(ShowListAlbumActivity.this, R.layout.my_layout_for_show_list_data, list);
            final ListView lv= (ListView) findViewById(R.id.listViewShowData);
            lv.setAdapter(adapter);
            lv.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1,
                                        int arg2, long arg3) {
                    // TODO Auto-generated method stub
                    Toast.makeText(ShowListAlbumActivity.this,"View -->"+ list.get(arg2).toString(), Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(ShowListAlbumActivity.this, CreateAlbumActivity.class);
                    Bundle bundle=new Bundle();
                    bundle.putInt("KEY", 1);
                    bundle.putString("getField1", list.get(arg2).getField1().toString());
                    bundle.putString("getField2", list.get(arg2).getField2().toString());
                    bundle.putString("getField3", list.get(arg2).getField3().toString());
                    intent.putExtra("DATA", bundle);
                    dataClick=list.get(arg2);
                    startActivityForResult(intent, MainActivity.OPEN_AUTHOR_DIALOG);
                }
            });
            lv.setOnItemLongClickListener(new OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                               int arg2, long arg3) {
                    // TODO Auto-generated method stub
                    final InforData data=list.get(arg2);
                    final int pos=arg2;
                    Toast.makeText(ShowListAlbumActivity.this, "Edit-->"+data.toString(), Toast.LENGTH_LONG).show();
                    AlertDialog.Builder b=new Builder(ShowListAlbumActivity.this);
                    b.setTitle("Remove");
                    b.setMessage("Xóa ["+data.getField2() +" - "+data.getField3() +"] hả?");
                    b.setPositiveButton("Có", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // TODO Auto-generated method stub
                            int n=database.delete("tblalbum", "id=?", new String[]{data.getField1().toString()});
                            if(n>0)
                            {
                                Toast.makeText(ShowListAlbumActivity.this, "Remove ok", Toast.LENGTH_LONG).show();
                                list.remove(pos);
                                adapter.notifyDataSetChanged();
                            }
                            else
                            {
                                Toast.makeText(ShowListAlbumActivity.this, "Remove not ok", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                    b.setNegativeButton("Không", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // TODO Auto-generated method stub
                            dialog.cancel();
                        }
                    });
                    b.show();
                    return false;
                }
            });
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==MainActivity.SEND_DATA_FROM_AUTHOR_ACTIVITY)
        {
            Bundle bundle=data.getBundleExtra("DATA_ALBUM");
            String f2=bundle.getString("maalbum");
            String f3=bundle.getString("tenalbum");
            String f1=dataClick.getField1().toString();
            ContentValues values=new ContentValues();
            values.put("maalbum", f2);
            values.put("tenalbum", f3);
            if(database!=null)
            {
                int n=database.update("tblAlbum", values, "id=?", new String[]{f1});
                if(n>0)
                {
                    Toast.makeText(ShowListAlbumActivity.this, "update ok ok ok ", Toast.LENGTH_LONG).show();
                    dataClick.setField2(f2);
                    dataClick.setField3(f3);
                    if(adapter!=null)
                        adapter.notifyDataSetChanged();
                }
            }
        }
    }
}
