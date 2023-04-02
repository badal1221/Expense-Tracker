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

import java.util.regex.Pattern;

public class Signup extends AppCompatActivity {

    EditText nametxt,mobtxt,emailtxt,pass,repass;
    TextView login;
    Button sign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        if(!MemoryData.getData(Signup.this).isEmpty()){
            Intent intent=new Intent(Signup.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://track-it-a092e-default-rtdb.firebaseio.com/");
        nametxt = findViewById(R.id.nametxt);
        mobtxt = findViewById(R.id.mobtxt);
        emailtxt = findViewById(R.id.emailtxt);
        pass = findViewById(R.id.pass);
        repass = findViewById(R.id.repass);
        login = findViewById(R.id.login);
        sign = findViewById(R.id.sign);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Signup.this, Login.class);
                startActivity(intent);
                finish();
            }
        });
        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=nametxt.getText().toString();
                String mobileno=mobtxt.getText().toString();
                String email=emailtxt.getText().toString();
                String password=pass.getText().toString();
                String repassword=repass.getText().toString();
                if(name.isEmpty()||mobileno.isEmpty()||email.isEmpty()||password.isEmpty()||repassword.isEmpty()){
                    Toast.makeText(Signup.this,"All fields are required",Toast.LENGTH_SHORT).show();
                } else if (!password.equals(repassword)) {
                    Toast.makeText(Signup.this,"Password doesn't match",Toast.LENGTH_SHORT).show();
                }else if(!isNumeric(mobileno) || mobileno.length()!=10){
                    Toast.makeText(Signup.this,"Enter a valid mobile number",Toast.LENGTH_SHORT).show();
                }
                else if(!isValid(email)){
                    Toast.makeText(Signup.this,"Enter a valid email id",Toast.LENGTH_SHORT).show();
                }
                else{
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.child("users").hasChild(mobileno)){
                                Toast.makeText(Signup.this, "Mobile number already exists", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                databaseReference.child("users").child(mobileno).child("name").setValue(name);
                                databaseReference.child("users").child(mobileno).child("password").setValue(password);
                                databaseReference.child("users").child(mobileno).child("email").setValue(email);
                                databaseReference.child("users").child(mobileno).child("balance").setValue("0");
                                Toast.makeText(Signup.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                                MemoryData.saveData("Loggedin",Signup.this);
                                MemoryData.savenumber(mobileno,Signup.this);
                                Intent intent=new Intent(Signup.this,MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(Signup.this, error.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
    public static boolean isValid(String email){
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }
}