package com.android.structure.fragments.dialogs;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.android.structure.helperclasses.StringHelper;
import com.android.structure.helperclasses.ui.helper.KeyboardHelper;
import com.android.structure.helperclasses.ui.helper.UIHelper;
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

public class PinDialogFragment extends DialogFragment {


    Unbinder unbinder;
    @BindView(R.id.txtTitle)
    AnyTextView txtTitle;
    @BindView(R.id.txtWrongPinNumber)
    AnyTextView txtWrongPinNumber;
    @BindView(R.id.txtPinCode)
    PinEntryEditText txtPinCode;
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
    private View.OnClickListener onCanceButtonClick;
    private SharedPreferenceManager sharedPreferenceManager;
    private View.OnClickListener onSaveClick;


    public PinDialogFragment() {
    }

    public static PinDialogFragment newInstance(View.OnClickListener onCanceButtonClick, View.OnClickListener onSaveClick) {
        PinDialogFragment frag = new PinDialogFragment();

        frag.onCanceButtonClick = onCanceButtonClick;
        frag.onSaveClick = onSaveClick;

        Bundle args = new Bundle();
        frag.setArguments(args);

        return frag;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, R.style.DialogTheme);

        sharedPreferenceManager = SharedPreferenceManager.getInstance(getContext());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_pin_dialog, container);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindData();

        txtPinCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().length() == 5) {

                    if (StringHelper.checkNotNullAndNotEmpty(SharedPreferenceManager.getInstance(getContext()).getCurrentUser().getPIN())) {
                        if (charSequence.toString().equals(SharedPreferenceManager.getInstance(getContext()).getCurrentUser().getPIN())) {
                            new Handler().postDelayed(() -> {
                                onSaveClick.onClick(view);
                                KeyboardHelper.hideSoftKeyboard(getContext(), view);
                                dismiss();
                            }, 300);
                        } else {
                            txtPinCode.setText("");
                            txtWrongPinNumber.setVisibility(View.VISIBLE);
                        }
                    } else {
                        UIHelper.showShortToastInCenter(getContext(), "No PIN assigned to this user.");
                        txtPinCode.setText("");
                        txtWrongPinNumber.setVisibility(View.VISIBLE);
                    }


                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    @Override
    public void onResume() {
        super.onResume();
        KeyboardHelper.showSoftKeyboardForcefully(getContext(), txtPinCode);

    }

    private void bindData() {
        txtTitle.setText(getTitle());
        contLogout.setVisibility(View.GONE);
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

    @OnClick({R.id.txtSave, R.id.txtCancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.txtSave:
                if (txtPinCode.getText().toString().length() == 5 && txtPinCode.getText().toString().equals("11111")) {
                    onSaveClick.onClick(view);
                    KeyboardHelper.hideSoftKeyboard(getContext(), view);
                    dismiss();
                } else {
                    txtWrongPinNumber.setVisibility(View.VISIBLE);
                }

                break;
            case R.id.txtCancel:
                onCanceButtonClick.onClick(view);
                KeyboardHelper.hideSoftKeyboard(getContext(), view);
                dismiss();
                break;
        }
    }


    public void clearField() {
        if (txtPinCode != null) {
            txtPinCode.setText(null);
        }
    }
}

