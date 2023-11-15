package ru.mirea.solovieva.mireaproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.provider.Settings;
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

import java.util.List;
import java.util.Objects;
import ru.mirea.solovieva.mireaproject.databinding.FirebaseBinding;

public class Firebase extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private FirebaseBinding binding;
    private FirebaseAuth mAuth;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FirebaseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mAuth = FirebaseAuth.getInstance();
        String uniqueID = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        binding.textView6.setText(uniqueID);
        binding.signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = "solovey@gmail.com";
                String password = "lol";
//                String email = String.valueOf(binding.editTextEmail.getText());
//                String password = String.valueOf(binding.editTextPassword.getText());
                signIn(email, password, v);
            }
        });
        binding.create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = "solovey@gmail.com";
                String password = "lol";
//                String email = String.valueOf(binding.editTextEmail.getText());
//                String password = String.valueOf(binding.editTextPassword.getText());
                createAccount(email, password, v);
            }
        });
        binding.verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {sendEmailVerification();
            }
        });
        binding.signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {signOut();
            }
        });

    }
    @Override
    public void onStart() {
        super.onStart();
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        updateUI(currentUser);
//
//        if(!checkRemote())
//        {
//            updateUI(currentUser);
//        }
    }


    private void updateUI(FirebaseUser user) {
        if (user != null) {

            Intent intent = new Intent(Firebase.this, MainActivity.class);
            startActivity(intent);

        } else {
            binding.textView.setText(R.string.signed_out);
            binding.textViewUI.setText(null);
            binding.create.setVisibility(View.VISIBLE);
            binding.editTextPassword.setVisibility(View.VISIBLE);
            binding.signin.setVisibility(View.VISIBLE);
        }
    }
//    private void createAccount(String email, String password, View v) {
//        Log.d(TAG, "createAccount:" + email);
//        mAuth.createUserWithEmailAndPassword(email, password)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            Log.d(TAG, "createUserWithEmail:success");
//                            FirebaseUser user = mAuth.getCurrentUser();
//                            updateUI(user);
//                            goSystem(v);
//                        } else {
//                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
//                            Toast.makeText(Firebase.this, "Authentication failed.",
//                                    Toast.LENGTH_SHORT).show();
//                            updateUI(null);
//                        }
//                    }
//                });
//    }

    private void createAccount(String email, String password, View v) {
        Log.d(TAG, "createAccount:" + email);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isCanceled()) {
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                            goSystem(v);
                        } else {
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(Firebase.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }

    private void signIn(String email, String password, View v) {
        Log.d(TAG, "signIn:" + email);
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isComplete()) {
                            FirebaseUser user = mAuth.getCurrentUser();

//                            updateUI(user);
//                            goSystem(v);
                            if(checkRemote())
                            {
                                Log.w(TAG, "signInWithEmail:failure", task.getException());
                                binding.textView.setText(R.string.auth_failed);
                                Toast.makeText(Firebase.this, "The AnyDesk application was found on your device.", Toast.LENGTH_SHORT).show();
                                DialogRemoteFind fragment = new DialogRemoteFind();
                                fragment.show(getSupportFragmentManager(), "mirea");
                            }
                            else{
                                binding.textView.setText(R.string.auth_suc);
                                Log.d(TAG, "signInWithEmail:success");
                                updateUI(user);
                                goSystem(v);
                            }

                        } else {
//                            Log.d(TAG, "signInWithEmail:success");
//                            FirebaseUser user = mAuth.getCurrentUser();
//                            updateUI(user);
//                            goSystem(v);
////                            FirebaseUser currentUser = mAuth.getCurrentUser();
////                            updateUI(currentUser);
//                            if(!checkRemote())
//                            {
//                                updateUI(user);
//                            }

                            Log.w(TAG, "signInWithEmail:failure", task.getException());

                            Toast.makeText(Firebase.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
//                        if (!task.isCanceled()) {
//
//                            binding.textView.setText(R.string.auth_suc);
//                        }
                    }
                });
    }

    private boolean checkRemote() {

        @SuppressLint("QueryPermissionsNeeded") List<PackageInfo> packs = getPackageManager().getInstalledPackages(0);

        for(int i=0;i < packs.size();i++) {
            PackageInfo p = packs.get(i);
            if(Objects.equals(p.packageName, "com.anydesk.anydeskandroid"))
            {
                DialogRemoteFind fragment = new DialogRemoteFind();
                fragment.show(getSupportFragmentManager(), "mirea");
                return true;
            }
        }
        return false;
    }

    private void signOut() {
        mAuth.signOut();
        updateUI(null);
    }

    private void sendEmailVerification() {
        binding.verify.setEnabled(false);
        final FirebaseUser user = mAuth.getCurrentUser();
        Objects.requireNonNull(user).sendEmailVerification()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override

                    public void onComplete(@NonNull Task<Void> task) {
                        binding.verify.setEnabled(true);
                        if (task.isSuccessful()) {
                            Toast.makeText(Firebase.this,
                                    "Verification email sent to " + user.getEmail(),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Log.e(TAG, "sendEmailVerification", task.getException());
                            Toast.makeText(Firebase.this,
                                    "Failed to send verification email.",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }

    public void goSystem(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void onContinueClicked() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }
    public void onOkClicked() {
        finish();
        System.exit(0);
    }

}
