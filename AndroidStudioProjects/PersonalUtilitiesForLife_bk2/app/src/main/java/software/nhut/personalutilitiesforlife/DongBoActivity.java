package software.nhut.personalutilitiesforlife;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Environment;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveApi;
import com.google.android.gms.drive.DriveContents;
import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.drive.DriveFolder;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.MetadataChangeSet;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import software.nhut.personalutilitiesforlife.constant.AppConstant;
import software.nhut.personalutilitiesforlife.layout.CircleMenuLayout;
import util.InputData;
import util.MyAssetsAndPreferences;
import util.MyCheckSum;
import util.MyCipher;
import util.MyDateTime;
import util.MyDialog;
import util.MyFileIO;
import util.MyMedia;
import util.MyPhone;
import util.MyZip;

public class DongBoActivity extends Activity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private CircleMenuLayout mCircleMenuLayout;

    private String[] mItemTexts;
    private int[] mItemImgs = new int[] {R.drawable.changepassword,
            R.drawable.export_data, R.drawable.output_rar,
            R.drawable.googledrive, R.drawable.wifiexport};
//            R.drawable.home_mbank_6_normal };

    private String[] dataPaths = new String[4];
    String currentPath;
    String syncFolder;
    GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Drive.API)
                .addScope(Drive.SCOPE_FILE)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        setContentView(R.layout.activity_sync);
        currentPath = getApplicationInfo().dataDir + File.separator;
        syncFolder = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + AppConstant.SYNC;
        dataPaths[0] = currentPath + AppConstant.THUMUC_QUANLY_CUOCHEN;
        dataPaths[1] = currentPath + AppConstant.THUMUC_QUANLY_TINNHANDANHBA;
        dataPaths[2] = currentPath + AppConstant.THUMUC_BANDO;
        dataPaths[3] = currentPath + "databases";

        mItemTexts = new String[mItemImgs.length];
        for (int i = 0; i < mItemTexts.length; i++) mItemTexts[i] = "";

        mCircleMenuLayout = (CircleMenuLayout) findViewById(R.id.id_menulayout);
        mCircleMenuLayout.setMenuItemIconsAndTexts(mItemImgs, mItemTexts);
        mCircleMenuLayout.setOnMenuItemClickListener(new CircleMenuLayout.OnMenuItemClickListener() {
            @Override
            public void itemClick(View view, int pos) {
                if (pos == 0) {
                    changePassword();
                } else if (pos == 1) {
                    exportWithEncrypted();
                } else if (pos == 2) {
                    exportWithZip(syncFolder);
                } else if (pos == 3) {
                    uploadToMEGASync();
                } else if (pos == 4) {
                    exportByWifi();
                }
            }

            @Override
            public void itemCenterClick(View view) {

            }
        });
    }

    private void exportByWifi() {
        if (MyPhone.isWifiOn(this))
            MyDialog.showExportWifiDialog(this);
        else
            Toast.makeText(this, R.string.Toast_Dongboactivity_uploadwifi_nowifi, Toast.LENGTH_LONG).show();
    }

    private void uploadToMEGASync() {
        if (MyPhone.isInternetOn(this))
            mGoogleApiClient.connect();
        else
            Toast.makeText(this, R.string.Toast_Dongboactivity_uploadGG_nointernet, Toast.LENGTH_LONG).show();
    }

    private void exportWithZip(final String outputPath) {
        final EditText txtPass = new EditText(this);
        txtPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        MyDialog.showNewPasswordDiaLog(DongBoActivity.this, new View[]{txtPass}, new InputData() {
            @Override public void inputData(DialogInterface dialog) {
                boolean zipError = false, zipPassError = false, noPass = false;
                String pass = txtPass.getText().toString();
                noPass = (pass.trim().length() == 0);
                try {
                    MyZip.zipDir(getApplicationInfo().dataDir, outputPath + File.separator + "data.zip");
                } catch (IOException e) {
                    e.printStackTrace(); zipError = true;
                }
//                try {
//                    String pass = txtPass.getText().toString();
//                    if (pass.trim().length() == 0) MyZip.zipDir(getApplicationInfo().dataDir, syncFolder + File.separator + "data.zip");
//                    else MyZip.zipDirWithPassword(getApplicationInfo().dataDir, syncFolder + File.separator + "data1.zip", pass);
                // compress with pass generate damaged zip file almost all times
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } finally {
//                    dialog.dismiss();
//                }
//                np ze  zpe   result
//                0  0   0     show ok, zip pass
//                0  0   1     show failed
//                0  1   0     show failed
//                0  1   1     show failed
//                1  0   0     show ok
//                1  0   1     show ok
//                1  1   0     show failed
//                1  1   1     show failed
//
//                np ze  zpe   result
//                0  0   0     show ok, zip pass
//                0  0   1     show failed
//                0  1   x     show failed
//                1  0   x     show ok
//                1  1   x     show failed
                if (!noPass && !zipError) {
                    try {
                        ZipFile zipFile = new ZipFile(outputPath + File.separator + "datawithpass.zip");
                        ZipParameters parameters = new ZipParameters();
                        parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE); // set compression method to deflate compression
                        parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
                        parameters.setEncryptFiles(true);
                        parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_AES);
                        parameters.setAesKeyStrength(Zip4jConstants.AES_STRENGTH_256);
                        parameters.setPassword(pass);
                        zipFile.addFile(new File(outputPath + File.separator + "data.zip"), parameters);
                    } catch (ZipException e) {
                        e.printStackTrace(); zipPassError = true;
                    } finally {
                        new File(outputPath + File.separator + "data.zip").delete();
                    }
                }
                if ((!zipError && !zipPassError) || (noPass && !zipError))
                    Toast.makeText(DongBoActivity.this, R.string.Toast_Dongboactivity_finish_exportzipfile_ok, Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(DongBoActivity.this, R.string.Toast_Dongboactivity_finish_exportzipfile_falied, Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
            @Override public void inputData(String s) {}
            @Override public void inputData(String... s) {}
            @Override public void inputData(List<String> s) {}
            @Override public void inputData(String s, int color) {}
        }, false);
    }

    private void changePassword() {
        final EditText txtPass[] = new EditText[3];
        for (int i = 0; i < txtPass.length; i++) {
            txtPass[i] = new EditText(DongBoActivity.this);
            txtPass[i].setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }
        MyDialog.showChangePasswordDiaLog(DongBoActivity.this, txtPass, new InputData() {
            @Override public void inputData(String s) {} @Override public void inputData(String s, int color) {}
            @Override public void inputData(String... s) {} @Override public void inputData(List<String> s) {}
            @Override public void inputData(DialogInterface dialog) {
                if (txtPass[0].getText().toString().equals
                        (MyAssetsAndPreferences.getStringFromPreferences(DongBoActivity.this, AppConstant.PASSWORD))) {
                    if (txtPass[1].getText().toString().equals(txtPass[2].getText().toString())) {
                        MyAssetsAndPreferences.saveToPreferences(DongBoActivity.this, AppConstant.PASSWORD, txtPass[1].getText().toString());
                        dialog.dismiss();
                        Toast.makeText(DongBoActivity.this, R.string.Toast_xacthucpassword_changepassok, Toast.LENGTH_SHORT).show();
                    } else Toast.makeText(DongBoActivity.this, R.string.Toast_xacthucpassword_notduplicated, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(DongBoActivity.this, R.string.Toast_xacthucpassword_wrongpass, Toast.LENGTH_SHORT).show();
                    txtPass[0].setText("");
                }
            }
        });
    }

    public void exportWithEncrypted(){
        MyFileIO.clearFolderContent(currentPath + "tempp", true);
        String key = "some keys";
        String outputPath = syncFolder + File.separator + "ewe";
        MyFileIO.makeFolder(outputPath);
        FilenameFilter fnf = new FilenameFilter() {
            @Override public boolean accept(File dir, String filename) {
                return (!dir.getName().equals("databases") || filename.endsWith(".sqlite"));
            }
        };
        StringBuilder sb = new StringBuilder();
        for (String s : dataPaths) {
            File fc[] = new File(s).listFiles(fnf);
            if (fc != null)
                for (File f : fc) {
                    MyCipher.encryptFile(key, outputPath + File.separator + f.getName() + ".e", f.getAbsolutePath());
                    sb.append(MyCheckSum.calculateMD5(f) + "\n");
//                    MyCipher.decryptFile(key, outputPath + File.separator + f.getName() + ".d", outputPath + File.separator + f.getName() + ".e");
//                    boolean result = MyCheckSum.compareFile(new File(f.getAbsolutePath()), new File(outputPath + File.separator + f.getName() + ".d"));
//                    Log.i("MyTag", f.getName() + " encrypt -> decrypt -> idential " + ((result) ? "OK" : "NO"));
                }
        }
        try {
            byte temp[] = MyCipher.getKey(key);
            String t = Arrays.toString(temp);
            sb.append(t);
        } catch (Exception e) {
            e.printStackTrace();
        }
        MyFileIO.writeStringFile(outputPath + File.separator + "listmd5.ls", sb.toString(), false);
        Toast.makeText(DongBoActivity.this, R.string.Toast_encryptedfinish_dongboact, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        MetadataChangeSet changeSet = new MetadataChangeSet.Builder()
                .setTitle(AppConstant.GOOGLE_DRIVE_BKFOLDER + MyDateTime.getDateString
                        (System.currentTimeMillis(), AppConstant.FULLTIMEFORMATWITHSECOND_WITHOUTNEWLINE)).build();
        Drive.DriveApi.getRootFolder(mGoogleApiClient).createFolder(
                mGoogleApiClient, changeSet).setResultCallback(new ResultCallback<DriveFolder.DriveFolderResult>() {
            @Override
            public void onResult(@NonNull DriveFolder.DriveFolderResult driveFolderResult) {
                if (!driveFolderResult.getStatus().isSuccess()) Log.i("MyTag", "Error while trying to create the folder");
                else {
                    final DriveId folderID = driveFolderResult.getDriveFolder().getDriveId();
                    Log.i("MyTag", "Created a folder: " + folderID);
                    // create file
                    // copy content
                    // done
                    Drive.DriveApi.newDriveContents(mGoogleApiClient).setResultCallback(new ResultCallback<DriveApi.DriveContentsResult>() {
                        @Override
                        public void onResult(@NonNull DriveApi.DriveContentsResult driveContentsResult) {
                            DriveFolder folder = folderID.asDriveFolder();
                            MetadataChangeSet changeSet = new MetadataChangeSet.Builder()
                                    .setTitle("datawithpass.zip")
                                    .setMimeType("application/zip")
                                    .setStarred(true).build();
                            folder.createFile(mGoogleApiClient, changeSet, driveContentsResult.getDriveContents())
                                    .setResultCallback(new ResultCallback<DriveFolder.DriveFileResult>() {
                                @Override
                                public void onResult(@NonNull DriveFolder.DriveFileResult driveFileResult) {
                                    if (!driveFileResult.getStatus().isSuccess()) Log.i("MyTag", "Error while trying to create the file");
                                    else {
                                        DriveFile file = driveFileResult.getDriveFile();
                                        Log.i("MyTag", "Created a file: " + file.getDriveId());
                                        file.open(mGoogleApiClient, DriveFile.MODE_WRITE_ONLY, null).setResultCallback(new ResultCallback<DriveApi.DriveContentsResult>() {
                                            @Override
                                            public void onResult(DriveApi.DriveContentsResult result) {
                                                if (!result.getStatus().isSuccess()) Log.i("MyTag", "Error while trying to open the file");
                                                else {
                                                    DriveContents driveContents = result.getDriveContents();
                                                    Log.i("MyTag", "Opening the file : " + driveContents.getDriveId());
                                                    try {
                                                        FileOutputStream writer = new FileOutputStream(driveContents.getParcelFileDescriptor().getFileDescriptor());
                                                        writer.write(MyFileIO.readByteFile(new File(syncFolder + File.separator + "datawithpass.zip")));
                                                        writer.flush(); writer.close();
                                                        Toast.makeText(DongBoActivity.this, R.string.Toast_uploadfinish_dongboact, Toast.LENGTH_LONG).show();
                                                    } catch (IOException e) {
                                                        e.printStackTrace();
                                                        Toast.makeText(DongBoActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                                    }
                                                    driveContents.commit(mGoogleApiClient, null).setResultCallback(new ResultCallback<Status>() {
                                                        @Override
                                                        public void onResult(Status result) {
                                                            if (!result.getStatus().isSuccess()) Log.i("MyTag", "Error while trying to commit the file to server");
                                                            else Log.i("MyTag", "Commit successfully");
                                                        }
                                                    });
                                                }
                                            }
                                        });
                                    }
                                }
                            });
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    private static final int RESOLVE_CONNECTION_REQUEST_CODE = 100;
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        if (connectionResult.hasResolution()) {
            try {
                connectionResult.startResolutionForResult(this, RESOLVE_CONNECTION_REQUEST_CODE);
            } catch (IntentSender.SendIntentException e) {
                // Unable to resolve, message user appropriately
            }
        } else {
            GooglePlayServicesUtil.getErrorDialog(connectionResult.getErrorCode(), this, 0).show();
        }

    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        switch (requestCode) {
            case RESOLVE_CONNECTION_REQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    mGoogleApiClient.connect();
                }
                break;
        }
    }
}
