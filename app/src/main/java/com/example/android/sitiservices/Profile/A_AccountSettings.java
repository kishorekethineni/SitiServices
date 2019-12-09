package com.example.android.sitiservices.Profile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.android.sitiservices.Login.A_SignIn;
import com.example.android.sitiservices.Login.D_CurrentUser;
import com.example.android.sitiservices.R;
import com.example.android.sitiservices.Services.D_ProfileOfWorker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
public class A_AccountSettings extends AppCompatActivity  {

    Button deleteAccount,upload,getimage;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    EditText gender,type;
    ImageView imageView;
    EditText name,mobile,bio,category,email,address;
    Button button;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a__account_settings);

        name = findViewById(R.id.name_of_worker);
        mobile = findViewById(R.id.mobile_of_worker);
        bio = findViewById(R.id.bio_of_worker);
        category = findViewById(R.id.category_of_worker);
        address=findViewById(R.id.address_of_worker);
        email = findViewById(R.id.email_of_worker);
        button = findViewById(R.id.submit_of_worker);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                D_ProfileOfWorker d_profileOfWorker = new D_ProfileOfWorker(name.getText().toString(),mobile.getText().toString(),email.getText().toString(),address.getText().toString(),"Please paste here",bio.getText().toString() );
               uploadToFirebase(d_profileOfWorker);
            }
        });
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
    }

    private void uploadToFirebase(D_ProfileOfWorker d_profileOfWorker)
    {
     DatabaseReference firebaseDatabase=FirebaseDatabase.getInstance().getReference("Workers");
     firebaseDatabase.child(category.getText().toString()).push().setValue(d_profileOfWorker).addOnCompleteListener(new OnCompleteListener<Void>() {
         @Override
         public void onComplete(@NonNull Task<Void> task) {
             if (task.isSuccessful())
             {
                 Toast.makeText(A_AccountSettings.this, "Success", Toast.LENGTH_SHORT).show();
             }
         }
     });
       // SetAdmin();
    }

    private void SetAdmin()
    {
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("Admin").child("W0FWrH9LbuP8THYf5C5Xq73h8y12");
        databaseReference.child("Email").setValue("abhilashguptha5610@gmail.com");
        databaseReference.child("Name").setValue("Abhilash");
        databaseReference.child("Password").setValue("123456");
        databaseReference.child("ProfileImage").setValue("Please paste here");
    }


}
