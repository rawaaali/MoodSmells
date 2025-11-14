package com.example.moodsmells;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    ArrayList<Memory> memoryList;
    private OnItemClickListener itemClickListener;

    public MyAdapter(Context context, ArrayList<Memory> memoryList) {
        this.context = context;
        this.memoryList = memoryList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Memory memory = memoryList.get(position);

        holder.name.setText(memory.getName());
        holder.mood.setText(memory.getMood());
        holder.date.setText(memory.getDate());
        holder.type.setText(memory.getTypeOfSmell());

        // لو أردت صورة يمكن إضافتها لاحقاً
        holder.itemView.setOnClickListener(v -> {
            if (itemClickListener != null) {
                itemClickListener.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return memoryList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name, mood, date, type;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.tvNameMemory);
            mood = itemView.findViewById(R.id.tvMoodMemory);
            date = itemView.findViewById(R.id.tvDateMemory);
            type = itemView.findViewById(R.id.tvTypeMemory);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.itemClickListener = listener;
    }
}


