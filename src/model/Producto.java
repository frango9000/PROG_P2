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
public class Producto implements IIdentifiable {

    private int idProducto;
    private String producto;
    private float precio;
    private int idCategoria;

    public Producto(int idProducto, String producto, float precio, int idCategoria) {
        this.idProducto = idProducto;
        this.producto = producto;
        this.precio = precio;
        this.idCategoria = idCategoria;
    }

    public Producto(String producto, float precio, int idCategoria) {
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

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + this.idProducto;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Producto other = (Producto) obj;
        if (this.idProducto != other.idProducto) {
            return false;
        }
        return true;
    }

    @Override
    public int getId() {
        return getIdProducto();
    }

}
