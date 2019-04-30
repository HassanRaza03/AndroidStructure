package com.android.structure.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;

import com.android.structure.BaseApplication;
import com.android.structure.callbacks.GenericClickableInterface;
import com.android.structure.constatnts.AppConstants;
import com.android.structure.fragments.LeftSideMenuFragment;
import com.android.structure.fragments.abstracts.BaseFragment;
import com.gdacciaro.iOSDialog.iOSDialogBuilder;

import com.android.structure.R;

import com.android.structure.widget.TitleBar;


public abstract class BaseActivity extends AppCompatActivity {

    protected DrawerLayout drawerLayout;
    protected TitleBar titleBar;
    private LeftSideMenuFragment leftSideMenuFragment;
    public BaseFragment baseFragment;
    public GenericClickableInterface genericClickableInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getViewId());
        setAndBindTitleBar();
        drawerLayout = findViewById(getDrawerLayoutId());
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        addDrawerFragment();
    }

    /**
     * Give Resource id of the view you want to inflate
     *
     * @return
     */
    protected abstract int getViewId();


    protected abstract int getTitlebarLayoutId();

    protected abstract int getDrawerLayoutId();

    protected abstract int getDockableFragmentId();

    protected abstract int getDrawerFragmentId();

    public DrawerLayout getDrawerLayout() {
        return drawerLayout;
    }


    public void addDrawerFragment() {
        leftSideMenuFragment = LeftSideMenuFragment.newInstance();
        getSupportFragmentManager().beginTransaction().replace(getDrawerFragmentId(), leftSideMenuFragment).commit();
    }


    private void setAndBindTitleBar() {
        titleBar = findViewById(getTitlebarLayoutId());
        titleBar.setVisibility(View.GONE);
        titleBar.resetViews();
    }

    public void closeApp() {
//        final GenericDialogFragment genericDialogFragment = GenericDialogFragment.newInstance();
//
//        genericDialogFragment.setTitle("Quit");
//        genericDialogFragment.setMessage("Do you want to close this application?");
//        genericDialogFragment.setButton1("Yes", new GenericClickableInterface() {
//            @Override
//            public void click() {
//                genericDialogFragment.getDialog().dismiss();
//                finish();
//            }
//        });
//
//        genericDialogFragment.setButton2("No", new GenericClickableInterface() {
//            @Override
//            public void click() {
//                genericDialogFragment.getDialog().dismiss();
//            }
//        });
//        genericDialogFragment.show(getSupportFragmentManager(), null);
        new iOSDialogBuilder(this)
                .setTitle("Quit")
                .setSubtitle("Do you want to close this application?")
                .setBoldPositiveLabel(false)
                .setCancelable(false)
                .setPositiveListener("Yes", dialog -> {
                    dialog.dismiss();
                    finish();
                })
                .setNegativeListener("No", dialog -> dialog.dismiss())
                .build().show();
    }

    public void addDockableFragment(Fragment fragment, boolean isTransition) {
        baseFragment = (BaseFragment) fragment;
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (isTransition) {
            fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
        }
        fragmentTransaction.replace(getDockableFragmentId(), fragment).addToBackStack(fragment.getClass().getSimpleName())
                .commit();
    }

    public TitleBar getTitleBar() {
        return titleBar;
    }

    public void openActivity(Class<?> tClass) {
        Intent i = new Intent(this, tClass);
        startActivity(i);
    }


    public void openImagePreviewActivity(String url, String title) {
        Intent i = new Intent(this, ImagePreviewActivity.class);
        i.putExtra(AppConstants.IMAGE_PREVIEW_TITLE, title);
        i.putExtra(AppConstants.IMAGE_PREVIEW_URL, url);
        startActivity(i);
    }

    public void openActivity(Class<?> tClass, String object) {
        Intent i = new Intent(this, tClass);
        i.putExtra(AppConstants.JSON_STRING_KEY, object);
        startActivity(i);
    }


    public LeftSideMenuFragment getLeftSideMenuFragment() {
        return leftSideMenuFragment;
    }

    public void reload() {
        Intent intent = getIntent();
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
    }

    public void clearAllActivitiesExceptThis(Class<?> cls) {
        Intent intents = new Intent(this, cls);
        intents.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intents);
        finish();
    }


    public void emptyBackStack() {
        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        if (fm == null) return;
        for (int i = 0; i < fm.getBackStackEntryCount(); i++) {
            fm.popBackStack();
        }
    }

    public void popBackStack() {
        if (getSupportFragmentManager() == null) {
            return;
        } else {
            getSupportFragmentManager().popBackStack();
        }
    }

    public void popStackTill(int stackNumber) {
        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        if (fm == null) return;
        for (int i = stackNumber; i < fm.getBackStackEntryCount(); i++) {
            fm.popBackStack();
        }
    }


    public void popStackTill(String tag) {
        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        if (fm == null) {
            return;
        }

        int backStackEntryCount = fm.getBackStackEntryCount();
        for (int i = backStackEntryCount - 1; i > 0; i--) {
            if (fm.getBackStackEntryAt(i).getName().equalsIgnoreCase(tag)) {
                return;
            } else {
                fm.popBackStack();
            }
        }
    }


    public void notifyToAll(int event, Object data) {
        BaseApplication.getPublishSubject().onNext(new Pair<>(event, data));
    }

    public void refreshFragment(BaseFragment fragment) {
        popBackStack();
        addDockableFragment(fragment, false);

    }

    public void setGenericClickableInterface(GenericClickableInterface genericClickableInterface) {
        this.genericClickableInterface = genericClickableInterface;
    }




}