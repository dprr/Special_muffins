package specialmuffins.huji.hackathon.happyreminder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import specialmuffins.huji.hackathon.happyreminder.firebase_db.FireBaseDBManager;
import specialmuffins.huji.hackathon.happyreminder.firebase_db.Json_API;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FireBaseDBManager.init(this);

        Log.d(TAG, "created!");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //daniel

//        Json_API.NotificationToAdd a = new Json_API.NotificationToAdd();
//        a.body = "yuhu!";
//        a.name = "dddd";
//        a.pid = "asdansod";
//        a.time = "7.fri.2030.2200";
//        FireBaseDBManager.getManager().addNf(null, a);

        FireBaseDBManager.getManager().deleteNf(null, "demo");

        FireBaseDBManager.getManager().addPhone("does it woik?");

        FireBaseDBManager.getManager().changeCurPId("pid2", "cur pid 2");

        FireBaseDBManager.getManager().getNf("aaa", "dnfId", new Json_API.WorkWithNotification() {
            @Override
            public void onNotificationReady(Json_API.Notification nc) {
                Log.d(TAG, "notification ready!");
                Toast.makeText(getApplicationContext(), nc.body, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
