package specialmuffins.huji.hackathon.happyreminder.firebase_services;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import specialmuffins.huji.hackathon.happyreminder.Info;
import specialmuffins.huji.hackathon.happyreminder.firebase_db.FireBaseDBManager;
import specialmuffins.huji.hackathon.happyreminder.firebase_db.entity.PhoneEntity;

/**
 * Created by elyasaf on 18-May-17.
 */

public class FirebasePhoneIdService extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.

        updateIfNeeded();
    }

    public static void updateIfNeeded() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d("firebase_service", "Refreshed token: " + refreshedToken);
        if (refreshedToken == null) return;
        PhoneEntity phone = new PhoneEntity(Info.constId, refreshedToken);
        FireBaseDBManager.getManager().updatePhoneNumber(phone);
    }
}
