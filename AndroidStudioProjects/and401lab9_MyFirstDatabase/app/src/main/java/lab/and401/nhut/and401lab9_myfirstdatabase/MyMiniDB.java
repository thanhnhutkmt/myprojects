package lab.and401.nhut.and401lab9_myfirstdatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nhut on 6/28/2017.
 */

public class MyMiniDB {
    private DBconnector connector;
    private final String DATABASE_NAME = "data";
    private SQLiteDatabase database;
    private Context context;
    private final int VERSION = 1;
    private final String TABLE_NAME = "Student";
    public MyMiniDB(Context context) {
        this.context = context;
        this.connector = new DBconnector(context, DATABASE_NAME, null, 1);
    }

    public void open() {
        database = connector.getWritableDatabase();
    }

    public boolean insertStudent(String name, int faculty) {
        ContentValues studentInfo = new ContentValues();
        studentInfo.put("name", name);
        studentInfo.put("faculty", faculty);
        return (database.insert(TABLE_NAME, null, studentInfo) != -1);
    }

    public List<String> getListStudent() {
        List<String> list = new ArrayList<String>();
        Cursor cursor = database.query(TABLE_NAME, null, null, null, null, null, null);
        if (cursor != null && cursor.moveToFirst())
        do {
            list.add(cursor.getString(1));
        } while(cursor.moveToNext());
        return list;
    }

    public boolean cleanAllEngineeringStudent() {
        return (database.delete(TABLE_NAME, null, null)> -1);
    }

    private class DBconnector extends SQLiteOpenHelper {
        public DBconnector(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table " + TABLE_NAME + "(id integer primary key autoincrement, name text, faculty integer)");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("drop table if exists " + TABLE_NAME);
            onCreate(db);
        }
    }
}
