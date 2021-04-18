package com.example.qrcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Profile extends AppCompatActivity {
    TextView username,mobile,c_name,c_mobile,c_email;
    Button logout,purchased,buycard,editcard;
    LinearLayout layout;
    private FirebaseAuth mAuth;
    StorageReference pathReference;
    DatabaseReference ref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        username=findViewById(R.id.username_textp);
        mobile=findViewById(R.id.mobile_textp);
        c_name=(TextView)findViewById(R.id.n_name);
        c_mobile=(TextView)findViewById(R.id.n_mob);
        c_email=(TextView)findViewById(R.id.n_email);
        layout=(LinearLayout)findViewById(R.id.layout);
        logout=findViewById(R.id.logout);
        purchased=findViewById(R.id.purchasedCard);
        buycard=findViewById(R.id.buyCard);
        editcard=findViewById(R.id.editmycard);

        mAuth = FirebaseAuth.getInstance();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        ref = database.getReference().child("User").child(mAuth.getUid());
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        pathReference = storageRef.child(mAuth.getUid()).child("Layout");

             setData();
             editcard.setOnClickListener(new View.OnClickListener( ) {
                 @Override
                 public void onClick(View view) {
                     Toast.makeText(Profile.this,"Sorry We Are Still Woring On it",Toast.LENGTH_SHORT).show();
                 }
             });
             purchased.setOnClickListener(new View.OnClickListener( ) {
                 @Override
                 public void onClick(View view) {
                     Toast.makeText(Profile.this,"Sorry We Are Still Woring On it",Toast.LENGTH_SHORT).show();

                 }
             });
             buycard.setOnClickListener(new View.OnClickListener( ) {
                 @Override
                 public void onClick(View view) {
                     Toast.makeText(Profile.this,"Sorry We Are Still Woring On it",Toast.LENGTH_SHORT).show();
                 }
             });
             logout.setOnClickListener(new View.OnClickListener( ) {
                 @Override
                 public void onClick(View view) {
                     FirebaseAuth.getInstance().signOut();
                     Intent intent=new Intent(Profile.this,MainActivity.class);
                     startActivity(intent);
                     finish();
                     

                 }
             });



    }

    private void setData() {
        final long ONE_MEGABYTE = 1024 * 1024;
        pathReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                Drawable d = new BitmapDrawable(getResources(), bitmap);
                layout.setBackground(d);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
            }
        });

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    String e=dataSnapshot.child("username").getValue().toString();
                    String mb=dataSnapshot.child("mobile_no").getValue().toString();
                    String email=dataSnapshot.child("email").getValue().toString();
                    username.setText(e);
                    mobile.setText(mb);
                    c_email.setText(email);
                    c_name.setText(e);
                    c_mobile.setText(mb);
                }catch (Exception e){

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

    }
}