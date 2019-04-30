package com.android.structure.fragments.dialogs;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.android.structure.constatnts.AppConstants;
import com.android.structure.helperclasses.ui.helper.KeyboardHelper;
import com.android.structure.managers.SharedPreferenceManager;
import com.android.structure.widget.AnyTextView;
import com.android.structure.widget.PinEntryEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import com.android.structure.R;

/**
 * Created by khanhamza on 21-Feb-17.
 */

public class PinEntryDialogFragment extends DialogFragment {


    Unbinder unbinder;
    @BindView(R.id.txtTitle)
    AnyTextView txtTitle;
    @BindView(R.id.txtPinCode)
    PinEntryEditText txtPinCode;
    @BindView(R.id.txtWrongPinNumber)
    AnyTextView txtWrongPinNumber;
    @BindView(R.id.txtLogout)
    AnyTextView txtLogout;
    @BindView(R.id.contLogout)
    LinearLayout contLogout;
    @BindView(R.id.txtSave)
    AnyTextView txtSave;
    @BindView(R.id.txtCancel)
    AnyTextView txtCancel;
    @BindView(R.id.contButton)
    LinearLayout contButton;
    private String Title;
    private View.OnClickListener onNextButtonClick;
    private View.OnClickListener onLogoutButtonClick;


    public PinEntryDialogFragment() {
    }

    public static PinEntryDialogFragment newInstance(View.OnClickListener onNextButtonClick, View.OnClickListener onLogoutButtonClick) {
        PinEntryDialogFragment frag = new PinEntryDialogFragment();

        frag.onNextButtonClick = onNextButtonClick;
        frag.onLogoutButtonClick = onLogoutButtonClick;

        Bundle args = new Bundle();
        frag.setArguments(args);

        return frag;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, R.style.DialogTheme);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pincode_popup, container);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bindData();

        txtPinCode.setOnPinEnteredListener(str -> {
            String pinCode = SharedPreferenceManager.getInstance(getContext()).getString(AppConstants.KEY_PIN_CODE);
            if (txtPinCode.getText().toString().equals(pinCode)) {
                onNextButtonClick.onClick(view);
                KeyboardHelper.hideSoftKeyboard(getContext(), txtPinCode);
            } else {
                txtWrongPinNumber.setVisibility(View.VISIBLE);
                txtPinCode.setText("");
            }
        });


        txtPinCode.setOnPinEnteredListener(str -> {
            if (txtPinCode.getText().toString().length() == 4) {
                onNextButtonClick.onClick(view);
                KeyboardHelper.hideSoftKeyboard(getContext(), txtPinCode);
            } else {
                txtWrongPinNumber.setVisibility(View.VISIBLE);
                txtPinCode.setText("");
            }
        });

        KeyboardHelper.showSoftKeyboard(getContext(), txtPinCode);
//        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void bindData() {
        txtTitle.setText(getTitle());
        contButton.setVisibility(View.GONE);
        txtWrongPinNumber.setVisibility(View.GONE);
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.txtLogout})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.txtLogout:
                this.dismiss();
                onLogoutButtonClick.onClick(view);
                break;
        }
    }
}

