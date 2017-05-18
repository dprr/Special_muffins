import java.util.Date;
import java.util.List;

/**
 * Created by abrock on 5/18/17.
 */
public class Skeleton {

    public String reminderTxt;
    public int goodDeedsCounter = 0;
    public String possibleDays = "0001111";
    public String startHour = "10:00";
    public String endHour = "16:30";
    public String nextAlertId;
    public String constPhoneId;

    public Skeleton() {}
    public Skeleton(String reminderTxt, int goodDeedsCounter, String possibleDays, String startHour, String endHour, String nextAlertId, String constPhoneId) {
        this.reminderTxt = reminderTxt;
        this.goodDeedsCounter = goodDeedsCounter;
        this.possibleDays = possibleDays;
        this.startHour = startHour;
        this.endHour = endHour;
        this.nextAlertId = nextAlertId;
        this.constPhoneId = constPhoneId;
    }


    public static class Phone {

        public String constPhoneId;
        public String firebaseId;

        public Phone() {}

        public Phone(String constPhoneId, String firebaseId){
            this.constPhoneId = constPhoneId;
            this.firebaseId = firebaseId;
        }
    }

    public static class Alert {

        public String skeletonId;
        public String time = "08.10.2017.19.30";
        public String constPhoneId;

        public Alert(){}
        public Alert(String skeletonId, String time, String constPhoneId){
            this.constPhoneId = constPhoneId;
            this.skeletonId = skeletonId;
            this.time = time;
        }
    }

}