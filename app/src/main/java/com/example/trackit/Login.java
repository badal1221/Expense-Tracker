package com.example.trackit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    EditText nametxt,mobtxt,pass;
    TextView click;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://track-it-a092e-default-rtdb.firebaseio.com/");
        nametxt = findViewById(R.id.nametxt);
        mobtxt = findViewById(R.id.mobtxt);
        pass = findViewById(R.id.pass);
        submit=findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=nametxt.getText().toString();
                String mobileno=mobtxt.getText().toString();
                String password=pass.getText().toString();
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(!snapshot.child("users").hasChild(mobileno)){
                            Toast.makeText(Login.this, "No account with this number", Toast.LENGTH_SHORT).show();
                        } else if (!snapshot.child("users").child(mobileno).child("password").getValue(String.class).equals(password)) {
                            Toast.makeText(Login.this, "Password doesn't match", Toast.LENGTH_SHORT).show();
                        }else if (!snapshot.child("users").child(mobileno).child("name").getValue(String.class).equals(name)) {
                            Toast.makeText(Login.this, "Username doesn't match", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                            MemoryData.saveData("Loggedin",Login.this);
                            MemoryData.savenumber(mobileno,Login.this);
                            Intent intent=new Intent(Login.this,MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(Login.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}