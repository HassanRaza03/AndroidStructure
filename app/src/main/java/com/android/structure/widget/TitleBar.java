package com.android.structure.widget;

import android.animation.Animator;
import android.app.Activity;
import android.content.Context;
import android.support.v4.view.GravityCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.structure.activities.BaseActivity;
import com.android.structure.activities.HomeActivity;

import com.android.structure.R;
import com.android.structure.libraries.residemenu.ResideMenu;


/**
 * Created by khanhamza on 02-Mar-17.
 */

public class TitleBar extends RelativeLayout {

    public TextView txtCircle;
    private ImageView imgTitle;

    private AnyTextView txtTitle;
    private TextView btnLeft1;
    private ImageButton btnRight3;
    private TextView btnRight2;
    //change Right button
    public ImageView btnRight1;

    RelativeLayout contDropDown;


    private TextView txtClearAll;

    private LinearLayout containerTitlebar1;

    public TitleBar(Context context) {
        super(context);
        initLayout(context);
    }

    public TitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initLayout(context);
    }

    public TitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initLayout(context);
    }


    private void initLayout(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.titlebar_main, this);

        bindViews();
    }


    private void bindViews() {
        imgTitle = findViewById(R.id.imgTitle);
        txtTitle = findViewById(R.id.txtTitle);
        btnLeft1 = findViewById(R.id.btnLeft1);
        btnRight3 = findViewById(R.id.btnRight3);
        btnRight2 = findViewById(R.id.btnRight2);
        btnRight1 = findViewById(R.id.btnRight1);
        txtClearAll = findViewById(R.id.txtClearAll);
        txtCircle = findViewById(R.id.txtCircle);
        containerTitlebar1 = findViewById(R.id.containerTitlebar1);
        contDropDown = findViewById(R.id.contDropDown);



    }

    public void resetViews() {
        imgTitle.setVisibility(GONE);
        txtTitle.setVisibility(GONE);
        btnLeft1.setVisibility(GONE);
        btnRight3.setVisibility(GONE);
        btnRight2.setVisibility(GONE);
        btnRight1.setVisibility(GONE);
        txtClearAll.setVisibility(GONE);
        containerTitlebar1.setVisibility(VISIBLE);
        txtCircle.setVisibility(GONE);
        contDropDown.setVisibility(GONE);

    }

    public void setSearchField(final BaseActivity mActivity, TextView.OnEditorActionListener onEditorActionListener) {
        containerTitlebar1.setVisibility(GONE);
    }


    public void closeSearchField(final BaseActivity mActivity) {
        containerTitlebar1.setVisibility(VISIBLE);

        if (mActivity != null) {
            mActivity.getSupportFragmentManager().popBackStack();
        }
    }

    public void setTitle(String title) {
        this.imgTitle.setVisibility(GONE);
        txtTitle.setVisibility(View.VISIBLE);
        txtTitle.setText(title);
    }


    public void showBackButton(final Activity mActivity) {
        this.btnLeft1.setVisibility(VISIBLE);
        this.btnLeft1.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_back, 0, 0, 0);
        this.btnLeft1.setText(null);
        btnLeft1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mActivity != null) {
//                    mActivity.getSupportFragmentManager().popBackStack();
                    mActivity.onBackPressed();
                }

            }
        });
    }


    // To maintain title in center
    public void showBackButtonInvisible() {
        this.btnLeft1.setVisibility(INVISIBLE);
        this.btnLeft1.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_back, 0, 0, 0);
        this.btnLeft1.setText(null);
    }


    public void showTitleImage() {
        this.imgTitle.setVisibility(VISIBLE);
        this.txtTitle.setVisibility(GONE);
    }


    public void showSidebar(final BaseActivity mActivity) {

        this.btnLeft1.setVisibility(VISIBLE);
        this.btnLeft1.setCompoundDrawablesWithIntrinsicBounds(R.drawable.menu_icon, 0, 0, 0);
        this.btnLeft1.setText(null);
        btnLeft1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mActivity != null) {
                    mActivity.getDrawerLayout().openDrawer(GravityCompat.START);
                }

            }
        });
    }


    public void showResideMenu(final HomeActivity homeActivity) {

        this.btnRight2.setVisibility(VISIBLE);
        this.btnRight2.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.menu_icon, 0);
        this.btnRight2.setText(null);
        btnRight2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (homeActivity != null) {
                    homeActivity.getResideMenu().openMenu(ResideMenu.DIRECTION_RIGHT);
                }

            }
        });
    }


    public void setTextButton(String text, OnClickListener onClickListener) {
        this.txtClearAll.setVisibility(VISIBLE);
        this.txtClearAll.setText(text);
        this.txtClearAll.setOnClickListener(onClickListener);
    }


    public void setRightButton(int drawable, OnClickListener onClickListener) {
        this.btnRight1.setVisibility(VISIBLE);
        this.btnRight1.setImageResource(drawable);
        this.btnRight1.setOnClickListener(onClickListener);
    }



    public void setRightButton(int drawable, OnClickListener onClickListener, int colorToTint) {
        this.btnRight1.setVisibility(VISIBLE);
        this.btnRight1.setImageResource(drawable);
        this.btnRight1.setColorFilter(colorToTint);
        this.btnRight1.setOnClickListener(onClickListener);
    }

    public void setTxtCircle(int numberOfItems) {
        if (numberOfItems > 0 && btnRight1.getVisibility() == VISIBLE) {
            txtCircle.setVisibility(VISIBLE);
            txtCircle.setText(String.valueOf(numberOfItems));
            if (numberOfItems > 99) {
                txtCircle.setText("99");
            }
        } else {
            txtCircle.setVisibility(GONE);
            txtCircle.setText("0");
        }
    }


    public void setRightButton3(int drawable, OnClickListener onClickListener) {
        this.btnRight3.setVisibility(VISIBLE);
        btnRight3.setImageResource(drawable);
        this.btnRight3.setOnClickListener(onClickListener);
    }

    public void setRightButton2(Activity activity, int drawable, String text, OnClickListener onClickListener) {
        this.btnRight2.setVisibility(VISIBLE);
        this.btnRight2.setText(text);
        btnRight2.setCompoundDrawablesWithIntrinsicBounds(drawable, 0, 0, 0);
        this.btnRight2.setOnClickListener(onClickListener);
    }


    public void showHome(final BaseActivity activity) {
        this.btnRight2.setVisibility(VISIBLE);
        btnRight2.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.home_icon2, 0);
        btnRight2.setText(null);
        this.btnRight2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (activity instanceof HomeActivity) {
//                    activity.reload();
                    activity.popStackTill(1);
//                    activity.notifyToAll(ON_HOME_PRESSED, TitleBar.this);

                } else {
                    activity.clearAllActivitiesExceptThis(HomeActivity.class);
                }
            }
        });
    }


    public void showAndHideDropDown() {
        int height = this.containerTitlebar1.getHeight();
        if (contDropDown.getVisibility() == View.VISIBLE) {

            contDropDown.animate()
                    .translationY(-height)
                    .setDuration(300)
                    .setListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            contDropDown.setVisibility(View.GONE);
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
            contDropDown.setVisibility(View.VISIBLE);
            contDropDown.animate()
                    .translationY(height)
                    .setDuration(300)
                    .setListener(null)
                    .start();
        }

    }

}
