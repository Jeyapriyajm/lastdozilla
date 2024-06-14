package com.example.lastdozilla;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;



public class RegisterActivity extends AppCompatActivity {



    private EditText username,password,confirm_password;
    private TextView login;
    private ProgressBar progressBar;
    private static final String TAG= "RegisterActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        Toast.makeText(RegisterActivity.this, "You can register now", Toast.LENGTH_LONG).show();

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        confirm_password = findViewById(R.id.confirm_password);
        progressBar = findViewById(R.id.progressBar);
        login = findViewById(R.id.register);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Button register_button = findViewById(R.id.register_button);
        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String textusername = username.getText().toString();
                String texpassword = password.getText().toString();
                String texconfirmPassword = confirm_password.getText().toString();

                if(TextUtils.isEmpty(textusername)){
                    Toast.makeText(RegisterActivity.this, "Please enter the user name", Toast.LENGTH_LONG);
                    username.setError("User Name is Requied");
                    username.requestFocus();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(textusername).matches()) {
                    Toast.makeText(RegisterActivity.this, "Please enter the valid user name",Toast.LENGTH_LONG);
                    username.setError("Please enter the valid user name");
                    username.requestFocus();

                } else if (TextUtils.isEmpty(texpassword)) {

                    Toast.makeText(RegisterActivity.this, "Pease Enter the Password",Toast.LENGTH_LONG);
                    password.setError("Password is Required");
                    password.requestFocus();

                } else if (texpassword.length() < 8) {
                    Toast.makeText(RegisterActivity.this, "the password is less than 8 character",Toast.LENGTH_LONG);
                    password.setError("password is week");
                    password.requestFocus();
                } else if (TextUtils.isEmpty(texconfirmPassword)) {

                    Toast.makeText(RegisterActivity.this, " Enter the Confirm Password",Toast.LENGTH_LONG);
                    confirm_password.setError("Confirm Password is Required");
                    confirm_password.requestFocus();
                } else if (!texpassword.equals(texconfirmPassword)) {

                    Toast.makeText(RegisterActivity.this, " Enter the same Password",Toast.LENGTH_LONG);
                    confirm_password.setError("Should same Confirm Password is Required");
                    confirm_password.requestFocus();

                    //clear the confirm password
                    password.clearComposingText();
                    confirm_password.clearComposingText();

                }else{
                    progressBar.setVisibility(View.VISIBLE);
                    registerUser(textusername,texpassword);
                }

            }
        });

    }

    private void registerUser(String textusername, String texpassword) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(textusername,texpassword).addOnCompleteListener(RegisterActivity.this,
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){

                            FirebaseUser firebaseUser = auth.getCurrentUser();
                            //profile change

                            UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder().setDisplayName(textusername).build();
                            firebaseUser.updateProfile(profileChangeRequest);

                            //ENTER USER DATA TO REALTIME DATABASE
                            ReadWriteUserDetails writeUserDetails = new ReadWriteUserDetails(textusername, texpassword);

                            DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference( "Registered users");


                            referenceProfile.child(firebaseUser.getUid()).setValue(writeUserDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful()){

                                        firebaseUser.sendEmailVerification();

                                        Toast.makeText(RegisterActivity.this, "User Register Successful, please verify your email address",
                                                Toast.LENGTH_LONG).show();

                                        Intent intent = new Intent(RegisterActivity.this, DashboardActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK
                                                    | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        finish();

                                }else {
                                        Toast.makeText(RegisterActivity.this, "User Register Failed",Toast.LENGTH_LONG).show();

                                    }
                                    progressBar.setVisibility(View.GONE);
                                    }
                            });



                        }else{
                            try {
                                throw  task.getException();

                            } catch (FirebaseAuthInvalidCredentialsException e) {
                                username.setError("Your email is invalid or already in use. Kindly re-enter.");
                                username.requestFocus();
                            } catch (FirebaseAuthUserCollisionException e) {
                                username.setError("User is already registered with this email. Use another email.");
                                username.requestFocus();
                            } catch (Exception e) {
                                Log.e(TAG, "Registration failed: " + e.getMessage(), e);
                                Toast.makeText(RegisterActivity.this, "Registration failed: " + e.getMessage(), Toast.LENGTH_LONG).show();

                            }
                            progressBar.setVisibility(View.GONE);


                    }
                    }
                });
    }


}