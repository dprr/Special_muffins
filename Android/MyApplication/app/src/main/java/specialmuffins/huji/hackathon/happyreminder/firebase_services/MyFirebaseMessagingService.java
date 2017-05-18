package specialmuffins.huji.hackathon.happyreminder.firebase_services;


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

        // todo get the unique string

        String uniqueString;
        SkeletonAlert a = FireBaseDBManager.getManager().getAllSkeletons().get(uniqueString);

        // todo build notification to screen
    }
}
