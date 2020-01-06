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

import com.tubespbp.API.BorrowClient;
import com.tubespbp.API.BorrowInterface;
import com.tubespbp.API.BukuClient;
import com.tubespbp.API.BukuInterface;
import com.tubespbp.DAO.borrowDAO;
import com.tubespbp.DAO.bukuDAO;
import com.tubespbp.R;

import java.text.SimpleDateFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BorrowListAdapter extends RecyclerView.Adapter<BorrowListAdapter.BorrowViewHolder> {
    private Context context;
    private List<borrowDAO> result;

    public BorrowListAdapter(Context context, List<borrowDAO> result){
        this.context=context;
        this.result=result;
    }

    public BorrowListAdapter.BorrowViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i){
        View view= LayoutInflater.from(context).inflate(R.layout.borrowedlist_fragment,viewGroup,false);
        final BorrowListAdapter.BorrowViewHolder holder = new BorrowListAdapter.BorrowViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull BorrowViewHolder holder, int position) {
        final borrowDAO buku = result.get(position);
        holder.judul.setText("Judul : "+buku.getJudul());
        holder.username.setText("Username : "+buku.getUsername());
        holder.status.setText("Tanggal Kembali : "+buku.getTanggal_kembali()+"\n");

        holder.bparent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Toast.makeText(context,"Ini data peminjaman",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return result.size();
    }

    public class BorrowViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView username,judul,status;
        private LinearLayout bparent;

        public BorrowViewHolder(@NonNull View itemView) {
            super(itemView);
            judul = itemView.findViewById(R.id.bor_judul);
            username = itemView.findViewById(R.id.bor_username);
            status = itemView.findViewById(R.id.bor_tanggal);
            bparent = itemView.findViewById(R.id.bor_lparent);
        }
        @Override
        public void onClick(View view) {
            Toast.makeText(context, "ini buku",Toast.LENGTH_SHORT).show();
        }
    }
}

