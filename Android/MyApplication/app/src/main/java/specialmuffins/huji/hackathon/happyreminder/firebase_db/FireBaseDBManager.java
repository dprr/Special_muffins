package specialmuffins.huji.hackathon.happyreminder.firebase_db;

import android.content.Context;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import specialmuffins.huji.hackathon.happyreminder.Info;
import specialmuffins.huji.hackathon.happyreminder.firebase_db.entity.PhoneEntity;
import specialmuffins.huji.hackathon.happyreminder.firebase_db.entity.SkeletonAlert;

/**
 * Created by Elisheva on 18-May-17.
 */

public class FireBaseDBManager {


    private static FireBaseDBManager manager = null;

    public static void init(Context context) {
        manager = new FireBaseDBManager(context);
    }

    public static FireBaseDBManager getManager() {
        return manager;
    }


    // ~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*
    private DatabaseReference db;

    private FireBaseDBManager(Context context) {
        allSkeletons = new HashMap<>();
        db = FirebaseDatabase.getInstance().getReference();
        db.child("skeleton")
                .orderByChild("constPhoneId")
                .equalTo(Info.constId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for (DataSnapshot snapshotChild : dataSnapshot.getChildren()) {
                            SkeletonAlert alert = snapshotChild.getValue(SkeletonAlert.class);
                            allSkeletons.put(snapshotChild.getKey(), alert);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    private Map<String, SkeletonAlert> allSkeletons;

    public Map<String, SkeletonAlert> getAllSkeletons() {
        return allSkeletons;
    }

    public interface AddSkeletonCallback {
        void onSkeletonAdded(SkeletonAlert finished, String newSkeletonId);
    }

    public void addSkeleton(final SkeletonAlert newbie, final AddSkeletonCallback callback) {
        final String uniqueSkeletonId = db.child("skeleton").push().getKey();
        HashMap<String, Object> updates = new HashMap<>();
        updates.put("/skeleton/" + uniqueSkeletonId, newbie);
        updates.put("/newAddedSkeleton/" + uniqueSkeletonId, uniqueSkeletonId);
        db.updateChildren(updates, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                callback.onSkeletonAdded(newbie, uniqueSkeletonId);
            }
        });
    }


    public void updatePhoneNumber(PhoneEntity toUpdate) {
        Map<String, Object> mapToUpdate = new HashMap<>();
        mapToUpdate.put(toUpdate.constPhoneId, toUpdate);

        db.child("phone").updateChildren(mapToUpdate);
    }

    public interface SkeletonReadyCallback
    {
        void onSkeletonReady(SkeletonAlert skeletonAlert);
    }

    public void getSkeleton(String skeletonId, final SkeletonReadyCallback callback)
    {
        db.child("skeleton").child(skeletonId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                SkeletonAlert fromServer = dataSnapshot.getValue(SkeletonAlert.class);
                callback.onSkeletonReady(fromServer);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}