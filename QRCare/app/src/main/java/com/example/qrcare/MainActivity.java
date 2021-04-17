package com.example.qrcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    Button login,register;
    private FirebaseAuth mAuth;
    TextInputEditText user_mail,user_pass;
    ProgressBar bar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login=findViewById(R.id.loginButton);
        register=findViewById(R.id.newuserButton);
        user_mail=findViewById(R.id.edit_email);
        user_pass=findViewById(R.id.edit_pass);
        bar=findViewById(R.id.bar);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser != null){
            Intent intent = new Intent(MainActivity.this, AllOptions.class);
            startActivity(intent);
            finish();

        }

        login.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {
                loginOp();
            }
        });
        register.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,Register.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void loginOp() {
        if(user_mail.getText().toString().isEmpty())
        {
            user_mail.setError("Enter your email");
        }
        else if(user_pass.getText().toString().isEmpty())
        {
            user_pass.setError("Enter your password");
        }
        else {
            bar.setVisibility(View.VISIBLE); //to show
            mAuth.signInWithEmailAndPassword(user_pass.getText().toString(), user_pass.getText().toString())
                    .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("TAG", "signInWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                updateUI(null);
                                bar.setVisibility(View.GONE); //
                                Intent intent = new Intent(MainActivity.this, AllOptions.class);
                                startActivity(intent);
                                finish();

                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("TAG", "signInWithEmail:failure", task.getException());
                                Toast.makeText(MainActivity.this, "Email or Password is incorrect.",
                                        Toast.LENGTH_SHORT).show();
                                updateUI(null);
                                bar.setVisibility(View.GONE); //to show

                                // ...
                            }

                            // ...
                        }
                    });
        }
    }
    private void updateUI(Object o) {

    }
}