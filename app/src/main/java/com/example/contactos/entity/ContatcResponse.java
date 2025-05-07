package com.example.contactos.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ContatcResponse {
    public int status;
    public String message;

    @SerializedName("colors")
    public List<Contactos> Contactoss;
}
