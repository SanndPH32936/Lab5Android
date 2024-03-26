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

public class SuaTodo extends AppCompatActivity {
    EditText edtTodo ;
    Button btnSua ,btnHuy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_todo);
        anhXa();
        Bundle bundle = getIntent().getExtras();
        if (bundle == null){
            return;
        }

        edtTodo.setText(bundle.getString("todo"));

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SuaTodo.this,MainActivity.class));
                finish();
            }
        });

        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String to = edtTodo.getText().toString();
                TodoModel newTodo = new TodoModel(to);

                suaTodo(bundle.getString("id"),  newTodo);


            }
        });
    }

    private void suaTodo(String id, TodoModel model) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.3:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

        Call<TodoModel> call = apiService.updateTodo(id,model);

        call.enqueue(new Callback<TodoModel>() {
            @Override
            public void onResponse(Call<TodoModel> call, Response<TodoModel> response) {
                    if (response.isSuccessful()){
                        TodoModel upTo = response.body();
                        startActivity(new Intent(SuaTodo.this,MainActivity.class));
                        Toast.makeText(SuaTodo.this, "Thành công", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(SuaTodo.this, "Sai", Toast.LENGTH_SHORT).show();
                    }
            }

            @Override
            public void onFailure(Call<TodoModel> call, Throwable t) {
                Toast.makeText(SuaTodo.this, "Lỗi", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void anhXa() {
        edtTodo = findViewById(R.id.edtTodoUpdate);
        btnSua = findViewById(R.id.btnSuaUpdate);
        btnHuy = findViewById(R.id.btnHuyUpdate);
    }
}