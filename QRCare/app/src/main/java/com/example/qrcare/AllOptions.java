package com.example.qrcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class AllOptions extends AppCompatActivity {
MaterialCardView myqrcode,profile,savedcards,fav,explore,referrals;
ExtendedFloatingActionButton button;
    private FirebaseAuth mAuth;
    DatabaseReference ref;
    Dialog showqr;
    TextView username,mobile;
    StorageReference storageRef,pathReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_options);
        myqrcode=findViewById(R.id.qrcode);
        profile=findViewById(R.id.profile);
        savedcards=findViewById(R.id.savedcards);
        fav=findViewById(R.id.favourite);
        explore=findViewById(R.id.explore);
        referrals=findViewById(R.id.referral);
        button=findViewById(R.id.extended_fab);
        username=findViewById(R.id.username_text);
        mobile=findViewById(R.id.mobile_text);
        showqr=new Dialog(this);



        mAuth = FirebaseAuth.getInstance();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        ref = database.getReference().child("User").child(mAuth.getUid());
        storageRef = FirebaseStorage.getInstance().getReference();
        pathReference = storageRef.child(mAuth.getUid()).child("QR CODE");
        updateUsername();


        myqrcode.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {
                showQr();
            }
        });
        profile.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AllOptions.this,Profile.class);
                startActivity(intent);
            }
        });
        savedcards.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AllOptions.this,CardList.class);
                intent.putExtra("type",1);
                startActivity(intent);
            }
        });
        fav.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AllOptions.this,CardList.class);
                intent.putExtra("type",1);
                startActivity(intent);
            }
        });
        explore.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {
                Toast.makeText(AllOptions.this,"Still Working On This",Toast.LENGTH_LONG).show();
            }
        });
        referrals.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AllOptions.this,Referral.class);
                startActivity(intent);
            }
        });
        button.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AllOptions.this,ScanCode.class);
                startActivity(intent);
            }
        });

    }

    private void updateUsername() {
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    String e=dataSnapshot.child("username").getValue().toString();
                    String mb=dataSnapshot.child("mobile_no").getValue().toString();
                    username.setText(e);
                    mobile.setText(mb);
                }catch (Exception e){

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    private void showQr() {
        showqr.setContentView(R.layout.dialogbox);
        showqr.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        final ImageView qrcode=showqr.findViewById(R.id.qrcode);
        Button close=showqr.findViewById(R.id.close);

        final long ONE_MEGABYTE = 1024 * 1024;
        pathReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                Drawable d = new BitmapDrawable(getResources(), bitmap);
                qrcode.setImageDrawable(d);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
            }
        });

        showqr.show();

        close.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {
                showqr.dismiss();

            }
        });
    }
}