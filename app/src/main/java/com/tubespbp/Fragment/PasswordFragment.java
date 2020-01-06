package com.tubespbp.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.tubespbp.R;

public class PasswordFragment extends Fragment {
    private FirebaseAuth mAuth;
    private EditText password,password2,password3,email;
    private Button submit;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        return inflater.inflate(R.layout.password_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        password=(EditText) getView().findViewById(R.id.oldpassword);
        password2=(EditText) getView().findViewById(R.id.newpassword);
        password3=(EditText) getView().findViewById(R.id.confirmpassword);
        email=(EditText) getView().findViewById(R.id.passwordemail);
        submit=(Button) getView().findViewById(R.id.buttonpassword);
        final FirebaseUser currentUser = mAuth.getCurrentUser();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(password.getText().toString().isEmpty()||password2.getText().toString().isEmpty()||password3.getText().toString().isEmpty()
                ||email.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "Field harus diisi terlebih dahlu", Toast.LENGTH_SHORT).show();
                }else if(password3.getText().toString().length()<6||password.getText().toString().length()<6||password2.getText().toString().length()<6){
                    Toast.makeText(getContext(),"Password harus terdiri dari 6 karakter atau lebih",Toast.LENGTH_SHORT).show();
                }else{
                    if(password2.getText().toString().equals(password3.getText().toString())){
                        if(email.getText().toString().equals(currentUser.getEmail())){
                            currentUser.updatePassword(password2.getText().toString());
                            currentUser.sendEmailVerification();
                            Toast.makeText(getContext(),"Update Password berhasil, Silahkan cek email anda",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(getContext(),"Email anda harus sama dengan email saat anda login",Toast.LENGTH_SHORT).show();
                        }

                    }
                    else{
                        Toast.makeText(getContext(),"Password baru dan confirm password harus sama",Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

    }
}
