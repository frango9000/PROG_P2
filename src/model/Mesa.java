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
public class Mesa {

    private int idMesa;
    private String mesa;
    private int capacidad;

    public Mesa(String mesa) {
        this.mesa = mesa;
    }

    public Mesa(String mesa, int capacidad) {
        this.mesa = mesa;
        this.capacidad = capacidad;
    }

    public Mesa(int idMesa, String mesa) {
        this.idMesa = idMesa;
        this.mesa = mesa;
    }

    public Mesa(int idMesa, String mesa, int capacidad) {
        this.idMesa = idMesa;
        this.mesa = mesa;
        this.capacidad = capacidad;
    }

    public int getIdMesa() {
        return idMesa;
    }

    public String getMesa() {
        return mesa;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setIdMesa(int idMesa) {
        this.idMesa = idMesa;
    }

    public void setMesa(String mesa) {
        this.mesa = mesa;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    @Override
    public String toString() {
        return "Mesa{" + "idMesa=" + idMesa + ", mesa=" + mesa + ", capacidad=" + capacidad + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + this.idMesa;
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
        final Mesa other = (Mesa) obj;
        if (this.idMesa != other.idMesa) {
            return false;
        }
        return true;
    }
    
}
