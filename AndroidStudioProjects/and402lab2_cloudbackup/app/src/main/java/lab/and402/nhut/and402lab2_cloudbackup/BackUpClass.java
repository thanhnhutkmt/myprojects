package lab.and402.nhut.and402lab2_cloudbackup;

import android.app.backup.BackupAgentHelper;
import android.app.backup.BackupDataInput;
import android.app.backup.BackupDataOutput;
import android.app.backup.FileBackupHelper;
import android.app.backup.SharedPreferencesBackupHelper;
import android.os.ParcelFileDescriptor;
import android.util.Log;

import java.io.File;
import java.io.IOException;

/**
 * Created by Nhut on 7/20/2017.
 */

public class BackUpClass extends BackupAgentHelper {
    public static String BACKUP_STRING = "bkString";
    public static String BACKUP_FILE = "bkFile";
    public static String MY_PREF_NAME = "my_pref";
    public static String FILENAME_ARRAY[] = {"file1.bk", "file2.bk"};

    @Override
    public void onCreate() {
        addHelper(BACKUP_STRING, new SharedPreferencesBackupHelper(this, MY_PREF_NAME));
        addHelper(BACKUP_FILE, new FileBackupHelper(this, FILENAME_ARRAY));
    }
}


