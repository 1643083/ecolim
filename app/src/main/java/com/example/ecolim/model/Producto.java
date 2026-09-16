package com.example.ecolim.model;

public class Producto {
    private String nombre, categoria, descripcion, garantia;
    private double precio;
    private int stock;

    public Producto(String nombre, String categoria, String descripcion, String garantia, double precio, int stock) {
        this.nombre = nombre;
        this.categoria = categoria;
        this.descripcion = descripcion;
        this.garantia = garantia;
        this.precio = precio;
        this.stock = stock;
    }

    public String getNombre() { return nombre; }
    public String getCategoria() { return categoria; }
    public String getDescripcion() { return descripcion; }
    public String getGarantia() { return garantia; }
    public double getPrecio() { return precio; }
    public int getStock() { return stock; }
}