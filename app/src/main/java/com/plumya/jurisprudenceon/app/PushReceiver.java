package com.plumya.jurisprudenceon.app;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;

import com.parse.ParsePushBroadcastReceiver;
import com.plumya.jurisprudenceon.MainActivity;
import com.plumya.jurisprudenceon.R;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Tomasz Milczarek on 26/12/15.
 */
public class PushReceiver extends ParsePushBroadcastReceiver {
    private static final String TAG = PushReceiver.class.getSimpleName();

    @Override
    protected Notification getNotification(Context context, Intent intent) {
        String notificationMessage = "";
        try {
            JSONObject json = new JSONObject(intent.getExtras().getString("com.parse.Data"));
            notificationMessage = json.getString("alert");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String readableNotificationMessage = NotificationMessageHelper.getMessage(notificationMessage);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        Intent notificationIntent = new Intent(context, MainActivity.class);
        notificationIntent.putExtra(MainActivity.NOTIFICATION_POSITION,
                NotificationMessageHelper.getCourtRoom(notificationMessage));
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY);

        Notification notification = builder
                .setDefaults(super.getNotification(context, intent).defaults)
                .setContentIntent(PendingIntent.getActivity(context, 0,
                        notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT))
                .setAutoCancel(true)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(context.getResources().getString(R.string.app_name))
                .setColor(context.getResources().getColor(R.color.colorPrimary))
                .setContentText(readableNotificationMessage)
                .build();
        return notification;
    }

    private static class NotificationMessageHelper {
        private final static String MESSAGE = "%s %s w %s!";
        public static String getMessage(String codes) {
            String[] messageCodes = codes.split(" ");
            String numberOfJudgements = messageCodes[0];
            String courtRoomName = "";
            String message = prepareMessage(messageCodes[0]);
            switch (messageCodes[1]) {
                case "ic_sn" : {
                    courtRoomName = "Izbie Cywilnej";
                    break;
                }
                case "ik_sn" : {
                    courtRoomName = "Izbie Karnej";
                    break;
                }
                case "ipusisp_sn" : {
                    courtRoomName = "Izbie Pracy i Polityki Społecznej";
                    break;
                }
                case "iw_sn" : {
                    courtRoomName = "Izbie Wojskowej";
                    break;
                }
                case "ps_sn" : {
                    courtRoomName = "pełnym składzie SN";
                    break;
                }
                default: {
                    message = "Nowe orzeczenie.";
                    break;
                }
            }
            return String.format(MESSAGE, numberOfJudgements, message, courtRoomName);
        }

        private static String prepareMessage(String messageCode) {
            String message;
            if (Integer.parseInt(messageCode) > 4) {
                message = "nowych orzeczeń";
            } else if (Integer.parseInt(messageCode) > 1) {
                message = "nowe orzeczenia";
            } else {
                message = "nowe orzeczenie";
            }
            return message;
        }

        private static int getCourtRoom(final String codes) {
            String[] messageCodes = codes.split(" ");
            int courtRoomNumber = 0;
            switch (messageCodes[1]) {
                case "ic_sn" : {
                    courtRoomNumber = 1;
                    break;
                }
                case "ik_sn" : {
                    courtRoomNumber = 2;
                    break;
                }
                case "ipusisp_sn" : {
                    courtRoomNumber = 3;
                    break;
                }
                case "iw_sn" : {
                    courtRoomNumber = 4;
                    break;
                }
                case "ps_sn" : {
                    courtRoomNumber = 5;
                    break;
                }
                default: {
                    break;
                }
            }
            return courtRoomNumber;
        }
    }
}
