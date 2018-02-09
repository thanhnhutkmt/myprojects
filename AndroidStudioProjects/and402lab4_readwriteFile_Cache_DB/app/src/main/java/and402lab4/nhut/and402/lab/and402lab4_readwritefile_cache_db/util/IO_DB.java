package and402lab4.nhut.and402.lab.and402lab4_readwritefile_cache_db.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Nhut on 7/21/2017.
 */

public class IO_DB {
    public static String read(String filePath, String filename) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(
                    new File(filePath + File.separator + filename)));
            char buffer[] = new char[1024*16];
            int num = 0;
            StringBuilder sb = new StringBuilder();
            while ((num = br.read(buffer, 0, buffer.length)) > 0) {
                char result[] = new char[num];
                for (int i = 0; i < num; i++) result[i] = buffer[i];
                sb.append(result);
            }
            br.close();
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            if (br != null) try {
                br.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return "";
        }
    }

    public static void write(String filePath, String filename, String fileContent) {
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(
                    new File(filePath + File.separator + filename)));
            bw.write(fileContent.toCharArray());
            bw.flush();
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
            if (bw != null) try {
                bw.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    public static boolean isExtDeviceOk(Context context) {
        return (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED));
    }

    public static void writeDB(String data, SQLiteDatabase db) {
        if (data.equals("##clear##")) db.delete(DBConnection.Table, null, null);
        else {
            ContentValues cv = new ContentValues();
            cv.put(DBConnection.IDCOL, System.currentTimeMillis());
            cv.put(DBConnection.DATACOL, data);
            db.insert(DBConnection.Table, null, cv);
        }
    }

    public static String readDB(SQLiteDatabase db) {
//        Cursor c = db.query(IO_DB.DBConnection.Table, null, IO_DB.DBConnection.DATACOL + " LIKE ? ",
//                new String[] {searchString}, null, null, null);
        Cursor c = db.query(DBConnection.Table, null, null, null, null, null, null);
        String data = "";
        if (c.moveToFirst())
            while (c.moveToNext()) {
                data += c.getString(c.getColumnIndex(DBConnection.IDCOL)) + " : " +
                    c.getString(c.getColumnIndex(DBConnection.DATACOL)) + "\n";
            }
        c.close();
        return data;
    }

    public static class DBConnection extends SQLiteOpenHelper {
        public static String DBName = "MyDB";
        public static String Table = "MyTable";
        public static String IDCOL = "id";
        public static String DATACOL = "Data";
        public static int version = 1;

        public DBConnection(Context context) {
            super(context, DBName, null, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("Create table " + Table + "("
                + IDCOL + " long primary key autoincrement,"
                + DATACOL + " nvarchar"
                + ");"
            );
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.i("MyTag", "newversion " + newVersion + ", oldversion " + oldVersion);
            if (newVersion == 1) { // regenerate db
                Log.i("MyTag", "onUpgrade");
                db.execSQL("Drop table if exists " + Table);
                onCreate(db);
            }
        }
    }
}
