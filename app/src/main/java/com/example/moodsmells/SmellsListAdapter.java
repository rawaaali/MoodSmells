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
    private ArrayList<Memory> list;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public SmellsListAdapter(Context context, ArrayList<Memory> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MemoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        return new MemoryViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull MemoryViewHolder holder, int position) {
        Memory memory = list.get(position);

        holder.tvSmellName.setText(memory.getSmellName());
        holder.tvMemoryDate.setText(memory.getMemoryDate());
        holder.tvMemoryDescription.setText(memory.getMemoryDescription());

        if (memory.getPhoto() != null && !memory.getPhoto().isEmpty()) {
            Glide.with(context)
                    .load(memory.getPhoto())
                    .into(holder.ivPhoto);
        } else {
            holder.ivPhoto.setImageResource(R.mipmap.ic_launcher);
        }
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
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
                    int position = getBindingAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(position);
                    }
                }
            });
        }
    }
}
