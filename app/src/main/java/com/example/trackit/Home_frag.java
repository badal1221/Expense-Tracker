package com.example.trackit;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Home_frag extends Fragment {

    public Home_frag() {
        // Required empty public constructor
    }
    RecyclerView recview;
    FloatingActionButton addnew;
    ArrayList<ExpenseList> arr=new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_home_frag, container, false);
        String mobno = getArguments().getString("mobno");
        addnew=view.findViewById(R.id.addnew);
        recview=view.findViewById(R.id.recview);
        recview.setLayoutManager(new LinearLayoutManager(getContext()));
        RecyclerexpenseAdapter adapter=new RecyclerexpenseAdapter(getContext(),arr);
        arr.add(new ExpenseList("paid","Insurance","21-03-2023","22:49",55));
        arr.add(new ExpenseList("paid","Insurance","21-03-2023","22:49",55));
        recview.setAdapter(adapter);
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://track-it-a092e-default-rtdb.firebaseio.com/");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //arr.clear();
                if(snapshot.child("users").child(mobno).hasChild("expense")){
                    for(DataSnapshot dataSnapshot:snapshot.child("users").child(mobno).child("expense").getChildren()){
                        String mttype=dataSnapshot.child("mttype").getValue(String.class);
                        String mttype1=dataSnapshot.child("mttype1").getValue(String.class);
                        String date=dataSnapshot.child("date").getValue(String.class);
                        String time=dataSnapshot.child("time").getValue(String.class);
                        String amount=dataSnapshot.child("mttype1").getValue(String.class);
                        arr.add(new ExpenseList(mttype,mttype1,date,time,Integer.parseInt(amount)));
                        adapter.updateData(arr);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        Dialog dialog = new Dialog(getContext());
        addnew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.setContentView(R.layout.addexp_dialog);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                EditText type,category,amount;
                Button cancel,add;
                type=dialog.findViewById(R.id.type);
                category=dialog.findViewById(R.id.category);
                amount=dialog.findViewById(R.id.amount);
                cancel=dialog.findViewById(R.id.cancel);
                add=dialog.findViewById(R.id.add);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String type0=type.getText().toString();
                        String type1=category.getText().toString();
                        String amt=amount.getText().toString();
                        if(type0.isEmpty()||type1.isEmpty()||amt.isEmpty()){
                            Toast.makeText(getContext(),"All fields are required",Toast.LENGTH_SHORT).show();
                        } else{
//                            Model m=new Model(sub,Integer.parseInt(t),Integer.parseInt(p));
//                            db.insertTask(m);
//                            adapter.updateData(m);
                            //Insert in firebase

                            final String currenttimestamp = String.valueOf(System.currentTimeMillis());
                            Timestamp timestamp=new Timestamp(Long.parseLong(currenttimestamp));
                            Date date=new Date(timestamp.getTime());
                            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                            SimpleDateFormat simpletimeFormat=new SimpleDateFormat("hh:mm aa", Locale.getDefault());
                            Log.d("ab",simpletimeFormat.format(date));
                            databaseReference.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                                    databaseReference.child("users").child(mobno).child("expense").child("mttype").setValue(type0);
//                                    databaseReference.child("users").child(mobno).child("expense").child("mttype1").setValue(type1);
//                                    databaseReference.child("users").child(mobno).child("expense").child("date").setValue(simpleDateFormat.format(date));
//                                    databaseReference.child("users").child(mobno).child("expense").child("time").setValue(simpletimeFormat.format(date));
//                                    databaseReference.child("users").child(mobno).child("expense").child("amount").setValue(amt);
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
                                }
                            });
                            dialog.dismiss();

                        }
                    }
                });
                dialog.show();
            }
        });
        return view;
    }
}