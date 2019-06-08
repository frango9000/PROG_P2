/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.model.abstractmodel;


import src.model.Orden;

/**
 * @author NarF
 */
public abstract class AbstractMesa {

    protected int id;
    protected String mesa;
    protected int capacidad;

    protected int idOrden;
    protected Orden orden;

    public AbstractMesa(String mesa, int capacidad, int idOrden) {
        this.mesa = mesa;
        this.capacidad = capacidad;
        this.idOrden = idOrden;
    }

    public AbstractMesa(int idMesa, String mesa, int capacidad, int idOrden) {
        this(mesa, capacidad, idOrden);
        this.id = idMesa;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMesa() {
        return mesa;
    }

    public void setMesa(String mesa) {
        this.mesa = mesa;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public int getIdOrden() {
        return idOrden;
    }

    public void setIdOrden(int idOrden) {
        this.idOrden = idOrden;
    }

    public Orden getOrden() {
        return orden;
    }

    public void setOrden(Orden orden) {
        this.orden = orden;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractMesa that = (AbstractMesa) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "Mesa{" +
                "id=" + id +
                ", mesa='" + mesa + '\'' +
                ", capacidad=" + capacidad +
                ", idOrden=" + idOrden +
                ", orden=" + orden.toString() +
                '}';
    }

}
