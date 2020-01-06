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

import com.tubespbp.API.BorrowClient;
import com.tubespbp.API.BorrowInterface;
import com.tubespbp.Adapter.BorrowListAdapter;
import com.tubespbp.Adapter.MyListAdapter;
import com.tubespbp.DAO.borrowDAO;
import com.tubespbp.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyListFragment extends Fragment {
    private List<borrowDAO> ListBorrow;
    private RecyclerView recyclerView;
    private MyListAdapter borrowBukuAdapter;
    private RecyclerView.LayoutManager layoutManager;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.mylist_adapter, container, false);
        recyclerView = v.findViewById(R.id.myborrow_recycler_view);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ListBorrow = new ArrayList<>();
        borrowBukuAdapter = new MyListAdapter(getContext(),ListBorrow);
        RecyclerView.LayoutManager blayoutmanager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(blayoutmanager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(borrowBukuAdapter);
        setRecyclerView();
    }

    private void setRecyclerView(){
        Bundle bundle = this.getArguments();
        final String user = bundle.getString("user");
        BorrowInterface bukuService = BorrowClient.getClient().create(BorrowInterface.class);
        Call<List<borrowDAO>> bukuDAOCall = bukuService.getBorrowByUsername(user);
        bukuDAOCall.enqueue(new Callback<List<borrowDAO>>() {
            @Override
            public void onResponse(Call<List<borrowDAO>> call, Response<List<borrowDAO>> response) {
                ListBorrow.addAll(response.body());
                borrowBukuAdapter.notifyDataSetChanged();
                Toast.makeText(getContext(),"List Buku",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<borrowDAO>> call, Throwable t) {
                Toast.makeText(getContext(), "Permasalahan Koneksi",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
