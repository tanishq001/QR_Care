package com.example.qrcare;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.card.MaterialCardView;

public class AllOptions extends AppCompatActivity {
MaterialCardView myqrcode;
Dialog showqr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_options);
        myqrcode=findViewById(R.id.qrcode);

        showqr=new Dialog(this);

        myqrcode.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {
                showQr();
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