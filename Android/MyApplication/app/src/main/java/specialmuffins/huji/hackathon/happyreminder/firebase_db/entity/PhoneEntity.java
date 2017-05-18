package specialmuffins.huji.hackathon.happyreminder.firebase_db.entity;

/**
 * Created by reem on 19/05/17.
 */

public class PhoneEntity {
    public String constPhoneId;
    public String firebaseId;

    public PhoneEntity() {}

    public PhoneEntity(String constPhoneId, String firebaseId){
        this.constPhoneId = constPhoneId;
        this.firebaseId = firebaseId;
    }
}
