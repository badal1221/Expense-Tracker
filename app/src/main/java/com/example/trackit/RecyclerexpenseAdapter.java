package com.example.trackit;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class RecyclerexpenseAdapter extends RecyclerView.Adapter<RecyclerexpenseAdapter.viewholder>{
    Context context;
    ArrayList<ExpenseList> arr;
    public RecyclerexpenseAdapter(Context context, ArrayList<ExpenseList> arr) {
        this.context = context;
        this.arr = arr;
    }
    @NotNull
    @Override
    public viewholder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.myexpense_row,parent,false);
        viewholder holder=new viewholder(v);
        return holder;
    }
    @Override
    public void onBindViewHolder(viewholder holder,int position) {
        ExpenseList list=arr.get(position);
        holder.mttype.setText(list.mttype);
        holder.mttype1.setText(list.mttype1);
        holder.date.setText(list.getDate()+" "+list.getTime());
        holder.balance.setText("Balance:₹"+list.cbalance);
        holder.amnt.setText(String.valueOf(list.getAmount()));
        if(list.mttype.equals("PAID")){
            holder.amnt.setTextColor(Color.RED);
        }
        else{
            holder.amnt.setTextColor(Color.GREEN);
        }
    }
    public void updateData(ArrayList<ExpenseList> arr){
        this.arr=arr;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return arr.size();
    }

    public class viewholder extends RecyclerView.ViewHolder{
        TextView mttype,mttype1,date,balance,amnt;
        public viewholder(@NotNull View itemView) {
            super(itemView);
            mttype=itemView.findViewById(R.id.mttype);
            mttype1=itemView.findViewById(R.id.mttype1);
            date=itemView.findViewById(R.id.date);
            balance=itemView.findViewById(R.id.balance);
            amnt=itemView.findViewById(R.id.amnt);
        }
    }
}
