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

import com.tubespbp.API.BukuClient;
import com.tubespbp.API.BukuInterface;
import com.tubespbp.DAO.bukuDAO;
import com.tubespbp.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeleteBukuAdapter extends RecyclerView.Adapter<DeleteBukuAdapter.BukuViewHolder> {
    private Context context;
    private List<bukuDAO> result;

    public DeleteBukuAdapter(Context context, List<bukuDAO> result){
        this.context=context;
        this.result=result;
    }

    public DeleteBukuAdapter.BukuViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i){
        View view= LayoutInflater.from(context).inflate(R.layout.removebook_fragment,viewGroup,false);
        final DeleteBukuAdapter.BukuViewHolder Deleteholder = new DeleteBukuAdapter.BukuViewHolder(view);
        return Deleteholder;
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
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        context);

                // set title dialog
                alertDialogBuilder.setTitle("Delete Buku Ini?");

                // set pesan dari dialog
                alertDialogBuilder
                        .setMessage("Klik Ya untuk delete")
                        .setIcon(R.mipmap.ic_launcher)
                        .setCancelable(false)
                        .setPositiveButton("Ya",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                BukuInterface bukuService = BukuClient.getClient().create(BukuInterface.class);
                                Call<bukuDAO> bukuDAOCall = bukuService.deleteBuku(buku.getJudul());
                                bukuDAOCall.enqueue(new Callback<bukuDAO>() {
                                    @Override
                                    public void onResponse(Call<bukuDAO> call, Response<bukuDAO> response) {
                                        Toast.makeText(context,"Buku Berhasil Didelete",Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onFailure(Call<bukuDAO> call, Throwable t) {
                                        Toast.makeText(context,"Permasalahan Koneksi",Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        })
                        .setNegativeButton("Tidak",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                // membuat alert dialog dari builder
                AlertDialog alertDialog = alertDialogBuilder.create();

                // menampilkan alert dialog
                alertDialog.show();
            }
        });
    }

    public int getItemCount(){return result.size();}

    public class BukuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView judul,pengarang,penerbit,tahun_terbit;
        private LinearLayout bparent;

        public BukuViewHolder(@NonNull View itemView){
            super(itemView);
            judul = itemView.findViewById(R.id.del_judul);
            pengarang = itemView.findViewById(R.id.del_Pengarang);
            penerbit = itemView.findViewById(R.id.del_Penerbit);
            tahun_terbit = itemView.findViewById(R.id.del_thterbit);
            bparent = itemView.findViewById(R.id.del_lparent);
        }

        @Override
        public void onClick(View view) {
            Toast.makeText(context, "ini buku",Toast.LENGTH_SHORT).show();
        }
    }
}
