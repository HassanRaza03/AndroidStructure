//package com.structure.managers;
//
//import android.content.Context;
//import android.hardware.Camera;
//import android.media.AudioManager;
//import android.media.CamcorderProfile;
//import android.media.MediaPlayer;
//import android.media.MediaRecorder;
//import android.media.RingtoneManager;
//import android.net.Uri;
//import android.os.Vibrator;
//import android.util.Log;
//import android.view.SurfaceHolder;
//
//
//import com.structure.R;
//import com.structure.model.NotificationModel;
//
//import java.io.IOException;
//
//public class MediaManager {
//    public static MediaRecorder startAudioRecording(String path) {
//        MediaRecorder mRecorder = new MediaRecorder();
//        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
//        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
//        mRecorder.setOutputFile(path);
//        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
//
//        try {
//            mRecorder.prepare();
//            mRecorder.start();
//        } catch (IllegalStateException | IOException e) {
//            Log.e("abc", "prepare() failed " + e.getMessage());
//        }
//        return mRecorder;
//    }
//
//    public static MediaRecorder getVideoRecorder(Camera camera, SurfaceHolder surfaceHolder, String path, boolean forFront) {
//        MediaRecorder mediaRecorder = new MediaRecorder();
//        camera.unlock();
//        mediaRecorder.setCamera(camera);
//        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
//        mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
//        mediaRecorder.setProfile(CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH));
//        mediaRecorder.setVideoSize(720, 480);
//        mediaRecorder.setVideoFrameRate(20);
//        mediaRecorder.setVideoEncodingBitRate(3000000);
//        mediaRecorder.setAudioSamplingRate(16000);
//        mediaRecorder.setOutputFile(path);
//        mediaRecorder.setMaxDuration(600000); // Set max duration 60 sec.
//        mediaRecorder.setMaxFileSize(16000000); // Set max file size 16M
//        mediaRecorder.setPreviewDisplay(surfaceHolder.getSurface());
//        if (forFront)
//            mediaRecorder.setOrientationHint(270);
//        else
//            mediaRecorder.setOrientationHint(90);
//
//        try {
//            mediaRecorder.prepare();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return mediaRecorder;
//    }
//
//    public static MediaPlayer startAudioPlayer(String path) {
//        MediaPlayer mp = new MediaPlayer();
//
//        try {
//            mp.setDataSource(path);
//            mp.prepare();
//            mp.start();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return mp;
//    }
//
//    public ChatSettingModel chatSettingModel;
//    Context mContext;
//    NotificationModel notificationModel;
//
//    public static MediaManager getChatInstance(Context mContext, String username) {
//        MediaManager mediaManager = new MediaManager();
//        mediaManager.chatSettingModel = LocalDB.getSettingByUserName(username);
//        mediaManager.mContext = mContext;
//        mediaManager.notificationModel = SharedPreferenceManager.getInstance().getNotificationModel();
//        return mediaManager;
//    }
//
//    public Uri setUpMessageTune() {
//        Uri tuneUri = null;
//        String messagingTune;
//        if (chatSettingModel != null
//                && chatSettingModel.getMessage_tone() != null) {
//
//            if (chatSettingModel.getUse_custom_notifications() == 1) /*Means Allow*/
//                messagingTune = chatSettingModel.getMessage_tone();
//            else
//                messagingTune = notificationModel.getMessage_tone(); /*Default*/
//
////            if (!messagingTune.isEmpty())
//                tuneUri = Uri.parse(messagingTune);
//        }
//
//        return tuneUri;
//
//    }
//
//
//    private MediaPlayer setUpMediaPlayer(String tone, boolean forIncoming) {
//        Uri alert;
//        MediaPlayer mMediaPlayer = new MediaPlayer();
//        try {
//            if (forIncoming) {
//                if (tone == null) { /*Means default is Selected*/
//                    Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
//                    mMediaPlayer = MediaPlayer.create(mContext, notification);
//                    return mMediaPlayer;
//                } else if (tone.isEmpty()) { /*Means None is Selected*/
////                    Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
////                    Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
//                    mMediaPlayer = MediaPlayer.create(mContext, R.raw.empty_sound);
//                    return mMediaPlayer;
//                } else
//                    alert = Uri.parse(tone);
//
//                mMediaPlayer = MediaPlayer.create(mContext, alert);
//            } else {
//                mMediaPlayer = MediaPlayer.create(mContext, R.raw.outgoing_tune);
//            }
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
//        return mMediaPlayer;
//    }
//
//    public MediaPlayer setUpCallerTune(boolean forIncoming) {
//        MediaPlayer mPlayer = null;
//        if (chatSettingModel != null
//                && chatSettingModel.getCall_tone() != null) {
//
//            String callingTune;
//            if (chatSettingModel.getUse_custom_notifications() == 1) /*Means Allow*/
//                callingTune = chatSettingModel.getCall_tone();
//            else
//                callingTune = notificationModel.getCall_tone(); /*Default*/
////                callingTune = ""; /*Default*/
//
//            mPlayer = setUpMediaPlayer(callingTune, forIncoming);
//        } else
//            mPlayer = setUpMediaPlayer(null, forIncoming);
//
//
//        if (mPlayer == null)
//            mPlayer = MediaPlayer.create(mContext, R.raw.incoming_tune);
//
////        mPlayer.setAudioStreamType(forIncoming ? AudioManager.STREAM_MUSIC : AudioManager.STREAM_VOICE_CALL);
//        mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
//
//        mPlayer.setLooping(true);
//
//        return mPlayer;
//
//    }
//
//
//    // Start without a delay
//    // Vibrate for 100 milliseconds
//    // Sleep for 1000 milliseconds
//    // The '0' here means to repeat indefinitely
//    // '0' is actually the index at which the pattern keeps repeating from (the start)
//    public Vibrator performCallVibration() {
//        Vibrator vibrator = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);
//
//        VibrationStatus vibrationStatus;
//        if (chatSettingModel != null
//                && chatSettingModel.getCall_vibrate_Status() != null) {
//            if (chatSettingModel.getUse_custom_notifications() == 1) /*Means Allow*/
//                vibrationStatus = chatSettingModel.getCall_vibrate_Status();
//            else
//                vibrationStatus = notificationModel.getCall_vibrate_Status();
//        } else
//            vibrationStatus = VibrationStatus.DEFAULT;
//
//        vibrator.vibrate(getVibrationArray(vibrationStatus), 0);
//        return vibrator;
//    }
//
//    public long[] getVibrationArray(VibrationStatus vibrationStatus) {
//
//        switch (vibrationStatus) {
//            case LONG:
//                return new long[]{0, 800, 250}; // For Long Tested
//            case SHORT:
//                return new long[]{0, 400, 250};// For Short Tested
//            case DEFAULT:
//                return new long[]{0, 1000, 1500}; // Default Case Tested
//            case OFF:
//                return new long[]{0, 0};  // // For Long i guess
//            default:
//                return new long[]{0, 1000, 1500}; // Default Case Tested
//        }
//    }
//
//    public long[] getMessageVibrationArray(VibrationStatus vibrationStatus) {
//
//        switch (vibrationStatus) {
//            case LONG:
//                return new long[]{0, 400, 300, 400}; // For Long Tested
//            case SHORT:
//                return new long[]{0, 300, 200, 300};// For Short Tested
//            case DEFAULT:
//                return new long[]{0, 400, 200, 400}; // Default Case Tested
//            case OFF:
//                return new long[]{0, 0};  // // For Long i guess
//            default:
//                return new long[]{0, 400, 200, 400}; // Default Case Tested
//        }
//    }
//
//    public static MediaPlayer startSound(Context mContext, int rawFile) {
//        MediaPlayer mPlayer = MediaPlayer.create(mContext, rawFile);
//        mPlayer.start();
//        return mPlayer;
//    }
//
//}
