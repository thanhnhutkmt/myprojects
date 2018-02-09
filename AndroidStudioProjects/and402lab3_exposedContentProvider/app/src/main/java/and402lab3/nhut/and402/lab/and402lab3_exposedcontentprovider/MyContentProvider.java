package and402lab3.nhut.and402.lab.and402lab3_exposedcontentprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;

public class MyContentProvider extends ContentProvider {
    private SQLiteDatabase db;
    public static String authorities = "and402lab3.nhut.and402.lab.and402lab3_exposedcontentprovider";

    public MyContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        if (uri.getAuthority().equals(authorities)) {
            Log.i("MyTag", "deleteed ");
            return db.delete(DBconnection.TableName, selection, selectionArgs);
        }
        return -1;
    }

    @Override
    public String getType(Uri uri) {
        return "vnd.android.cursor.item";
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        if (uri.getAuthority().equals(authorities)) {
            long n = db.insert(DBconnection.TableName, null, values);
            Log.i("MyTag", "Inserted " + n);
        }
        return uri;
    }

    @Override
    public boolean onCreate() {
        Log.i("MyTag", "cp onCreate ");
        db = new DBconnection(getContext()).getWritableDatabase();
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        if (uri.getAuthority().equals(authorities)) {
            Log.i("MyTag", "query()");
            return db.query(DBconnection.TableName, null, selection,
                    selectionArgs, null, null, sortOrder);
        }
        return null;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        if (uri.getAuthority().equals(authorities)) {
            Log.i("MyTag", "update()");
            return db.update(DBconnection.TableName, values, selection, selectionArgs);
        }
        return -1;
    }

    public static class DBconnection extends SQLiteOpenHelper {
        private static String DBname = "MyDB";
        private static int version = 3;
        private static String TableName = "MYTABLE";
        public static String DBColidname = "id";
        public static String DBColmsgname = "message";

        public DBconnection(Context context) {
            super(context, DBname, null, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE " + TableName + "("
                    + DBColidname +" INT AUTO_INCREMENT PRIMARY KEY,"
                    + DBColmsgname + " NVARCHAR"
                    + ");"
            );
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            if (newVersion > oldVersion) {
                db.execSQL("DROP TABLE IF EXISTS " + TableName);
                onCreate(db);
            }
        }
    }
}
