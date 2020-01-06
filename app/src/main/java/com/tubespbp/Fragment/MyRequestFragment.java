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

import com.tubespbp.API.RequestClient;
import com.tubespbp.API.RequestInterface;
import com.tubespbp.Adapter.MyRequestAdapter;
import com.tubespbp.Adapter.RequestListAdapter_Admin;
import com.tubespbp.DAO.requestDAO;
import com.tubespbp.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyRequestFragment extends Fragment {
    private List<requestDAO> ListRequest;
    private RecyclerView recyclerView;
    private MyRequestAdapter reqeustAdapter;
    private RecyclerView.LayoutManager layoutManager;


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.userrequestlist_adapter, container, false);
        recyclerView = v.findViewById(R.id.userrequest_recycler_view);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ListRequest = new ArrayList<>();
        reqeustAdapter = new MyRequestAdapter(getContext(),ListRequest);
        RecyclerView.LayoutManager blayoutmanager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(blayoutmanager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(reqeustAdapter);
        setRecyclerView();
    }

    private void setRecyclerView(){
        Bundle bundle = this.getArguments();
        final String user = bundle.getString("user");
        RequestInterface requestService = RequestClient.getClient().create(RequestInterface.class);
        Call<List<requestDAO>> requestDAOCall = requestService.getRequestByUsername(user);
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
