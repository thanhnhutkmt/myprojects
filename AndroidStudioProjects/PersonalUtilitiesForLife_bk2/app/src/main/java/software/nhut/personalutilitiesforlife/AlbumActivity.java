package software.nhut.personalutilitiesforlife;

import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;
import android.widget.ViewFlipper;

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
import com.google.android.gms.drive.Metadata;
import com.google.android.gms.drive.MetadataBuffer;
import com.google.android.gms.drive.MetadataChangeSet;
import com.google.android.gms.drive.query.Filters;
import com.google.android.gms.drive.query.Query;
import com.google.android.gms.drive.query.SearchableField;
import com.google.android.gms.drive.query.SortOrder;
import com.google.android.gms.drive.query.SortableField;
import com.google.android.gms.drive.widget.DataBufferAdapter;
import com.google.android.gms.vision.text.Line;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import software.nhut.personalutilitiesforlife.adapter.AdapterShelfImage;
import software.nhut.personalutilitiesforlife.constant.AppConstant;
import software.nhut.personalutilitiesforlife.data.AlbumBackupItem;
import util.InputData;
import util.MyData;
import util.MyDateTime;
import util.MyDialog;
import util.MyFileIO;
import util.MyZip;

public class AlbumActivity extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private List<ImageView> list;
    private AdapterShelfImage adapterShelfImage;
    private final int IMAGE_PER_SCREEN = 15;
    private final int CACHED_SHELF = 4;
    private LinearLayout back, next;
    private File albumFolder;
    private GoogleApiClient mGoogleApiClient;
    private ImageView imv_prev, imv_next;

    private float x1,x2;
    private final int length = 140;

    private NotificationCompat.Builder mBuilder;
    private NotificationManager mNotifyManager;
    private boolean blink = false;
    private int id = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);
        mNotifyManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        list = new ArrayList<>();
        list.add((ImageView) findViewById(R.id.imv1));
        list.add((ImageView) findViewById(R.id.imv2));
        list.add((ImageView) findViewById(R.id.imv3));
        list.add((ImageView) findViewById(R.id.imv4));
        list.add((ImageView) findViewById(R.id.imv5));
        list.add((ImageView) findViewById(R.id.imv6));
        list.add((ImageView) findViewById(R.id.imv7));
        list.add((ImageView) findViewById(R.id.imv8));
        list.add((ImageView) findViewById(R.id.imv9));
        list.add((ImageView) findViewById(R.id.imv10));
        list.add((ImageView) findViewById(R.id.imv11));
        list.add((ImageView) findViewById(R.id.imv12));
        list.add((ImageView) findViewById(R.id.imv13));
        list.add((ImageView) findViewById(R.id.imv14));
        list.add((ImageView) findViewById(R.id.imv15));
        imv_next = (ImageView) findViewById(R.id.album_imv_next);
        imv_prev = (ImageView) findViewById(R.id.album_imv_prev);

        for (ImageView imv : list) imv.setOnClickListener(this);
        imv_next.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                adapterShelfImage.next();
            }
        });
        imv_prev.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                adapterShelfImage.previous();
            }
        });

        LinearLayout ll = (LinearLayout) findViewById(R.id.activity_album);
        ll.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.i("MyTag", "action " + event.getAction());
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    x1 = event.getX();
                    Log.i("MyTag", "x1 " + x1);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    x2 = event.getX();
                    Log.i("MyTag", "x2 " + x2);
                    if (x2 > length + x1) {
                        adapterShelfImage.previous();
                    } else if (x1 > length + x2) {
                        adapterShelfImage.next();
                    }
                }
                return false;
            }
        });

        mGoogleApiClient = new GoogleApiClient.Builder(this)
            .addApi(Drive.API)
            .addScope(Drive.SCOPE_FILE)
            .addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this)
            .build();

        selectMediaHouse(false);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        Intent intent = new Intent(this, SlideActivity.class);
        for (int i = 0; i < list.size(); i++)
            if (id == list.get(i).getId()) {
                try {
                    intent.putExtra(AppConstant.ALBUM_PATHSTRING, adapterShelfImage.getImagePath(i));
                    startActivity(intent);
                } catch(ArrayIndexOutOfBoundsException ex) {
                    Log.i("MyTag", "This frame is empty so no image to load.");
                }
                break;
            }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_album, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        if (i == R.id.mnureload) {
            reload();
        } else if (i == R.id.mnualbuminfo) {
            showFolderInfo(albumFolder);
        } else if (i == R.id.mnualbum_size) {
            adapterShelfImage.sort(AdapterShelfImage.COMPARESIZE);
        } else if (i == R.id.mnualbum_date) {
            adapterShelfImage.sort(AdapterShelfImage.COMPAREDATE);
        } else if (i == R.id.mnualbum_name) {
            adapterShelfImage.sort(AdapterShelfImage.COMPARENAME);
        } else if (i == R.id.mnucompress_backup) {
            compressBackup();
        } else if (i == R.id.mnuopenfolder) {
            selectMediaHouse(true);
        } else if (i == R.id.mnucreate_album) {
            createAlbum();
        } else if (i == R.id.mnurestore_album) {
            showListBackup();
        }
        return super.onOptionsItemSelected(item);
    }

    private void createAlbum() {
        MyDialog.showInputDialog(this, R.string.AlertDialogInputText_title_createalbum, new InputData() {
            @Override
            public void inputData(String s) {
                File f = new File(albumFolder.getAbsolutePath() + File.separator + s);
                if (!f.exists()) f.mkdir();
                if (f.exists() && f.isDirectory()) { //recheck after creating folder
                    albumFolder = new File(f.getAbsolutePath());
                    adapterShelfImage = new AdapterShelfImage(AlbumActivity.this, albumFolder, list, IMAGE_PER_SCREEN, CACHED_SHELF);
                }
            }
            @Override public void inputData(DialogInterface dialog) {}
            @Override public void inputData(String... s) {}
            @Override public void inputData(List<String> s) {}
            @Override public void inputData(String s, int color) {}
        });
    }

    private void reload() {
        adapterShelfImage = new AdapterShelfImage(this, albumFolder, list, IMAGE_PER_SCREEN, CACHED_SHELF);
    }

    private void selectMediaHouse(boolean cancelable) {
        MyDialog.selectFolderDialog(AlbumActivity.this, Environment.getExternalStorageDirectory().getAbsolutePath(), cancelable, new InputData() {
            @Override public void inputData(String s) {} @Override public void inputData(List<String> s) {}
            @Override public void inputData(String s, int color) {} @Override public void inputData(DialogInterface dialog) {}
            @Override public void inputData(String... s) {
                albumFolder = new File(s[0]);Log.i("MyTag", "new Media folder : " + albumFolder.getAbsolutePath());
                adapterShelfImage = new AdapterShelfImage(AlbumActivity.this, albumFolder, list, IMAGE_PER_SCREEN, CACHED_SHELF);
            }
        });
    }

    private void compressBackup() {
        mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setContentTitle(getResources().getString(R.string.Notification_backupimage_GG_title))
                .setContentText(getResources().getString(R.string.Notification_compressImage_GG))
                .setSmallIcon(R.drawable.zip_upload_icon);
        mBuilder.setProgress(0, 0, true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                final String path = (albumFolder == null) ? Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_DCIM).getAbsolutePath() : albumFolder.getAbsolutePath();
//                Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "abcd";
//                MyZip.zipDir(new File(path),
//                        Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "album.zip",
//                        MyZip.COMPRESS_ULTRA
//                ); // log4j lib error duplicated added folder in zip -> reason not found.
                mNotifyManager.notify(id, mBuilder.build());
                MyZip.zipDir1(path, Environment.getExternalStorageDirectory().getAbsolutePath()
                        + File.separator + "album.zip");
                if (!mGoogleApiClient.isConnected()) mGoogleApiClient.connect();
                upload();
//                AlbumActivity.this.runOnUiThread(new Runnable() {
//                    @Override public void run() {
//                        pd.dismiss();
//                    }
//                });
            }
        }).start();
    }

    private void showFolderInfo(File albumFolder) {
        String size = MyData.getFolderSize(albumFolder, null);
        String date = MyData.getDate(albumFolder);
        String numberOfImage = Long.toString(MyData.countFile(albumFolder, AdapterShelfImage.fnf));
        HashMap<String, String> info = new HashMap<>();
        info.put("Number of image", numberOfImage);
        info.put("Size", size);
        info.put("Date", date);
        MyDialog.showInfoDialog(AlbumActivity.this, info);
    }

    private AlertDialog ad;
    private void showListBackup() {
        if (!mGoogleApiClient.isConnected()) mGoogleApiClient.connect();
        Log.i("MyTag", "Search result :");
        final List<AlbumBackupItem> list = new ArrayList<>();
        SortOrder sortOrder = new SortOrder.Builder()
                .addSortDescending(SortableField.CREATED_DATE).build();
        Query sortedQuery = new Query.Builder()
                .addFilter(Filters.and(Filters.eq(SearchableField.MIME_TYPE, "application/zip"),
                        Filters.eq(SearchableField.TITLE, "album.zip")))
                .setSortOrder(sortOrder).build();
        Drive.DriveApi.query(mGoogleApiClient, sortedQuery)
            .setResultCallback(new ResultCallback<DriveApi.MetadataBufferResult>() {
                @Override
                public void onResult(DriveApi.MetadataBufferResult result) {
                    for (Metadata md : result.getMetadataBuffer()) if (md.getWebContentLink() != null) list.add(new AlbumBackupItem(md));
                    ad = MyDialog.showListViewDiaLog(AlbumActivity.this, R.string.AlertDialog_title_album_restore, list, null, new AdapterView.OnItemLongClickListener() {
                        @Override
                        public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
//                            new Thread(new Runnable() {
//                                @Override public void run() {
//                                    InputStream input = null;
//                                    FileOutputStream output = null;
//                                    HttpURLConnection connection = null;
//                                    try {
//                                        String link = list.get(position).getLink();
//                                        connection = (HttpURLConnection) new URL(link).openConnection();
//                                        connection.connect();
//                                        Log.i("MyTag", link + " Response code " + connection.getResponseCode());
//                                        if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
//                                            throw new Exception("Server returned HTTP " + connection.getResponseCode() + " " + connection.getResponseMessage());
//                                        }
//                                        // this will be useful to display download percentage might be -1: server did not report the length
//                                        int fileLength = connection.getContentLength();
//                                        Log.i("MyTag", "File length : " + fileLength);
//                                        // download the file
//                                        input = connection.getInputStream();
//                                        output = new FileOutputStream(albumFolder.getAbsolutePath() + File.separator + "album.zip");
//
//                                        byte data[] = new byte[4096];
//                                        long total = 0;
//                                        int count;
//                                        while ((count = input.read(data)) != -1) {
//                                            output.write(data, 0, count);
//                                            // publishing the progress....
//                                            total += count;
//                                            Log.i("MyTag", "percent " + (int) (total * 100 / fileLength) + "%");//updateNotification((int) (total * 100 / fileLength));
//                                        }
//                                    } catch (Exception e) {
//                                        e.printStackTrace();
//                                    } finally {
//                                        try {
//                                            if (output != null) output.close();
//                                            if (input != null) input.close();
//                                        } catch (IOException e) {
//                                            e.printStackTrace();
//                                        }
//                                        if (connection != null) connection.disconnect();
//                                    }
//                                }
//                            }).start();
//                            return false;
//                        }
//                    });
                            getBackup(list.get(position).getFile(), list.get(position).getLongSize() , albumFolder.getParentFile().getAbsolutePath() + File.separator + "album.zip");
                            ad.dismiss();
                            return false;
                        }
                    });
                }
            });
    }

    private void getBackup(DriveFile file, final long fileSize, final String outWithNameAndPath) {
        showDownloadNot(getResources().getString(R.string.Notification_downloadtitle_GG),
                getResources().getString(R.string.Notification_downloadstart_GG), R.drawable.zip_download_icon);
        Log.i("MyTag", "Start to download & save file as " + outWithNameAndPath);
        file.open(mGoogleApiClient, DriveFile.MODE_READ_ONLY, new DriveFile.DownloadProgressListener() {
            @Override public void onProgress(long bytesDownloaded, long bytesExpected) {}
        }).setResultCallback(new ResultCallback<DriveApi.DriveContentsResult>() {
            @Override public void onResult(@NonNull DriveApi.DriveContentsResult result) {
                if (!result.getStatus().isSuccess())
                    Log.i("MyTag", "Error while trying to open the file");
                else {
                    DriveContents driveContents = result.getDriveContents();
                    Log.i("MyTag", "Opening the file : " + driveContents.getDriveId());
                    InputStream is = driveContents.getInputStream();
                    long downloadByte = 0; int percent = 0, percent_old = -1;
                    try {
                        FileOutputStream fos = new FileOutputStream(outWithNameAndPath);
                        byte buffer[] = new byte[10240];
                        int numOfRead;
                        while ((numOfRead = is.read(buffer)) != -1) {
                            fos.write(buffer, 0, numOfRead);
                            downloadByte += numOfRead; percent = (int) (100*downloadByte/fileSize);
                            if (percent != percent_old) {
                                updateDownloadNot(percent, getResources().getString(R.string.Notification_downloadonProgress_GG) + " " + percent + "%");
                                Log.i("MyTag", "DownlaodByte " + downloadByte + "/" + fileSize + ", Percent " + percent + "%");
                                percent_old = percent;
                            }
                        }
                        fos.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    updateDownloadNot(100, getResources().getString(R.string.Notification_downloadfinish_GG));
                    File bk = new File(outWithNameAndPath);
                    MyZip.extract(bk);
                    reload();
                }
            }
        });
    }

    private void showDownloadNot(String title, String content, int icon) {
        if (mBuilder == null) mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setContentTitle(title).setContentText(content).setSmallIcon(icon);
        mBuilder.setProgress(100, 0, false);
        mNotifyManager.notify(id, mBuilder.build());
    }

    private void updateDownloadNot(int percent, String content) {
        mBuilder.setContentText(content);
        mBuilder.setProgress(100, percent, false);
        mBuilder.setSmallIcon((blink || (percent == 100)) ? R.drawable.zip_download_icon : R.drawable.zip_icon);
        blink = !blink;
        mNotifyManager.notify(id, mBuilder.build());
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
//        if (gd_cmd == SEARCH) search();
//        else if (gd_cmd == UPLOAD) upload();
    }

    private void upload() {
        MetadataChangeSet changeSet = new MetadataChangeSet.Builder()
                .setTitle(AppConstant.GOOGLE_DRIVE_ALBUMFOLDER + MyDateTime.getDateString
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
                                    .setTitle("album.zip")
                                    .setMimeType("application/zip")
                                    .setStarred(true).build();
                            folder.createFile(mGoogleApiClient, changeSet, driveContentsResult.getDriveContents())
                                    .setResultCallback(new ResultCallback<DriveFolder.DriveFileResult>() {
                                        @Override
                                        public void onResult(@NonNull DriveFolder.DriveFileResult driveFileResult) {
                                            if (!driveFileResult.getStatus().isSuccess()) Log.i("MyTag", "Error while trying to create the file");
                                            else {
                                                final DriveFile file = driveFileResult.getDriveFile();
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
//                                                                byte filecontent[] = MyFileIO.readByteFile(new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "album.zip"));
//                                                                long len = filecontent.length;
//                                                                int buffer = (len < 10240) ? (int)len : (len/100 < 10240) ? (int)(len/100) : 10240;
//                                                                int index = 0;
//                                                                mBuilder.setContentText(getResources().getString(R.string.Notification_uploadstart_GG))
//                                                                        .setProgress(100, 0, false);
//                                                                mNotifyManager.notify(id, mBuilder.build());
//                                                                while(index < len) {
//                                                                    writer.write(filecontent, index, buffer);
//                                                                    mBuilder.setProgress(100, (int) (100*(index + buffer)/len), false)
//                                                                            .setSmallIcon((blink) ? R.drawable.zip_upload_icon : R.drawable.zip_icon); blink = !blink;
//                                                                    mNotifyManager.notify(id, mBuilder.build());
//                                                                    index += buffer;
//                                                                    buffer = ((len - index) < buffer) ? (int)(len - index) : buffer;
//                                                                }
//                                                                writer.flush(); writer.close();
//                                                                mBuilder.setContentText(getResources().getString(R.string.Notification_backupfinished_GG))
//                                                                        .setProgress(100, 100, false).setSmallIcon(R.drawable.zip_upload_icon);
//                                                                mNotifyManager.notify(id, mBuilder.build());
                                                                mBuilder.setContentText(getResources().getString(R.string.Notification_uploadstart_GG));
                                                                mNotifyManager.notify(id, mBuilder.build());
                                                                writer.write(MyFileIO.readByteFile(new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "album.zip")));
                                                                writer.flush(); writer.close();
                                                            } catch (IOException e) {
                                                                e.printStackTrace();
                                                            }
                                                            driveContents.commit(mGoogleApiClient, null).setResultCallback(new ResultCallback<Status>() {
                                                                @Override
                                                                public void onResult(Status result) {
                                                                    if (!result.getStatus().isSuccess()) Log.i("MyTag", "Error while trying to commit the file to server");
                                                                    else {
                                                                        mBuilder.setContentText(getResources().getString(R.string.Notification_backupfinished_GG)).setProgress(100, 100, false);
                                                                        mNotifyManager.notify(id, mBuilder.build());
                                                                        Log.i("MyTag", "Commit successfully");
                                                                    }
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
                e.printStackTrace();
            }
        } else GooglePlayServicesUtil.getErrorDialog(connectionResult.getErrorCode(), this, 0).show();
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        switch (requestCode) {
            case RESOLVE_CONNECTION_REQUEST_CODE:
                if (resultCode == RESULT_OK) mGoogleApiClient.connect();
                break;
        }
    }
}