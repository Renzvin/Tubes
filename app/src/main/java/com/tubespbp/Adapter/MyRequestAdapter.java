package com.tubespbp.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tubespbp.API.RequestClient;
import com.tubespbp.API.RequestInterface;
import com.tubespbp.DAO.requestDAO;
import com.tubespbp.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyRequestAdapter extends RecyclerView.Adapter<MyRequestAdapter.RequestBukuViewHolder>{
    private Context context;
    private List<requestDAO> result;

    public MyRequestAdapter(Context context, List<requestDAO> result) {
        this.context = context;
        this.result = result;
    }

    public MyRequestAdapter.RequestBukuViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.userrequestlist_fragment, viewGroup, false);
        final MyRequestAdapter.RequestBukuViewHolder holder = new MyRequestAdapter.RequestBukuViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyRequestAdapter.RequestBukuViewHolder holder, int position) {
        final requestDAO buku = result.get(position);
        holder.judul.setText("Judul : " + buku.getJudul());
        holder.pengarang.setText("Pengarang : " + buku.getPengarang());
        holder.penerbit.setText("Penerbit : " + buku.getPenerbit());
        holder.tahun_terbit.setText("Tahun Terbit : " + buku.getTahun_terbit()+"\n");

        holder.bparent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"Ini List Request",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public int getItemCount(){return result.size();}

    public class RequestBukuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView judul, pengarang, penerbit, tahun_terbit;
        private LinearLayout bparent;

        public RequestBukuViewHolder(@NonNull View itemView) {
            super(itemView);
            judul = itemView.findViewById(R.id.myreq_judul);
            pengarang = itemView.findViewById(R.id.myreq_Pengarang);
            penerbit = itemView.findViewById(R.id.myreq_Penerbit);
            tahun_terbit = itemView.findViewById(R.id.myreq_thterbit);
            bparent = itemView.findViewById(R.id.myreq_blparent);
        }

        @Override
        public void onClick(View view) {
            Toast.makeText(context, "ini request", Toast.LENGTH_SHORT).show();
        }
    }
}
