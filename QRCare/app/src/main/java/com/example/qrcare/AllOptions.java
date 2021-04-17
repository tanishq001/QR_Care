package com.example.qrcare;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

public class AllOptions extends AppCompatActivity {
MaterialCardView myqrcode,profile,savedcards,fav,explore,referrals;
ExtendedFloatingActionButton button;
Dialog showqr;
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
        showqr=new Dialog(this);

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
    }

    private void showQr() {
        showqr.setContentView(R.layout.dialogbox);
        showqr.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ImageView qrcode=showqr.findViewById(R.id.qrcode);
        Button close=showqr.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {
                showqr.dismiss();

            }
        });
        showqr.show();
    }
}