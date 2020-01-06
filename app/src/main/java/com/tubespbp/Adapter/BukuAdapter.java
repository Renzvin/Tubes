package com.tubespbp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tubespbp.DAO.bukuDAO;
import com.tubespbp.Fragment.BookListFragment;
import com.tubespbp.R;

import org.w3c.dom.Text;

import java.util.List;

public class BukuAdapter extends RecyclerView.Adapter<BukuAdapter.BukuViewHolder> {
    private Context context;
    private List<bukuDAO> result;

    public BukuAdapter(Context context, List<bukuDAO> result){
        this.context=context;
        this.result=result;
    }

    public BukuViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i){
        View view= LayoutInflater.from(context).inflate(R.layout.booklist_fragment,viewGroup,false);
        final BukuViewHolder holder = new BukuViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull BukuViewHolder holder, int position) {
        final bukuDAO buku = result.get(position);
        holder.judul.setText("Judul : "+buku.getJudul());
        holder.pengarang.setText("Pengarang : "+buku.getPengarang());
        holder.penerbit.setText("Penerbit : "+buku.getPenerbit());
        holder.tahun_terbit.setText("Tahun Terbit : "+buku.getTahun_terbit()+"\n");

        holder.bparent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "ini buku",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public int getItemCount(){return result.size();}

    public class BukuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView judul,pengarang,penerbit,tahun_terbit;
        private LinearLayout bparent;

        public BukuViewHolder(@NonNull View itemView){
            super(itemView);
            judul = itemView.findViewById(R.id.judul);
            pengarang = itemView.findViewById(R.id.Pengarang);
            penerbit = itemView.findViewById(R.id.Penerbit);
            tahun_terbit = itemView.findViewById(R.id.thterbit);
            bparent = itemView.findViewById(R.id.blparent);
        }

        @Override
        public void onClick(View view) {
            Toast.makeText(context, "ini buku",Toast.LENGTH_SHORT).show();
        }
    }
}
