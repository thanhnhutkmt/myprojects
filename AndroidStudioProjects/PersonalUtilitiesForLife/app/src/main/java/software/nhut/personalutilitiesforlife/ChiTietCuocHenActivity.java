package software.nhut.personalutilitiesforlife;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.net.Uri;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.tweetcomposer.TweetComposer;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import software.nhut.personalutilitiesforlife.constant.AppConstant;
import software.nhut.personalutilitiesforlife.data.CuocHen;
import software.nhut.personalutilitiesforlife.data.TinNhanDanhBaCuocGoi;
import util.MyClipboard;
import util.MyDateTime;
import util.MyDialog;
import util.MyFileIO;
import util.MyPhone;



public class ChiTietCuocHenActivity extends AppCompatActivity {
    TextView txtGioThuNgayThangNam_ChiTietCuocHen, txtDiaDiem_ChiTietCuocHen,
            txtThongDiep_ChiTietCuocHen, txtNguoiLienHe_ChiTietCuocHen, lastItemClicked;
    EditText txtTuaDe_ChiTietCuocHen, txtNoiDung_ChiTietCuocHen;
    ListView lvDiaDiem_ChiTietCuocHen, lvThongDiep_ChiTietCuocHen, lvGhiChu_ChiTietCuocHen;
    ArrayAdapter<String> adapterDiaDiem, adapterThongDiep, adapterGhiChu;
    List<String> listDiaDiem, listThongDiep, listGhiChu;
    CuocHen c;
    Intent i;
    Calendar calendar;
    SimpleDateFormat sdf;
    private boolean stop;
    private String contactFolder;
    CallbackManager callbackManager;
    ShareDialog shareDialog;
    private TwitterLoginButton loginButton;
    private CountDownTimer saveTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_cuoc_hen);

        addControls();
        addEvents();
    }

    private void initFacebook() {
        FacebookSdk.setApplicationId(AppConstant.FACEBOOK_APP_ID);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);
        // this part is optional
//        shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {

//        });

    }

    private void addEvents() {
        initFacebook();
        txtGioThuNgayThangNam_ChiTietCuocHen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyGioThuNgayThang();
            }
        });

        txtNoiDung_ChiTietCuocHen.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                lastItemClicked = txtNoiDung_ChiTietCuocHen;
                return false;
            }
        });

        txtDiaDiem_ChiTietCuocHen.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                lastItemClicked = txtDiaDiem_ChiTietCuocHen;
                return false;
            }
        });

        txtThongDiep_ChiTietCuocHen.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                lastItemClicked = txtThongDiep_ChiTietCuocHen;
                return false;
            }
        });

        txtNguoiLienHe_ChiTietCuocHen.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                lastItemClicked = txtNguoiLienHe_ChiTietCuocHen;
                return false;
            }
        });
        lvDiaDiem_ChiTietCuocHen.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                listDiaDiem.remove(position);
                adapterDiaDiem.notifyDataSetChanged();
                MyDialog.scaleListView(lvDiaDiem_ChiTietCuocHen);
                return false;
            }
        });
        lvGhiChu_ChiTietCuocHen.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                listGhiChu.remove(position);
                adapterGhiChu.notifyDataSetChanged();
                MyDialog.scaleListView(lvGhiChu_ChiTietCuocHen);
                return false;
            }
        });
        lvThongDiep_ChiTietCuocHen.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                listThongDiep.remove(position);
                adapterThongDiep.notifyDataSetChanged();
                MyDialog.scaleListView(lvThongDiep_ChiTietCuocHen);
                return false;
            }
        });
        lvDiaDiem_ChiTietCuocHen.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MyClipboard.sendTextToClipboard(ChiTietCuocHenActivity.this, listDiaDiem.get(position));
            }
        });
        lvGhiChu_ChiTietCuocHen.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MyClipboard.sendTextToClipboard(ChiTietCuocHenActivity.this, listGhiChu.get(position));
            }
        });
        lvThongDiep_ChiTietCuocHen.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MyClipboard.sendTextToClipboard(ChiTietCuocHenActivity.this, listThongDiep.get(position));
            }
        });
    }

    private void xuLyGioThuNgayThang() {
        DatePickerDialog.OnDateSetListener callBack1 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                txtGioThuNgayThangNam_ChiTietCuocHen.setText(sdf.format(calendar.getTime()));
            }
        };
        DatePickerDialog dpd = new DatePickerDialog(
                ChiTietCuocHenActivity.this,
                callBack1,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        dpd.show();
        TimePickerDialog.OnTimeSetListener callBack2 = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);
//                txtGioThuNgayThangNam_ChiTietCuocHen.setText(sdf.format(calendar.getTime()));
            }
        };
        TimePickerDialog tpd = new TimePickerDialog(
                ChiTietCuocHenActivity.this,
                callBack2,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true
        );
        tpd.show();
    }

    private void setAlarm(long appointmentTime, String msg) {
        for (int i = 0; i < 3; i++) cancelAlarm(c.getAlarmID() + i);
        int alarmID;
        do { alarmID = (int)(Math.random() * 10000000);
        } while(!c.setAlarmID(this, alarmID));
        AlarmManager alarmMgr = (AlarmManager) getSystemService(ALARM_SERVICE);
        Log.i("MyTag", "delta time " + MyDateTime.getDateString(appointmentTime, "dd/MM/yyyy HH:mm:ss") + " "
                + MyDateTime.getDateString(System.currentTimeMillis(), "dd/MM/yyyy HH:mm:ss") + " " + (appointmentTime - System.currentTimeMillis()));
        if (appointmentTime > System.currentTimeMillis())
            for (int i = 0; i < 3; i++) {
                alarmMgr.set(AlarmManager.RTC_WAKEUP, appointmentTime
                        - 1000 * 60 * 5 * (3 - i), createPIAlarm("appointmentcontent", msg, alarmID + i));
                Log.i("MyTag", "Set alarm id " + (alarmID + i));
            }
    }

    private PendingIntent createPIAlarm(String label, String content, int alarmID) {
        Intent intent = new Intent(this, NotificationAlarmReceiver.class);
        if (content != null) intent.putExtra(label, content);
        else intent.putExtra(label, alarmID);
        return PendingIntent.getBroadcast(this, alarmID, intent, 0);
    }

    private void cancelAlarm(int alarmID) {
        ((AlarmManager) getSystemService(ALARM_SERVICE)).cancel(PendingIntent.getBroadcast(this,
                alarmID, new Intent(this, NotificationAlarmReceiver.class), PendingIntent.FLAG_NO_CREATE));
    }

    private void addControls() {
        contactFolder = getApplicationInfo().dataDir
                + File.separator + AppConstant.THUMUC_QUANLY_TINNHANDANHBA;
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        txtGioThuNgayThangNam_ChiTietCuocHen = (TextView) findViewById(R.id.txtGioThuNgayThangNam_ChiTietCuocHen);
        txtTuaDe_ChiTietCuocHen = (EditText) findViewById(R.id.txtTuaDe_ChiTietCuocHen);
        txtNoiDung_ChiTietCuocHen = (EditText) findViewById(R.id.txtNoiDung_ChiTietCuocHen);
        txtDiaDiem_ChiTietCuocHen = (TextView) findViewById(R.id.txtDiaDiem_ChiTietCuocHen);
        txtThongDiep_ChiTietCuocHen = (TextView) findViewById(R.id.txtThongDiep_ChiTietCuocHen);
        txtNguoiLienHe_ChiTietCuocHen = (TextView) findViewById(R.id.txtNguoiLienHe_ChiTietCuocHen);
        lvDiaDiem_ChiTietCuocHen = (ListView) findViewById(R.id.lvDiaDiem_ChiTietCuocHen);
        lvThongDiep_ChiTietCuocHen = (ListView) findViewById(R.id.lvThongDiep_ChiTietCuocHen);
        lvGhiChu_ChiTietCuocHen = (ListView) findViewById(R.id.lvGhiChu_ChiTietCuocHen);

        saveTimer = new CountDownTimer(61000, 30000) {
            @Override public void onTick(long millisUntilFinished) {
                boolean tat = (txtGioThuNgayThangNam_ChiTietCuocHen.getCurrentTextColor()
                    == Color.RED) ? true : false;
                CuocHen ch = new CuocHen(txtTuaDe_ChiTietCuocHen.getText().toString(),
                    txtNoiDung_ChiTietCuocHen.getText().toString(),
                    txtGioThuNgayThangNam_ChiTietCuocHen.getText().toString(),
                    listDiaDiem, listThongDiep, listGhiChu, tat);
                if (c != null) if (ch.equals(c)) return;
                List<CuocHen> l = new ArrayList<>(); l.add(ch);
                String path = getApplicationInfo().dataDir + File.separator
                    + AppConstant.THUMUC_QUANLY_CUOCHEN;
                boolean result = MyFileIO.saveData(l, path, QuanLyCuocHenActivity.SAVETEMPNAME2);
                if (result) {
                    Toast.makeText(ChiTietCuocHenActivity.this,
                            R.string.Toast_body_Chitietcuochen_saveDialog, Toast.LENGTH_SHORT).show();
                    c = ch;
                    MyFileIO.saveData(l, path, QuanLyCuocHenActivity.SAVETEMPNAME1);
                }
            }
            @Override public void onFinish() {
                if (saveTimer != null) saveTimer.start();
            }
        };
        saveTimer.start();
        calendar = Calendar.getInstance();
        sdf = new SimpleDateFormat(AppConstant.FULLTIMEFORMAT_WITHOUTNEWLINE);

        registerForContextMenu(txtNoiDung_ChiTietCuocHen);
        registerForContextMenu(txtDiaDiem_ChiTietCuocHen);
        registerForContextMenu(txtThongDiep_ChiTietCuocHen);
        registerForContextMenu(txtNguoiLienHe_ChiTietCuocHen);

        c = null;
        i = getIntent();
        c = (CuocHen) i.getSerializableExtra(AppConstant.QUANLYCUOCHEN_CHUOIGUI_CUOCHEN);
        if (c != null) showCuocHen();
        else {
            listDiaDiem = new ArrayList<>();
            listThongDiep = new ArrayList<>();
            listGhiChu = new ArrayList<>();
        }
        adapterDiaDiem = new ArrayAdapter<String>(ChiTietCuocHenActivity.this, android.R.layout.simple_list_item_1, listDiaDiem);
        adapterThongDiep = new ArrayAdapter<String>(ChiTietCuocHenActivity.this, android.R.layout.simple_list_item_1, listThongDiep);
        adapterGhiChu = new ArrayAdapter<String>(ChiTietCuocHenActivity.this, android.R.layout.simple_list_item_1, listGhiChu);
        lvDiaDiem_ChiTietCuocHen.setAdapter(adapterDiaDiem);
        lvThongDiep_ChiTietCuocHen.setAdapter(adapterThongDiep);
        lvGhiChu_ChiTietCuocHen.setAdapter(adapterGhiChu);
        if (c != null) {
            MyDialog.scaleListViewAtFirst(lvDiaDiem_ChiTietCuocHen);
            MyDialog.scaleListViewAtFirst(lvThongDiep_ChiTietCuocHen);
            MyDialog.scaleListViewAtFirst(lvGhiChu_ChiTietCuocHen);
        }
    }

    private void showCuocHen() {
        txtGioThuNgayThangNam_ChiTietCuocHen.setText(c.getGioThuNgayThangNam());
        txtGioThuNgayThangNam_ChiTietCuocHen.setTextColor((c.isTat()) ? Color.RED : Color.BLACK);
        txtTuaDe_ChiTietCuocHen.setText(c.getTieuDe());
        txtNoiDung_ChiTietCuocHen.setText(c.getNoiDung());

        listDiaDiem = c.getListDiaDiem();
        listThongDiep = c.getListTinNhan();
        listGhiChu = c.getListNguoiLienHe();
    }

    @Override
    public void onBackPressed() {
        returnResult();
    }

    private void returnResult() {
        String time = txtGioThuNgayThangNam_ChiTietCuocHen.getText().toString();
        String title = txtTuaDe_ChiTietCuocHen.getText().toString().trim();
        if (time.length() > 0 && title.length() > 0) {
            boolean tat = (txtGioThuNgayThangNam_ChiTietCuocHen.getCurrentTextColor() == Color.RED) ? true : false;
            c = new CuocHen(txtTuaDe_ChiTietCuocHen.getText().toString(),
                    txtNoiDung_ChiTietCuocHen.getText().toString(),
                    txtGioThuNgayThangNam_ChiTietCuocHen.getText().toString(),
                    listDiaDiem, listThongDiep, listGhiChu, tat);
            setAlarm(c.getTime().getTime(), title);
            i.putExtra(AppConstant.CHITIETCUOCHEN_CHUOIGUI_CUOCHEN, c);
            setResult(AppConstant.CHITIETCUOCHEN_RESULTCODE, i);
            finish();
        } else {
            MyDialog.showYesNoDiaLog(this, R.string.AlertDialog_title_Chitietcuochen_confirmDialg,
                    R.string.AlertDialog_body_Chitietcuochen_confirmDialg, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    c = null;
                    setResult(AppConstant.CHITIETCUOCHEN_RESULTCODE, i);
                    finish();
                }
            }, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        saveTimer.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        saveTimer.cancel();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        new File(getApplicationInfo().dataDir + File.separator +
                AppConstant.THUMUC_QUANLY_CUOCHEN + File.separator +
                QuanLyCuocHenActivity.SAVETEMPNAME1).delete();
        new File(getApplicationInfo().dataDir + File.separator +
                AppConstant.THUMUC_QUANLY_CUOCHEN + File.separator +
                QuanLyCuocHenActivity.SAVETEMPNAME2).delete();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_chitietcuochen, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.mnuXoa_chitietcuochen) {
            c = null;
            i.putExtra(AppConstant.CHITIETCUOCHEN_CHUOIGUI_CUOCHEN, c);
            setResult(AppConstant.CHITIETCUOCHEN_RESULTCODE, i);
            finish();
        } else if (id == R.id.mnuTatMo_chitietcuochen) {
            boolean tat = c.isTat();
            c.setTat(!tat);
            txtGioThuNgayThangNam_ChiTietCuocHen.setTextColor((c.isTat()) ? Color.RED : Color.BLACK);
        } else if (id == R.id.mnupostFB_chitietcuochen) {
            postFB(c);
        } else if (id == R.id.mnutweetTwitter_chitietcuochen) {
            tweetTwitter(c);
        }
        return super.onOptionsItemSelected(item);
    }

    private void postFB(CuocHen c) {
        ShareLinkContent content = new ShareLinkContent.Builder()
//            .setQuote(c.toString())
            .setContentTitle(getResources().getString(R.string.postFacebook_title_Note))
            .setContentDescription(getResources().getString(R.string.postFacebook_desc_Note))
            .setContentUrl(Uri.parse("https://facebook.com"))//https://mega.nz/#!gJhADIxS!wJJyrRv-hZOsXk4FMf8rQ2tBoGP0Z0DY552nuS7aQ_s"))
            .build();
        MyClipboard.sendTextToClipboard(this, (c != null) ? c.toString(this) : "");
        shareDialog.show(content);
    }

    private void tweetTwitter(CuocHen c) {
        // compose a tweet - must have twitter app installed on phone
        TweetComposer.Builder builder = new TweetComposer.Builder(this)
                .text(c.toString(this));
        builder.show();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menucontext_chitietcuochen, menu);
        MenuItem mnuLayTinNhan = menu.findItem(R.id.mnuLayTinNhan);
        MenuItem mnuLayTuDanhBa = menu.findItem(R.id.mnuLayTuDanhBa);
        MenuItem mnuNhapTay = menu.findItem(R.id.mnuNhapTay);
        MenuItem mnuLayTuGoogleMap = menu.findItem(R.id.mnuLayTuGoogleMap);
        MenuItem mnuPasteTuClipBoard = menu.findItem(R.id.mnuPasteTuClipBoard);
        if (lastItemClicked == txtNoiDung_ChiTietCuocHen) {
            mnuNhapTay.setVisible(false);
        } else if (lastItemClicked == txtDiaDiem_ChiTietCuocHen) {
            mnuLayTuDanhBa.setVisible(false);
        } else if (lastItemClicked == txtThongDiep_ChiTietCuocHen) {
            mnuLayTuDanhBa.setVisible(false);
            mnuLayTuGoogleMap.setVisible(false);
        } else if (lastItemClicked == txtNguoiLienHe_ChiTietCuocHen) {
            mnuLayTinNhan.setVisible(false);
            mnuLayTuGoogleMap.setVisible(false);
        }

        if (mnuPasteTuClipBoard.isVisible()) {
            mnuPasteTuClipBoard.setEnabled(MyClipboard.hasTextInClipboard(ChiTietCuocHenActivity.this));
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int i = item.getItemId();
        if (i == R.id.mnuLayTinNhan) {
            List<List<TinNhanDanhBaCuocGoi>> l = new ArrayList<>();
            MyPhone.loadDataFromApp(contactFolder, l);
            createAlertDialog(R.string.txtlistTinNhanInput_Title_ChiTietCuocHen, l.get(2));//MyPhone.getAllMessages(ChiTietCuocHenActivity.this));
        } else if (i == R.id.mnuLayTuDanhBa) {
            List<List<TinNhanDanhBaCuocGoi>> l = new ArrayList<>();
            MyPhone.loadDataFromApp(contactFolder, l);
            createAlertDialog(R.string.txtlistDanhBaInput_Title_ChiTietCuocHen, l.get(1));//MyPhone.getAllContacts(ChiTietCuocHenActivity.this));
        } else if (i == R.id.mnuNhapTay) {
            createAlertDialog(R.string.txtInputDialog_Title_ChiTietCuocHen, null);
        } else if (i == R.id.mnuPasteTuClipBoard) {
            inputData(MyClipboard.getTextFromClipboard(ChiTietCuocHenActivity.this));
        } else if (i == R.id.mnuLayTuGoogleMap) {
            layTuGoogleMap();
        }
        return super.onContextItemSelected(item);
    }

    private void layTuGoogleMap() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.AlertDialog_Title_ChiTietCuocHen_GoogleMapAPIGooglePlaceAPI);
        TextView txtContent = new TextView(this);
        txtContent.setText(R.string.AlertDialog_Content_ChiTietCuocHen_GoogleMapAPIGooglePlaceAPI);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        txtContent.setGravity(Gravity.CENTER_VERTICAL + Gravity.LEFT);
        txtContent.setLayoutParams(lp);
        txtContent.setPadding(20, 20, 20, 20);
        builder.setView(txtContent);
        builder.setPositiveButton(R.string.AlertDialog_button_ChiTietCuocHen_GooglePlacesAPI,
                new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    startActivityForResult(builder.build(ChiTietCuocHenActivity.this),
                            AppConstant.CHITIETCUOCHEN_OPENMAP_REQUESTCODE);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });
        builder.setNegativeButton(R.string.AlertDialog_button_ChiTietCuocHen_GoogleMapAPI,
                new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent i = new Intent(ChiTietCuocHenActivity.this, BanDoActivity.class);
                startActivityForResult(i, AppConstant.CHITIETCUOCHEN_OPENMAP_REQUESTCODE);
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        loginButton.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppConstant.CHITIETCUOCHEN_OPENMAP_REQUESTCODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                String info = place.getName() + " - " + place.getAddress()
                        + " - " + place.getPhoneNumber();
                inputData(info);
            } else {
                if (MyClipboard.hasTextInClipboard(ChiTietCuocHenActivity.this))
                    inputData(MyClipboard.getTextFromClipboard(ChiTietCuocHenActivity.this));
            }
        }
//        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void inputData(String data) {
        if (data == null || data.trim().length() == 0) return;
        else if (lastItemClicked == txtNoiDung_ChiTietCuocHen) {
            String newContent = txtNoiDung_ChiTietCuocHen.getText().toString() + data;
            txtNoiDung_ChiTietCuocHen.setText(newContent);
        } else if (lastItemClicked == txtDiaDiem_ChiTietCuocHen) {
            listDiaDiem.add(data);
            adapterDiaDiem.notifyDataSetChanged();
            MyDialog.scaleListView(lvDiaDiem_ChiTietCuocHen);
        } else if (lastItemClicked == txtThongDiep_ChiTietCuocHen) {
            listThongDiep.add(data);
            adapterThongDiep.notifyDataSetChanged();
            MyDialog.scaleListView(lvThongDiep_ChiTietCuocHen);
        } else if (lastItemClicked == txtNguoiLienHe_ChiTietCuocHen) {
            listGhiChu.add(data);
            adapterGhiChu.notifyDataSetChanged();
            MyDialog.scaleListView(lvGhiChu_ChiTietCuocHen);
        }
    }

//    public static void setListViewHeightBasedOnChildren(ListView listView) {
//        ListAdapter listAdapter = listView.getAdapter();
//        if (listAdapter == null || listAdapter.getCount() == 0) {
//            // pre-condition
//            return;
//        }
//
//        int totalHeight = listView.getPaddingTop() + listView.getPaddingBottom();
//        for (int i = 0; i < listAdapter.getCount(); i++) {
//            View listItem = listAdapter.getView(i, null, listView);
//            listItem.measure(View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST), View.MeasureSpec.UNSPECIFIED);
//            totalHeight += listItem.getMeasuredHeight();
//        }
//
//        ViewGroup.LayoutParams params = listView.getLayoutParams();
//        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
//        listView.setLayoutParams(params);
//    }

    private void createAlertDialog(int idTitle, final List<TinNhanDanhBaCuocGoi> list) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(ChiTietCuocHenActivity.this);
        builder.setTitle(idTitle);
        if (list != null) {
            // Set up the listview to show listContact
            ListView lvTinNhan = new ListView(ChiTietCuocHenActivity.this);
            ArrayAdapter adapter = new ArrayAdapter(ChiTietCuocHenActivity.this, android.R.layout.simple_list_item_1, list);
            lvTinNhan.setAdapter(adapter);
            lvTinNhan.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    inputData(list.get(position).getContent());
                    Toast.makeText(ChiTietCuocHenActivity.this, R.string.Toast_txtlistInput_ChiTietCuocHen, Toast.LENGTH_SHORT).show();
                    return false;
                }
            });
            builder.setView(lvTinNhan);
        } else {
            // Set up the input
            final EditText input = new EditText(ChiTietCuocHenActivity.this);
            // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
            input.setInputType(InputType.TYPE_CLASS_TEXT);// | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            builder.setView(input);

            // Set up the buttons
            builder.setPositiveButton(R.string.txtInput_NutLuu_ChiTietCuocHen, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    inputData(input.getText().toString());
                }
            });
        }
        builder.setNegativeButton(R.string.txtInput_NutHuy_ChiTietCuocHen, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }
}

/*
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));

        loginButton = MyDialog.showTweetDialog(this);
        loginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                // The TwitterSession is also available through:
                // Twitter.getInstance().core.getSessionManager().getActiveSession()
                TwitterSession session = result.data;
                TwitterAuthToken authToken = session.getAuthToken();
                String token = authToken.token;
                String secret = authToken.secret;
                Log.i("MyTag", "Logged in Twitter : token " + token + " secret " + secret);
//                // TODO: Remove toast and use the TwitterSession's userID
//                // with your app's user model
//                String msg = "@" + session.getUserName() + " logged in! (#" + session.getUserId() + ")";
//                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
//
//                // TODO: Use a more specific parent
//                final ViewGroup parentView = (ViewGroup) getWindow().getDecorView().getRootView();
//                // TODO: Base this Tweet ID on some data from elsewhere in your app
//                long tweetId = 631879971628183552L;
//                TweetUtils.loadTweet(tweetId, new Callback<Tweet>() {
//                    @Override
//                    public void success(Result<Tweet> result) {
//                        TweetView tweetView = new TweetView(ChiTietCuocHenActivity.this, result.data);
//                        parentView.addView(tweetView);
//                    }
//                    @Override
//                    public void failure(TwitterException exception) {
//                        Log.d("TwitterKit", "Load Tweet failure", exception);
//                    }
//                });
                Intent intent = new ComposerActivity.Builder(ChiTietCuocHenActivity.this)
                        .session(session)
                        .createIntent();
                startActivity(intent);
            }
            @Override
            public void failure(TwitterException exception) {
                Log.d("TwitterKit", "Login with Twitter failure", exception);
            }
        });
    public static final String TWITTER_KEY = "vME9asxuqPn6dHthsgZtTIyem";
    public static final String TWITTER_SECRET = "kEVvnFzsnWvblaUCZSPViZsEIgliqNwgayaD3hLFX2zwrL8Wy3";


//        final TwitterSession session = TwitterCore.getInstance().getSessionManager()
//                .getActiveSession();
//        final Intent intent = new ComposerActivity.Builder(ChiTietCuocHenActivity.this)
//                .session(session)
//                .createIntent();
//        startActivity(intent);
 */