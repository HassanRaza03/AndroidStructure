package com.android.structure.fragments.dialogs;

import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.AppCompatRatingBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;

import com.android.structure.R;

import com.android.structure.callbacks.RatingBarDataInterface;


import butterknife.BindView;
import butterknife.ButterKnife;


public class RatingDialogFragment extends DialogFragment implements
        RatingBar.OnRatingBarChangeListener {


    @BindView(R.id.ratingbarDeliverySpeed)
    AppCompatRatingBar ratingbarDeliverySpeed;
    @BindView(R.id.ratingBarProductAccuracy)
    AppCompatRatingBar ratingBarProductAccuracy;
    @BindView(R.id.btnYes)
    Button btnYes;
    @BindView(R.id.btnNo)
    Button btnNo;
    private View.OnClickListener onClickListener;
    public float ratingProductAccuracy = 0f, ratingProductDeliverySpeed = 0f;
    RatingBarDataInterface ratingBarDataInterface;


    public static RatingDialogFragment newInstance(View.OnClickListener onClickListener) {
        RatingDialogFragment frag = new RatingDialogFragment();
        Bundle args = new Bundle();
//        args.putString(NAME_KEY, name);
//        args.putInt(KEY_POSITION, position);
        frag.setArguments(args);
        frag.onClickListener = onClickListener;
        return frag;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_FRAME, R.style.DialogTheme);
    }

    private void setStarColor() {
        LayerDrawable stars = (LayerDrawable) ratingbarDeliverySpeed.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
        stars.getDrawable(0).setColorFilter(getResources().getColor(R.color.star_grey), PorterDuff.Mode.SRC_ATOP);
        stars.getDrawable(1).setColorFilter(getResources().getColor(R.color.star_grey), PorterDuff.Mode.SRC_ATOP);

        LayerDrawable stars2 = (LayerDrawable) ratingBarProductAccuracy.getProgressDrawable();
        stars2.getDrawable(2).setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
        stars2.getDrawable(0).setColorFilter(getResources().getColor(R.color.star_grey), PorterDuff.Mode.SRC_ATOP);
        stars2.getDrawable(1).setColorFilter(getResources().getColor(R.color.star_grey), PorterDuff.Mode.SRC_ATOP);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.popup_rating, container);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setStarColor();
        setListeners();
    }

    private void setListeners() {
        btnYes.setOnClickListener(onClickListener);
        btnNo.setOnClickListener(onClickListener);
        ratingbarDeliverySpeed.setOnRatingBarChangeListener(this);
        ratingBarProductAccuracy.setOnRatingBarChangeListener(this);
    }

    public void setRatingBarDataInterface(RatingBarDataInterface ratingBarDataInterface) {
        this.ratingBarDataInterface = ratingBarDataInterface;
    }

    @Override
    public void onRatingChanged(RatingBar ratingBar, float rating, boolean b) {
        switch (ratingBar.getId()) {

            case R.id.ratingbarDeliverySpeed:
                ratingProductDeliverySpeed = rating;
                break;

            case R.id.ratingBarProductAccuracy:
                ratingProductAccuracy = rating;
                break;
        }
        ratingBarDataInterface.getRatings(ratingProductDeliverySpeed, ratingProductAccuracy);
    }
}