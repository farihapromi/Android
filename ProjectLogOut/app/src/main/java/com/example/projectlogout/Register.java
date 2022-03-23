package com.example.projectlogout;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuthException;

public class Register extends AppCompatActivity {
    EditText nFullName,nEmail,nPassword,nPhone;
    Button nRegisterBtn;
    TextView nLoginBtn;
    FirebaseAuth fAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        nFullName=findViewById(R.id.fullName);
        nEmail=findViewById(R.id.email);
        nPassword=findViewById(R.id.password);
        nPhone=findViewById(R.id.phoneNum);
        nRegisterBtn=findViewById(R.id.regbtn);
        nLoginBtn=findViewById(R.id.createText);
        fAuth=FirebaseAuth.getInstance();
        progressBar=findViewById(R.id.progressbar);


        nRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=nEmail.getText().toString().trim();
                String password=nPassword.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    nEmail.setError("Email is Required");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    nPassword.setError("Password required");
                    return;
                }
                if(password.length()<6){
                    nPassword.setError("Password must be greter of equal to 6");
                    return;
                }
                progressBar.setVisibility(view.VISIBLE);
                //reguster to the user firebase
                fAuth.createUserWithEmailAndPassword(email,password).addCompleteListener<AuthResult>.onCompeleListner{

                }









            }
        });


    }
}




