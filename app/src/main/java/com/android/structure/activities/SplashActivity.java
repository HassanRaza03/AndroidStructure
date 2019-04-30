package com.android.structure.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.android.structure.managers.SharedPreferenceManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.android.structure.R;

public class SplashActivity extends AppCompatActivity {

    private static final String TAG = "SPLASH SCREEN";
    @BindView(R.id.contParentLayout)
    LinearLayout contParentLayout;
    private final int SPLASH_TIME_OUT = 2000;
    private final int ANIMATIONS_DELAY = 2000;
    private final int ANIMATIONS_TIME_OUT = 250;
    private final int FADING_TIME = 500;
    private boolean hasAnimationStarted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
//        contParentLayout.setVisibility(View.INVISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

    }

//    private void animateSplashLayout(final boolean showLoginScreen) {
//
//
//        // Move Layout to center
//
//        //->
//        DisplayMetrics metrics = getResources().getDisplayMetrics();
//        ObjectAnimator translationY = ObjectAnimator.ofFloat(contParentLayout, "y", metrics.heightPixels / 2 - contParentLayout.getHeight() / 2); // metrics.heightPixels or root.getHeight()
//        translationY.setDuration(1);
//        translationY.start();
//        //<-
//
//        // Fading in Layout
//
//        AnimationHelper.fade(contParentLayout, 0, VISIBLE, VISIBLE, 0.7f, FADING_TIME, new Animator.AnimatorListener() {
//            @Override
//            public void onAnimationStart(Animator animator) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animator animator) {
//
//                // If directly go Home or Login Screen.
//                if (showLoginScreen) {
//                    translateAnimation(MainActivity.class);
//                } else {
////                    updateAppOrChangeActivity(HomeActivity.class);
//                    translateAnimation(HomeActivity.class);
//
//                }
//            }
//
//            @Override
//            public void onAnimationCancel(Animator animator) {
//
//            }
//
//            @Override
//            public void onAnimationRepeat(Animator animator) {
//
//            }
//        });
//
//
//    }
//
//    private void translateAnimation(Class activityClass) {
//
//
//        contParentLayout.animate().
//
//                setListener(new Animator.AnimatorListener() {
//                    @Override
//                    public void onAnimationStart(Animator animator) {
//                        contParentLayout.setVisibility(VISIBLE);
//                    }
//
//                    @Override
//                    public void onAnimationEnd(Animator animator) {
//                        contParentLayout.setTranslationY(0);
//                        contParentLayout.setAlpha(1);
////                        updateAppOrChangeActivity(MainActivity.class);
//                        changeActivity(activityClass);
//                    }
//
//                    @Override
//                    public void onAnimationCancel(Animator animator) {
//
//                    }
//
//                    @Override
//                    public void onAnimationRepeat(Animator animator) {
//
//                    }
//                })
//                .alpha(1)
//                .translationY(0)
//                .setDuration(SPLASH_TIME_OUT)
//                .start();
//    }

    private void changeActivity(Class activityClass) {
        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {

                Intent i;
                // This method will be executed once the timer is over
                // Start your app main activity
                i = new Intent(SplashActivity.this, activityClass);

                startActivity(i);
                overridePendingTransition(android.R.anim.fade_in, R.anim.fade_out);
                // close this activity
                finish();
            }
        }, ANIMATIONS_TIME_OUT);
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        if (hasFocus) {

            if (SharedPreferenceManager.getInstance(getApplicationContext()).getCurrentUser() == null) {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
//                        animateSplashLayout(true);
                        changeActivity(MainActivity.class);
                    }
                }, ANIMATIONS_DELAY);

            } else {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        changeActivity(HomeActivity.class);
//                        animateSplashLayout(false);
                    }
                }, ANIMATIONS_DELAY);
            }
        }
    }


    @Override
    protected void onDestroy() {

        super.onDestroy();

    }
}
