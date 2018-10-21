package com.soleeklab.www.countries;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
     Button Register;
     EditText emailText,newPassword,confirmPassword;
    View loadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setAlpha(0.0f);

        mAuth=FirebaseAuth.getInstance();

        emailText=(EditText)findViewById(R.id.register_email);
        newPassword=(EditText)findViewById(R.id.register_password);
        confirmPassword=(EditText)findViewById(R.id.register_confirmPass);

    }


    public void Register(View view) {
        loadingIndicator.setAlpha(1.0f);
            internetCheck();

    }

    public void internetCheck() {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {

            RegisterCheck();


        } else {
            loadingIndicator.setAlpha(0.0f);
            Toast.makeText(getApplicationContext(), "No Internet connection", Toast.LENGTH_LONG).show();

        }
    }

    private void RegisterCheck() {
        String Email=emailText.getText().toString();
        String pass=newPassword.getText().toString();
        String confPass=confirmPassword.getText().toString();


        if (Email.isEmpty()||Email.contains(" ")){
            emailText.setError("fill here");
            loadingIndicator.setAlpha(0.0f);
            return;
        }
        if (pass.isEmpty()||pass.contains(" ")){
            newPassword.setError("fill here");
            loadingIndicator.setAlpha(0.0f);
            return;
        }
        if (confPass.isEmpty()||confPass.contains(" ")){
            confirmPassword.setError("fill here");
            loadingIndicator.setAlpha(0.0f);
            return;
        }
        if (pass.length()<8){
            newPassword.setError("length must be greater than 8");
            loadingIndicator.setAlpha(0.0f);
            return;
        }

        if(!confPass.equals(pass)){
            confirmPassword.setError("password not match");
            loadingIndicator.setAlpha(0.0f);
            return;
        }


        mAuth.createUserWithEmailAndPassword(Email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent HomeIntent=new Intent(RegisterActivity.this,CountriesActivity.class);
                            startActivity(HomeIntent);
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            loadingIndicator.setAlpha(0.0f);
                            Toast.makeText(getApplicationContext(),"Error in your email",Toast.LENGTH_LONG).show();

                        }
                    }
                });

    }

    public void backToLoginPage(View view) {
        Intent i=new Intent(RegisterActivity.this,LoginActivity.class);
        startActivity(i);
        finish();
    }


}
