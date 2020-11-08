package com.example.todo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.facebook.FacebookSdk;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import java.util.Arrays;

import com.google.firebase.auth.FirebaseUser;

public class LOGIN extends AppCompatActivity {
   EditText mEmail,mPassword;
   Button mLoginBtn;
   TextView mRegisterText,t2;
   FirebaseAuth fAuth;
    LoginButton mLoginButton;
    CallbackManager mCallbackManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_l_o_g_i_n);

        mEmail = findViewById(R.id.emailId);
        mPassword = findViewById(R.id.password);
        fAuth = FirebaseAuth.getInstance();
        mLoginBtn = findViewById(R.id.loginBtn);
        mRegisterText = findViewById(R.id.registerText);
        FacebookSdk.sdkInitialize(getApplicationContext());
        mLoginButton = (LoginButton) findViewById(R.id.login_button);
        mCallbackManager = CallbackManager.Factory.create();
        mLoginButton.setReadPermissions(Arrays.asList("email"));
        t2 = (TextView) findViewById(R.id.fbText);



        if (fAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }





    // to move from login class to register class
    public void register(View view) {
        startActivity(new Intent(getApplicationContext(),REGISTER.class));
    }


  // to login using email and password

    public void loginact(View view) {
        String email = mEmail.getText().toString().trim();
        String password = mPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            mEmail.setError("Email cannot be empty");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            mPassword.setError("Password cannot be empty");
            return;
        }
        if (password.length() < 6) {
            mPassword.setError("password should contain minimum of 6 characters");
            return;
        }
        fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(LOGIN.this, "Logged in", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            } else {
                Toast.makeText(LOGIN.this, "Error!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }

            });
}




//facebook 
public void loginButtonActivity(View view) {
    mLoginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            handleFacebookToken(loginResult.getAccessToken());
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }

        @Override
        public void onCancel() {
            Toast.makeText(getApplicationContext(), "User cancelled it", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onError(FacebookException error) {
            Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();

        }








    });
}

    private void handleFacebookToken(AccessToken accessToken) {
        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        fAuth.signInWithCredential(credential).addOnCompleteListener(task -> {
                    if(task.isSuccessful()) {
                        FirebaseUser myUserObj = fAuth.getCurrentUser();
                        updateUI(myUserObj);
                    }
                     else
                    {
                        Toast.makeText(getApplicationContext(),"Could not register",Toast.LENGTH_LONG).show();
                    }
                });



    }
    private void updateUI(FirebaseUser myUserObj) {
        if(myUserObj != null)
             t2.setText(myUserObj.getDisplayName());
         else
             t2.setText("");
    }


}