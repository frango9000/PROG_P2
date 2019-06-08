/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.model.abstractmodel;

/**
 * @author NarF
 */
public abstract class AbstractServido {

    protected int id;
    protected int idOrden;
    protected int idProducto;

    public AbstractServido(int id, int idOrden, int idProducto) {
        this.id = id;
        this.idOrden = idOrden;
        this.idProducto = idProducto;
    }

    public AbstractServido(int idOrden, int idProducto) {
        this.idOrden = idOrden;
        this.idProducto = idProducto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractServido servido = (AbstractServido) o;

        return id == servido.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "Servido{" +
                "id=" + id +
                ", idOrden=" + idOrden +
                ", idProducto=" + idProducto +
                '}';
    }
}
