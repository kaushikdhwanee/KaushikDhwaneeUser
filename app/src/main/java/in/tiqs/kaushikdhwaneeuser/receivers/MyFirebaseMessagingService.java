package in.tiqs.kaushikdhwaneeuser.receivers;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import in.tiqs.kaushikdhwaneeuser.R;
import in.tiqs.kaushikdhwaneeuser.act.MainActivity;
import in.tiqs.kaushikdhwaneeuser.act.Notif;
import in.tiqs.kaushikdhwaneeuser.act.SubCat;

/**
 * Created by ADMIN on 9/16/2016.
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {

    String Mess;
    int target;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.e("mess","   mess   "+ remoteMessage.getData().get("message"));


        String str = remoteMessage.getData().get("message");
        try {
        JSONObject j = new JSONObject(str);

            Mess = j.getString("message");
            target = j.getInt("target");
            Log.e("JsonStr", Mess);
        } catch (JSONException e)
        {
            e.printStackTrace();
            Log.e("JsonStr", e.toString());

        }
        Intent i =null;
        if (target==1)
       {
            i = new Intent(this,Notif.class);
           Log.e("Notification", "n");
       }
        else  if (target==2)
       {

          i = new Intent(this,SubCat.class);
           Log.e("SubCat", "SubCat");

       }

        ShowNotification(Mess,i);

    }

    private void ShowNotification(String message,Intent ii)
    {
        Intent i=ii;
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 , i,
                PendingIntent.FLAG_CANCEL_CURRENT);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);


        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                    .setLargeIcon((BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher)))
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("Kaushik Dhwanee")
                    .setContentText(message)
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(message));
            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify(0 , notificationBuilder.build());

        } else {
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                    .setLargeIcon((BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher)))
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("Kaushik Dhwanee")
                    .setContentText(message)
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(message));
            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify(0 , notificationBuilder.build());
        }

    }


}
