package specialmuffins.huji.hackathon.happyreminder.firebase_db;

import android.content.Context;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    public static FireBaseDBManager getManager() { return manager;}


    // ~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*
    private DatabaseReference db;

    private FireBaseDBManager(Context context){
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




    //
//    @Override
//    public List<Notification> getAll(String pid) {
//
//        return null;
//    }
//
//    @Override
//    public void getNf(String pid, String nfId, final WorkWithNotification callback) {
//        Log.d("DB MANAGER", "starting get()");
//        db.child("nfInfo").child(nfId).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                Notification nf = dataSnapshot.getValue(Notification.class);
//                callback.onNotificationReady(nf);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//    }
//
//
//    @Override
//    public int addNf(String pid, NotificationToAdd nf) {
//        String newKey = db.child("newNf").push().getKey();
//
//        db.child("newNf").child(newKey).setValue(nf);
//
//        return 0;
//    }
//
//    @Override
//    public int deleteNf(String pid, String nfId) {
//        String newKey = db.child("deleteNf").push().getKey();
//        String val = nfId+" : " + 0;
//        db.child("deleteNf").child(nfId).setValue(0);
//
//        return 0;
//    }
//
//    @Override
//    public int changeNf(String pid, NotificationToAdd nf, String nfId) {
//        //just delete and add new, nothing too bright..
//        deleteNf(pid, nfId);
//        addNf(pid, nf);
//        return 0;
//    }
//
//    @Override
//    public int addPhone(String pid) {
//        db.child("newP").child(pid).setValue(0);
//        return 0;
//    }
//
//    @Override
//    public int changeCurPId(String pid, String CurPId) {
//        db.child("pId").child(pid).child(CurPId).setValue(CurPId);
//        return 0;
//    }
}
