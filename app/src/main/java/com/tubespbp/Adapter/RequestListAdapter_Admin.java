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

public class RequestListAdapter_Admin extends RecyclerView.Adapter<RequestListAdapter_Admin.RequestBukuViewHolder> {
    private Context context;
    private List<requestDAO> result;

    public RequestListAdapter_Admin(Context context, List<requestDAO> result) {
        this.context = context;
        this.result = result;
    }

    public RequestListAdapter_Admin.RequestBukuViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.requestlist_fragment, viewGroup, false);
        final RequestListAdapter_Admin.RequestBukuViewHolder holder = new RequestListAdapter_Admin.RequestBukuViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RequestListAdapter_Admin.RequestBukuViewHolder holder, int position) {
        final requestDAO buku = result.get(position);
        holder.judul.setText("Judul : " + buku.getJudul());
        holder.pengarang.setText("Pengarang : " + buku.getPengarang());
        holder.penerbit.setText("Penerbit : " + buku.getPenerbit());
        holder.tahun_terbit.setText("Tahun Terbit : " + buku.getTahun_terbit()+"\n");
        holder.username.setText("Username : " + buku.getUsername());

        holder.bparent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        context);

                // set title dialog
                alertDialogBuilder.setTitle("Delete Request Buku Ini?");

                // set pesan dari dialog
                alertDialogBuilder
                        .setMessage("Klik Ya untuk delete")
                        .setIcon(R.mipmap.ic_launcher)
                        .setCancelable(false)
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                RequestInterface requestService = RequestClient.getClient().create(RequestInterface.class);
                                Call<requestDAO> requestDAOCall = requestService.deleteRequest(buku.getJudul());
                                requestDAOCall.enqueue(new Callback<requestDAO>() {
                                    @Override
                                    public void onResponse(Call<requestDAO> call, Response<requestDAO> response) {
                                        Toast.makeText(context, "Request telah didelete", Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onFailure(Call<requestDAO> call, Throwable t) {
                                        Toast.makeText(context, "Permasalahan Koneksi", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        })
                        .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
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

    public class RequestBukuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView judul, pengarang, penerbit, tahun_terbit, username;
        private LinearLayout bparent;

        public RequestBukuViewHolder(@NonNull View itemView) {
            super(itemView);
            judul = itemView.findViewById(R.id.req_judul);
            pengarang = itemView.findViewById(R.id.req_Pengarang);
            penerbit = itemView.findViewById(R.id.req_Penerbit);
            tahun_terbit = itemView.findViewById(R.id.req_thterbit);
            username = itemView.findViewById(R.id.req_username);
            bparent = itemView.findViewById(R.id.req_lparent);
        }

        @Override
        public void onClick(View view) {
            Toast.makeText(context, "ini request", Toast.LENGTH_SHORT).show();
        }
    }
}
