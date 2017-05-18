package specialmuffins.huji.hackathon.happyreminder;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class SplashScreen extends AppCompatActivity {

    Button pushMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);
        pushMe = (Button) findViewById(R.id.button3);
        pushMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("note", "it was pressed");
                Toast.makeText(getApplicationContext(), "Soon", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
