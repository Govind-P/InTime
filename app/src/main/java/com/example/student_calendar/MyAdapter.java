package com.example.student_calendar;

import android.content.Context;
import android.content.Intent;
import android.telecom.Call;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
    public MyAdapter(Context context, List<Taskclass> datalist) {
        this.context = context;
        this.datalist = datalist;
    }

    private Context context;
    private List<Taskclass> datalist;



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            holder.title.setText(datalist.get(position).getTopic());
            holder.status.setText(datalist.get(position).getStatus());
            String dues="Due:"+datalist.get(position).getDueDate();
            holder.due.setText(dues);

        holder.cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(((CompoundButton) view).isChecked()){
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference ref = database.getReference("TaskDetails");
                    String status = "Completed";
                    Map<String, Object> updates = new HashMap<>();
                    updates.put("status", "Completed");

                    ref.updateChildren(updates).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // Update failed
                                }
                            });
                }
            }
        });
        holder.recCard.setOnClickListener(view -> {

            Intent intent=new Intent(context, DetailsActivity.class);
            intent.putExtra("title",datalist.get(holder.getAdapterPosition()).getTopic());
            intent.putExtra("desc",datalist.get(holder.getAdapterPosition()).getDesc());
            intent.putExtra("sub",datalist.get(holder.getAdapterPosition()).getSub());
            intent.putExtra("prio",datalist.get(holder.getAdapterPosition()).getPrio());
            intent.putExtra("due",datalist.get(holder.getAdapterPosition()).getDueDate());
            intent.putExtra("duetime",datalist.get(holder.getAdapterPosition()).getDueTime());
            intent.putExtra("notify",datalist.get(holder.getAdapterPosition()).getNotify());
            intent.putExtra("image",datalist.get(holder.getAdapterPosition()).getImageurl());
            intent.putExtra("key",datalist.get(holder.getAdapterPosition()).getKey());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }
}

class MyViewHolder extends RecyclerView.ViewHolder{
    TextView title,status,due;
    CheckBox cb;
    CardView recCard;
    public MyViewHolder(@NonNull View itemView){
        super(itemView);
        title=itemView.findViewById(R.id.recTitle);
        status=itemView.findViewById(R.id.recDesc);
        due=itemView.findViewById(R.id.recDue);
        cb=itemView.findViewById(R.id.cb);
        recCard=itemView.findViewById(R.id.recCard);
    }
}