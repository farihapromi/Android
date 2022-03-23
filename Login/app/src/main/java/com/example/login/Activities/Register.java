package com.example.login.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.biometrics.BiometricPrompt;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.login.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class Register extends AppCompatActivity {
    ImageView imgPhoto;
    static int PReqCode=1;
    static int REQUESCODE=1;
    Uri pickedImgUri;
    private EditText userEmail,userPassword,userPhone,userName;
    private ProgressBar loadingProgress;
    private Button regBtn;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        userEmail=findViewById(R.id.regEmail);
        userName=findViewById(R.id.regName);
        userPassword=findViewById(R.id.regPass);
        userPhone=findViewById(R.id.regPhone);
        regBtn=findViewById(R.id.regbtn);
        loadingProgress=findViewById(R.id.progressBar);
        mAuth= FirebaseAuth.getInstance();
        loadingProgress.setVisibility(View.INVISIBLE);



        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                regBtn.setVisibility(View.INVISIBLE);
                loadingProgress.setVisibility(View.INVISIBLE);

                final String email=userEmail.getText().toString();
                final String password=userPassword.getText().toString();
                final String name=userName.getText().toString();
                final String phone=userPhone.getText().toString();
                if(email.isEmpty() || name.isEmpty() ||password.isEmpty()|| phone.isEmpty()){
                    //We need to show error msg
                    showMessage("Please verify  all fields");
                    regBtn.setVisibility(View.VISIBLE);
                    loadingProgress.setVisibility(View.VISIBLE);
                    
                }
                else{
                    //everything is ok.You can create account
                    CreateUserAccount(email,name,password,phone);
                }

            }
        });



        imgPhoto=findViewById(R.id.img1);
        imgPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Build.VERSION.SDK_INT>=22){
                    checkAndRequestForPermision();

                }
                else{
                    openGallery();
                }
            }


        });
    }

    private void CreateUserAccount(String email, final String name, String password, String phone) {


//this method create user account
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            showMessage("Account created");
                            //after created accout we have to update name
                            updateUserInfo(name,pickedImgUri,mAuth.getCurrentUser());
                        }
                        else{
                            showMessage("Account creation failed"+task.getException().getMessage());
                            regBtn.setVisibility(View.VISIBLE);
                            loadingProgress.setVisibility(View.INVISIBLE);
                        }

                    }
                });




    }



private void updateUserInfo( final String name,Uri pickedImgUri, final FirebaseUser currentUser){

        StorageReference mStorage= FirebaseStorage.getInstance().getReference().child("user_photos");
        StorageReference imageFilePath=mStorage.child(pickedImgUri.getLastPathSegment());
        imageFilePath.putFile(pickedImgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                //Image uploaded successfully
                //now we can get image url
                imageFilePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        //URI CONTAIN USER IMAGE URI

                        UserProfileChangeRequest profileUpdate=new UserProfileChangeRequest.Builder()

                                .setDisplayName(name)
                                .setPhotoUri(uri)
                                .build();

                        currentUser.updateProfile(profileUpdate)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            showMessage("Register successful");
                                            updateUI();

                                        }

                                    }
                                });



                    }
                });

            }
        });



    }

    private void updateUI() {
        Intent homeActivity=new Intent(getApplicationContext(),HomeActivity.class);
        startActivity(homeActivity);
        finish();
    }

    //simple method to show toast msg
    private void showMessage(String meassge) {
        Toast.makeText(getApplicationContext(),meassge,Toast.LENGTH_LONG).show();


    }

    private void openGallery() {
        Intent galleryIntent=new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,REQUESCODE);


    }



    private void checkAndRequestForPermision() {
        if (ContextCompat.checkSelfPermission(Register.this, Manifest.permission.READ_EXTERNAL_STORAGE) !=

                PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(Register.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {

                Toast.makeText(Register.this, "Please accept for required permision", Toast.LENGTH_SHORT).show();

            } else {
                ActivityCompat.requestPermissions(Register.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PReqCode);

            }

        } else {
            openGallery();

        }}


        @Override
        protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
        {
            super.onActivityResult(requestCode, resultCode, data);
            if (resultCode == RESULT_OK && requestCode == REQUESCODE && data != null) {


                pickedImgUri = data.getData();
                imgPhoto.setImageURI(pickedImgUri);


            }

        }
    }
