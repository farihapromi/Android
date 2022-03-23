package com.example.loginregister;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText username,password,repassword;
    Button btnSignUp,btnSignIn;
    DBHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username=(EditText) findViewById(R.id.username);
        password=(EditText) findViewById(R.id.password);
        repassword=(EditText) findViewById(R.id.repassword);
        btnSignUp=(Button) findViewById(R.id.btnSignUp);
        btnSignIn=(Button) findViewById(R.id.btnSignIn);

        myDB=new DBHelper(this);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user=username.getText().toString();
                String pass=password.getText().toString();
                String repass=repassword.getText().toString();
                if(user.equals("")||pass.equals("")|| repass.equals("")){
                    Toast.makeText(MainActivity.this,"Fill all the field",Toast.LENGTH_SHORT).show();

                }
                else{
                    if(pass.equals(repass))
                    {
                      Boolean userCheckResult= myDB.checkusername(user);
                      if(userCheckResult==false){
                        Boolean regResult=  myDB.insertData(user,pass);
                          if(regResult == true){
                              Toast.makeText(MainActivity.this,"Registration Successfull",Toast.LENGTH_SHORT).show();
                              Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
                              startActivity(intent);

                          }
                          else
                          {
                              Toast.makeText(MainActivity.this,"Not Registered",Toast.LENGTH_SHORT).show();
                          }

                    }
                      else{
                          Toast.makeText(MainActivity.this,"User already exists \n.Please SignIn",Toast.LENGTH_SHORT).show();
                      }

                      }
                    else{
                        Toast.makeText(MainActivity.this,"password did not match",Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
            }
        });

    }
}