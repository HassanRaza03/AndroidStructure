package com.android.structure.fragments.dialogs;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.android.structure.adapters.recyleradapters.SampleAdapter;
import com.android.structure.helperclasses.ui.helper.KeyboardHelper;
import com.android.structure.models.TupleModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import com.android.structure.R;

/**
 * Created by khanhamza on 21-Feb-17.
 */

public class SampleListDialogFragment extends DialogFragment {


    Unbinder unbinder;
    @BindView(R.id.recyclerViewLab)
    RecyclerView recyclerViewLab;
    SampleAdapter sampleAdapter;
    ArrayList<TupleModel> arrData;


    private View.OnClickListener onCanceButtonClick;


    public SampleListDialogFragment() {
    }

    public static SampleListDialogFragment newInstance(ArrayList<TupleModel> arrData, View.OnClickListener onDonePress) {
        SampleListDialogFragment frag = new SampleListDialogFragment();

        frag.onCanceButtonClick = onDonePress;
        frag.arrData = arrData;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lab_dialog, container);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sampleAdapter = new SampleAdapter(getContext(), arrData, null);
        bindRecyclerView();
        sampleAdapter.notifyDataSetChanged();

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }


    private void bindRecyclerView() {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerViewLab.setLayoutManager(mLayoutManager);
        ((DefaultItemAnimator) recyclerViewLab.getItemAnimator()).setSupportsChangeAnimations(false);
        int resId = R.anim.layout_animation_fall_bottom;
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getContext(), resId);
//        recylerView.setLayoutAnimation(animation);
        recyclerViewLab.setAdapter(sampleAdapter);
    }


    @Override
    public void onResume() {
        super.onResume();

        KeyboardHelper.hideSoftKeyboard(getContext(), getView());
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.btnOK})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnOK:
                if (onCanceButtonClick != null) {
                    onCanceButtonClick.onClick(view);
                }
                dismiss();
                break;
        }
    }
}

