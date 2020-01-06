package com.tubespbp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.auth.data.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.tubespbp.API.AccountClient;
import com.tubespbp.API.AccountInterface;
import com.tubespbp.DAO.accountDAO;
import com.tubespbp.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity {
private EditText email;
private EditText password;
private Button login;
private TextView register;
private TextView forget_password;
private CheckBox remember_me;
private FirebaseAuth mAuth;
private accountDAO account;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        setAtribute();
        login();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setAtribute(){
        email = (EditText) findViewById(R.id.LEmail);
        password = (EditText) findViewById(R.id.LPassword);
        login = (Button) findViewById(R.id.login);
        register = (TextView) findViewById(R.id.Lregister);
        forget_password = (TextView) findViewById(R.id.forget_password);
    }

    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser currentUser) {
        if(currentUser != null){

        }
    }

    private void login() {
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //menampung imputan user
                final String emailUser = email.getText().toString().trim();
                final String passwordUser = password.getText().toString().trim();

                //validasi email dan password
                // jika email kosong
                if (emailUser.isEmpty()) {
                    email.setError("Email tidak boleh kosong");
                }
                // jika email not valid
                else if (!Patterns.EMAIL_ADDRESS.matcher(emailUser).matches()) {
                    email.setError("Email tidak valid");
                }
                // jika password kosong
                else if (passwordUser.isEmpty()) {
                    password.setError("Password tidak boleh kosong");
                }
                //jika password kurang dari 6 karakter
                else if (passwordUser.length() < 6) {
                    password.setError("Password minimal terdiri dari 6 karakter");
                } else {
                    mAuth.signInWithEmailAndPassword(emailUser, passwordUser)
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    // ketika gagal locin maka akan do something
                                    if (!task.isSuccessful()) {
                                        Toast.makeText(LoginActivity.this,
                                                "Gagal login karena " + task.getException().getMessage()
                                                , Toast.LENGTH_LONG).show();
                                        updateUI(null);
                                    } else {
                                        Bundle bundle = new Bundle();
                                        bundle.putString("email", emailUser);
                                        bundle.putString("pass", passwordUser);
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        AccountInterface accountService = AccountClient.getClient().create(AccountInterface.class);
                                        final Call<List<accountDAO>> accountDAOCall =accountService.getAccount();
                                        if(user.isEmailVerified()){
                                            updateUI(user);

                                            accountDAOCall.enqueue(new Callback<List<accountDAO>>() {
                                                @Override
                                                public void onResponse(Call<List<accountDAO>> call, Response<List<accountDAO>> response) {
                                                    for(int i=0;i<response.body().size();i++){
                                                        if(response.body().get(i).getEmail().equals(email.getText().toString())){
                                                            account=response.body().get(i);
                                                        }
                                                    }
                                                    if(account.getRole().equalsIgnoreCase("Admin")){
                                                        Intent i = new Intent(LoginActivity.this,AdminActivity.class);
                                                        i.putExtra("username",account.getUsername());
                                                        i.putExtra("role",account.getRole());
                                                        startActivity(i);
                                                        finish();
                                                    }else if(account.getRole().equalsIgnoreCase("User")){
                                                        Intent i = new Intent(LoginActivity.this,UserActivity.class);
                                                        i.putExtra("username",account.getUsername());
                                                        i.putExtra("role",account.getRole());
                                                        startActivity(i);
                                                        finish();
                                                    }

                                                }

                                                @Override
                                                public void onFailure(Call<List<accountDAO>> call, Throwable t) {
                                                    Toast.makeText(LoginActivity.this,"Account not Found",Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        }else{
                                            Toast.makeText(LoginActivity.this,"Make sure ur email is verified",Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                }
                            });
                }
            }
        });
    }
}
