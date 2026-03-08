package com.example.moodsmells;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    Context context;
    ArrayList<SmellsItem> list;
    private OnItemClickListener listener;

    public MyAdapter(Context context, ArrayList<SmellsItem> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SmellsItem item = list.get(position);

        holder.smellName.setText(item.getSmellsName());
        holder.smellIntensity.setText(item.getSmellsIntensity());
        holder.memoryType.setText(item.getMemoryType());
        holder.phone.setText(item.getPhone());
        holder.smellColor.setText(item.getSmellColor());
        holder.memoryId.setText(item.getMemoryId());
        holder.smellSource.setText(item.getSmellSource());
        holder.memoryDate.setText(item.getMemoryDate());
        holder.smellCategory.setText(item.getSmellCategory());
        holder.memoryDescription.setText(item.getMemoryDescription());
        holder.memoryLocation.setText(item.getMemoryLocation());
        holder.smellStrength.setText(item.getSmellStrength());
        holder.smellStyle.setText(item.getSmellStyle());
        holder.feeling.setText(item.getFeeling());
        holder.photo.setText(item.getPhoto());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView smellName, smellIntensity,memoryType,phone,smellColor,memoryId,smellSource,memoryDate,smellCategory,memoryDescription,memoryLocation,smellStrength,smellStyle,feeling,photo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            smellName = itemView.findViewById(R.id.etSmellName);
            smellIntensity = itemView.findViewById(R.id.etSmellIntensity);
            memoryType = itemView.findViewById(R.id.etMemoryType);
            phone = itemView.findViewById(R.id.etPhone);
            smellColor=itemView.findViewById(R.id.etSmellColor);
            memoryId=itemView.findViewById(R.id.etMemoryId);
            smellSource=itemView.findViewById(R.id.etSmellSource);
            memoryDate=itemView.findViewById(R.id.etMemoryDate);
            smellCategory=itemView.findViewById(R.id.etSmellCategory);
            memoryDescription=itemView.findViewById(R.id.etMemoryDescription);
            memoryLocation=itemView.findViewById(R.id.etMemoryLocation);
            smellStrength=itemView.findViewById(R.id.etSmellStrength);
            smellStyle=itemView.findViewById(R.id.etSmellStyle);
            feeling=itemView.findViewById(R.id.etFeeling);
            photo=itemView.findViewById(R.id.imgPhoto);





        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}


