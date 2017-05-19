package specialmuffins.huji.hackathon.happyreminder.firebase_services;

import android.app.NotificationManager;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

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
        SkeletonAlert alert = FireBaseDBManager.getManager().getAllSkeletons().get(skeletonId);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this).setContentTitle(alert.reminderTxt);
        int mNotificationId = 001;
        NotificationManager mNotifyMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mNotifyMgr.notify(mNotificationId, mBuilder.build());
    }
}
