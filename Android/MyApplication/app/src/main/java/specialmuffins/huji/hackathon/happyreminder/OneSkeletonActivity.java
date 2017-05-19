package specialmuffins.huji.hackathon.happyreminder;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.TimePicker;

import specialmuffins.huji.hackathon.happyreminder.firebase_db.FireBaseDBManager;
import specialmuffins.huji.hackathon.happyreminder.firebase_db.entity.SkeletonAlert;

/**
 * Created by reem on 19/05/17.
 */

public class OneSkeletonActivity extends AppCompatActivity {
    public static SkeletonAlert skeletonAlertToWorkWith;
    public static boolean isSkeletonNew;

    CheckBox cbxSunday;
    CheckBox cbxMonday;
    CheckBox cbxTuesday;
    CheckBox cbxWednesday;
    CheckBox cbxThursday;
    CheckBox cbxFriday;
    CheckBox cbxSaturday;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skeleton_one);

//        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
//                new TimePickerDialog.OnTimeSetListener() {
//
//                    @Override
//                    public void onTimeSet(TimePicker view, int hourOfDay,
//                                          int minute) {
//
//
//                    }
//                }, 10, 55, false);
//        timePickerDialog.show();


        cbxSunday = (CheckBox) findViewById(R.id.cbx_skeleton_sunday);
        cbxMonday = (CheckBox) findViewById(R.id.cbx_skeleton_monday);
        cbxTuesday = (CheckBox) findViewById(R.id.cbx_skeleton_tuesday);
        cbxWednesday = (CheckBox) findViewById(R.id.cbx_skeleton_wednesday);
        cbxThursday = (CheckBox) findViewById(R.id.cbx_skeleton_thursday);
        cbxFriday = (CheckBox) findViewById(R.id.cbx_skeleton_friday);
        cbxSaturday = (CheckBox) findViewById(R.id.cbx_skeleton_saturday);


        View dismissButton = findViewById(R.id.fab_skeleton_dismiss);
        dismissButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        final TextView tvStartTime = (TextView) findViewById(R.id.tv_skeleton_start_hour);
        tvStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String timeString = tvStartTime.getText().toString();
                final int hour, minute;
                hour = Integer.valueOf(timeString.substring(0,2));
                minute = Integer.valueOf(timeString.substring(3,5));
                TimePickerDialog timePickerDialog = new TimePickerDialog(OneSkeletonActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                String hourStr = String.valueOf(hourOfDay);
                                if (hourStr.length() == 1) { hourStr = "0" + hourStr; }
                                String minuteStr = String.valueOf(minute);
                                if (minuteStr.length() == 1) { minuteStr = "0" + minuteStr; }
                                tvStartTime.setText(hourStr + ":" + minuteStr);
                            }
                        },hour, minute, false);
                timePickerDialog.setTitle("Select Time");
                timePickerDialog.show();
            }
        });

        final TextView tvEndTime = (TextView) findViewById(R.id.tv_skeleton_end_hour);
        tvEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String timeString = tvEndTime.getText().toString();
                final int hour, minute;
                hour = Integer.valueOf(timeString.substring(0,2));
                minute = Integer.valueOf(timeString.substring(3,5));
                TimePickerDialog timePickerDialog = new TimePickerDialog(OneSkeletonActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                String hourStr = String.valueOf(hourOfDay);
                                if (hourStr.length() == 1) { hourStr = "0" + hourStr; }
                                String minuteStr = String.valueOf(minute);
                                if (minuteStr.length() == 1) { minuteStr = "0" + minuteStr; }
                                tvEndTime.setText(hourStr + ":" + minuteStr);
                            }
                        },hour, minute, false);
                timePickerDialog.setTitle("Select Time");
                timePickerDialog.show();
            }
        });

        final TextView edtTitle = (TextView) findViewById(R.id.edt_skeleton_title);


        // set all the views by the object
        edtTitle.setText(skeletonAlertToWorkWith.reminderTxt);
        cbxSunday.setChecked(skeletonAlertToWorkWith.possibleDays.substring(0,1).equals("1"));
        cbxMonday.setChecked(skeletonAlertToWorkWith.possibleDays.substring(1,2).equals("1"));
        cbxTuesday.setChecked(skeletonAlertToWorkWith.possibleDays.substring(2,3).equals("1"));
        cbxWednesday.setChecked(skeletonAlertToWorkWith.possibleDays.substring(3,4).equals("1"));
        cbxThursday.setChecked(skeletonAlertToWorkWith.possibleDays.substring(4,5).equals("1"));
        cbxFriday.setChecked(skeletonAlertToWorkWith.possibleDays.substring(5,6).equals("1"));
        cbxSaturday.setChecked(skeletonAlertToWorkWith.possibleDays.substring(6,7).equals("1"));

        tvStartTime.setText(skeletonAlertToWorkWith.startHour);
        tvEndTime.setText(skeletonAlertToWorkWith.endHour);


        View buttonFinish = findViewById(R.id.fab_skeleton_done);
        buttonFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skeletonAlertToWorkWith.reminderTxt = edtTitle.getText().toString();
                String daysBoolean = "";
                if (cbxSaturday.isChecked()) daysBoolean += "1"; else daysBoolean += "0";
                if (cbxFriday.isChecked()) daysBoolean += "1"; else daysBoolean += "0";
                if (cbxThursday.isChecked()) daysBoolean += "1"; else daysBoolean += "0";
                if (cbxWednesday.isChecked()) daysBoolean += "1"; else daysBoolean += "0";
                if (cbxTuesday.isChecked()) daysBoolean += "1"; else daysBoolean += "0";
                if (cbxMonday.isChecked()) daysBoolean += "1"; else daysBoolean += "0";
                if (cbxSunday.isChecked()) daysBoolean += "1"; else daysBoolean += "0";
                skeletonAlertToWorkWith.possibleDays = daysBoolean;
                skeletonAlertToWorkWith.startHour = tvStartTime.getText().toString();
                skeletonAlertToWorkWith.endHour = tvEndTime.getText().toString();
                skeletonAlertToWorkWith.constPhoneId = Info.constId;

                FireBaseDBManager.AddSkeletonCallback callback = new FireBaseDBManager.AddSkeletonCallback() {
                    @Override
                    public void onSkeletonAdded(SkeletonAlert finished, String newSkeletonId) {
                        setResult(RESULT_OK);
                        finish();
                    }
                };
                if (isSkeletonNew) {
                    FireBaseDBManager.getManager().addSkeleton(skeletonAlertToWorkWith, callback);
                } else {
                    FireBaseDBManager.getManager().updateSkeleton(skeletonAlertToWorkWith, callback);
                }
            }
        });
    }
}
