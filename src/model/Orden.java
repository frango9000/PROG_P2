/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.model;

import java.time.LocalDateTime;

/**
 *
 * @author NarF
 */
public class Orden {
    private final int idOrden;
    private LocalDateTime apertura;
    private LocalDateTime cierre;
    private float total;
    private int idMesa;

    public Orden(int idOrden, LocalDateTime apertura, int idMesa) {
        this.idOrden = idOrden;
        this.apertura = apertura;
        this.idMesa = idMesa;
    }

    public int getIdOrden() {
        return idOrden;
    }

    public LocalDateTime getApertura() {
        return apertura;
    }

    public LocalDateTime getCierre() {
        return cierre;
    }

    public float getTotal() {
        return total;
    }

    public int getIdMesa() {
        return idMesa;
    }

    public void setApertura(LocalDateTime apertura) {
        this.apertura = apertura;
    }

    public void setCierre(LocalDateTime cierre) {
        this.cierre = cierre;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public void setIdMesa(int idMesa) {
        this.idMesa = idMesa;
    }

    @Override
    public String toString() {
        return "Orden{" + "idOrden=" + idOrden + ", apertura=" + apertura + ", cierre=" + cierre + ", total=" + total + ", idMesa=" + idMesa + '}';
    }
    
}
