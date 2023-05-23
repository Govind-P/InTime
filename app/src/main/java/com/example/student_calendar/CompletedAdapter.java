package com.example.student_calendar;

import android.content.Context;
import android.content.Intent;
import android.telecom.Call;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CompletedAdapter extends RecyclerView.Adapter<CViewHolder> {
    public CompletedAdapter(Context context, List<Taskclass> ctask) {
        this.context = context;
        this.ctask = ctask;
    }

    private Context context;
    private List<Taskclass> ctask;



    @NonNull
    @Override
    public CViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_completed,parent,false);
        return new CViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CViewHolder holder, int position) {
        holder.title.setText(ctask.get(position).getTopic());
        holder.sub.setText(ctask.get(position).getSub());
        holder.recCard.setOnClickListener(view -> {
            Intent intent=new Intent(context, CompleteDetails.class);
            intent.putExtra("title",ctask.get(holder.getAdapterPosition()).getTopic());
            intent.putExtra("desc",ctask.get(holder.getAdapterPosition()).getDesc());
            intent.putExtra("sub",ctask.get(holder.getAdapterPosition()).getSub());
            intent.putExtra("prio",ctask.get(holder.getAdapterPosition()).getPrio());
            intent.putExtra("due",ctask.get(holder.getAdapterPosition()).getDueDate());
            intent.putExtra("duetime",ctask.get(holder.getAdapterPosition()).getDueTime());
            intent.putExtra("notify",ctask.get(holder.getAdapterPosition()).getNotify());
            intent.putExtra("image",ctask.get(holder.getAdapterPosition()).getImageurl());
            intent.putExtra("key",ctask.get(holder.getAdapterPosition()).getKey());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return ctask.size();
    }
}

class CViewHolder extends RecyclerView.ViewHolder{
    TextView title,sub;
    CardView recCard;
    public CViewHolder(@NonNull View itemView){
        super(itemView);
        title=itemView.findViewById(R.id.cTitle);
        sub=itemView.findViewById(R.id.con);
        recCard=itemView.findViewById(R.id.crecCard);
    }
}
