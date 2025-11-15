package com.example.moodsmells;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SmellsListAdapter extends RecyclerView.Adapter<SmellsListAdapter.MyViewHolder> {

    Context context;
    ArrayList<SmellsItem> smellsList;
    private OnItemClickListener itemClickListener;
    private FirebaseServices fbs;

    public SmellsListAdapter(Context context, ArrayList<SmellsItem> smellsList) {
        this.context = context;
        this.smellsList = smellsList;
        this.fbs = FirebaseServices.getInstance();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        SmellsItem smells = smellsList.get(position);


        // ======== النصوص ===========
        holder.SmellsName.setText(smells.getName());
        holder.Mood.setText(smells.getMood());
        holder.Year.setText(smells.getYear());
        holder.Type.setText(smells.getType());







        // ======== النقر على العنصر ============
        holder.itemView.setOnClickListener(v -> {
            if (itemClickListener != null)
                itemClickListener.onItemClick(position);
        });
    }

    @Override
    public int getItemCount() {
        return smellsList.size();
    }

    // ========= المفضلة ========






    // ========= ViewHolder ===========
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView SmellsName, Mood, Year, Type;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            SmellsName = itemView.findViewById(R.id.tvName);
            Mood = itemView.findViewById(R.id.tvMood);
            Year = itemView.findViewById(R.id.tvDate);
            Type = itemView.findViewById(R.id.tvType);

        }
    }

    // ========= Interface for onClick ========
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.itemClickListener = listener;
    }
}
