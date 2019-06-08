/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.gui.tablemodels;

import src.model.Orden;

import java.util.Arrays;

/**
 *
 * @author NarF
 */
public class OrdenTableModel extends GenericDomainTableModel<Orden> {

    public OrdenTableModel() {
        super(Arrays.asList("id", "Apertura", "Cierre", "Total"));
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return Integer.class;
            case 1:
                return String.class;
            case 2:
                return String.class;
            case 3:
                return Float.class;
        }
        throw new ArrayIndexOutOfBoundsException(columnIndex);
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Orden orden = getDomainObject(rowIndex);
        switch (columnIndex) {
            case 0:
                return orden.getId();
            case 1:
                return orden.getAperturaToUserString();
            case 2:
                if (orden.getCierre() == null) {
                    return "";
                } else {
                    return orden.getCierreToUserString();
                }
            case 3:
                return orden.getTotal();
            default:
                throw new ArrayIndexOutOfBoundsException(columnIndex);
        }
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        Orden orden = getDomainObject(rowIndex);
        switch (columnIndex) {
            case 0:
                orden.setId((Integer) value);
                break;
            case 1:
                orden.setAperturaUser((String) value);
                break;
            case 2:
                orden.setCierreUser((String) value);
                break;
            case 3:
                orden.setTotal((Float) value);
                break;
            default:
                throw new ArrayIndexOutOfBoundsException(columnIndex);
        }
        notifyTableCellUpdated(rowIndex, columnIndex);
    }

}
