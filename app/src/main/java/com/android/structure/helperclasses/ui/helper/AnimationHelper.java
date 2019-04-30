package com.android.structure.helperclasses.ui.helper;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.FloatRange;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.animation.Animation;

/**
 * Created by muhammadhumzakhan on 8/8/2017.
 */

public class AnimationHelper {

    static AnimationHelper animationHelper;
    Context mContext;

    public static AnimationHelper getInstance(Context mContext) {
        if (animationHelper == null)
            animationHelper = new AnimationHelper();

        animationHelper.mContext = mContext;
        return animationHelper;
    }

    private boolean isRtl(Resources resources) {
        return resources.getConfiguration().getLayoutDirection() == View.LAYOUT_DIRECTION_RTL;
    }

    private int getThemeColor(Context context, int id) {
        Resources.Theme theme = context.getTheme();
        TypedArray a = theme.obtainStyledAttributes(new int[]{id});
        int result = a.getColor(0, 0);
        a.recycle();
        return result;
    }


    public void fadeViews(final View view) {
        view.animate()
                .translationY(view.getHeight())
                .alpha(0.0f)
                .setDuration(1500)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        view.setVisibility(View.GONE);
                    }
                });

    }


    public static void scaleView(@NonNull View view, @FloatRange(from = 0, to = 1) float scale) {
        if (view.getScaleX() != scale || view.getScaleY() != scale) {
            long duration = view.getContext().getResources().getInteger(android.R.integer.config_shortAnimTime);
            view.animate().scaleX(scale).scaleY(scale).setDuration(duration).start();
        }
    }

    public static void showAndHide(final View view, long duration) {
        view.setAlpha(0.2f);
        view.animate()
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        view.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        view.setVisibility(View.GONE);
                        view.setAlpha(0.2f);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                })
                .alpha(0)
                .setDuration(duration)
                .start();
    }


    public static void fade(final View view, final float alphaStart, final int startVisiblity, final int endVisiblity, final float alphaEnd, long duration, final Animator.AnimatorListener animatorListener) {
        view.setAlpha(alphaStart);
        view.animate()
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        animatorListener.onAnimationStart(animation);
                        view.setVisibility(startVisiblity);
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        animatorListener.onAnimationEnd(animation);
                        view.setVisibility(endVisiblity);
                        view.setAlpha(alphaEnd);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        animatorListener.onAnimationCancel(animation);
                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {
                        animatorListener.onAnimationRepeat(animation);
                    }
                })
                .alpha(alphaEnd)
                .setDuration(duration)
                .start();
    }


    public static void fade(final View view, final float alphaStart, final int startVisiblity, final int endVisiblity, final float alphaEnd, long duration) {
        view.setAlpha(alphaStart);
        view.animate()
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        view.setVisibility(startVisiblity);
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        view.setVisibility(endVisiblity);
                        view.setAlpha(alphaEnd);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                })
                .alpha(alphaEnd)
                .setDuration(duration)
                .start();
    }


    public static void changeViewColor(final View view) {

        // Load initial and final colors.
        final int initialColor = Color.parseColor("#E11E2E");
        final int finalColor = Color.parseColor("#ff000000");
//        final int finalColor = 0;

        ValueAnimator anim = ValueAnimator.ofFloat(0, 1);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // Use animation position to blend colors.
                float position = animation.getAnimatedFraction();
                int blended = blendColors(initialColor, finalColor, position);

                // Apply blended color to the view.
                view.setBackgroundColor(blended);
            }
        });

        anim.setDuration(1500).start();
    }

    private static int blendColors(int from, int to, float ratio) {
        final float inverseRatio = 1f - ratio;

        final float r = Color.red(to) * ratio + Color.red(from) * inverseRatio;
        final float g = Color.green(to) * ratio + Color.green(from) * inverseRatio;
        final float b = Color.blue(to) * ratio + Color.blue(from) * inverseRatio;

        return Color.rgb((int) r, (int) g, (int) b);
    }

    static boolean hidden = true;


    Context context;
    //Top Bar Work
    Animation animationslideFromBtoTop, animationslideFromTtoBottom;

    //Bottom Bar Work
    Animation animationslideFromBottomtoT, animationslideFromToptoB;


    public void animateTopFrame(View bottomFrame) {
        if (bottomFrame.getVisibility() == View.VISIBLE) {
            bottomFrame.setVisibility(View.INVISIBLE);
            bottomFrame.startAnimation(animationslideFromBtoTop);
        } else {
            bottomFrame.setVisibility(View.VISIBLE);
            bottomFrame.startAnimation(animationslideFromTtoBottom);
        }
    }

    public void animateBottomFrame(final View bottomFrame) {
        if (bottomFrame.getVisibility() == View.VISIBLE) {
            bottomFrame.setVisibility(View.INVISIBLE);
            bottomFrame.startAnimation(animationslideFromToptoB);
        } else {
            bottomFrame.setVisibility(View.VISIBLE);
            bottomFrame.startAnimation(animationslideFromBottomtoT);
        }

    }


    public void hideAndShowBottomBar(final View container) {
        int height = container.getHeight();
        if (container.getVisibility() == View.VISIBLE) {

            container.animate()
                    .translationY(height)
                    .setDuration(300)
                    .setListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            container.setVisibility(View.GONE);
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    })
                    .start();
        } else {
            container.setVisibility(View.VISIBLE);
            container.animate()
                    .translationY(0)
                    .setDuration(300)
                    .setListener(null)
                    .start();
        }

    }

}
