/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 *
 * @author NarF
 */
public class Orden implements IPersistable {

    private int idOrden;
    private LocalDateTime apertura;
    private LocalDateTime cierre;
    private float total;

    private ArrayList<Servido> servidosF = new ArrayList<>();

    public Orden() {
        apertura = LocalDateTime.now();
        total = 0f;
        cierre = null;
    }

    public Orden(int idOrden, String dbDateTime, float total) {
        this.idOrden = idOrden;
        this.apertura = LocalDateTime.parse(dbDateTime, DateTimeFormatter.ofPattern(DateTimeFormat.DB_FORMAT));
        this.total = total;
        cierre = null;
    }

    public Orden(LocalDateTime apertura, LocalDateTime cierre, float total) {
        this.apertura = apertura;
        this.cierre = cierre;
        this.total = total;
    }

    public ArrayList<Servido> getServidos() {
        return servidosF;
    }

    public void setServidos(ArrayList<Servido> servidosF) {
        this.servidosF = servidosF;
    }

    public void addServido(Servido servidos) {
        servidosF.add(servidos);
    }

    public void addServidos(ArrayList<Servido> servido) {
        servidosF.addAll(servido);
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

    public void setIdOrden(int idOrden) {
        this.idOrden = idOrden;
    }

    public void setApertura(LocalDateTime apertura) {
        this.apertura = apertura;
    }

    public void setAperturaUser(String userFormat) {
        setApertura(LocalDateTime.parse(userFormat, DateTimeFormatter.ofPattern(DateTimeFormat.USER_FORMAT)));
    }

    public void setAperturaDB(String dbFormat) {
        setApertura(LocalDateTime.parse(dbFormat, DateTimeFormatter.ofPattern(DateTimeFormat.DB_FORMAT)));
    }

    public void setCierre(LocalDateTime cierre) {
        this.cierre = cierre;
    }

    public void setCierreUser(String userFormat) {
        setCierre(LocalDateTime.parse(userFormat, DateTimeFormatter.ofPattern(DateTimeFormat.USER_FORMAT)));
    }

    public void setCierreDB(String dbFormat) {
        setCierre(LocalDateTime.parse(dbFormat, DateTimeFormatter.ofPattern(DateTimeFormat.DB_FORMAT)));
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public void cerrarOrden() {
        cierre = LocalDateTime.now();
    }

    public boolean isClosed() {
        return cierre == null;
    }

    @Override
    public String toString() {
        return "Orden{" + "idOrden=" + idOrden + ", apertura=" + apertura + ", cierre=" + cierre + ", total=" + total + (servidosF == null ? "" : servidosF) + '}';
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
        return this.idOrden == other.idOrden;
    }

    @Override
    public int getId() {
        return getIdOrden();
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
