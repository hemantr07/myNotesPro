package com.example.notespro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginAccountActivity extends AppCompatActivity {

    EditText emailEditText, passwordEditText ;
    Button loginAccountBtn ;
    ProgressBar progressBar ;
    TextView createAccountBtnText ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_account);

        emailEditText = findViewById(R.id.email_edit_text) ;
        passwordEditText = findViewById(R.id.password_edit_text) ;
        loginAccountBtn = findViewById(R.id.login_account_btn) ;
        progressBar = findViewById(R.id.progress_bar) ;
        createAccountBtnText = findViewById(R.id.create_account_btn_textView) ;

        loginAccountBtn.setOnClickListener( (v) -> loginUser());
        createAccountBtnText.setOnClickListener((v) -> startActivity(new Intent(LoginAccountActivity.this, CreateAccountActivity.class)));



    }

    void loginUser()
    {
        String email = emailEditText.getText().toString() ;
        String password = passwordEditText.getText().toString() ;


        boolean isValidate = validateData(email, password) ;

        if( ! isValidate)
        {
            return ;
        }

        loginAccountInFirebase(email, password);
    }

    void loginAccountInFirebase(String email, String password)
    {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance() ;
        changeInProgress(true) ;
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                changeInProgress(false);

                if(task.isSuccessful())
                {
                    // Login Successful
                    if(firebaseAuth.getCurrentUser().isEmailVerified())
                    {
                        // go to main Activity
                        startActivity(new Intent(LoginAccountActivity.this, MainActivity.class));
                        finish() ;

                    }
                    else
                    {   // Email not verified
                        Utility.showToast(LoginAccountActivity.this, "Email not verified, Please verify your email");
                    }

                }
                else
                {
                    // Login not successful
                    Utility.showToast(LoginAccountActivity.this, task.getException().getLocalizedMessage());
                }


            }
        });



    }



    void changeInProgress(boolean inProgress)
    {
        if( inProgress)
        {
            progressBar.setVisibility(View.VISIBLE);
            loginAccountBtn.setVisibility(View.GONE);
        }
        else
        {
            progressBar.setVisibility(View.GONE);
            loginAccountBtn.setVisibility(View.VISIBLE);
        }
    }




    boolean validateData(String email, String password)
    {
        if(! Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            emailEditText.setError("Email is invalid");
            return false ;
        }

        if( passwordEditText.length() < 6)
        {
            passwordEditText.setError("Password length is less than 6");
            return false ;
        }

        return true ;
    }




}