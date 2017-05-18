package specialmuffins.huji.hackathon.happyreminder.firebase_db;
/**
 * Created by elisheva on 5/18/17.
 */
import java.lang.String;
import java.util.List;
public interface Json_API {

    public interface WorkWithNotification {
        void onNotificationReady(Notification nc);
    }
    /**
     * gets list of all notifications associated with phone
     * @param pid phone id in the system
     * @return list if all notifications associated with phone
     */
    List<Notification> getAll(String pid);

    /**
     * gets a specific notification by id
     * @param nfId the id to get a nf for
     * @param pid the phones id
     * @return the notification requested.
     */
    void getNf(String pid, String nfId, WorkWithNotification callback);

    /**
     * adds a new nf
     * @param pid the phones id
     * @param nf the nf to add
     * @return 0 on success, -1 if fail
     */
    int addNf(String pid, NotificationToAdd nf);

    /**
     * deletes a node
     * @param pid the phones id
     * @param nfId the nf to add
     * @return 0 if successful, -1 if fail
     */
    int deleteNf(String pid, String nfId);

    /**
     * changes an existing nf
     * @param pid the phones id
     * @param nf ALL fields of the nf to change too
     * @param nfId the id of the nf wished to change
     * @return
     */
    int changeNf(String pid, NotificationToAdd nf, String nfId);

    /**
     * adds new phone
     * @param pid the phones id
     * @return 0 on success, -1 otherwise
     */
    int addPhone(String pid);

    /**
     * changes the curresnt phone id
     * @param pid the id of the phone we need to change
     * @param CurPId the new current id of the phone.
     * @return 0 on success, -1 if fail
     */
    int changeCurPId(String pid, String CurPId);


    public class Notification{
        //unique notification id. this is how this communicates.
        public String id;
        //notification name - only andriod cares
        public String name;
        //alarm dame. set as freq.days.begintime.endtime
        public String time;
        //body of notification
        public String body;
    }

    public class NotificationToAdd{
        public NotificationToAdd(String name, String time, String body, String pid) {
            this.name = name;
            this.time = time;
            this.body = body;
            this.pid = pid;
        }

        //notification name - only andriod cares
        public String name;
        //alarm dame. set as freq.days.begintime.endtime (example: 7.tuewedthu.2030.2200)
        public String time;
        //body of notification
        public String body;
        //the id of the phone
        public String pid;
    }

}