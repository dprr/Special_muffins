package specialmuffins.huji.hackathon.happyreminder.firebase_db.entity;


import java.util.Date;
import java.util.List;

/**
 * Created by reem on 18/05/17.
 */
public class SkeletonAlert {


    /**
     * Created by abrock on 5/18/17.
     */

        public String reminderTxt;
        public int goodDeedsCounter = 0;
        public String possibleDays = "0000000";
        public String startHour = "10:00";
        public String endHour = "16:30";
        public String nextAlertId;
        public String constPhoneId;

        public SkeletonAlert() {}
        public SkeletonAlert(String reminderTxt, int goodDeedsCounter, String possibleDays, String startHour, String endHour, String nextAlertId, String constPhoneId) {
            this.reminderTxt = reminderTxt;
            this.goodDeedsCounter = goodDeedsCounter;
            this.possibleDays = possibleDays;
            this.startHour = startHour;
            this.endHour = endHour;
            this.nextAlertId = nextAlertId;
            this.constPhoneId = constPhoneId;
        }

    }
