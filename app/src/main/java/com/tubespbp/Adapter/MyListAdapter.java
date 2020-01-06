package com.tubespbp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tubespbp.DAO.borrowDAO;
import com.tubespbp.R;

import java.text.SimpleDateFormat;
import java.util.List;

public class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.BorrowViewHolder> {
    private Context context;
    private List<borrowDAO> result;

    public MyListAdapter(Context context, List<borrowDAO> result){
        this.context=context;
        this.result=result;
    }

    public MyListAdapter.BorrowViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i){
        View view= LayoutInflater.from(context).inflate(R.layout.mylist_fragment,viewGroup,false);
        final MyListAdapter.BorrowViewHolder holder = new MyListAdapter.BorrowViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyListAdapter.BorrowViewHolder holder, int position) {
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
            judul = itemView.findViewById(R.id.mybor_judul);
            username = itemView.findViewById(R.id.mybor_username);
            status = itemView.findViewById(R.id.mybor_tanggal);
            bparent = itemView.findViewById(R.id.mybor_lparent);
        }
        @Override
        public void onClick(View view) {
            Toast.makeText(context, "ini buku",Toast.LENGTH_SHORT).show();
        }
    }
}
