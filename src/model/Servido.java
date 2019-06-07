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
public class Servido implements IPersistable {

    private int idServido;
    private int idOrden;
    private int idProducto;
    Producto producto;

    public Servido(int idServido, int idOrden, int idProducto) {
        this.idServido = idServido;
        this.idOrden = idOrden;
        this.idProducto = idProducto;
    }

    public Servido(int idOrden, int idProducto) {
        this.idOrden = idOrden;
        this.idProducto = idProducto;
    }

    public Servido(int idServido, int idOrden, Producto producto) {
        this.idServido = idServido;
        this.idOrden = idOrden;
        this.producto = producto;
        idProducto = this.producto.getId();
    }

    public Servido(int idOrden, Producto producto) {
        this.idOrden = idOrden;
        this.producto = producto;
        idProducto = this.producto.getId();
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public int getIdServido() {
        return idServido;
    }

    public void setIdServido(int idServido) {
        this.idServido = idServido;
    }

    public int getIdOrden() {
        return idOrden;
    }

    public void setIdOrden(int idOrden) {
        this.idOrden = idOrden;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    @Override
    public String toString() {
        return "Servido{" + "idServido=" + idServido + ", idOrden=" + idOrden + ", idProducto=" + idProducto + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 43 * hash + this.idServido;
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
        final Servido other = (Servido) obj;
        return this.idServido == other.idServido;
    }

    @Override
    public int getId() {
        return getIdServido();
    }

    @Override
    public int updateOnDb() {
        return 0;
    }

    @Override
    public int refreshFromDb() {
        return 0;
    }

}
