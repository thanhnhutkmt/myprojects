package dhcl.ltdhk9.tuan.quanlybaihat;

/**
 * Created by ANH QUOC on 9/4/2016.
 */
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import android.os.Bundle;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
/**
 * class hiển thị thông tin Album và Spinner
 * và hiển thị thông tin bài hát vào ListView
 * đồng thời cho phép thao tác với bài hát
 */
public class InsertBaihatActivity extends Activity {

    SQLiteDatabase database=null;
    List<InforData>listBaihat=null;
    List<InforData>listAlbum=null;
    InforData albumData=null;
    MySimpleArrayAdapter adapter=null;
    int day,month,year;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_baihat);
        Spinner pinner=(Spinner) findViewById(R.id.spinner1);
        listAlbum=new ArrayList<InforData>();
        InforData d1=new InforData();
        d1.setField1("_");
        d1.setField2("Show All");
        d1.setField3("_");
        listAlbum.add(d1);
        //Lệnh xử lý đưa dữ liệu là Album vào Spinner
        database=openOrCreateDatabase("mydata.db", SQLiteDatabase.CREATE_IF_NECESSARY, null);
        if(database!=null)
        {

            Cursor cursor=database.query("tblAlbum", null, null, null, null, null, null);
            cursor.moveToFirst();
            while(cursor.isAfterLast()==false)
            {
                InforData d=new InforData();
                d.setField1(cursor.getInt(0));
                d.setField2(cursor.getString(1));
                d.setField3(cursor.getString(2));
                listAlbum.add(d);
                cursor.moveToNext();
            }
            cursor.close();
        }
        adapter=new MySimpleArrayAdapter(InsertBaihatActivity.this, R.layout.my_layout_for_show_list_data,listAlbum);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pinner.setAdapter(adapter);
        //Xử lý sự kiện chọn trong Spinner
        //chọn Album nào thì hiển thị toàn bộ bài hát của Album đó mà thôi
        //Nếu chọn All thì hiển thị toàn bộ không phân hiệt Album
        pinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub
                if(arg2==0)
                {
                    //Hiển thị bài hát trong CSDL
                    albumData=null;
                    loadAllListBaihat();
                }
                else
                {
                    //Hiển thị sách theo tác giả chọn trong Spinner
                    albumData=listAlbum.get(arg2);
                    loadListBaihatByAlbum(albumData.getField1().toString());
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
                albumData=null;
            }
        });

        setCurrentDateOnView();
        //lệnh xử lý DatePickerDialog
        Button bChangeDate=(Button) findViewById(R.id.buttonDate);
        bChangeDate.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                showDialog(113);
            }
        });
        //Lệnh xử lý thêm mới một sản phẩm theo tác giả đang chọn
        Button btnInsertBaihat =(Button) findViewById(R.id.buttonInsertBaihat);
        btnInsertBaihat.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(albumData==null)
                {
                    Toast.makeText(InsertBaihatActivity.this, "Please choose an Album to insert", Toast.LENGTH_LONG).show();
                    return;
                }
                EditText txtTitle=(EditText) findViewById(R.id.editTextTitle);
                ContentValues values=new ContentValues();
                values.put("title", txtTitle.getText().toString());
                Calendar c=Calendar.getInstance();
                c.set(year, month, day);
                SimpleDateFormat dfmt=new SimpleDateFormat("dd-MM-yyyy");
                values.put("dateadded",dfmt.format(c.getTime()));
                values.put("albumid", albumData.getField1().toString());
                long bId=database.insert("tblBaihat", null, values);
                if(bId>0)
                {
                    Toast.makeText(InsertBaihatActivity.this, "Insert Bài hát OK", Toast.LENGTH_LONG).show();
                    loadListBaihatByAlbum(albumData.getField1().toString());
                }
                else
                {
                    Toast.makeText(InsertBaihatActivity.this, "Insert Bài hát Failed", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    /*
     * Hàm hiển thị bài hát trong CSDL
     */
    public void loadAllListBaihat()
    {
        Cursor cur=database.query("tblBaihat", null, null, null, null, null, null);
        cur.moveToFirst();
        listBaihat=new ArrayList<InforData>();
        while(cur.isAfterLast()==false)
        {
            InforData d=new InforData();
            d.setField1(cur.getInt(0));
            d.setField2(cur.getString(1));
            d.setField3(cur.getString(2));
            listBaihat.add(d);
            cur.moveToNext();
        }
        cur.close();
        adapter=new MySimpleArrayAdapter(InsertBaihatActivity.this, R.layout.my_layout_for_show_list_data, listBaihat);
        ListView lv=(ListView) findViewById(R.id.listViewBook);
        lv.setAdapter(adapter);
    }
    /**
     * hàm hiển thị bài hát theo Album
     */
    public void loadListBaiHatByAlbum(String authorid)
    {
        Cursor cur=database.query("tblBaihat", null, "albumid=?", new String[]{albumid}, null, null, null);
        cur.moveToFirst();
        listBaihat=new ArrayList<InforData>();
        while(cur.isAfterLast()==false)
        {
            InforData d=new InforData();
            d.setField1(cur.getInt(0));
            d.setField2(cur.getString(1));
            d.setField3(cur.getString(2));
            listBaihat.add(d);
            cur.moveToNext();
        }
        cur.close();
        adapter=new MySimpleArrayAdapter(InsertBaihatActivity.this, R.layout.my_layout_for_show_list_data, listBaihat);
        ListView lv=(ListView) findViewById(R.id.listViewBook);
        lv.setAdapter(adapter);
    }
    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if(id==113)
        {
            return new DatePickerDialog(this, dateChange, year, month, day);
        }
        return null;
    }
    /**
     * xử lý DatePickerDialog
     */
    private DatePickerDialog.OnDateSetListener dateChange= new OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year1, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            year=year1;
            month=monthOfYear;
            day=dayOfMonth;
            EditText eDate=(EditText) findViewById(R.id.editTextDate);
            eDate.setText(day+"-"+(month+1)+"-"+year);
        }
    };
    /**
     * thiết lập ngày tháng năm hiện tại
     */
    public void setCurrentDateOnView()
    {
        EditText eDate=(EditText) findViewById(R.id.editTextDate);
        Calendar cal=Calendar.getInstance();
        day=cal.get(Calendar.DAY_OF_MONTH);
        month=cal.get(Calendar.MONTH);
        year=cal.get(Calendar.YEAR);
        eDate.setText(day+"-"+(month+1)+"-"+year);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_insert_book, menu);
        return true;
    }
}
