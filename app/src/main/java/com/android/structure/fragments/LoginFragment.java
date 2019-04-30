package com.android.structure.fragments;

import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.structure.activities.HomeActivity;
import com.android.structure.fragments.abstracts.BaseFragment;
import com.android.structure.helperclasses.ui.helper.KeyboardHelper;
import com.android.structure.widget.AnyEditTextView;
import com.android.structure.widget.AnyTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import com.android.structure.R;

import com.android.structure.widget.TitleBar;

/**
 * Created by hamza.ahmed on 7/19/2018.
 */

public class LoginFragment extends BaseFragment {


    Unbinder unbinder;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.edtUserName)
    AnyEditTextView edtUserName;
    @BindView(R.id.edtPassword)
    AnyEditTextView edtPassword;
    @BindView(R.id.cbShowPassword)
    CheckBox cbShowPassword;
    @BindView(R.id.btnLogin)
    AnyTextView btnLogin;
    @BindView(R.id.contContent)
    LinearLayout contContent;

    public static LoginFragment newInstance() {

        Bundle args = new Bundle();

        LoginFragment fragment = new LoginFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public int getDrawerLockMode() {
        return 0;
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_login_v2;
    }

    @Override
    public void setTitlebar(TitleBar titleBar) {

    }

    @Override
    public void setListeners() {
        cbShowPassword.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                edtPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            } else {
                edtPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            }
            edtPassword.setSelection(edtPassword.getText().length());
        });
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        KeyboardHelper.showSoftKeyboard(getContext(), edtUserName);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btnLogin)
    public void onViewClicked() {
        getBaseActivity().finish();
        getBaseActivity().openActivity(HomeActivity.class);
    }


}
