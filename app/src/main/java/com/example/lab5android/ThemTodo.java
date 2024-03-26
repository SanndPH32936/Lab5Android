package com.example.lab5android;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ThemTodo extends AppCompatActivity {
    EditText edtTodo ;
    Button btnThem ,btnHuy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_todo);
        anhXa();

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String td = edtTodo .getText().toString();


                if (td.isEmpty()){
                    Toast.makeText(ThemTodo.this, "Không được để trống", Toast.LENGTH_SHORT).show();
                }else {
                    TodoModel model = new TodoModel(td);
                    themTodoNew(model);
                }

            }
        });

    }

    private void themTodoNew(TodoModel td) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.3:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);



            Call<TodoModel> call = apiService.addTodo(td);

            call.enqueue(new Callback<TodoModel>() {
                @Override
                public void onResponse(Call<TodoModel> call, Response<TodoModel> response) {
                    if (response.isSuccessful()){
                        Toast.makeText(ThemTodo.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ThemTodo.this, MainActivity.class));
                    }else {
                        Toast.makeText(ThemTodo.this, "Sai", Toast.LENGTH_SHORT).show();

                    }
                }

                @Override
                public void onFailure(Call<TodoModel> call, Throwable t) {
                    Toast.makeText(ThemTodo.this, "Lỗi", Toast.LENGTH_SHORT).show();

                }
            });



    }

    private void anhXa() {
        edtTodo = findViewById(R.id.edtTodoNew);
        btnHuy = findViewById(R.id.btnHuyNew);
        btnThem = findViewById(R.id.btnThemNew);
    }
}