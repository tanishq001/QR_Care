package com.example.qrcare;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.zxing.WriterException;

import java.io.ByteArrayOutputStream;

public class Register extends AppCompatActivity {
    Button register;
    TextInputEditText name,number,email,password;
    ProgressBar bar;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();;
    private static final String TAG = "MyActivity";
    DatabaseReference reff= FirebaseDatabase.getInstance().getReference();
    FirebaseUser currentUser = mAuth.getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        register=findViewById(R.id.registerbtn);
        name=findViewById(R.id.edit_name);
        number=findViewById(R.id.edit_number);
        email=findViewById(R.id.edit_emailreg);
        password=findViewById(R.id.edit_passreg);
        bar=findViewById(R.id.barreg);




        if(currentUser != null){
            Intent intent = new Intent(Register.this,AllOptions.class);
            startActivity(intent);
        }
        register.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {
                regOp();
            }
        });

    }

    private void regOp() {
        if(name.getText().toString().isEmpty())
        {
            name.setError("Enter Name");
        }
        else if(number.getText().toString().isEmpty())
        {
           number.setError("Enter Your Number");
        }
        else if(email.getText().toString().isEmpty())
        {
            email.setError("Enter Email");
        }
        else if(password.getText().toString().isEmpty())
        {
            password.setError("Enter Password");
        }

        else if(password.getText().toString().length()<6)
        {
            password.setError("Password Should be of 6 letter");
        }
        else{
            bar.setVisibility(View.VISIBLE); //to show
            mAuth.createUserWithEmailAndPassword(email.getText().toString(),password.getText().toString())
                    .addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG,"createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                updateUI();
                                //writeNewUser(new_mail.getText().toString(),name.getText().toString(),mobile_no.getText().toString());
                                //t
                                boolean result =generateQr();
                                if(result==false){
                                    bar.setVisibility(View.GONE);
                                    Toast.makeText(Register.this,"Qr not generated",Toast.LENGTH_SHORT).show();
                                }
                                else {

                                    User userd=new User(name.getText().toString(),email.getText().toString(),number.getText().toString());
                                    reff.child("User").child(mAuth.getUid()).setValue(userd);

                                    bar.setVisibility(View.GONE);
                                    Intent intent =new Intent(Register.this,ChooseLayout.class);
                                    startActivity(intent);
                                    Toast.makeText(Register.this,"Registered Successfully.",Toast.LENGTH_SHORT).show();
                                    finish();

                                }


                            } else {
                                bar.setVisibility(View.GONE); //to show

                                // If sign in fails, display a message to the user.
                                Log.w(TAG , "createUserWithEmail:failure", task.getException());
                                Toast.makeText(Register.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                updateUI();

                            }

                            // ...
                        }
                    });

        }
    }
    private boolean generateQr() {
        String inputValue=mAuth.getUid();
        QRGEncoder qrgEncoder;
        Bitmap bitmap;
        boolean result;
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();



        if (inputValue.length() > 0) {
            WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
            Display display = manager.getDefaultDisplay();
            Point point = new Point();
            display.getSize(point);
            int width = point.x;
            int height = point.y;
            int smallerDimension = width < height ? width : height;
            smallerDimension = smallerDimension * 3 / 4;

            qrgEncoder = new QRGEncoder(
                    inputValue, null,
                    QRGContents.Type.TEXT,
                    smallerDimension);
            try {
                bitmap = qrgEncoder.encodeAsBitmap();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] data = baos.toByteArray();

                StorageReference mountainsRef = storageRef.child(mAuth.getUid()).child("QR CODE");

                UploadTask uploadTask = mountainsRef.putBytes(data);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Toast.makeText(Register.this,String.valueOf(exception),Toast.LENGTH_SHORT).show();                        // ...

                    }

                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // ...
                    }
                });
                return true;

            } catch ( WriterException e) {
                Log.v(TAG, e.toString());
                return false;
            }


        } else {
            return false;
        }

    }
    private void updateUI() {
    }
    private void reload() {
        mAuth.getCurrentUser().reload().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    updateUI();
                    Toast.makeText(Register.this,
                            "Reload successful!",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Log.e(TAG, "reload", task.getException());
                    Toast.makeText(Register.this,
                            "Failed to reload user.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}