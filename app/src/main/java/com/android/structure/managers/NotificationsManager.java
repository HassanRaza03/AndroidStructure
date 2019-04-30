//package com.structure.managers;
//
//import android.app.Notification;
//import android.app.NotificationManager;
//import android.app.PendingIntent;
//import android.content.Context;
//import android.content.Intent;
//import android.media.RingtoneManager;
//import android.net.Uri;
//import android.support.v4.app.NotificationCompat;
//
//import edu.aku.family_hifazat.BaseApplication;
//import edu.aku.family_hifazat.R;
//import edu.aku.family_hifazat.activities.MainActivity;
//import edu.aku.family_hifazat.constatnts.AppConstants;
//import edu.aku.family_hifazat.managers.DateManager;
//
///**
// * Created by muhammadmuzammil on 7/25/2017.
// */
//
//// Class name NotificationsManager instead of NotificationManager because this one conflict with android class
//
//public class NotificationsManager {
//    public static void showMessageNotification() {
//        Pair<RealmResults<MessageModel>, RealmResults<ChatModel>> pair = LocalDB.getAllUnSeenMessages();
//        if (pair == null)
//            return;
//
//        RealmResults<MessageModel> messages = pair.first;
//
//        int distinctSendersCount = pair.second.size();
//        String summary = getSummary(messages.size(), distinctSendersCount);
//        if (distinctSendersCount > 0) {
//            NotificationManager mNotificationManager = (NotificationManager) BaseApplication.getAppContext().getSystemService(Context.NOTIFICATION_SERVICE);
//            String ticker = null;
//            ChatModel chatModel = pair.second.last();
//            switch (chatModel.getType()) {
//                case SINGLE:
//                    ticker = "Message from " + pair.second.last().getContactDetail().getName();
//                    break;
//                case GROUP:
//                    ticker = "Message from " + pair.second.last().getGroupDetailModel().getChatName();
//                    break;
//            }
//            NotificationCompat.Builder mBuilder = getNotificationBuilder(ticker, summary, getMessagesInboxStyle(pair.second, messages, distinctSendersCount > 1)
//                    .setSummaryText(summary));
//
//            if (distinctSendersCount == 1) {
//
//                Intent intent = new Intent(BaseApplication.getAppContext(), ChatActivity.class);
//                intent.putExtra("isNotification", true);
//
//                switch (chatModel.getType()) {
//                    case SINGLE:
//                        ContactDetailModel userContactDetail = pair.second.get(0).getContactDetail();
//                        mBuilder.setLargeIcon(Helper.getProfileImage(userContactDetail.getAvatar()));
//                        mBuilder.setContentTitle(userContactDetail.getName());
//                        intent.putExtra("username", userContactDetail.getUserName());
//                        break;
//                    case GROUP:
//                        GroupDetailModel groupDetailModel = pair.second.get(0).getGroupDetailModel();
//                        mBuilder.setContentTitle(groupDetailModel.getChatName());
//                        intent.putExtra("username", chatModel.getUsername());
//                        break;
//                }
//
//                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                mBuilder.setContentIntent(PendingIntent.getActivity(BaseApplication.getAppContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT));
//
//            } else {
//                mBuilder.setContentTitle(BaseApplication.getAppContext().getString(R.string.app_name));
//                mBuilder.setContentIntent(PendingIntent.getActivity(BaseApplication.getAppContext(), 0, new Intent(BaseApplication.getAppContext(), MainActivity.class), 0));
//            }
//
//            boolean showNotification = setCustomNotification(mBuilder, pair.second.last().getUsername());
//            if (showNotification)
//                mNotificationManager.notify(AppConstants.MESSAGE_NOTIFICATION_ID, mBuilder.build());
//        }
//    }
//
//    private static String getSummary(int messageCount, int chatsCount) {
//        return messageCount + " new responseMessage" + (messageCount > 1 ? "s" : "") + (chatsCount > 1 ? " from " + chatsCount + " chats" : "");
//    }
//
//    private static NotificationCompat.InboxStyle getMessagesInboxStyle(RealmResults<ChatModel> chats, RealmResults<MessageModel> messages, boolean multipleChats) {
//        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
//        int size = messages.size();
//        int limit = 7;
//
//        for (int i = (size > limit) ? (size - limit) : 0; i < size; i++) {
//            MessageModel responseMessage = messages.get(i);
//            ChatModel chatModel = chats.where().equalTo("id", responseMessage.getChatId()).findFirst();
//            String type = "";
//            switch (responseMessage.getMessageType()) {
//                case MEDIA_IMAGE:
//                    type += "Photo";
//                    break;
//                case MEDIA_VOICE:
//                    type += "Voice Message";
//                    break;
//                case MEDIA_DOC:
//                    type += "Doc";
//                    break;
//                case MEDIA_VIDEO:
//                    type += "Video Message";
//                    break;
//                case INFO:
//                    break;
//                case RECORDING:
//                    type += "Voice Message";
//                    break;
//            }
//
//            switch (chatModel.getType()) {
//                case SINGLE:
//                    inboxStyle.addLine((multipleChats ? chatModel.getContactDetail().getName() + ": " : "") + type + responseMessage.getText());
//                    break;
//                case GROUP:
//                    ContactDetailModel memberDetail =
//                            chatModel.getGroupDetailModel().getParticipantModels().where().equalTo("contactDetailModel.UserName", responseMessage.getRemoteJid()).findFirst() != null  //Accessing getContactDetailModel on a NullObject Causes NullPointerException
//                                    ? chatModel.getGroupDetailModel().getParticipantModels().where().equalTo("contactDetailModel.UserName", responseMessage.getRemoteJid()).findFirst().getContactDetailModel()
//                                    : null;
//
//                    inboxStyle.addLine(
//                            (multipleChats ? chatModel.getGroupDetailModel().getChatName() + ": " : "")  //GROUP 1:  USER1 : Message
//                                    + getName(memberDetail)
//                                    + type + responseMessage.getText()
//                    );
//                    break;
//            }
//        }
//
//
//        return inboxStyle;
//    }
//
//    public static String getName(ContactDetailModel memberDetail) {
//        if (memberDetail != null) {
//            switch (memberDetail.getStatus()) {
//                case ANONYMOUS:
//                case PENDING:
//                    return memberDetail.getBareUserName() + " : ";
//                default:
//                    return memberDetail.getName() + " : ";
//            }
//        }
//        return "";
//    }
//
//    public static void clearNotificationBar(Context context) {
//        ((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE)).cancelAll();
//    }
//
//    public static void clearNotificationBar(Context context, int id) {
//        ((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE)).cancel(id);
//    }
//
//    private static NotificationCompat.Builder  getNotificationBuilder(String ticker, String summary, android.support.v4.app.NotificationCompat.Style style) {
//        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(BaseApplication.getAppContext());
////        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//        mBuilder.setTicker(ticker);
//        mBuilder.setContentText(summary);
//        mBuilder.setSmallIcon(R.drawable.notification_icon);
////        mBuilder.setSound(uri);
//        mBuilder.setAutoCancel(false);
//        mBuilder.setStyle(style);
//        mBuilder.setPriority(Notification.PRIORITY_HIGH);
//        return mBuilder;
//    }
//
//    public static void showMissedCallNotification(String username, CallType callType) {
//        NotificationManager mNotificationManager = (NotificationManager) BaseApplication.getAppContext().getSystemService(Context.NOTIFICATION_SERVICE);
//        ContactDetailModel userContactDetail = LocalDB.getContactById(username);
//        NotificationCompat.Builder mBuilder = getNotificationBuilder("Missed call from " + userContactDetail.getName(), userContactDetail.getName(), new NotificationCompat.InboxStyle().addLine(userContactDetail.getName()));
//        mBuilder.setContentTitle("Missed " + (callType == CallType.VIDEO_CALL ? "Video" : "Voice") + " Call");
//        mBuilder.setLargeIcon(getProfileImage(userContactDetail.getAvatar()));
//        setCustomNotification(mBuilder, username);
//
//        Intent intent = new Intent(BaseApplication.getAppContext(), MainActivity.class);
//        intent.putExtra("isNotification", true);
//        intent.putExtra("tabToShow", 0); //
//        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        mBuilder.setContentIntent(PendingIntent.getActivity(BaseApplication.getAppContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT));
////        mNotificationManager.notify(AppConstants.VOIP_NOTIFICATION_ID, mBuilder.build());
//        // Temporary
//        mNotificationManager.notify((int) DateManager.getCurrentMillis(), mBuilder.build());
//    }
//
//    private static boolean setCustomNotification(NotificationCompat.Builder mBuilder, String mUserName) {
//        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//        MediaManager mediaManager = MediaManager.getChatInstance(BaseApplication.getAppContext(), mUserName);
//
//        if (mediaManager != null && mediaManager.chatSettingModel != null) {
//
//            MuteType muteType = mediaManager.chatSettingModel.getMute_notifications_Type();
//            if (muteType != MuteType.OFF) { // Means Sound will not come only
//                if ((mediaManager.chatSettingModel.getMute_end() - DateManager.getCurrentMillis()) > 0) { // Means mute Duration is not end yet
//                    if (mediaManager.chatSettingModel.isShow_notification()) { //In WhatsApp If its true, then Heads-up Notifications will not display , notification just stay at top without coming on screen
//                        mBuilder.setPriority(Notification.PRIORITY_LOW);
//                        return true; // We need to show notification, so passing true
//                    }
//                    // Not Setting Any Sound
//                    return false;
//                }
//            }
//
//            Uri messageTune = mediaManager.setUpMessageTune();
//            if (messageTune != null)
//                mBuilder.setSound(messageTune);
//            else
//                mBuilder.setSound(uri);
//
//            /*If users set Per chat Notification*/
//            if (mediaManager.chatSettingModel.getUse_custom_notifications() == 1) {
//                mBuilder.setVibrate(mediaManager.getMessageVibrationArray(mediaManager.chatSettingModel.getMessage_vibrate_Status()));
//                mBuilder.setLights(mediaManager.chatSettingModel.getMessage_light_Code(), LIGHT_DURATION_ON, LIGHT_DURATION_OFF);
//            } else { /*Default Chat Notification*/
//                mBuilder.setVibrate(mediaManager.getMessageVibrationArray(mediaManager.notificationModel.getMessage_vibrate_Status()));
//                mBuilder.setLights(mediaManager.notificationModel.getMessage_light_Code(), LIGHT_DURATION_ON, LIGHT_DURATION_OFF);
//            }
//        } else {
//            mBuilder.setSound(uri);
//        }
//
//        return true;
//    }
//
//
//}
