/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.model.abstractmodel;

import src.model.Categoria;

/**
 * @author NarF
 */
public abstract class AbstractProducto {

    protected int id;
    protected String producto;
    protected float precio;
    protected int idCategoria;
    protected Categoria categoria;

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

    public AbstractProducto(String producto, float precio, Categoria categoria) {
        this.producto = producto;
        this.precio = precio;
        this.categoria = categoria;
        this.idCategoria = categoria.getId();
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

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
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


}
