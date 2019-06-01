/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author NarF
 */
public class Orden {

    private int idOrden;
    private LocalDateTime apertura;
    private LocalDateTime cierre;
    private float total;
    private int idMesa;

    public Orden(int idMesa) {
        this.idMesa = idMesa;
        apertura = LocalDateTime.now();
        total = 0f;
        cierre = null;
    }

    public Orden(int idOrden, String dbDateTime, float total, int idMesa) {
        this.idOrden = idOrden;
        this.apertura = LocalDateTime.parse(dbDateTime, DateTimeFormatter.ofPattern(DateTimeFormat.DB_FORMAT));
        this.total = total;
        this.idMesa = idMesa;
        cierre = null;
    }

    public int getIdOrden() {
        return idOrden;
    }

    public LocalDateTime getApertura() {
        return apertura;
    }

    public String getAperturaToDbString() {
        return DateTimeFormat.toDbString(apertura);
    }

    public String getAperturaToUserString() {
        return DateTimeFormat.toUserString(apertura);
    }

    public LocalDateTime getCierre() {
        return cierre;
    }

    public String getCierreToDbString() {
        return DateTimeFormat.toDbString(cierre);
    }

    public String getCierreToUserString() {
        return DateTimeFormat.toUserString(cierre);
    }

    public float getTotal() {
        return total;
    }

    public int getIdMesa() {
        return idMesa;
    }

    public void setIdOrden(int idOrden) {
        this.idOrden = idOrden;
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

    public void cerrarMesa() {
        cierre = LocalDateTime.now();
    }

    public boolean isClosed() {
        return cierre == null;
    }

    @Override
    public String toString() {
        return "Orden{" + "idOrden=" + idOrden + ", apertura=" + apertura + ", cierre=" + cierre + ", total=" + total + ", idMesa=" + idMesa + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + this.idOrden;
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
        final Orden other = (Orden) obj;
        if (this.idOrden != other.idOrden) {
            return false;
        }
        return true;
    }
    

}
