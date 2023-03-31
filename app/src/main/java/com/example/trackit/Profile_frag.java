package com.example.trackit;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.File;

public class Profile_frag extends Fragment {

    public Profile_frag() {
        // Required empty public constructor
    }
    TextView logout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_profile_frag, container, false);
        String mobno = getArguments().getString("mobno");
        logout=view.findViewById(R.id.logout);
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