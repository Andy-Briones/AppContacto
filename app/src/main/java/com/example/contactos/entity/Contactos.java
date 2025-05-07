package com.example.contactos.entity;

public class Contactos {
    public String id;
    public String nombre;
    public String direccion;
    public String telefono;
    public String genero;

    public Contactos ()
    {}

    public Contactos(String nombre, String direccion, String genero, String telefono)
    {
        this.direccion=direccion;
        this.nombre=nombre;
        this.genero=genero;
        this.telefono=telefono;
    }
}
