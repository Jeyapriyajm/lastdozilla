package com.example.lastdozilla;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.core.utilities.Utilities;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameEditText,passwordEditText;
    private ProgressBar progressBar;
    private TextView registerTextView;
    private FirebaseAuth authprofile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        progressBar = findViewById(R.id.progressBar);
        registerTextView = findViewById(R.id.register);




        authprofile = FirebaseAuth.getInstance();

        //Register

        registerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //Login

        Button login_button = findViewById(R.id.register_button);
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if(TextUtils.isEmpty(username)){
                    Toast.makeText(LoginActivity.this, "Please enter the user name", Toast.LENGTH_LONG);
                    usernameEditText.setError("User Name is Requied");
                    usernameEditText.requestFocus();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(username).matches()) {
                    Toast.makeText(LoginActivity.this, "Please enter the valid user name", Toast.LENGTH_LONG);
                    usernameEditText.setError("Please enter the valid user name");
                    usernameEditText.requestFocus();
                }else if (TextUtils.isEmpty(password)) {

                    Toast.makeText(LoginActivity.this, "Pease Enter the Password",Toast.LENGTH_LONG);
                    passwordEditText.setError("Password is Required");
                    passwordEditText.requestFocus();

                } else if (password.length() < 8) {
                    Toast.makeText(LoginActivity.this, "the password is less than 8 character", Toast.LENGTH_LONG);
                    passwordEditText.setError("password is week");
                    passwordEditText.requestFocus();
                }else{
                    progressBar.setVisibility(View.VISIBLE);
                    loginUser(username,password);
                }

            }


        });



    }

    private void loginUser(String email, String password1) {


        authprofile.signInWithEmailAndPassword(email,password1).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()){
                    Toast.makeText(LoginActivity.this, "You are Loged in now",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK
                            | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();

                }else {
                    Toast.makeText(LoginActivity.this, "Something went wrong",Toast.LENGTH_LONG).show();
                }
                progressBar.setVisibility(View.GONE);

            }
        });







    }
}