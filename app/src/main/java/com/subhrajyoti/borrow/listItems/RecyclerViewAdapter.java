package com.subhrajyoti.borrow.listItems;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.subhrajyoti.borrow.R;
import com.subhrajyoti.borrow.db.HistoryModel;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> {

    private List<HistoryModel> borrowModelList;
    private View.OnLongClickListener longClickListener;

    public RecyclerViewAdapter(List<HistoryModel> borrowModelList, View.OnLongClickListener longClickListener) {
        this.borrowModelList = borrowModelList;
        this.longClickListener = longClickListener;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecyclerViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, int position) {
        HistoryModel borrowModel = borrowModelList.get(position);
        holder.itemTextView.setText(borrowModel.getSongName());
        holder.nameTextView.setText(borrowModel.getSongURL());
        holder.itemView.setTag(borrowModel);
        holder.itemView.setOnLongClickListener(longClickListener);
    }

    @Override
    public int getItemCount() {
        return borrowModelList.size();
    }

    public void addItems(List<HistoryModel> borrowModelList) {
        this.borrowModelList = borrowModelList;
        notifyDataSetChanged();
    }

    static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private TextView itemTextView;
        private TextView nameTextView;
        private TextView dateTextView;

        RecyclerViewHolder(View view) {
            super(view);
            itemTextView = view.findViewById(R.id.itemTextView);
            nameTextView = view.findViewById(R.id.nameTextView);
            dateTextView = view.findViewById(R.id.dateTextView);
        }
    }
}