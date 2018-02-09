package and402lab4.nhut.and402.lab.android402lab4_listemailcontact;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.api.client.googleapis.auth.oauth2.GoogleBrowserClientRequestUrl;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.people.v1.*;
import com.google.api.services.people.v1.model.Person;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String REQUESTURL = "https://www.googleapis.com/oauth2/v1/userinfo?access_token=";
    private static final String SCOPE = "oauth2:https://www.googleapis.com/auth/userinfo.profile";

    private static final String WEBCLIENTID = "856562707497-ksm4ih9fic6jjt8dpngi0noer7k8cba0.apps.googleusercontent.com";
    private static final String WEBclientSecret = "-QmTNFyoR-H8UmhhxpF_ajtq";
    FileManipulation fm;
    private EmailAccount account[];
    private ExpandableListView lv;
    public userAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cleanCache();
        lv = (ExpandableListView) findViewById(R.id.listviewemailaccount);
        lv.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int position) {
                if (adapter != null && adapter.getListChild().get(position).size() == 0)
                    GooglePeopleAPIWEBAPI(position, account[position].getToken());
            }
        });

        GoogleAPI();
    }

    public ExpandableListView getLv() {
        return lv;
    }

    private void cleanCache() {
        File cache = getCacheDir();
        fm = new FileManipulation() {
            long foldersize = 0;
            @Override
            public void manipulateFolderContent(File[] content) {
                for (File f : content) manipulateFolder(f, fm);
            }
            @Override
            public void ManipulateFolder(File folder) {
                folder.delete();
                System.out.println(folder.getName() + " " + genSize(foldersize));
                foldersize = 0;
            }
            @Override
            public void manipulateFile(File file) {
                foldersize += file.length();
                System.out.println(file.getName() + " " + genSize(file.length()));
                file.delete();
            }
        };
        if (cache.exists()) manipulateFolder(cache, fm);
    }

    private String genSize(long size) {
        if (size < 1024) return size + " B";
        else if (size < 1024 * 1024)
            return size / 1024.0 + " KB";
        else if (size < 1024 * 1024 * 1024)
            return size / (1024.0 * 1024) + " MB";
        else if (size < 1024 * 1024 * 1024 * 1024)
            return size / (1024.0 * 1024 * 1024) + " GB";
        else //if (size < 1024*1024*1024*1024*1024)
            return size / (1024.0 * 1024 * 1024 * 1024) + " TB";
    }

    private void manipulateFolder(File f, FileManipulation fm) {
        if (f.isDirectory()) {
            File content[] = f.listFiles();
            if (content != null && content.length > 0)
                fm.manipulateFolderContent(content);
            fm.ManipulateFolder(f);
        } else if (f.isFile()) fm.manipulateFile(f);
    }

    public void GooglePeopleAPIWEBAPI(final int position, final String token) {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    HttpTransport httpTransport = new NetHttpTransport();
//                    JacksonFactory jsonFactory = new JacksonFactory();
//                    final List<Person> listPerson = new PeopleService.Builder(
//                            httpTransport, jsonFactory, new GoogleCredential.Builder()
//                            .setTransport(httpTransport).setJsonFactory(jsonFactory)
//                            .setClientSecrets(WEBCLIENTID, WEBclientSecret).build()
//                            .setAccessToken(token)
//                    ).build()
//                            .people().connections().list("people/me")
//                            .setPersonFields("names,emailAddresses,genders,photos,phoneNumbers")
//                            .execute().getConnections();
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            adapter.addToListChild(position,
//                                Arrays.asList(EmailAccount.convertListPerson(listPerson)));
//                        }
//                    });
//                } catch (IOException e) {
//                    Log.i("MyTag", "Error");
//                    e.printStackTrace();
//                }
//            }
//        }).start();
        if (token.length() > 0) {
            MyClipboard.sendTextToClipboard(this, " access_token=" + token + "&");
            onActivityResult(position, 0, new Intent());
            return;
        }
        String authorizationUrl = new GoogleBrowserClientRequestUrl(WEBCLIENTID,
                "http://localhost", Arrays.asList("https://www.googleapis.com/auth/contacts.readonly"))
                .build();
        startActivityForResult(new Intent(Intent.ACTION_VIEW, Uri.parse(authorizationUrl)), position);
    }

    @Override
    protected void onActivityResult(final int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String textinclipboard = MyClipboard.getTextFromClipboard(MainActivity.this);
        final String code = (textinclipboard.contains("access_token="))
            ? textinclipboard.split("access_token=")[1].split("&")[0] : "";
        if (code.length() == 0) return;
        MyClipboard.clearClipboard(this);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    HttpTransport httpTransport = new NetHttpTransport();
                    JacksonFactory jsonFactory = new JacksonFactory();
                    final List<Person> listPerson = new PeopleService.Builder(
                            httpTransport, jsonFactory, new GoogleCredential.Builder()
                            .setTransport(httpTransport).setJsonFactory(jsonFactory)
                            .setClientSecrets(WEBCLIENTID, WEBclientSecret).build()
                            .setAccessToken(code) // -> don't use new GoogleTokenRequest... set token from website directly
                                                  // need internet permission
                        ).setApplicationName(getResources().getString(R.string.app_name))
                        .build()
                            .people().connections().list("people/me")
                            .setPersonFields("names,emailAddresses,genders,photos,phoneNumbers")
                            .execute().getConnections();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (listPerson != null) adapter.addToListChild(requestCode,
                            Arrays.asList(EmailAccount.convertListPerson(listPerson)), code);
                        }
                    });
                } catch (IOException e) {
                    Log.i("MyTag", "Error");
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void GoogleAPI() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.GET_ACCOUNTS)
                != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Please enable permission GET ACCOUNTS and try again",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        final Account accountarray[] = AccountManager.get(this)
                .getAccountsByType(GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE);
        account = new EmailAccount[accountarray.length];
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < accountarray.length; i++) {
                    try {
                        // GoogleAuthUtil.getToken() -> error unregistered_on_api ..
                        // go to https://console.developers.google.com/apis/credentials
                        // create a new credential OAuth 2.0 client IDs
                        // name[Android client 1] type[Android]
                        //keytool -exportcert -keystore [path to keystore file] -list -v to get SHA1
                        String token = GoogleAuthUtil.getToken(
                                MainActivity.this, accountarray[i], SCOPE);
                        JSONObject jo = new JSONObject(loadInfo(token));
                        System.out.println(accountarray[i].name);
                        String name = (jo.has("name")) ? jo.getString("name") : "N/A";
                        String gender = (jo.has("gender")) ? jo.getString("gender") : "N/A";
                        String picture = (jo.has("picture")) ? jo.getString("picture") : "";
                        account[i] = new EmailAccount(name,
                            gender, accountarray[i].name, picture, "");
                    } catch (UserRecoverableAuthException userRecoverableException) {
                        MainActivity.this.startActivityForResult(
                                userRecoverableException.getIntent(), 0);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        boolean oktogo = true;
                        for (EmailAccount ea : account) if (ea == null) {
                            oktogo = false;
                            break;
                        }
                        if (oktogo && accountarray.length > 0) {
                            adapter = new userAdapter(MainActivity.this, R.layout.item, account);
                            lv.setAdapter(adapter);
                        }
                    }
                });
            }
        }).start();
    }

    private String loadInfo(String token) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(
                    new InputStreamReader(new URL(REQUESTURL + token).openStream()));
            int num = 0;
            char buffer[] = new char[1024 * 2];
            StringBuilder sb = new StringBuilder();
            while ((num = br.read(buffer)) > 0) sb.append(buffer, 0, num);
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "{}";
        }
    }

    private interface FileManipulation {
        // long foldersize = 0;
        void manipulateFolderContent(File content[]);
        void ManipulateFolder(File folder);
        void manipulateFile(File file);
    }
}
