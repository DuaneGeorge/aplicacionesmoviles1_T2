package com.example.aplicationt2.service;

import com.example.aplicationt2.entity.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface UserService {

    @GET("/comments")
    public abstract Call<List<User>> listaUsuarios();
}
