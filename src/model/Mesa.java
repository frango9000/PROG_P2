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
    private final int idMesa;
    private String mesa;
    private int capacidad;

    public Mesa(int idMesa, String mesa) {
        this.idMesa = idMesa;
        this.mesa = mesa;
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
}
