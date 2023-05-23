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

public class NoteAdapter extends RecyclerView.Adapter<NoteViewHolder> {
    public NoteAdapter(Context context, List<Note> notelist) {
        this.context = context;
        this.notelist = notelist;
    }

    private Context context;
    private List<Note> notelist;



    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_note,parent,false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        holder.title.setText(notelist.get(position).getTopic());
        holder.sub.setText(notelist.get(position).getSub());
        holder.recCard.setOnClickListener(view -> {
            Intent intent=new Intent(context, NoteDetails.class);
            intent.putExtra("title",notelist.get(holder.getAdapterPosition()).getTopic());
            intent.putExtra("desc",notelist.get(holder.getAdapterPosition()).getDesc());
            intent.putExtra("sub",notelist.get(holder.getAdapterPosition()).getSub());
            intent.putExtra("url",notelist.get(holder.getAdapterPosition()).getDownloadUrl());
            intent.putExtra("key",notelist.get(holder.getAdapterPosition()).getKey());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return notelist.size();
    }
}

class NoteViewHolder extends RecyclerView.ViewHolder{
    TextView title,sub;
    CardView recCard;
    public NoteViewHolder(@NonNull View itemView){
        super(itemView);
        title=itemView.findViewById(R.id.noteTitle);
        sub=itemView.findViewById(R.id.noteSubject);
        recCard=itemView.findViewById(R.id.recCard);
    }
}
