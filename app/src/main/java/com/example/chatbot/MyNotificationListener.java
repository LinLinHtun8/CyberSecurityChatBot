//package com.example.chatbot;
//
//import android.service.notification.NotificationListenerService;
//import android.service.notification.StatusBarNotification;
//import android.util.Log;
//import android.widget.Toast;
//
//public class MyNotificationListener extends NotificationListenerService {
//
//    @Override
//    public  void  onNotificationPosted(StatusBarNotification noti){
//
//        String packName = noti.getPackageName();
//
//        if(packName.contains("sms") || packName.contains("messaging")){
//
//            CharSequence Cs = noti.getNotification().extras.getCharSequence("android.Cs");
//
//            if(Cs == null){
//                return;
//            }
//
//            String text = Cs.toString();
//
//            Log.i("SMS Notification", "text: "+ text);
//
//            if(text.toLowerCase().contains("clue")|| text.toLowerCase().contains("you win") || text.toLowerCase().contains("code")){
//                Toast.makeText(this, "Clue Detective: "+ text, Toast.LENGTH_SHORT).show();
//            }
//
//
//        }
//
//    }
//
//
//
//}
package com.example.chatbot;

import  android.service.notification.*;
import  android.util.*;
import android.widget.Toast;

public class MyNotificationListener extends  NotificationListenerService{

    @Override
    public void onNotificationPosted(StatusBarNotification sbn){

        String packageName = sbn.getPackageName();

        if(packageName.contains("sms")|| packageName.contains("message")){

            CharSequence cs= sbn.getNotification().extras.getCharSequence("android.text");

            if(cs== null) return;;

            String text = cs.toString();
            Log.i("🔔 SMS Notification", "Text: " + text);

            if (text.trim().toLowerCase().contains("clue") || text.trim().toLowerCase().contains("you win") || text.toLowerCase().contains("code")){

                Toast.makeText(this, "Detect clue in your notification" + text, Toast.LENGTH_SHORT).show();

            }


        }

    }

}