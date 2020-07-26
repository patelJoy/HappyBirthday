package io.google.happybirthday;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.telephony.SmsManager;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AlarmReceiver extends WakefulBroadcastReceiver {

    private SqliteDatabase mDatabase;
    public ArrayList<Birthdays> allContacts=new ArrayList<>();
    @Override
    public void onReceive(final Context context, Intent intent) {

        Toast.makeText(context,"HII JOY",Toast.LENGTH_LONG).show();
        mDatabase = new SqliteDatabase(context);
        allContacts = mDatabase.listBirthdays();

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM");
        String formattedDate = df.format(c);

        Toast.makeText(context,"HII JOY"+allContacts.size(),Toast.LENGTH_LONG).show();
        for(int i=0;i<allContacts.size();i++){
            if((allContacts.get(i).getDate().substring(0,5)).equals(formattedDate)) {
                try {
                    SmsManager.getSmsManagerForSubscriptionId(1).sendTextMessage(allContacts.get(i).getPhnum(), null, allContacts.get(i).getWishStr(), null, null);
//                    SimUtils.sendSMS(context,1,allContacts.get(i).getPhnum(),null,allContacts.get(i).getWishStr(),null,null);
                    Toast.makeText(context, "SMS Sent Successfully", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(context, "SMS Failed to Send, Please try again", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
