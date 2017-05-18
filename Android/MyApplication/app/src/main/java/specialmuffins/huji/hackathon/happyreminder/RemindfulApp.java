package specialmuffins.huji.hackathon.happyreminder;

import android.app.Application;
import android.util.Log;

import com.google.firebase.FirebaseApp;

import specialmuffins.huji.hackathon.happyreminder.firebase_db.FireBaseDBManager;
import specialmuffins.huji.hackathon.happyreminder.firebase_services.FirebasePhoneIdService;


/**
 * Created by reem on 18/05/17.
 */

public class RemindfulApp extends Application {
    private static final String TAG = "app";

    @Override
    public void onCreate() {
        super.onCreate();
        Info.init(this); // the const is needed for firebase!

        FirebaseApp.initializeApp(this);
        FireBaseDBManager.init(this);
        FirebasePhoneIdService.updateIfNeeded();

        Log.d(TAG, "created!");
    }
}
