/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.model.abstractmodel;

/**
 * @author NarF
 */
public abstract class AbstractProducto {

    protected int id;
    protected String producto;
    protected float precio;
    protected int idCategoria;

    public AbstractProducto(int id, String producto, float precio, int idCategoria) {
        this.id = id;
        this.producto = producto;
        this.precio = precio;
        this.idCategoria = idCategoria;
    }

    public AbstractProducto(String producto, float precio, int idCategoria) {
        this.producto = producto;
        this.precio = precio;
        this.idCategoria = idCategoria;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractProducto that = (AbstractProducto) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "AbstractProducto{" +
                "id=" + id +
                ", producto='" + producto + '\'' +
                ", precio=" + precio +
                ", idCategoria=" + idCategoria +
                '}';
    }
}
