package com.example.qrcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

public class ChooseLayout extends AppCompatActivity {
    ListView choose_layout;
    String[] name={"1","2","3","4"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_layout);

        choose_layout=findViewById(R.id.choose_layout);
        int[] image={
                R.drawable.v_1,R.drawable.v_2,
                R.drawable.v_3,R.drawable.v_4
        };
        ChooseAdapter adapter=new ChooseAdapter(this,name,image);
        choose_layout.setAdapter(adapter);
        choose_layout.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(position==0){
                    uploadLayout(R.drawable.v_1);
                }
                else if(position==1){
                    uploadLayout(R.drawable.v_2);
                }
                else if(position==2){
                    uploadLayout(R.drawable.v_3);
                }
                else if (position==3){
                    uploadLayout(R.drawable.v_4);
                }
            }
        });
    }

    public void uploadLayout(int vector){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),vector);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        StorageReference mountainsRef = storageRef.child(mAuth.getUid()).child("Layout");

        UploadTask uploadTask = mountainsRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(ChooseLayout.this,String.valueOf(exception),Toast.LENGTH_SHORT).show();                        // ...

            }

        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(ChooseLayout.this,"Layout Selected Successfully",Toast.LENGTH_SHORT).show();// ...
            }
        });
        Intent intent=new Intent(ChooseLayout.this,AllOptions.class);
        startActivity(intent);
        finish();
    }

}