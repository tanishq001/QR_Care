package com.example.qrcare;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class ExampleAdapter extends RecyclerView.Adapter<ExampleAdapter.ExampleViewHolder> {
    private ArrayList<ExampleItem> mExampleList;
    public static class ExampleViewHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public TextView number;
        public TextView address;
        public ExampleViewHolder(View itemView) {
            super(itemView);
           name = itemView.findViewById(R.id.list_name);
           number = itemView.findViewById(R.id.list_number);
           address = itemView.findViewById(R.id.list_address);
        }
    }
    public ExampleAdapter(ArrayList<ExampleItem> exampleList) {
        mExampleList = exampleList;
    }
    @Override
    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.samplecard, parent, false);
        ExampleViewHolder evh = new ExampleViewHolder(v);
        return evh;
    }
    @Override
    public void onBindViewHolder(ExampleViewHolder holder, int position) {
        ExampleItem currentItem = mExampleList.get(position);
        holder.name.setText(currentItem.getName());
        holder.number.setText(currentItem.getNumber());
        holder.address.setText(currentItem.getAddress());
    }
    @Override
    public int getItemCount() {
        return mExampleList.size();
    }
}