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

        holder.name.setText(item.getNameMemory());
        holder.mood.setText(item.getMood());
        holder.year.setText(item.getYear());
        holder.type.setText(item.getType());
        // إذا أردت عرض الصورة لاحقاً
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView name, mood, year, type;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.tvName);
            mood = itemView.findViewById(R.id.tvMood);
            year = itemView.findViewById(R.id.tvDate);
            type = itemView.findViewById(R.id.tvType);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}


