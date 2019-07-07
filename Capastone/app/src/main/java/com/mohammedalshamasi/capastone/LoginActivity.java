package com.mohammedalshamasi.capastone;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    TextInputEditText mPassword,mEmail;
    Button mLoginButton;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setTitle(R.string.login);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mEmail=findViewById(R.id.email);
        mPassword=findViewById(R.id.password);
        mLoginButton=findViewById(R.id.login_button);
        auth=FirebaseAuth.getInstance();

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt_email = mEmail.getText().toString();
                String txt_password = mPassword.getText().toString();
                if (TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_password)){
                    Toast.makeText(LoginActivity.this, getString(R.string.Error_fileds), Toast.LENGTH_SHORT).show();
                } else if (txt_password.length() < 6 ){
                    Toast.makeText(LoginActivity.this, getString(R.string.Password_error), Toast.LENGTH_SHORT).show();
                }else
                    auth.signInWithEmailAndPassword(txt_email,txt_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                                startActivity(intent);
                            }
                            else
                                Toast.makeText(LoginActivity.this,getString(R.string.auth_fail),Toast.LENGTH_SHORT).show();
                        }
                    });
            }
        });

    }
}
