package dhcl.ltdhk9.tuan.quanlybaihat;

import java.util.Locale;

import android.os.Bundle;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
/**
 * hàm hình chính cho phép chọn các thao tác
 * Album
 *
 */
public class MainActivity extends Activity {

    Button btnCreateDatabase=null;
    Button btnInsertAlbum=null;
    Button btnShowAlbumList=null;
    Button getBtnShowAlbumList2=null;
    Button btnTransaction=null;
    Button btnShowDetail=null;
    Button btnInsertBaihat=null;
    public static final int OPEN_AUTHOR_DIALOG=1;
    public static final int SEND_DATA_FROM_AUTHOR_ACTIVITY=2;
    SQLiteDatabase database=null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnInsertAlbum=(Button) findViewById(R.id.btnInsertAlbum);
        btnInsertAlbum.setOnClickListener(new MyEvent());
        btnShowAlbumList=(Button) findViewById(R.id.buttonShowAlbumList);
        btnShowAlbumList.setOnClickListener(new MyEvent());
        btnInsertBaihat=(Button) findViewById(R.id.buttonInsertBaihat);
        btnInsertBaihat.setOnClickListener(new MyEvent());
        getDatabase();
    }
    /**
     * hàm kiểm tra xem bảng có tồn tại trong CSDL?
     * @param database - cơ sở dữ liệu
     * @param tableName - tên bảng cần kiểm tra
     * @return trả về true nếu tồn tại
     */
    public boolean isTableExists(SQLiteDatabase database, String tableName) {
        Cursor cursor = database.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '"+tableName+"'", null);
        if(cursor!=null) {
            if(cursor.getCount()>0) {
                cursor.close();
                return true;
            }
            cursor.close();
        }
        return false;
    }
    /**
     * hàm tạo CSDL và các bảng liên quan
     * @return
     */
    public SQLiteDatabase getDatabase()
    {
        try
        {
            database=openOrCreateDatabase("mydata.db", SQLiteDatabase.CREATE_IF_NECESSARY, null);
            if(database!=null)
            {
                if(isTableExists(database,"Album"))
                    return database;
                database.setLocale(Locale.getDefault());
                database.setVersion(1);
                String sqlAlbum="create table Album ("
                        +"id integer primary key autoincrement,"
                        +"maalbum text, "
                        +"tenalbum text)";
                database.execSQL(sqlAlbum);
                String sqlBaihat="create table tblBaihat ("
                        +"id integer primary key autoincrement,"
                        +"title text, "
                        +"dateadded date,"
                        +"albumid integer not null constraint authorid references tblAuthors(id) on delete cascade)";
                database.execSQL(sqlBaihat);
                //Cách tạo trigger khi nhập dữ liệu sai ràng buộc quan hệ
                String sqlTrigger="create trigger fk_insert_baihat before insert on tblBaihat "
                        +" for each row "
                        +" begin "
                        +" 	select raise(rollback,'them du lieu tren bang tblBaihat bi sai') "
                        +" 	where (select id from tblAlbum where id=new.albumid) is null ;"
                        +" end;";
                database.execSQL(sqlTrigger);
                Toast.makeText(MainActivity.this, "OK OK", Toast.LENGTH_LONG).show();
            }
        }
        catch(Exception e)
        {
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
        }
        return database;
    }
    public void createDatabaseAndTrigger()
    {
        if(database==null)
        {
            getDatabase();
            Toast.makeText(MainActivity.this, "OK OK", Toast.LENGTH_LONG).show();
        }
    }
    /**
     * hàm mở màn hình nhập Tác giả
     */
    public void showInsertAlbumDialog()
    {
        Intent intent=new Intent(MainActivity.this, CreateAlbumActivity.class);
        startActivityForResult(intent, OPEN_AUTHOR_DIALOG);
    }
    /**
     * hàm xem danh sách Album dùng Activity
     */
    public void showAlbumList1()
    {
        Intent intent=new Intent(MainActivity.this, ShowListAlbumActivity.class);
        startActivity(intent);
    }
    /**
     * hàm xem danh sách Album dùng ListActivity
     */
    public void showAlbumList2()
    {
        Intent intent=new Intent(MainActivity.this, ShowListAlbumActivity2.class);
        startActivity(intent);
    }

    public void interactDBWithTransaction()
    {
        if(database!=null)
        {
            database.beginTransaction();
            try
            {
                //làm cái gì đó tùm lum ở đây,
                //chỉ cần có lỗi sảy ra thì sẽ kết thúc transaction
                ContentValues values=new ContentValues();
                values.put("maalbum", "xx");
                values.put("tenalbum", "yyy");
                database.insert("tblAlbum", null, values);
                database.delete("tblAlbum", "ma=?", new String[]{"x"});
                //Khi nào hàm này được gọi thì các thao tác bên trên mới thực hiện được
                //Nếu nó không được gọi thì mọi thao tác bên trên đều bị hủy
                database.setTransactionSuccessful();
            }
            catch(Exception ex)
            {
                Toast.makeText(MainActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
            }
            finally
            {
                database.endTransaction();
            }
        }
    }
    /**
     * hàm xử lý kết quả trả về
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==SEND_DATA_FROM_AUTHOR_ACTIVITY)
        {
            Bundle bundle= data.getBundleExtra("DATA_ALBUM");
            String maalbum=bundle.getString("maalbum");
            String tenalbum=bundle.getString("tenalbum");
            ContentValues content=new ContentValues();
            content.put("maalbum", maalbum);
            content.put("tenalbum", tenalbum);
            if(database!=null)
            {
                long authorid=database.insert("tblAlbum", null, content);
                if(authorid==-1)
                {
                    Toast.makeText(MainActivity.this,authorid+" - "+ maalbum +" - "+tenalbum +" ==> insert error!", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(MainActivity.this, authorid+" - "+maalbum +" - "+tenalbum +" ==>insert OK!", Toast.LENGTH_LONG).show();
                }
            }

        }
    }
    /**
     * class xử lý sự kiện
     * @author drthanh
     *
     */
    private class MyEvent implements OnClickListener
    {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            if(v.getId()==R.id.btnInsertAuthor)
            {
                showInsertAlbumDialog();
            }
            else if(v.getId()==R.id.buttonShowAuthorList)
            {
                showAlbumList1();
            }

            else if(v.getId()==R.id.buttonInsertBaihat)
            {
                Intent intent=new Intent(MainActivity.this, InsertBaihatActivity.class);
                startActivity(intent);
            }
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_simple_database_main, menu);
        return true;
    }
}
