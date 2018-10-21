package com.soleeklab.www.countries;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    Button registerButton;
    EditText loginPass;
    EditText loginEmail;
    View loadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setAlpha(0.0f);



        mAuth = FirebaseAuth.getInstance();

        loginEmail = (EditText) findViewById(R.id.loginEmail);
        loginPass = (EditText) findViewById(R.id.loginPass);

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            goToHomeActivtiy();
        }


        loginPass = (EditText) findViewById(R.id.loginPass);
        loginPass.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE) {
                    loadingIndicator.setAlpha(1.0f);
                    internetCheck();
                    return true;
                }

                return false;
            }
        });

        registerButton = (Button) findViewById(R.id.btn_register);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(registerIntent);
                finish();

            }
        });

    }

    public void Login(View view) {

        loadingIndicator.setAlpha(1.0f);
        internetCheck();


    }

    public void goToHomeActivtiy() {
        Intent HomeIntent = new Intent(LoginActivity.this, CountriesActivity.class);
        startActivity(HomeIntent);
        finish();
    }

    public void internetCheck() {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {

            loginCheck();


        } else {
            loadingIndicator.setAlpha(0.0f);
            Toast.makeText(getApplicationContext(), "No Internet connection", Toast.LENGTH_LONG).show();

        }
    }

    public void loginCheck() {
        String Email = loginEmail.getText().toString();
        String Password = loginPass.getText().toString();
        if (Email.isEmpty() || Email.contains(" ")) {
            loginEmail.setError("fill here");
            loadingIndicator.setAlpha(0.0f);

            return;
        }
        if (Password.isEmpty() || Password.contains(" ")) {
            loginPass.setError("fill here");
            loadingIndicator.setAlpha(0.0f);
            return;
        }
        mAuth.signInWithEmailAndPassword(Email, Password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            goToHomeActivtiy();

                        } else {
                            loadingIndicator.setAlpha(0.0f);
                            loginEmail.setError("email is not correct");
                            loginPass.setError("password is not correct");


                        }
                    }
                });
    }
}
