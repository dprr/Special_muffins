package specialmuffins.huji.hackathon.happyreminder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "created!");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
