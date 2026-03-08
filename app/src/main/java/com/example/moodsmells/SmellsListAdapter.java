package com.example.moodsmells;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class SmellsListAdapter extends RecyclerView.Adapter<SmellsListAdapter.MemoryViewHolder> {

    private Context context;
    private ArrayList<SmellsItem> List;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public SmellsListAdapter(Context context, ArrayList<SmellsItem> List) {
        this.context = context;
        this.List = List;
    }

    @NonNull
    @Override
    public MemoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        return new MemoryViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull MemoryViewHolder holder, int position) {
        Memory memory = List.get(position);

        // مثال لعرض بعض الخصائص في الـ RecyclerView
        holder.tvSmellName.setText(memory.getSmellName());
        holder.tvMemoryDate.setText(memory.getMemoryDate());
        holder.tvMemoryDescription.setText(memory.getMemoryDescription());

        // إذا عندك صورة
        if (memory.getPhoto() != null && !memory.getPhoto().isEmpty()) {
            Glide.with(context)
                    .load(memory.getPhoto())
                    .into(holder.ivPhoto);
        } else {
            holder.ivPhoto.setImageResource(R.drawable.ic_launcher_background);
        }
    }

    @Override
    public int getItemCount() {
        return List.size();
    }

    public static class MemoryViewHolder extends RecyclerView.ViewHolder {

        TextView tvSmellName, tvMemoryDate, tvMemoryDescription;
        ImageView ivPhoto;

        public MemoryViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            tvSmellName = itemView.findViewById(R.id.etSmellName);
            tvMemoryDate = itemView.findViewById(R.id.etMemoryDate);
            tvMemoryDescription = itemView.findViewById(R.id.etMemoryDescription);
            ivPhoto = itemView.findViewById(R.id.imgPhoto);

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(position);
                    }
                }
            });
        }
    }
}
