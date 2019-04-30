package com.android.structure.fragments;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;

import com.android.structure.R;
import com.android.structure.fragments.abstracts.BaseFragment;
import com.android.structure.widget.TitleBar;

public class HomeFragment extends BaseFragment {


    public static HomeFragment newInstance() {

        Bundle args = new Bundle();

        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public int getDrawerLockMode() {
        return DrawerLayout.LOCK_MODE_LOCKED_CLOSED;
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_login_v2;
    }

    @Override
    public void setTitlebar(TitleBar titleBar) {
        titleBar.setVisibility(View.VISIBLE);
        titleBar.setTitle("HOME");
        titleBar.showSidebar(getBaseActivity());
        titleBar.showResideMenu(getHomeActivity());
    }

    @Override
    public void setListeners() {

    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}
