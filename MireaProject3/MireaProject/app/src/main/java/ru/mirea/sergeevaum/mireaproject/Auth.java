package ru.mirea.sergeevaum.mireaproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

import ru.mirea.sergeevaum.mireaproject.databinding.ActivityAuthBinding;


public class Auth extends AppCompatActivity{
    private static final String TAG = MainActivity.class.getSimpleName();
    private ActivityAuthBinding binding;
    // START declare_auth
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    private String email;
    private String password;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAuthBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance("https://mireaproject-91ac6-default-rtdb.firebaseio.com").getReference();

        binding.signedInButtons.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                email = binding.emailPasswordFields.getText().toString();
                password = binding.passwordEdit.getText().toString();
                String hashedPassword = PasswordHasher.hash(password);
                if (hashedPassword != null) {
                    // Отправка хэшированного пароля в Firebase Database
                    databaseReference.child("user").child("email").setValue(email);
                    databaseReference.child("user").child("password").setValue(hashedPassword);
                }
                Log.d(MainActivity.class.getSimpleName(), "Password" + password);
                signIn(email, password);
                updateUI(mAuth.getCurrentUser());
            }
        });



        binding.emailPasswordButtons.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                email = binding.emailPasswordFields.getText().toString();
                password = binding.passwordEdit.getText().toString();
                String hashedPassword = PasswordHasher.hash(password);
                if (hashedPassword != null) {
                    // Отправка хэшированного пароля в Firebase Database
                    databaseReference.child("user").child("email").setValue(email);
                    databaseReference.child("user").child("password").setValue(hashedPassword);
                }
                createAccount(email, password);
                updateUI(mAuth.getCurrentUser());
            }
        });

        binding.signedOutButtons.setOnClickListener(v -> {
            signOut();
            updateUI(null);
        });

        binding.verifyEmailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmailVerification();
                updateUI(mAuth.getCurrentUser());
            }
        });
        binding.nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Auth.this, MainActivity.class);
                startActivity(i);
            }
        });



    }
    // [START on_start_check_user]

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }
    // [END on_start_check_user]
    private void updateUI(FirebaseUser user) {
        if (user != null) {
            binding.statusTextView.setText(getString(R.string.emailpassword_status_fmt,
                    user.getEmail(), user.isEmailVerified()));
            binding.textViewID.setText(getString(R.string.firebase_status_fmt, user.getUid()));
            binding.emailPasswordButtons.setVisibility(View.GONE);
            binding.emailPasswordFields.setVisibility(View.GONE);
            binding.passwordEdit.setVisibility(View.GONE);
            binding.signedInButtons.setVisibility(View.GONE);
            binding.signedOutButtons.setVisibility(View.VISIBLE);
            binding.verifyEmailButton.setEnabled(!user.isEmailVerified());
            binding.verifyEmailButton.setVisibility(View.VISIBLE);
            binding.LinerLayout3.setVisibility(View.VISIBLE);
            binding.nextButton.setEnabled(true);

        } else {
            binding.statusTextView.setText(R.string.signed_out);
            binding.detailTextView.setText(null);
            binding.emailPasswordButtons.setVisibility(View.VISIBLE);
            binding.passwordEdit.setVisibility(View.VISIBLE);
            binding.emailPasswordFields.setVisibility(View.VISIBLE);
            binding.signedInButtons.setVisibility(View.VISIBLE);
            binding.verifyEmailButton.setVisibility(View.GONE);
            binding.signedOutButtons.setVisibility(View.GONE);
            binding.LinerLayout3.setVisibility(View.VISIBLE);
            binding.nextButton.setEnabled(false);
        }
    }

    private void createAccount(String email, String password) {
        Log.d(TAG, "createAccount:" + email);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            Log.w(TAG, "createUserWithEmail:failure",task.getException());
                            Toast.makeText(Auth.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }
    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(Auth.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                        if (!task.isSuccessful()) {
                            binding.statusTextView.setText(R.string.auth_failed);
                        }
                    }
                });
    }
    private void signOut() {
        mAuth.signOut();
        updateUI(null);
    }

    private void sendEmailVerification() {
        binding.verifyEmailButton.setEnabled(false);
        final FirebaseUser user = mAuth.getCurrentUser();
        Objects.requireNonNull(user).sendEmailVerification()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        binding.verifyEmailButton.setEnabled(true);
                        if (task.isSuccessful()) {
                            Toast.makeText(Auth.this,
                                    "Verification email sent to " + user.getEmail(),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Log.e(TAG, "sendEmailVerification", task.getException());
                            Toast.makeText(Auth.this,
                                    "Failed to send verification email.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


}