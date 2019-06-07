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
public class Categoria implements IPersistable {

    private int idCategoria;
    private String categoria;

    public Categoria(String categoria) {
        this.categoria = categoria;
    }

    public Categoria(int idCategoria, String categoria) {
        this.idCategoria = idCategoria;
        this.categoria = categoria;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return categoria;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + this.idCategoria;
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
        final Categoria other = (Categoria) obj;
        return this.idCategoria == other.idCategoria;
    }

    @Override
    public int getId() {
        return getIdCategoria();
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
