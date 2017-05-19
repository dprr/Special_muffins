package specialmuffins.huji.hackathon.happyreminder.activity_main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

import specialmuffins.huji.hackathon.happyreminder.OneSkeletonActivity;
import specialmuffins.huji.hackathon.happyreminder.R;
import specialmuffins.huji.hackathon.happyreminder.firebase_db.FireBaseDBManager;
import specialmuffins.huji.hackathon.happyreminder.firebase_db.entity.SkeletonAlert;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int REQUEST_START_ACTIVITY_WORK_ON_SKELETON = 678;


    SkeletonAlertAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "created!");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.main_rcv_skeletons);

        mAdapter = new SkeletonAlertAdapter(new ArrayList<>(FireBaseDBManager.getManager().getAllSkeletons().values()), this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        View plusView = findViewById(R.id.main_fab);
        plusView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(MainActivity.this, OneSkeletonActivity.class), REQUEST_START_ACTIVITY_WORK_ON_SKELETON);
                OneSkeletonActivity.skeletonAlertToWorkWith = new SkeletonAlert();
                OneSkeletonActivity.isSkeletonNew = true;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_START_ACTIVITY_WORK_ON_SKELETON) {
            mAdapter.updateSkeletons(new ArrayList<SkeletonAlert>(FireBaseDBManager.getManager().getAllSkeletons().values()));
        }
        super.onActivityResult(requestCode, resultCode, data);

    }
}
