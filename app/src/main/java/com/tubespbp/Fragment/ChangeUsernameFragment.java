package com.tubespbp.Fragment;

import android.os.Bundle;
import android.provider.ContactsContract;
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
import com.tubespbp.API.AccountClient;
import com.tubespbp.API.AccountInterface;
import com.tubespbp.API.BukuInterface;
import com.tubespbp.DAO.accountDAO;
import com.tubespbp.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangeUsernameFragment extends Fragment {
    private FirebaseAuth mAuth;
    private EditText username,username2;
    private Button submit;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        return inflater.inflate(R.layout.username_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle temp = this.getArguments();
        final String user = temp.getString("user");
        final String jabatan = temp.getString("jabatan");
        username = (EditText) getView().findViewById(R.id.oldusername);
        username2 = (EditText) getView().findViewById(R.id.newusername);
        final FirebaseUser currentUser = mAuth.getCurrentUser();
        submit = (Button) getView().findViewById(R.id.buttonusername);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(username.getText().toString().isEmpty()||username2.getText().toString().isEmpty()){
                    Toast.makeText(getContext(),"Field harus diisi terlebih dahulu",Toast.LENGTH_SHORT).show();
                }else{
                    AccountInterface accountService = AccountClient.getClient().create(AccountInterface.class);
                    Call<String> accountDAOCall = accountService.putAccount(username2.getText().toString(),currentUser.getEmail(),jabatan);
                    accountDAOCall.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            currentUser.sendEmailVerification();
                            Toast.makeText(getContext(),"Change Username Success. Please Check Your Email",Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            Toast.makeText(getContext(),"Permasalahan Koneksi",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}
