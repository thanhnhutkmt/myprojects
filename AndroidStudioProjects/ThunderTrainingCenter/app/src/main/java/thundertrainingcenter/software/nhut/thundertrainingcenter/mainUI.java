package thundertrainingcenter.software.nhut.thundertrainingcenter;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import thundertrainingcenter.software.nhut.thundertrainingcenter.DO.ExamResult;
import thundertrainingcenter.software.nhut.thundertrainingcenter.DO.Favcourse;
import thundertrainingcenter.software.nhut.thundertrainingcenter.DO.Learner;
import thundertrainingcenter.software.nhut.thundertrainingcenter.DO.Regcourse;
import thundertrainingcenter.software.nhut.thundertrainingcenter.DO.ShowCourse;

public class mainUI extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public static String USERID, PASS, LEARNERID;
    public static String SERVER;
    private Learner learner;
    private List<Regcourse> listRegcourse;
    private List<Favcourse> listFavcourse;
    private List<ShowCourse> listCourse;
    private List<ExamResult> listExamResult;
    private List<String> listMessasge;
    private ImageView image_iv;
    private TextView username_tv, email_tv;
    private RelativeLayout content;
    private int dataLoaded = 0;
    private NavigationView navigationView;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_ui);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        image_iv = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.nav_header_image);
        username_tv = (TextView) navigationView.getHeaderView(0).findViewById(R.id.nav_header_username);
        email_tv = (TextView) navigationView.getHeaderView(0).findViewById(R.id.nav_header_email);

        content = (RelativeLayout) findViewById(R.id.content_main_ui);
        setSupportActionBar(toolbar);
        fab.setVisibility(View.GONE);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final StringBuilder sb = new StringBuilder("");
                        try {
                            System.out.println("update learner info");
                            HttpURLConnection con = (HttpURLConnection) new URL(
                                "http://" + mainUI.SERVER + "/TrainingCenter/serviceupdatelearner.controller")
                                    .openConnection();
                            con.setRequestMethod("POST");
                            con.setDoOutput(true);
                            con.setConnectTimeout(3000);
                            OutputStreamWriter osw = new OutputStreamWriter(con.getOutputStream());
                            String name[] = learner.getFullName().split(" ");
                            String fullname[] = new String[3];
                            if (name.length == 3) fullname = learner.getFullName().split(" ");
                            else if (name.length == 2) {
                                fullname[0] = name[0];
                                fullname[1] = "";
                                fullname[2] = name[2];
                            } else if (name.length == 1) {
                                fullname[0] = name[0];
                                fullname[1] = "";
                                fullname[2] = "";
                            }
                            osw.write(String.format("userid=%s&pwd=%s&learnerId=%s&lastName=%s&middleName=%s&firstName=%s" +
                                            "&SexDisplay=%s&birthDateString=%s&address=%s&email1=%s&mobile=%s&iDcard=%s",
                                    USERID, PASS, LEARNERID, fullname[0], fullname[1], fullname[2], learner.getSex(), learner.getBirthday(),
                                    learner.getAddress(), learner.getEmail(), learner.getMobilePhone(), learner.getiDcard()
                            ));
                            osw.flush();
                            BufferedReader br = new BufferedReader(
                                    new InputStreamReader(con.getInputStream()));
                            String temp = "";
                            while ((temp = br.readLine()) != null) sb.append(temp);
                            br.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        getInfo("http://" + mainUI.SERVER + "//TrainingCenter/serviceuserinfo.controller");
                    }
                }).start();
            }
        });
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer,
            toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        listRegcourse = new ArrayList<>();
        listFavcourse = new ArrayList<>();
        listCourse = new ArrayList<>();
        listExamResult = new ArrayList<>();
        listMessasge = new ArrayList<>();
        learner = new Learner();
        getAllData();

        showLoadingDialog();
    }

    private void showLoadingDialog() {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setIndeterminate(true);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setCancelable(false);
        pd.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (dataLoaded < 1) try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pd.dismiss();
                    }
                });
                Log.i("MyTag", "start load image, username, email");
                while (image_iv == null || username_tv == null
                    || email_tv == null || learner.getImage() == null)
                try {
                    Thread.sleep(200);
                    Log.i("MyTag", "loop image, username, email");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.i("MyTag", "load image, username, email");
                        try {
                            image_iv.setImageBitmap(BitmapFactory.decodeStream(
                                    new FileInputStream(getCacheDir() + File.separator + "avatar.png")));
                        } catch (IOException e) {
                            image_iv.setImageResource(learner.getSex().equals("Nam") ?
                                    R.drawable.male_default : R.drawable.female_default);
                        }
                        username_tv.setText(learner.getFullName());
                        email_tv.setText(learner.getEmail());
                    }
                });
            }
        }).start();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            startActivity(new Intent(mainUI.this, GoodbyeScreen.class));
            finish();
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.main_ui, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        fab.setVisibility(View.GONE);

        int id = item.getItemId();
        if (id == R.id.nav_info) {
            loadNavInfo();
        } else if (id == R.id.nav_regcourse) {
            loadNavRegCourse();
        } else if (id == R.id.nav_favcourse) {
            loadNavFavCourse();
        } else if (id == R.id.nav_announcement) {
            loadNavMessage();
        } else if (id == R.id.nav_course) {
            loadNavCourse();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void loadData(JSONObject learnerinfo) throws Exception {
        System.out.println("loadData "  + learnerinfo.toString());
        if (learnerinfo.has("result") && learnerinfo.getString("result").equals("succeed")) {
            System.out.println("loadding data ...");
            LEARNERID = learnerinfo.getString("learnerId");
            learner.setFullName(learnerinfo.getString("fullname"));
            learner.setSex(learnerinfo.getString("sex"));
            learner.setBirthday(learnerinfo.getString("birthday"));
            learner.setAddress(learnerinfo.getString("address"));
            learner.setMobilePhone(learnerinfo.getString("mobilephone"));
            learner.setEmail(learnerinfo.getString("email"));
            learner.setiDcard(learnerinfo.getString("CMND"));
            learner.setImage(learnerinfo.getString("image"));
            new Thread(new Runnable() {
                @Override
                public void run() {
                    saveImage("http://" + mainUI.SERVER + "/TrainingCenter/images/"
                        + learner.getImage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.i("MyTag", "load image, username, email");
                            try {
                                image_iv.setImageBitmap(BitmapFactory.decodeStream(
                                        new FileInputStream(getCacheDir() + File.separator + "avatar.png")));
                            } catch (IOException e) {
                                image_iv.setImageResource(learner.getSex().equals("Nam") ?
                                        R.drawable.male_default : R.drawable.female_default);
                            }
                            username_tv.setText(learner.getFullName());
                            email_tv.setText(learner.getEmail());
                        }
                    });
                }
            }).start();
            JSONArray ja = learnerinfo.getJSONArray("regcourse");
            System.out.println(ja.toString());
            for (int i = 0; i < ja.length(); i++) {
                JSONObject jo = ja.getJSONObject(i);
                System.out.println(jo.toString());
                Regcourse regcourse = new Regcourse();
                regcourse.setTitle(jo.getString("title"));
                regcourse.setTimeTable(jo.getString("timetable"));
                regcourse.setOpenDay(jo.getString("openday"));
                regcourse.setRoom(jo.getString("room"));
                regcourse.setFee(jo.getString("fee"));
                regcourse.setFeeStatus(jo.getString("feestatus"));
                System.out.println(regcourse);
                listRegcourse.add(regcourse);
            }

            ja = learnerinfo.getJSONArray("favcourse");
            System.out.println(ja.toString());
            for (int i = 0; i < ja.length(); i++) {
                JSONObject jo = ja.getJSONObject(i);
                System.out.println(jo.toString());
                Favcourse favcourse = new Favcourse();
                favcourse.setTitle(jo.getString("title"));
                favcourse.setTime(jo.getString("time"));
                favcourse.setFee(jo.getString("fee"));
                favcourse.setGroup(jo.getString("group"));
                favcourse.setDescription(jo.getString("description"));
                System.out.println(favcourse);
                listFavcourse.add(favcourse);
            }
        } else if (learnerinfo.has("course")) {
            System.out.println("parsing course info");
            JSONArray ja = learnerinfo.getJSONArray("course");
            System.out.println(ja.toString());
            for (int i = 0; i < ja.length(); i++) {
                ShowCourse sc = new ShowCourse();
                JSONObject jo = ja.getJSONObject(i);
                System.out.println(jo.toString());
                sc.setTitle(jo.getString("title"));
                sc.setDescription(jo.getString("description"));
                JSONArray joja = jo.getJSONArray("class");
                System.out.println(joja.toString());
                for (int j = 0; j < joja.length(); j++) {
                    JSONObject o = joja.getJSONObject(j);
                    Regcourse rc = new Regcourse();
                    rc.setTimeTable(o.getString("timetable"));
                    rc.setRoom(o.getString("room"));
                    rc.setOpenDay(o.getString("openday"));
                    rc.setFee(o.getString("fee"));
                    sc.getList().add(rc);
                }
                listCourse.add(sc);
            }
        } else if (learnerinfo.has("examresult")) {
            JSONArray ja = learnerinfo.getJSONArray("examresult");
            System.out.println(ja.toString());
            for (int i = 0; i < ja.length(); i++) {
                JSONObject jo = ja.getJSONObject(i);
                System.out.println(jo.toString());
                ExamResult examresult = new ExamResult();
                examresult.setCourseName(jo.getString("coursename"));
                examresult.setMark(jo.getString("schedule"));
                examresult.setExamDate(jo.getString("examdate"));
                examresult.setOpenDay(jo.getString("openday"));
                examresult.setSchedule(jo.getString("schedule"));
                System.out.println(examresult);
                listExamResult.add(examresult);
            }
        } else if (learnerinfo.has("message")) {
            listMessasge.clear();
            JSONArray ja = learnerinfo.getJSONArray("message");
            System.out.println(ja.toString());
            for (int i = 0; i < ja.length(); i++) {
                JSONObject jo = ja.getJSONObject(i);
                System.out.println(jo.toString());
                String msg = "Ngày gửi : " + jo.getString("sendday") + "\n"
                    + "Nội dung : " + jo.getString("msg");
                listMessasge.add(msg);
            }
        }
        dataLoaded++;
    }

    private void getAllData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    getInfo("http://" + mainUI.SERVER + "//TrainingCenter/serviceuserinfo.controller");
                    while (dataLoaded < 1) Thread.sleep(200);
                    getInfo("http://" + mainUI.SERVER + "/TrainingCenter/servicelistcourse.controller");
                    while (dataLoaded < 2) Thread.sleep(200);
                    getInfo("http://" + mainUI.SERVER + "/TrainingCenter/servicemessage.controller");
                    while (dataLoaded < 3) Thread.sleep(200);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void getInfo(final String url) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final StringBuilder sb = new StringBuilder("");
                    HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
                    con.setRequestMethod("POST");
                    con.setDoOutput(true);
                    con.setConnectTimeout(3000);
                    OutputStreamWriter osw = new OutputStreamWriter(con.getOutputStream());
                    osw.write("userid=" + USERID + "&pwd=" + PASS + "&learnerId" + LEARNERID);
                    osw.flush();
                    BufferedReader br = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));
                    String temp = "";
                    while ((temp = br.readLine()) != null) sb.append(temp);
                    br.close();
                    System.out.println("json result " + sb.toString());
                    loadData(new JSONObject(sb.toString()));
                } catch (Exception e) {
                    e.printStackTrace();
                    getInfo(url);
                }
            }
        }).start();
    }

    private void loadNavMessage() {
        getInfo("http://" + mainUI.SERVER + "/TrainingCenter/servicemessage.controller");
        content.removeAllViews();
        ListView list = new ListView(this);
        list.setAdapter(new ArrayAdapter<String>(
            this, android.R.layout.simple_list_item_1, listMessasge));
//        list.setAdapter(new BaseAdapter() {
//            @Override
//            public int getCount() {
//                return listFavcourse.size();
//            }
//
//            @Override
//            public Object getItem(int i) {
//                return listFavcourse.get(i);
//            }
//
//            @Override
//            public long getItemId(int i) {
//                return 0;
//            }
//
//            @Override
//            public View getView(int i, View view, ViewGroup viewGroup) {
//                if (view == null)
//                    view = View.inflate(mainUI.this, R.layout.layout_message, null);
//
//                EditText et_coursename = (EditText) view.findViewById(R.id.et_coursename);
//                EditText et_group = (EditText) view.findViewById(R.id.et_group);
//                EditText et_fee_favcourse = (EditText) view.findViewById(R.id.et_fee_favcourse);
//                EditText et_time = (EditText) view.findViewById(R.id.et_time);
//                EditText et_description = (EditText) view.findViewById(R.id.et_description);
//
//                ExamResult fav = listExamResult.get(i);
//                et_coursename.setText(fav.getTitle());
//                et_group.setText(fav.getGroup());
//                et_fee_favcourse.setText(fav.getFee());
//                et_time.setText(fav.getTime());
//                et_description.setText(fav.getDescription());
//
//                return view;
//            }
//        });
        content.addView(list);
    }

    private void loadNavFavCourse() {
        content.removeAllViews();
        ListView list = new ListView(this);
        list.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return listFavcourse.size();
            }

            @Override
            public Object getItem(int i) {
                return listFavcourse.get(i);
            }

            @Override
            public long getItemId(int i) {
                return 0;
            }

            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {
                if (view == null)
                    view = View.inflate(mainUI.this, R.layout.layout_favcourse, null);

                EditText et_coursename = (EditText) view.findViewById(R.id.et_coursename);
                EditText et_group = (EditText) view.findViewById(R.id.et_group);
                EditText et_fee_favcourse = (EditText) view.findViewById(R.id.et_fee_favcourse);
                EditText et_time = (EditText) view.findViewById(R.id.et_time);
                EditText et_description = (EditText) view.findViewById(R.id.et_description);

                Favcourse fav = listFavcourse.get(i);
                et_coursename.setText(fav.getTitle());
                et_group.setText(fav.getGroup());
                et_fee_favcourse.setText(fav.getFee());
                et_time.setText(fav.getTime());
                et_description.setText(fav.getDescription());

                return view;
            }
        });
        content.addView(list);
        System.out.println("list fav course " + listFavcourse.size());
    }

    private void loadNavRegCourse() {
        content.removeAllViews();
        ListView lv = new ListView(this);
        lv.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return listRegcourse.size();
            }

            @Override
            public Object getItem(int i) {
                return listRegcourse.get(i);
            }

            @Override
            public long getItemId(int i) {
                return 0;
            }

            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {
                if (view == null)
                    view = View.inflate(mainUI.this, R.layout.layout_learnerregcourse, null);

                EditText et_coursename = (EditText) view.findViewById(R.id.et_coursename);
                EditText et_schedule = (EditText) view.findViewById(R.id.et_schedule);
                EditText et_room = (EditText) view.findViewById(R.id.et_room);
                EditText et_openingday = (EditText) view.findViewById(R.id.et_openingday);
                EditText et_fee = (EditText) view.findViewById(R.id.et_fee);
                EditText et_feestatus = (EditText) view.findViewById(R.id.et_feestatus);

                Regcourse regc = listRegcourse.get(i);
                et_coursename.setText(regc.getTitle());
                et_schedule.setText(regc.getTimeTable());
                et_room.setText(regc.getRoom());
                et_openingday.setText(regc.getOpenDay());
                et_fee.setText(regc.getFee());
                et_feestatus.setText(regc.getFeeStatus());

                return view;
            }
        });
        content.addView(lv);
    }

    private boolean saveImage(String url) {
        try {
            File f = new File(getCacheDir() + File.separator + "avatar.png");
            if (f.exists()) f.delete();
            BitmapFactory.decodeStream(new URL(url).openStream())
                .compress(Bitmap.CompressFormat.PNG, 10, new FileOutputStream(f));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void loadNavInfo() {
        content.removeAllViews();
        content.addView(View.inflate(this, R.layout.layout_learnerinfo, null));
        EditText et_fullname = (EditText) content.findViewById(R.id.et_fullname);
        Spinner sp_sex = (Spinner) content.findViewById(R.id.sp_sex);
        EditText et_birthday = (EditText) content.findViewById(R.id.et_birthday);
        EditText et_address = (EditText) content.findViewById(R.id.et_address);
        EditText et_phone = (EditText) content.findViewById(R.id.et_phone);
        EditText et_email = (EditText) content.findViewById(R.id.et_email);
        EditText et_idcard = (EditText) content.findViewById(R.id.et_idcard);
        fab.setVisibility(View.VISIBLE);
        sp_sex.setAdapter(new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, new String[]{"Nam", "Nữ"}));
        if (learner.getImage() == null) return;
        et_fullname.setText(learner.getFullName());
        sp_sex.setSelection((learner.getSex().equals("Nam") ? 0 : 1), true);
        et_birthday.setText(learner.getBirthday());
        et_address.setText(learner.getAddress());
        et_phone.setText(learner.getMobilePhone());
        et_email.setText(learner.getEmail());
        et_idcard.setText(learner.getiDcard());

        et_fullname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                learner.setFullName(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        sp_sex.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                learner.setSex((i == 0) ? "Nam" : "Nữ");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        et_birthday.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                learner.setBirthday(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        et_address.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                learner.setAddress(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        et_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                learner.setMobilePhone(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        et_email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                learner.setEmail(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        et_idcard.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                learner.setiDcard(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private int selectedCourse = -1;
    private void loadNavCourse() {
        content.removeAllViews();
        content.addView(View.inflate(this, R.layout.layout_course, null));
        Spinner sp_course = (Spinner) content.findViewById(R.id.sp_course);
        final TextView tv_desc_course = (TextView) content.findViewById(R.id.tv_desc_course);
        final ListView lv = (ListView) content.findViewById(R.id.lv_classofcourse);
        final Button btn = (Button) content.findViewById(R.id.btn_regcourse);

        final List<String> listtitle = new ArrayList<>();
        for (int i = 0; i < listCourse.size(); i++)
            listtitle.add(listCourse.get(i).getTitle());
        sp_course.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return listtitle.size();
            }

            @Override
            public Object getItem(int i) {
                return listtitle.get(i);
            }

            @Override
            public long getItemId(int i) {
                return 0;
            }

            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {
                if (view == null)
                    view = View.inflate(mainUI.this, android.R.layout.simple_list_item_1, null);
                TextView tv = (TextView)view;
                tv.setText(listtitle.get(i));
                return view;
            }
        });

        sp_course.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                tv_desc_course.setText(listCourse.get(i).getDescription());
                final List<Regcourse> list = listCourse.get(i).getList();
                lv.setAdapter(new BaseAdapter() {
                    List<View> listview = new ArrayList<View>();

                    @Override
                    public int getCount() {
                        return list.size();
                    }

                    @Override
                    public Object getItem(int i) {
                        return list.get(i);
                    }

                    @Override
                    public long getItemId(int i) {
                        return 0;
                    }

                    @Override
                    public View getView(final int i, View view, ViewGroup viewGroup) {
                        if (view == null) {
                            view = View.inflate(mainUI.this, R.layout.layout_classcourse, null);
                            view.setBackgroundColor(Color.GRAY);
                        }
                        if (listview.size() < list.size()) listview.add(view);
                        EditText et_schedule = (EditText) view.findViewById(R.id.et_schedule);
                        EditText et_room = (EditText) view.findViewById(R.id.et_room);
                        EditText et_openingday = (EditText) view.findViewById(R.id.et_openingday);
                        EditText et_fee = (EditText) view.findViewById(R.id.et_fee);

                        Regcourse regc = list.get(i);
                        et_schedule.setText(regc.getTimeTable());
                        et_room.setText(regc.getRoom());
                        et_openingday.setText(regc.getOpenDay());
                        et_fee.setText(regc.getFee());

                        int textviewid_array[] = {R.id.tv_schedule, R.id.tv_room,
                            R.id.tv_openingday, R.id.tv_fee};
                        final View finalView = view;
                        for (int id : textviewid_array) {
                            ((TextView)view.findViewById(id)).setOnClickListener(
                                    new View.OnClickListener() {
                                @Override
                                public void onClick(View textview) {
                                    if (selectedCourse != i)
                                        for (int j = 0;  j < listview.size(); j++)
                                            listview.get(j).setBackgroundColor(Color.GRAY);
                                    finalView.setBackgroundColor((selectedCourse == i) ?
                                            Color.GRAY : Color.BLACK);
                                    selectedCourse = (selectedCourse == i) ? -1 : i;
                                }
                            });
                        }
                        return view;
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final StringBuilder sb = new StringBuilder("");
                        try {
                            if (selectedCourse < 0) return;
                            HttpURLConnection con = (HttpURLConnection) new URL(
                                "http://" + mainUI.SERVER + "/TrainingCenter/serviceregisterclasses.controller")
                                    .openConnection();
                            con.setRequestMethod("POST");
                            con.setDoOutput(true);
                            con.setConnectTimeout(3000);
                            OutputStreamWriter osw = new OutputStreamWriter(con.getOutputStream());
                            osw.write("title=" + USERID + "&description="
                                + tv_desc_course.getText().toString() + "&createdDate=" + selectedCourse);
                            System.out.println("title=" + USERID + "&description="
                                + tv_desc_course.getText().toString() + "&createdDate=" + selectedCourse);
                            Log.i("Mytag", "title=" + USERID + "&description="
                                    + tv_desc_course.getText().toString() + "&createdDate=" + selectedCourse);
                            osw.flush();
                            BufferedReader br = new BufferedReader(
                                    new InputStreamReader(con.getInputStream()));
                            String temp = "";
                            while ((temp = br.readLine()) != null) sb.append(temp);
                            br.close();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(mainUI.this, sb.toString(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        getInfo("http://" + mainUI.SERVER + "//TrainingCenter/serviceuserinfo.controller");
                    }
                }).start();
            }
        });
    }
}
