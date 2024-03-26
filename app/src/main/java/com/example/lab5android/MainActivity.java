package com.example.lab5android;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    RecyclerView rcTodo;
    Button btnThem ;
    AdapterTodo adapter;
    List<TodoModel> list =new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       anhXa();
       btnThem.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               startActivity(new Intent(MainActivity.this,ThemTodo.class));
           }
       });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.3:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

        Call<List<TodoModel>> call = apiService.getTodo();


        call.enqueue(new Callback<List<TodoModel>>() {
            @Override
            public void onResponse(Call<List<TodoModel>> call, Response<List<TodoModel>> response) {
                if (response.isSuccessful()){
                    list = response.body();
                    adapter = new AdapterTodo(getApplicationContext(),list);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                    rcTodo.setLayoutManager(layoutManager);
                    rcTodo.setAdapter(adapter);
                    adapter.notifyDataSetChanged();


                }else {
                    Toast.makeText(MainActivity.this, "Sai", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<TodoModel>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Loi: "+t, Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void anhXa() {
        rcTodo = findViewById(R.id.rcTodo);
        btnThem = findViewById(R.id.btnThem);
    }
}