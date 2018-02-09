package lab.and401.nhut.and401lab10_lockscreennotification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.session.MediaController;
import android.media.session.MediaSession;
import android.media.session.MediaSessionManager;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Nhut on 6/29/2017.
 */

public class MediaPlayerService extends Service {
    public static final String ACTION_PLAY = "action_play";
    public static final String ACTION_PAUSE = "action_pause";
    public static final String ACTION_REWIND = "action_rewind";
    public static final String ACTION_FAST_FORWARD = "action_forward";
    public static final String ACTION_NEXT = "action_next";
    public static final String ACTION_PREVIOUS = "action_previous";
    public static final String ACTION_STOP = "action_stop";

    private MediaPlayer mMediaPlayer;
    private MediaSessionManager mManager;
    private MediaSession mediaSession;
    private MediaController mediaController;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("MediaPlayerService", "onCreate");
        Toast.makeText(this, "My Service created", Toast.LENGTH_SHORT).show();
        mMediaPlayer = MediaPlayer.create(this, R.raw.sound);
        mMediaPlayer.setLooping(false);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("MediaPlayerService", "onStartCommand");
        if (mManager == null) initMediaSessions();
        handleIntent(intent);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.e("MediaPlayerService", "onDestroy");
        super.onDestroy();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.e("MediaPlayerService", "onUnbind");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            mediaSession.release();
        return super.onUnbind(intent);
    }

    private void handleIntent(Intent intent) {
        if (intent == null || intent.getAction() == null) return;
        String action = intent.getAction();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (action.equalsIgnoreCase(ACTION_PLAY)) {
                mediaController.getTransportControls().play();
//            } else if (action.equalsIgnoreCase(ACTION_PAUSE)) {
//                mediaController.getTransportControls().pause();
//            } else if (action.equalsIgnoreCase(ACTION_REWIND)) {
//                mediaController.getTransportControls().rewind();
//            } else if (action.equalsIgnoreCase(ACTION_FAST_FORWARD)) {
//                mediaController.getTransportControls().fastForward();
//            } else if (action.equalsIgnoreCase(ACTION_NEXT)) {
//                mediaController.getTransportControls().skipToNext();
//            } else if (action.equalsIgnoreCase(ACTION_PREVIOUS)) {
//                mediaController.getTransportControls().skipToPrevious();
            } else if (action.equalsIgnoreCase(ACTION_STOP)) {
                mediaController.getTransportControls().stop();
            }
        }
    }

    private void buildNotification(String title, String intentAction) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Notification.Builder builder = new Notification.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Media title")
                .setContentText("Media artist")
                .setDeleteIntent(PendingIntent.getService(this, 1,
                    new Intent(this, MediaPlayerService.class).setAction(ACTION_STOP), 0))
                .setStyle(new Notification.MediaStyle())
                .addAction(
                    new Notification.Action.Builder(
                    android.R.drawable.ic_media_play,
                    title,
                    PendingIntent.getService(this, 1,
                        new Intent(this, MediaPlayerService.class).setAction(intentAction)
                        , 0)
                    ).build()
                );
            ((NotificationManager)getSystemService(NOTIFICATION_SERVICE)).notify(1, builder.build());
        }
    }

    private void initMediaSessions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mediaSession = new MediaSession(this, "simple player session");
            mediaController = new MediaController(this, mediaSession.getSessionToken());
            mediaSession.setCallback(new MediaSession.Callback() {
                @Override
                public void onPlay() {
                    super.onPlay();
                    Log.e("MediaSession", "onPlay");
                    if (mMediaPlayer != null && !mMediaPlayer.isPlaying())
                        mMediaPlayer.start();
                        buildNotification("Play", ACTION_PLAY);
                }

                @Override
                public void onStop() {
                    super.onStop();
                    Log.e("MediaSession", "onStop");
                    mMediaPlayer.stop();
                    ((NotificationManager)getSystemService(NOTIFICATION_SERVICE)).cancel(1);
                    stopService(new Intent(getApplicationContext(), MediaPlayerService.class));
                }

//                @Override
//                public void onPause() {
//                    super.onPause();
//                    Log.e("MediaSession", "onPause");
//                    if (mMediaPlayer != null && mMediaPlayer.isPlaying()) mMediaPlayer.pause();
//                    buildNotification("Pause", ACTION_PAUSE);
//                }
//
//                @Override
//                public void onSeekTo(long pos) {
//                    mMediaPlayer.seekTo((int) pos);
//                    super.onSeekTo(pos);
//                }
//
//                @Override
//                public void onSetRating(Rating rating) {
//                    super.onSetRating(rating);
//                }
//
//                @Override
//                public void onSkipToNext() {
//                    super.onSkipToNext();
//                    Log.e("MediaPlayerService", "onSkipToNext");
//                }
//
//                @Override
//                public void onSkipToPrevious() {
//                    super.onSkipToPrevious();
//                    Log.e("MediaPlayerService", "onSkipToPrevious");
//                }
//
//                @Override
//                public void onFastForward() {
//                    super.onFastForward();
//                    Log.e("MediaPlayerService", "onFastForward");
//                    int currentPos = mMediaPlayer.getCurrentPosition();
//                    int max = mMediaPlayer.getDuration();
//                    mMediaPlayer.seekTo(currentPos < max + 3 ? currentPos + 3 : max);
//                }
//
//                @Override
//                public void onRewind() {
//                    super.onRewind();
//                    Log.e("MediaPlayerService", "onRewind");
//                    int currentPos = mMediaPlayer.getCurrentPosition();
//                    mMediaPlayer.seekTo(currentPos > 3 ? currentPos - 3 : 0);
//                }
//
            });
        }
    }
}
