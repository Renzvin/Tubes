package com.tubespbp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.tubespbp.API.AccountClient;
import com.tubespbp.API.AccountInterface;
import com.tubespbp.DAO.accountDAO;
import com.tubespbp.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RegisterActivity extends AppCompatActivity {
    private Button register;
    private EditText rusername,remail,rpassword,rcomfirmpassword;
    private TextView have_account;
    private RadioGroup radioGroup;
    private RadioButton role;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setAtribute();
        have_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAcount();
            }
        });

    }

    private void setAtribute(){
    rusername = (EditText) findViewById(R.id.RUsername);
    remail = (EditText) findViewById(R.id.REmail);
    rpassword = (EditText) findViewById(R.id.RPassword);
    rcomfirmpassword = (EditText) findViewById(R.id.RConfirmPassword);
    register = (Button) findViewById(R.id.Register);
    have_account = (TextView) findViewById(R.id.rlogin);
    radioGroup = (RadioGroup) findViewById(R.id.radio);
    int selectedId = radioGroup.getCheckedRadioButtonId();
    role = findViewById(selectedId);
    mAuth = FirebaseAuth.getInstance();
    }

    private void createAcount(){
        if(rusername.getText().toString().isEmpty()||remail.getText().toString().isEmpty()
        ||rpassword.getText().toString().isEmpty()||rcomfirmpassword.getText().toString().isEmpty()){
            Toast.makeText(this,"Field Tidak Boleh Kosong",Toast.LENGTH_SHORT).show();
        }else{
            final String emailUser = remail.getText().toString().trim();
            final String passwordUser = rpassword.getText().toString().trim();
            String confirmpassword = rcomfirmpassword.getText().toString().trim();

            if (emailUser.isEmpty()){
                remail.setError("Email tidak boleh kosong");
            }
            // jika email not valid
            else if (!Patterns.EMAIL_ADDRESS.matcher(emailUser).matches()){
                remail.setError("Email tidak valid");
            }
            // jika password kosong
            else if (passwordUser.isEmpty()){
                rpassword.setError("Password tidak boleh kosong");
            }
            //jika password kurang dari 6 karakter
            else if (passwordUser.length() < 6){
                rpassword.setError("Password minimal terdiri dari 6 karakter");
            }else if(confirmpassword.length() < 6){
                rcomfirmpassword.setError("Confirm Password minimal terdiri dari 6 karakter");
            }else if(passwordUser.isEmpty()){
                rcomfirmpassword.setError("Confirm Password tidak boleh kosong");
            }
            else {
                if (confirmpassword.equals(passwordUser)) {
                    int selectedId = radioGroup.getCheckedRadioButtonId();
                    role = findViewById(selectedId);
                    AccountInterface accountService = AccountClient.getClient().create(AccountInterface.class);
                    Call<String> accountDAOCall = accountService.addAccount(rusername.getText().toString(),remail.getText().toString(),
                            role.getText().toString());

                    accountDAOCall.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            mAuth.createUserWithEmailAndPassword(emailUser, passwordUser)
                                    .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            //jika gagal register do something
                                            if (!task.isSuccessful()) {
                                                Toast.makeText(RegisterActivity.this,
                                                        "Register gagal karena " + task.getException().getMessage(),
                                                        Toast.LENGTH_LONG).show();
                                            } else {
                                                //jika sukses akan menuju ke login activity
                                                final FirebaseUser user = mAuth.getCurrentUser();
                                                user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            updateUI(user);
                                                            Toast.makeText(RegisterActivity.this, "Send Email Verification", Toast.LENGTH_SHORT).show();
                                                            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                                        }
                                                    }
                                                });
                                            }
                                        }
                                    });
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            Toast.makeText(RegisterActivity.this, "Permasalahan Koneksi",Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    rcomfirmpassword.setError("Confirm password tidak sama");
                }
            }
        }
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
}
