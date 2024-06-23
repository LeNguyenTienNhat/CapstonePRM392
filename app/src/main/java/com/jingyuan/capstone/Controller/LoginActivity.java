package com.jingyuan.capstone.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;
import com.jingyuan.capstone.DTO.Firebase.UserDTO;
import com.jingyuan.capstone.R;

public class LoginActivity extends AppCompatActivity {
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    public EditText usernameInputField;
    public EditText passwordInputField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setContentView(R.layout.activity_login);
        usernameInputField = findViewById(R.id.user);
        passwordInputField = findViewById(R.id.password);
        Button signInBtn = findViewById(R.id.signInBtn);
        Button signUpSuggestBtn = findViewById(R.id.signUpSuggestText);
        signInBtn.setOnClickListener(LoginActivity.this::onSignInBtnClick);
        signUpSuggestBtn.setOnClickListener(LoginActivity.this::setUpSignUpScreen);
    }

    @Override
    public void onStart() {
        super.onStart();
        testUpdateUI();
    }

    public void onSignUpBtnClick(View v) {
        String username = usernameInputField.getText().toString();
        String password = passwordInputField.getText().toString();
        createAccount(username,password);
    }

    public void onSignInBtnClick(View v) {
        String username = usernameInputField.getText().toString();
        String password = passwordInputField.getText().toString();
        signIn(username,password);
    }

    public void setUpSignUpScreen(View v) {
        setContentView(R.layout.activity_signup);
        usernameInputField = findViewById(R.id.user);
        passwordInputField = findViewById(R.id.password);
        Button signUpBtn = findViewById(R.id.signUpBtn);
        Button signInSuggestBtn = findViewById(R.id.signInSuggestText);
        signUpBtn.setOnClickListener(LoginActivity.this::onSignUpBtnClick);
        signInSuggestBtn.setOnClickListener(LoginActivity.this::setUpLogInScreen);
    }

    public void setUpLogInScreen(View v) {
        setContentView(R.layout.activity_login);
        usernameInputField = findViewById(R.id.user);
        passwordInputField = findViewById(R.id.password);
        Button signInBtn = findViewById(R.id.signInBtn);
        Button signUpSuggestBtn = findViewById(R.id.signUpSuggestText);
        signInBtn.setOnClickListener(LoginActivity.this::onSignInBtnClick);
        signUpSuggestBtn.setOnClickListener(LoginActivity.this::setUpSignUpScreen);
    }

    private void createAccount(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Account created successfully.",
                                    Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void signIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(LoginActivity.this, "Sign in successfully.",
                                Toast.LENGTH_SHORT).show();
                        FirebaseUser user = mAuth.getCurrentUser();
                        updateUI(user);
                    } else {
                        Toast.makeText(LoginActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void updateUI(FirebaseUser user) {
        Intent i = new Intent(getApplicationContext(), HomeActivity.class);
        String uid = user.getUid();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("User").document(uid);
        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot snap = task.getResult();
                UserDTO userDTO = snap.toObject(UserDTO.class);
                assert userDTO != null;
                i.putExtra("username",userDTO.getUsername());
                i.putExtra("email",userDTO.getEmail());
                i.putExtra("pfp",userDTO.getPfp());
                startActivity(i);
                finish();
            }
        });
    }

    private void testUpdateUI() {
        Intent i = new Intent(getApplicationContext(), HomeActivity.class);
        i.putExtra("username","Ambatukam");
        i.putExtra("email","ambatukam@gmail.com");
        i.putExtra("pfp","https://firebasestorage.googleapis.com/v0/b/capstone-c62ee.appspot.com/o/1686506930924.jpg?alt=media");
        startActivity(i);
        finish();
    }
}