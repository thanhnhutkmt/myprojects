package lab.and401.nhut.and401lab9_ormlitelib;

import android.content.Context;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

/**
 * Created by Nhut on 6/29/2017.
 */

public class MyMiniDB extends OrmLiteSqliteOpenHelper {
    private static final String DATABASE_NAME = "bank";
    private static final int DATABASE_VERSION = 1;
    private Context mContext;
    private Dao<Person, Long> mSimpleDao = null;

    public MyMiniDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.mContext = context;
    }

    public Dao<Person, Long> getDao() throws SQLException {
        if (mSimpleDao == null) mSimpleDao = getDao(Person.class);
        return mSimpleDao;
    }

    public List<Person> getData() {
        MyMiniDB db = new MyMiniDB(mContext);
        Dao<Person, Long> dao = null;
        try {
            dao = db.getDao();
            return dao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public int addData(Person person) {
        Dao<Person, Long> dao = null;
        try {
            dao = getDao();
            return dao.create(person);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public void deleteAll() {
        try {
            Dao<Person ,Long> dao = getDao();
            List<Person> list = dao.queryForAll();
            dao.delete(list);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Person.class);
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, Person.class, true);
            onCreate(database, connectionSource);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}
