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
import com.tubespbp.DAO.borrowDAO;
import com.tubespbp.DAO.requestDAO;
import com.tubespbp.R;

import java.text.SimpleDateFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReturnListAdapter extends RecyclerView.Adapter<ReturnListAdapter.ReturnViewHolder> {
    private Context context;
    private List<borrowDAO> result;

    public ReturnListAdapter(Context context, List<borrowDAO> result) {
        this.context = context;
        this.result = result;
    }

    public ReturnListAdapter.ReturnViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.returnbook_fragment, viewGroup, false);
        final ReturnListAdapter.ReturnViewHolder holder = new ReturnListAdapter.ReturnViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ReturnViewHolder holder, int position) {
        final borrowDAO buku = result.get(position);
        holder.judul.setText("Judul : "+buku.getJudul());
        holder.username.setText("Username : "+buku.getUsername());
        holder.status.setText("Tanggal Kembali : "+buku.getTanggal_kembali()+"\n");

        holder.bparent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        context);

                // set title dialog
                alertDialogBuilder.setTitle("Delete Data Peminjaman Ini?");

                // set pesan dari dialog
                alertDialogBuilder
                        .setMessage("Klik Ya untuk delete")
                        .setIcon(R.mipmap.ic_launcher)
                        .setCancelable(false)
                        .setPositiveButton("Ya",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                BorrowInterface borrowService = BorrowClient.getClient().create(BorrowInterface.class);
                                Call<borrowDAO> bukuDAOCall = borrowService.deleteBorrow(buku.getJudul());
                                bukuDAOCall.enqueue(new Callback<borrowDAO>() {
                                    @Override
                                    public void onResponse(Call<borrowDAO> call, Response<borrowDAO> response) {
                                        Toast.makeText(context,"Buku Berhasil Didelete",Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onFailure(Call<borrowDAO> call, Throwable t) {
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

    @Override
    public int getItemCount() {
        return result.size();
    }

    public class ReturnViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView username,judul,status;
        private LinearLayout bparent;

        public ReturnViewHolder(@NonNull View itemView) {
            super(itemView);
            judul = itemView.findViewById(R.id.ret_judul);
            username = itemView.findViewById(R.id.ret_username);
            status = itemView.findViewById(R.id.ret_tanggal);
            bparent = itemView.findViewById(R.id.ret_lparent);
        }
        @Override
        public void onClick(View view) {
            Toast.makeText(context, "ini buku",Toast.LENGTH_SHORT).show();
        }
    }
}
