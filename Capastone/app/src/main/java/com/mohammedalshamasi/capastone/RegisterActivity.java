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

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
    TextInputEditText mUsername,mPassword,mEmail;
    Button mRegisterButton;
    private   FirebaseAuth auth;
    DatabaseReference ref;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().setTitle(R.string.register);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        MobileAds.initialize(this,"ca-app-pub-3940256099942544~3347511713");
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
        mAdView.loadAd(adRequest);

        mUsername=findViewById(R.id.username);
        mPassword=findViewById(R.id.password);
        mEmail=findViewById(R.id.email);
        mRegisterButton=findViewById(R.id.Reg_button);
        auth=FirebaseAuth.getInstance();

        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt_username = mUsername.getText().toString();
                String txt_email = mEmail.getText().toString();
                String txt_password = mPassword.getText().toString();

                if (TextUtils.isEmpty(txt_username) || TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_password)){
                    Toast.makeText(RegisterActivity.this, getString(R.string.Error_fileds), Toast.LENGTH_SHORT).show();
                } else if (txt_password.length() < 6 ){
                    Toast.makeText(RegisterActivity.this, getString(R.string.Password_error), Toast.LENGTH_SHORT).show();
                } else {
                    Register(txt_username, txt_email, txt_password);
                }
            }

        });
    }
    private void Register(final String u, String e, String p){

        auth.createUserWithEmailAndPassword(e,p).addOnCompleteListener(this,new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){

                    FirebaseUser firebaseUser=auth.getCurrentUser();
                    assert firebaseUser != null;
                    String userId= firebaseUser.getUid();
                    ref= FirebaseDatabase.getInstance().getReference(getString(R.string.User)).child(userId);
                    HashMap<String , String> hashMap=new HashMap<>();
                    hashMap.put(getString(R.string.id),userId);
                    hashMap.put(getString(R.string.Username_small),u);
                    hashMap.put(getString(R.string.imageUrl),getString(R.string.Default_settings));
                    ref.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Intent intent= new Intent(RegisterActivity.this,MainActivity.class);
                               startActivity(intent);
                            }
                        }
                    });
                }

                else
                    Toast.makeText(RegisterActivity.this,getString(R.string.email_password_error),Toast.LENGTH_SHORT).show();
            }
        });
    }
}
