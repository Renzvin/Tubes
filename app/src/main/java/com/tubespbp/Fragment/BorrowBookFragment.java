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

import com.tubespbp.API.BorrowClient;
import com.tubespbp.API.BorrowInterface;
import com.tubespbp.API.BukuClient;
import com.tubespbp.API.BukuInterface;
import com.tubespbp.API.RequestClient;
import com.tubespbp.API.RequestInterface;
import com.tubespbp.Adapter.BorrowListAdapter;
import com.tubespbp.DAO.bukuDAO;
import com.tubespbp.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Formatter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BorrowBookFragment extends Fragment {
    private EditText judul;
    private Button submit;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.userborrowbook, container, false);
        return v;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = this.getArguments();
        final String user = bundle.getString("user");
        judul = (EditText) getView().findViewById(R.id.borrow_judul);
        submit = (Button) getView().findViewById(R.id.borrowinput);
        final Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE,7);
        Date future = c.getTime();
        SimpleDateFormat converter = new SimpleDateFormat("YYYY-MM-dd");
        final String kembali = converter.format(future);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(judul.getText().toString().isEmpty()){
                    Toast.makeText(getContext(),"Field tidak boleh kosong",Toast.LENGTH_SHORT).show();
                }else{
                    BukuInterface bookService = BukuClient.getClient().create(BukuInterface.class);
                    Call<List<bukuDAO>> searchDAOCall = bookService.getBuku(judul.getText().toString());
                    searchDAOCall.enqueue(new Callback<List<bukuDAO>>() {
                        @Override
                        public void onResponse(Call<List<bukuDAO>> call, Response<List<bukuDAO>> response) {
                            BorrowInterface bukuService = BorrowClient.getClient().create(BorrowInterface.class);
                            Call<String> borrowDAOCall = bukuService.addBorrow(user,judul.getText().toString(),kembali);
                            borrowDAOCall.enqueue(new Callback<String>() {
                                @Override
                                public void onResponse(Call<String> call, Response<String> response) {
                                    Toast.makeText(getContext(),"Berhasil meminjam buku",Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onFailure(Call<String> call, Throwable t) {
                                    Toast.makeText(getContext(),"Permasalahan Koneksi",Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                        @Override
                        public void onFailure(Call<List<bukuDAO>> call, Throwable t) {
                            Toast.makeText(getContext(),"Permasalahan Koneksi",Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });
    }
}
