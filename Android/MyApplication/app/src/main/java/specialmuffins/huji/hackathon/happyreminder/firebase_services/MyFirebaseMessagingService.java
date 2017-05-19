package specialmuffins.huji.hackathon.happyreminder.firebase_services;

import android.os.Handler;
import android.os.Vibrator;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import specialmuffins.huji.hackathon.happyreminder.Info;
import specialmuffins.huji.hackathon.happyreminder.OneSkeletonActivity;
import specialmuffins.huji.hackathon.happyreminder.R;
import specialmuffins.huji.hackathon.happyreminder.firebase_db.FireBaseDBManager;
import specialmuffins.huji.hackathon.happyreminder.firebase_db.entity.SkeletonAlert;

/**
 * Created by elyasaf on 18-May-17.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        String skeletonId = remoteMessage.getData().get("skeletonId");

        Context app = getApplicationContext();
        Info.init(app); // the const is needed for firebase!
        FirebaseApp.initializeApp(app);
        FireBaseDBManager.init(app);
        FirebasePhoneIdService.updateIfNeeded();


        FireBaseDBManager.getManager().getSkeleton(skeletonId, new FireBaseDBManager.SkeletonReadyCallback() {
            @Override
            public void onSkeletonReady(SkeletonAlert skeletonAlert) {
                NotificationCompat.Builder myB = new NotificationCompat.Builder(getApplicationContext());

                Intent result = new Intent(getApplicationContext(), OneSkeletonActivity.class);
                TaskStackBuilder stackBuilder = TaskStackBuilder.create(getApplicationContext());
                stackBuilder.addParentStack(OneSkeletonActivity.class);
                stackBuilder.addNextIntent(result);
                PendingIntent resultPending = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
                myB.setContentIntent(resultPending);

                myB.setSmallIcon(R.mipmap.ic_launcher);
                myB.setContentText(skeletonAlert.reminderTxt);
                myB.setContentTitle("Reminder!");

                NotificationManager mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                mManager.notify(mNotificationId, myB.build());
                mNotificationId++;
                OneSkeletonActivity.skeletonAlertToWorkWith = skeletonAlert;
                OneSkeletonActivity.isSkeletonNew = false;

                vibrate();
            }
        });
    }

    private void vibrate() {

        final Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(400);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                v.vibrate(1200);
            }
        }, 650);
    }

    static int mNotificationId = 001;
}
