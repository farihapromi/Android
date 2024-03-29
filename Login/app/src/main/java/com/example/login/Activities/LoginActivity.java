package com.example.login.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.login.R;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private EditText userMail,userPassword;
    private Button btnLogin;
    private ProgressBar loginProgress;
    private FirebaseAuth mAuth;
    private Intent HomeActivity;
    private ImageView loginPhoto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userMail=findViewById(R.id.login_email);
        userPassword=findViewById(R.id.login_password);
        btnLogin=findViewById(R.id.btnLogin);
        loginProgress=findViewById(R.id.progressBarLogin);
        loginProgress.setVisibility(View.INVISIBLE);
        mAuth=FirebaseAuth.getInstance();
        loginPhoto=findViewById(R.id.loginImg);
        loginPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent register=new Intent(getApplicationContext(),Register.class);
                startActivity(register);
                finish();
            }
        });
        HomeActivity=new Intent (this,com.example.login.Activities.HomeActivity.class);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginProgress.setVisibility(View.VISIBLE);
                btnLogin.setVisibility(View.INVISIBLE);
                final String mail=userMail.getText().toString();
                final String password=userPassword.getText().toString();

                if(mail.isEmpty()|| password.isEmpty()){
                    showMessage("Please verify Account");
                    btnLogin.setVisibility(View.VISIBLE);
                    loginProgress.setVisibility(View.INVISIBLE);
                }
                else
                {
                    SignIn(mail,password);
                }

            }
        });
    }

    private void SignIn(String mail, String password) {

        mAuth.signInWithEmailAndPassword(mail, password)
.addOnCompleteListener(new OnCompleteListener<AuthResult>() {
    @Override
    public void onComplete(@NonNull Task<AuthResult> task) {
        if(task.isSuccessful()){
            loginProgress.setVisibility(View.INVISIBLE);
            btnLogin.setVisibility(View.VISIBLE);
            updateUI();
        }
        else
            showMessage(task.getException().getMessage());
            btnLogin.setVisibility(View.VISIBLE);
            loginProgress.setVisibility(View.INVISIBLE);

    }

});
    }


    private void updateUI() {
        startActivity(HomeActivity);
        finish();
    }

    private void showMessage(String text) {
        Toast.makeText(getApplicationContext(),text,Toast.LENGTH_LONG).show();
        
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user=mAuth.getCurrentUser();
        if(user != null){
            //user is connected
            updateUI();
        }
    }

}