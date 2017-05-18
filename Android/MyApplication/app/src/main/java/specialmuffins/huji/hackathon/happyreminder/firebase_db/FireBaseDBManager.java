package specialmuffins.huji.hackathon.happyreminder.firebase_db;

import android.content.Context;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

/**
 * Created by Elisheva on 18-May-17.
 */

public class FireBaseDBManager implements Json_API{


    private static FireBaseDBManager manager = null;
    public static void init(Context context) {
        manager = new FireBaseDBManager();
    }
    public static FireBaseDBManager getManager() { return manager;}


    // ~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*
    private DatabaseReference db;

    private FireBaseDBManager(){
        db = FirebaseDatabase.getInstance().getReference();
    }


    @Override
    public List<Notification> getAll(String pid) {
        return null;
    }

    @Override
    public void getNf(String pid, String nfId, final WorkWithNotification callback) {
//        db.child("nfInfo").child(nfId).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                Notification a = new Notification();
//                a = dataSnapshot.getValue(Notification.class);
//                callback.onNotificationReady(a);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
        db.child("nfInfo").child(nfId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Notification nf = dataSnapshot.getValue(Notification.class);
                callback.onNotificationReady(nf);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    @Override
    public int addNf(String pid, NotificationToAdd nf) {
        String newKey = db.child("newNf").push().getKey();

        db.child("newNf").child(newKey).setValue(nf);

        return 0;
    }

    @Override
    public int deleteNf(String pid, String nfId) {
        String newKey = db.child("deleteNf").push().getKey();
        String val = nfId+" : " + 0;
        db.child("deleteNf").child(nfId).setValue(0);

        return 0;
    }

    @Override
    public int changeNf(String pid, NotificationToAdd nf, String nfId) {
        //just delete and add new, nothing too bright..
        deleteNf(pid, nfId);
        addNf(pid, nf);
        return 0;
    }

    @Override
    public int addPhone(String pid) {
        db.child("newP").child(pid).setValue(0);
        return 0;
    }

    @Override
    public int changeCurPId(String pid, String CurPId) {
        return 0;
    }
}
