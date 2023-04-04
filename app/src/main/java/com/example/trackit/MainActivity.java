package com.example.trackit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView btnview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        btnview=findViewById(R.id.btnview);

        loadfrag(new Home_frag(),0);
        btnview.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id=item.getItemId();
                if(id==R.id.nav_home)
                    loadfrag(new Home_frag(),1);
                else if(id==R.id.nav_report)
                    loadfrag(new Stats_frag(),1);
                else
                    loadfrag(new Profile_frag(),1);
                return true;
            }
        });
    }
    public void loadfrag(Fragment frag, int flag){
        String mobileno=MemoryData.getnumber(MainActivity.this);
        FragmentManager fm=getSupportFragmentManager();
        FragmentTransaction ft= fm.beginTransaction();

        Bundle bundle = new Bundle();
        bundle.putString("mobno", mobileno);
        frag.setArguments(bundle);

        if(flag==0)
            ft.replace(R.id.container,frag);
        else
            ft.replace(R.id.container,frag);
        ft.commit();
    }

}