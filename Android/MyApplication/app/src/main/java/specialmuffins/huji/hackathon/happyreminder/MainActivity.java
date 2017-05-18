package specialmuffins.huji.hackathon.happyreminder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button pushMe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pushMe = (Button)findViewById(R.id.pressButton);
        pushMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Log.d("note", "it was pressed");
                Toast.makeText(getApplicationContext(), "Yes", Toast.LENGTH_SHORT).show();

            }
        });
        //daniel
    }
}
