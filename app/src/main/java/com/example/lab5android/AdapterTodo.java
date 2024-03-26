package com.example.lab5android;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AdapterTodo extends RecyclerView.Adapter<AdapterTodo.HolderAdapter>{
    Context context;
    List<TodoModel>list = new ArrayList<>();

    public AdapterTodo(Context context, List<TodoModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public HolderAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_todolist,parent,false);


        return new HolderAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderAdapter holder, int position) {
            TodoModel td = list.get(position);
            holder.tvTodo.setText(td.getTodo());

            holder.btnXoa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
//                    builder.setTitle("Thông báo");
//                    builder.setMessage("Xóa");
//                    builder.setPositiveButton("Đóng", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
//                        }
//                    });
//                    AlertDialog dialog = builder.create();
//                    dialog.show();
                    xoaTodo(td.get_id(),position);
                }
            });

            holder.btnSua.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context,SuaTodo.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("id",td.get_id());
                    bundle.putString("todo",td.getTodo());
                    intent.putExtras(bundle);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });

    }

    private void xoaTodo(String id,int index) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.3:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

        Call<TodoModel> call = apiService.deleteTodo(id);

        call.enqueue(new Callback<TodoModel>() {
            @Override
            public void onResponse(Call<TodoModel> call, Response<TodoModel> response) {
                if (response.isSuccessful()){
                    list.remove(index);
                    notifyDataSetChanged();
                    Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TodoModel> call, Throwable t) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }




    class HolderAdapter extends RecyclerView.ViewHolder {
         ImageButton btnXoa,btnSua;
         TextView tvTodo;
         public HolderAdapter(@NonNull View itemView) {
             super(itemView);
             btnXoa = itemView.findViewById(R.id.btnDelete);
             tvTodo = itemView.findViewById(R.id.tvTodo);
             btnSua = itemView.findViewById(R.id.btnEdit);
         }
     }
}
