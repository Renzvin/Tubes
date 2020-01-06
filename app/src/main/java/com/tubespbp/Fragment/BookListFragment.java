package com.tubespbp.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.tubespbp.API.BukuClient;
import com.tubespbp.API.BukuInterface;
import com.tubespbp.Adapter.BukuAdapter;
import com.tubespbp.DAO.bukuDAO;
import com.tubespbp.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookListFragment extends Fragment {
    private List<bukuDAO> ListBuku;
    private RecyclerView recyclerView;
    private BukuAdapter bukuAdapter;
    private RecyclerView.LayoutManager layoutManager;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.booklist_adapter, container, false);

        recyclerView = v.findViewById(R.id.recycler_view);

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ListBuku = new ArrayList<>();
        bukuAdapter = new BukuAdapter(getContext(),ListBuku);
        RecyclerView.LayoutManager blayoutmanager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(blayoutmanager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(bukuAdapter);
        setRecyclerView();
    }

    private void setRecyclerView(){
        BukuInterface bukuService = BukuClient.getClient().create(BukuInterface.class);
        Call<List<bukuDAO>> bukuDAOCall = bukuService.getBuku();
        bukuDAOCall.enqueue(new Callback<List<bukuDAO>>() {
            @Override
            public void onResponse(Call<List<bukuDAO>> call, Response<List<bukuDAO>> response) {
                ListBuku.addAll(response.body());
                bukuAdapter.notifyDataSetChanged();
                Toast.makeText(getContext(),"List Buku",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<bukuDAO>> call, Throwable t) {
                Toast.makeText(getContext(), "Permasalahan Koneksi",Toast.LENGTH_SHORT).show();
            }
        });
    }

}
