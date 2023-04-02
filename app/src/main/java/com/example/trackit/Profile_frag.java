package com.example.trackit;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;

public class Profile_frag extends Fragment {

    public Profile_frag() {
        // Required empty public constructor
    }
    TextView logout,balance,name,phoneno;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_profile_frag, container, false);
        String mobno = getArguments().getString("mobno");
        logout=view.findViewById(R.id.logout);
        balance=view.findViewById(R.id.balance);
        name=view.findViewById(R.id.name);
        phoneno=view.findViewById(R.id.phoneno);
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://track-it-a092e-default-rtdb.firebaseio.com/");
        Dialog dialog = new Dialog(getContext());
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                balance.setText("Balance:"+snapshot.child("users").child(mobno).child("balance").getValue(String.class));
                name.setText("Name:"+snapshot.child("users").child(mobno).child("name").getValue(String.class));
                phoneno.setText(mobno);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        balance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.setContentView(R.layout.editbalance_dialog);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                EditText amount;
                Button cancel,change;
                amount=dialog.findViewById(R.id.amount);
                cancel=dialog.findViewById(R.id.cancel);
                change=dialog.findViewById(R.id.change);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                change.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String amt=amount.getText().toString();
                        databaseReference.child("users").child(mobno).child("balance").setValue(amt);
                        balance.setText("Balance:"+amt);
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File dir = getContext().getFilesDir();
                File file = new File(dir, "data.txt");
                boolean deleted = file.delete();
                File file1 = new File(dir, "number.txt");
                boolean deleted1 = file1.delete();
                Intent intent = new Intent(getContext(),Signup.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        return view;
    }
}