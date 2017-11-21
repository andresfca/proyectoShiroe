package com.example.andres.shiroestore.activity.login;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.Toast;

import com.example.andres.shiroestore.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SingInActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private EditText userName;
    private EditText email;
    private EditText password;
    private Switch swtShowPassword;
    private ProgressBar miprogress;
    private ObjectAnimator anim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_in);

        miprogress = (ProgressBar) findViewById(R.id.circularProgress);
        anim = ObjectAnimator.ofInt(miprogress, "progress", 0, 100);

        swtShowPassword=(Switch)findViewById(R.id.swtPassword);
        userName=(EditText)findViewById(R.id.txtSingUpUserName);
        email =(EditText)findViewById(R.id.txtSingUpEmail);
        password= (EditText)findViewById(R.id.txtSingUpPassword);
        swtShowPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // checkbox status is changed from uncheck to checked.
                if (!isChecked) {
                    // show password
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    // hide password
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });
        firebaseAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener(){

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
            }
        };
    }
    private void mostrarProgress() {
        //agregamos el tiempo de la animacion a mostrar
        anim.setDuration(15000);
        anim.setInterpolator(new DecelerateInterpolator());
        miprogress.setVisibility(View.VISIBLE);
        //iniciamos el progressbar
        anim.start();
    }
    public void crearUsuario(String email,String password){
        mostrarProgress();
        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Intent i = new Intent(SingInActivity.this,MainActivity.class);
                    startActivity(i);
                    finish();
                }else{
                    Toast.makeText(SingInActivity.this,task.getException()
                            .getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    public void crearUsuario(View v){
        crearUsuario(email.getText().toString(),password.getText().toString());
    }
    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }
    @Override
    protected void onStop() {
        super.onStop();
        firebaseAuth.removeAuthStateListener(authStateListener);
    }

}
