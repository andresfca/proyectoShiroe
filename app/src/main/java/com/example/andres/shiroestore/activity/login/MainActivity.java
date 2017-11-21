package com.example.andres.shiroestore.activity.login;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.andres.shiroestore.R;
import com.example.andres.shiroestore.activity.admin.AdminMainViewActivity;
import com.example.andres.shiroestore.activity.user.StoreActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private Button mLogin;
    private TextView mCreateAccount;
    private EditText mUserLogin, user,password;
    private String var;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private ProgressBar miprogress;
    private ObjectAnimator anim;

   /* protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }*/
   @Override
   protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_main);

       miprogress = (ProgressBar) findViewById(R.id.circularProgress);
       anim = ObjectAnimator.ofInt(miprogress, "progress", 0, 100);

       mLogin = (Button) findViewById(R.id.btnLogin);
       mCreateAccount = (TextView) findViewById(R.id.txtCreateAccount);
       mUserLogin = (EditText) findViewById(R.id.txtLoginUser);

       //mLogin.setOnClickListener(mListenerLogin);
       mCreateAccount.setOnClickListener(mListenerCreateAccount);
       firebaseAuth = FirebaseAuth.getInstance();
       authStateListener = new FirebaseAuth.AuthStateListener(){

           @Override
           public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
               FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
           }
       };
   }


    private View.OnClickListener mListenerCreateAccount = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(MainActivity.this, SingInActivity.class);
            startActivity(intent);
        }
    };

   /* private View.OnClickListener mListenerLogin = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            var = mUserLogin.getText().toString();
            if (var.equalsIgnoreCase("Andres")) {
                Intent intent = new Intent(MainActivity.this, AdminMainViewActivity.class);
                startActivity(intent);
                finish();
            } else {
                if (var.equalsIgnoreCase("user")){
                    Intent intent = new Intent(MainActivity.this, StoreActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(MainActivity.this, getResources().getString(R.string.userPasswordIncorrect), Toast.LENGTH_SHORT).show();
                }
            }
        }
    };*/


    public void login(String email, String password){
            if (email.equalsIgnoreCase("acantill26@cuc.edu.co") && password.equalsIgnoreCase("123456789")) {
                Intent intent = new Intent(MainActivity.this, AdminMainViewActivity.class);
                startActivity(intent);
                finish();
            }
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Intent i = new Intent(MainActivity.this,StoreActivity.class);
                    startActivity(i);
                    finish();
                }else {
                    Toast.makeText(MainActivity.this, task.getException()
                            .getMessage(),Toast.LENGTH_LONG).show();
                }

            }
        });
    }
    public void login(View v){
        mostrarProgress();
        user = (EditText)findViewById(R.id.txtLoginUser);
        password = (EditText)findViewById(R.id.txtLoginPassword);
        login(user.getText().toString(),password.getText().toString());
    }

    private void mostrarProgress() {
        //agregamos el tiempo de la animacion a mostrar
        anim.setDuration(15000);
        anim.setInterpolator(new DecelerateInterpolator());
        miprogress.setVisibility(View.VISIBLE);
        //iniciamos el progressbar
        anim.start();
    }


}
