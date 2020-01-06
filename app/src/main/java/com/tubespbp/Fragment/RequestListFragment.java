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
import com.tubespbp.API.RequestClient;
import com.tubespbp.API.RequestInterface;
import com.tubespbp.Adapter.BukuAdapter;
import com.tubespbp.Adapter.RequestListAdapter_Admin;
import com.tubespbp.DAO.bukuDAO;
import com.tubespbp.DAO.requestDAO;
import com.tubespbp.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestListFragment extends Fragment {
    private List<requestDAO> ListRequest;
    private RecyclerView recyclerView;
    private RequestListAdapter_Admin reqeustAdapter;
    private RecyclerView.LayoutManager layoutManager;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.requestbookadmin_adapter, container, false);
        recyclerView = v.findViewById(R.id.request_recycler_view);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ListRequest = new ArrayList<>();
        reqeustAdapter = new RequestListAdapter_Admin(getContext(),ListRequest);
        RecyclerView.LayoutManager blayoutmanager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(blayoutmanager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(reqeustAdapter);
        setRecyclerView();
    }

    private void setRecyclerView(){
        RequestInterface requestService = RequestClient.getClient().create(RequestInterface.class);
        Call<List<requestDAO>> requestDAOCall = requestService.getRequest();
        requestDAOCall.enqueue(new Callback<List<requestDAO>>() {
            @Override
            public void onResponse(Call<List<requestDAO>> call, Response<List<requestDAO>> response) {
                ListRequest.addAll(response.body());
                reqeustAdapter.notifyDataSetChanged();
                Toast.makeText(getContext(),"List Request",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<requestDAO>> call, Throwable t) {
                Toast.makeText(getContext(),"Permasalahan Koneksi",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
