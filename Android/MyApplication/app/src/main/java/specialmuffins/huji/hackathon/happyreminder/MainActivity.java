package specialmuffins.huji.hackathon.happyreminder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import specialmuffins.huji.hackathon.happyreminder.firebase_db.FireBaseDBManager;
import specialmuffins.huji.hackathon.happyreminder.firebase_db.Json_API;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FireBaseDBManager.init(this);

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

        FireBaseDBManager.getManager().getNf("aaa", "dnfId", new Json_API.WorkWithNotification() {
            @Override
            public void onNotificationReady(Json_API.Notification nc) {
                Toast.makeText(getApplicationContext(), nc.body, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
