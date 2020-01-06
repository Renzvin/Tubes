package com.tubespbp.Fragment;

import android.app.DownloadManager;
import android.content.Intent;
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

import com.firebase.ui.auth.data.model.User;
import com.tubespbp.API.BukuClient;
import com.tubespbp.API.BukuInterface;
import com.tubespbp.API.RequestClient;
import com.tubespbp.API.RequestInterface;
import com.tubespbp.Activity.UserActivity;
import com.tubespbp.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddRequestFragment extends Fragment {
    private EditText judul,pengarang,penerbit,tahun_terbit;
    private Button submit;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.useraddrequest, container, false);
        return v;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = this.getArguments();
        final String user = bundle.getString("user");
        judul = (EditText) getView().findViewById(R.id.useradd_judul);
        pengarang = (EditText) getView().findViewById(R.id.useradd_pengarang);
        penerbit = (EditText) getView().findViewById(R.id.useradd_Penerbit);
        tahun_terbit = (EditText) getView().findViewById(R.id.useradd_tahunterbit);
        submit = (Button) getView().findViewById(R.id.userinput);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(judul.getText().toString().isEmpty()||pengarang.getText().toString().isEmpty()
                        ||penerbit.getText().toString().isEmpty()||tahun_terbit.getText().toString().isEmpty()){
                    Toast.makeText(getContext(),"Field tidak boleh kosong",Toast.LENGTH_SHORT).show();
                }else{
                    if(tahun_terbit.getText().toString().length()<=4||tahun_terbit.getText().toString().length()>0){
                        RequestInterface requestService = RequestClient.getClient().create(RequestInterface.class);
                        Call<String> requestDAOCall = requestService.addRequest(judul.getText().toString(),pengarang.getText().toString(),
                                penerbit.getText().toString(),tahun_terbit.getText().toString(),user);
                        requestDAOCall.enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                Toast.makeText(getContext(),"Request Berhasil Masuk",Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
                                Toast.makeText(getContext(),"Permasalahan Koneksi",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }else{
                        tahun_terbit.setError("Tahun Hanya Bisa 4 Digit!");
                    }
                }
            }
        });
    }
}
