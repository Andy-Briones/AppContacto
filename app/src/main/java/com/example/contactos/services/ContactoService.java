package com.example.contactos.services;

import com.example.contactos.ContactosActivity;
import com.example.contactos.entity.Contactos;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ContactoService {
    @GET("/Contactos")
    Call<List<Contactos>> getColors(@Query("limit") int limit, @Query("page") int page);

    //@GET("/Colores")
    //Call<List<Colores>> getColors();

    @POST("/Contactos")
    Call<Contactos>create(@Body Contactos colores);

    //https://67ff052558f18d7209efd0c8.mockapi.io/Colores
    @PUT("/Colores/{id}")
    Call<Contactos>update(@Body Contactos colores, @Path("id") int id);

    @DELETE("Colores/{id}")
    Call<Contactos>delete(@Path("id") int id);

}
