/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.model.abstractmodel;

import src.model.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author NarF
 */
public class AbstractOrden {

    protected int id;
    protected LocalDateTime apertura;
    protected LocalDateTime cierre;
    protected float total;


    /**
     * Constructor usado por  el creador de ordenes de produccion (Abrir mesa)
     */
    public AbstractOrden() {
        apertura = LocalDateTime.now();
        total = 0f;
        cierre = null;
    }

    /**
     * constructor usado por el editor para crear objetos nuevos e introducirlos a la base de datos
     *
     * @param apertura LocalDateTime apertura
     * @param cierre   LocalDateTime cierre
     * @param total    precio total
     */
    public AbstractOrden(LocalDateTime apertura, LocalDateTime cierre, float total) {
        this.apertura = apertura;
        this.cierre = cierre;
        this.total = total;
    }

    /**
     * constructor usado por OrdenesDao para crear objetos que esten en la base de datos
     *
     * @param id      id orden
     * @param dbOpen  LocalDateTime apertura
     * @param dbClose LocalDateTime cierre
     * @param total   precio total
     */
    public AbstractOrden(int id, String dbOpen, String dbClose, float total) {
        this.id = id;
        this.apertura = LocalDateTime.parse(dbOpen, DateTimeFormatter.ofPattern(DateTimeFormat.DB_FORMAT));
        if (dbClose != null)
            this.cierre = LocalDateTime.parse(dbClose, DateTimeFormatter.ofPattern(DateTimeFormat.DB_FORMAT));
        this.total = total;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getApertura() {
        return apertura;
    }

    public void setApertura(LocalDateTime apertura) {
        this.apertura = apertura;
    }

    public LocalDateTime getCierre() {
        return cierre;
    }

    public void setCierre(LocalDateTime cierre) {
        this.cierre = cierre;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }



    public String getAperturaToDbString() {
        return DateTimeFormat.toDbString(apertura);
    }

    public String getAperturaToUserString() {
        return DateTimeFormat.toUserString(apertura);
    }

    public String getCierreToDbString() {
        return DateTimeFormat.toDbString(cierre);
    }

    public String getCierreToUserString() {
        return DateTimeFormat.toUserString(cierre);
    }

    public void setAperturaUser(String userFormat) {
        setApertura(LocalDateTime.parse(userFormat, DateTimeFormatter.ofPattern(DateTimeFormat.USER_FORMAT)));
    }

    public void setAperturaDB(String dbFormat) {
        setApertura(LocalDateTime.parse(dbFormat, DateTimeFormatter.ofPattern(DateTimeFormat.DB_FORMAT)));
    }

    public void setCierreUser(String userFormat) {
        setCierre(LocalDateTime.parse(userFormat, DateTimeFormatter.ofPattern(DateTimeFormat.USER_FORMAT)));
    }

    public void setCierreDB(String dbFormat) {
        setCierre(LocalDateTime.parse(dbFormat, DateTimeFormatter.ofPattern(DateTimeFormat.DB_FORMAT)));
    }

    public void cerrarOrden() {
        cierre = LocalDateTime.now();
    }

    public boolean isClosed() {
        return cierre == null;
    }

    @Override
    public String toString() {
        return "AbstractOrden{" +
                "id=" + id +
                ", apertura=" + apertura +
                ", cierre=" + cierre +
                ", total=" + total +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractOrden that = (AbstractOrden) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
