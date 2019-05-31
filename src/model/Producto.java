/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.model;

/**
 *
 * @author NarF
 */
public class Producto {
    private final int idProducto;
    private String producto;
    private float precio;
    private int idCategoria;

    public Producto(int idProducto, String producto, float precio, int idCategoria) {
        this.idProducto = idProducto;
        this.producto = producto;
        this.precio = precio;
        this.idCategoria = idCategoria;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public String getProducto() {
        return producto;
    }

    public float getPrecio() {
        return precio;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    @Override
    public String toString() {
        return "Producto{" + "idProducto=" + idProducto + ", producto=" + producto + ", precio=" + precio + ", idCategoria=" + idCategoria + '}';
    }
}
