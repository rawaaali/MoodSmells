package com.example.moodsmells.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.moodsmells.Class.Memory;
import com.example.moodsmells.R;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private final Context context;
    private final ArrayList<Memory> filteredList;
    private OnItemClickListener listener;

    public MyAdapter(Context context, ArrayList<Memory> list) {
        this.context = context;
        this.filteredList = list;
    }

    public MyAdapter(FragmentActivity activity, ArrayList<Memory> filteredList) {
        this.context = activity;
        this.filteredList = filteredList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Memory item = filteredList.get(position);

        holder.smellName.setText(item.getSmellName());
        holder.smellIntensity.setText(item.getSmellIntensity());
        holder.memoryType.setText(item.getMemoryType());
        holder.phone.setText(item.getPhone());
        holder.smellColor.setText(item.getSmellColor());
        holder.smellSource.setText(item.getSmellSource());
        holder.memoryDate.setText(item.getMemoryDate());
        holder.smellCategory.setText(item.getSmellCategory());
        holder.memoryDescription.setText(item.getMemoryDescription());
        holder.memoryLocation.setText(item.getMemoryLocation());
        holder.smellStrength.setText(item.getSmellStrength());
        holder.smellStyle.setText(item.getSmellStyle());
        holder.feeling.setText(item.getFeeling());

        if (item.getPhoto() != null && !item.getPhoto().isEmpty()) {
            Glide.with(context).load(item.getPhoto()).into(holder.photo);
        } else {
            holder.photo.setImageResource(R.mipmap.ic_launcher);
        }

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                int pos = holder.getBindingAdapterPosition();
                if (pos != RecyclerView.NO_POSITION) {
                    listener.onItemClick(pos);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return filteredList != null ? filteredList.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView smellName, smellIntensity, memoryType, phone, smellColor, smellSource, memoryDate, smellCategory, memoryDescription, memoryLocation, smellStrength, smellStyle, feeling;
        ImageView photo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            smellName = itemView.findViewById(R.id.etSmellName);
            smellIntensity = itemView.findViewById(R.id.etSmellIntensity);
            memoryType = itemView.findViewById(R.id.etMemoryType);
            phone = itemView.findViewById(R.id.etPhone);
            smellColor = itemView.findViewById(R.id.etSmellColor);
            smellSource = itemView.findViewById(R.id.etSmellSource);
            memoryDate = itemView.findViewById(R.id.etMemoryDate);
            smellCategory = itemView.findViewById(R.id.etSmellType);
            memoryDescription = itemView.findViewById(R.id.etMemoryDescription);
            memoryLocation = itemView.findViewById(R.id.etMemoryPlace);
            smellStrength = itemView.findViewById(R.id.etSmellStrength);
            smellStyle = itemView.findViewById(R.id.etSmellStyle);
            feeling = itemView.findViewById(R.id.etFeeling);
            photo = itemView.findViewById(R.id.imgPhoto);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
