package lab.and401.nhut.and401lab9_customcontentprovider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.util.HashMap;

/**
 * Created by Nhut on 6/29/2017.
 */

public class CustomContentProvider extends ContentProvider {
    private final int VERSION = 1;
    private final String DATABASE_NAME = "data";
    private final String TABLE_NAME = "Student";
    private static final String PROVIDER_NAME = "lab.and401.nhut.provider";
    //column name to map
    static final String ID = "id";
    static final String NAME = "name";
    static final String NICK_NAME = "nickname";
    static final Uri CONTENT_URI = Uri.parse("content://" + PROVIDER_NAME + "/nicknames");

    private static final int NICKNAME = 1;
    private static final int NICKNAME_ID = 2;

    private SQLiteDatabase database;
    private Context context;
    private static final UriMatcher mUriMatcher;

    private static HashMap<String, String> mNicknameMap;

    static {
        mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        mUriMatcher.addURI(PROVIDER_NAME, "nicknames", NICKNAME);
        mUriMatcher.addURI(PROVIDER_NAME, "nicknames /#", NICKNAME_ID);
    }

    private class DBconnector extends SQLiteOpenHelper {
        public DBconnector(Context context, String name,
                           SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table " + TABLE_NAME +
                    "(" + ID + " integer primary key autoincrement, " +
                    NAME + " text not null, " +
                    NICK_NAME + " text not null);");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("drop table if exists " + TABLE_NAME);
            onCreate(db);
        }
    }

    @Override
    public boolean onCreate() {
        database = new DBconnector(getContext(), DATABASE_NAME, null, VERSION).getWritableDatabase();
        return (database == null);
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection,
                        String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(TABLE_NAME);
        switch (mUriMatcher.match(uri)) {
            case NICKNAME:
                queryBuilder.setProjectionMap(mNicknameMap);
                break;
            case NICKNAME_ID:
                queryBuilder.appendWhere(ID + " =" + uri.getLastPathSegment());
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        if (sortOrder == null || sortOrder.trim().equals("")) {
            sortOrder = NAME;
        }
        Cursor cursor = queryBuilder.query(database, projection,
                selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        switch (mUriMatcher.match(uri)) {
            case NICKNAME:
                return "vnd.android.cursor.dir/vnd.example.nicknames";
            case NICKNAME_ID:
                return "vnd.android.cursor.item/vnd.example.nicknames";
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long row = database.insert(TABLE_NAME, "", values);
        if (row > 0) {
            Uri newUri = ContentUris.withAppendedId(CONTENT_URI, row);
            getContext().getContentResolver().notifyChange(newUri, null);
            return newUri;
        }
        throw new SQLException("Fail to add a new record into" + uri);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int count = 0;
        switch (mUriMatcher.match(uri)) {
            case NICKNAME:
                count = database.delete(TABLE_NAME, selection, selectionArgs);
                break;
            case NICKNAME_ID:
                count = database.delete(TABLE_NAME, ID + " =" + uri.getLastPathSegment()
                    + ((!TextUtils.isEmpty(selection)) ? "AND(" + selection + ")" : ""), selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int count = 0;
        switch(mUriMatcher.match(uri)) {
            case NICKNAME:
                count = database.update(TABLE_NAME, values, selection, selectionArgs);
                break;
            case NICKNAME_ID:
                count = database.update(TABLE_NAME, values, ID + " =" + uri.getLastPathSegment() +
                        ((!TextUtils.isEmpty(selection)) ? "AND(" + selection + ")" : ""), selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }
}
