package com.example.lab5android;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {
    @GET("/")
    Call<List<TodoModel>> getTodo();

    @POST("/")
    Call<TodoModel> addTodo(@Body TodoModel model);

    @DELETE("/{id}")
    Call<TodoModel> deleteTodo(@Path("id") String id);

    @PUT("/{id}")
    Call<TodoModel> updateTodo(@Path("id") String id,@Body TodoModel model);
}
