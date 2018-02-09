package lab.and401.nhut.and401lab10_notifications;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.RemoteInput;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        int [] btnid = {R.id.btn_maxprior, R.id.btn_highprior, R.id.btn_lowprior, R.id.btn_minprior,
                        R.id.btn_defaultprior, R.id.btn_oldnotification, R.id.btn_bigtext,
                        R.id.btn_bigpicture, R.id.btn_inboxtype, R.id.btn_nougatnotification};
        for (int id : btnid) ((Button)findViewById(id)).setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btn_maxprior:
                showPriorityNewNotification("new max priority notification", Notification.PRIORITY_MAX);
                break;
            case R.id.btn_highprior:
                showPriorityNewNotification("new high priority notification", Notification.PRIORITY_HIGH);
                break;
            case R.id.btn_lowprior:
                showPriorityNewNotification("new low priority notification", Notification.PRIORITY_LOW);
                break;
            case R.id.btn_minprior:
                showPriorityNewNotification("new min priority notification", Notification.PRIORITY_MIN);
                break;
            case R.id.btn_defaultprior:
                showPriorityNewNotification("new default priority notification", Notification.PRIORITY_DEFAULT);
                break;
            case R.id.btn_oldnotification:
                showOldStyleNotification();
                break;
            case R.id.btn_bigtext:
                showBigTextOldNotification();
                break;
            case R.id.btn_bigpicture:
                showBigPictureNewNotification();
                break;
            case R.id.btn_inboxtype:
                showInboxStyleNewNotification();
                break;
            case R.id.btn_nougatnotification:
                showNougatNotification();
                break;
            default:
                break;
        }
    }
    private int NOTIFICATION_ID = 1;
    private void showPriorityNewNotification(String title, int priority) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            Notification.Builder builder = new Notification.Builder(this)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setWhen(System.currentTimeMillis())
                    .setContentText("Android notification")
                    .setContentTitle(title)
                    .setPriority(priority);
            ((NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE))
                    .notify(NOTIFICATION_ID++, builder.build());
        }
    }

    private void showBigPictureNewNotification() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            Bitmap icon = BitmapFactory.decodeResource(this.getResources(), R.drawable.big_picture);
            Notification.Builder builder = new Notification.Builder(this)
                    .setWhen(System.currentTimeMillis())
                    .setContentText("Reduced content")
                    .setContentTitle("Reduced big picture title")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setLargeIcon(icon)
                    .setStyle(new Notification.BigPictureStyle().bigPicture(icon));
            ((NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE))
                    .notify(NOTIFICATION_ID++, builder.build());
        }
    }

    private void showInboxStyleNewNotification() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            Notification.Builder builder = new Notification.Builder(this)
                .setWhen(System.currentTimeMillis())
                .setContentText("Android ATC update")
                .setContentTitle("2 new mails")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setStyle(new Notification.InboxStyle()
                    .addLine("Android ATC update")
                    .addLine("New Android opportunities")
                    .setSummaryText("+1 more"));
            ((NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE))
                    .notify(NOTIFICATION_ID++, builder.build());
        }
    }

    private void showBigTextOldNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("Big text")
            .setContentText("Android ATC has its main objective ...")
            .setDefaults(Notification.DEFAULT_ALL)
            .setStyle(new NotificationCompat.BigTextStyle().bigText(getString(R.string.summary_text)));
        ((NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE))
                .notify(NOTIFICATION_ID++, builder.build());
    }

    private void showOldStyleNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("old style")
            .setContentText("Android ATC has its main objective ...");
        ((NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE))
                .notify(NOTIFICATION_ID++, builder.build());
    }

    private final int NOUGAT = 24;
    private void showNougatNotification() {
        Notification.Builder builder = null;
        if (android.os.Build.VERSION.SDK_INT >= 24) {
            builder = new Notification.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Billy wonka")
                .setContentText("hey, want to go for dinner tonight?")
                .addAction(
                    new Notification.Action.Builder(R.mipmap.ic_launcher,
                    "Reply ...", PendingIntent.getActivity(this, 0,
                    new Intent(this, MainActivity.class),
                    PendingIntent.FLAG_UPDATE_CURRENT))
                        .addRemoteInput(new RemoteInput.Builder("key text reply").setLabel("").build())
                        .build()
                );
            ((NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE))
                    .notify(NOTIFICATION_ID++, builder.build());
        }
    }
}
