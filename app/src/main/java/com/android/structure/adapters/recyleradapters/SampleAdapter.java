package com.android.structure.adapters.recyleradapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.android.structure.R;
import com.android.structure.callbacks.OnItemClickListener;
import com.android.structure.models.TupleModel;
import com.android.structure.widget.AnyTextView;

/**
 */
public class SampleAdapter extends RecyclerView.Adapter<SampleAdapter.ViewHolder> {

    Typeface bold;

    private final OnItemClickListener onItemClick;


    private Context activity;
    private List<TupleModel> arrData;

    public SampleAdapter(Context activity, List<TupleModel> arrData, OnItemClickListener onItemClickListener) {
        this.arrData = arrData;
        this.activity = activity;
        this.onItemClick = onItemClickListener;
        bold = Typeface.createFromAsset(activity.getAssets(), "fonts/HelveticaNeue Medium.ttf");
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = null;
        itemView = LayoutInflater.from(activity)
                .inflate(R.layout.item_lab, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int i) {
        TupleModel model = arrData.get(i);

        setListener(holder, model);
    }

    private void setListener(final ViewHolder holder, final TupleModel model) {
//        holder.contListItem.setOnClickListener(view -> onItemClick.onItemClick(holder.getAdapterPosition(), model, view));
    }


    @Override
    public int getItemCount() {
        return arrData.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txtLabName)
        AnyTextView txtLabName;
        @BindView(R.id.txtRange)
        AnyTextView txtRange;
        @BindView(R.id.txtResult)
        AnyTextView txtResult;
        @BindView(R.id.txtUnit)
        AnyTextView txtUnit;
        @BindView(R.id.txtDate)
        AnyTextView txtDate;
        @BindView(R.id.txtState)
        AnyTextView txtState;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
