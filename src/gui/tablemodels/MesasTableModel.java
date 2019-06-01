/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.gui.tablemodels;

import src.gui.tablemodels.GenericDomainTableModel;
import java.util.Arrays;
import src.model.Mesa;

/**
 *
 * @author NarF
 */
public class MesasTableModel extends GenericDomainTableModel<Mesa> {

    public MesasTableModel() {
        super(Arrays.asList(new Object[]{"id", "Mesa", "Capacidad"}));        
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
                return Integer.class;
        }
        throw new ArrayIndexOutOfBoundsException(columnIndex);
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Mesa mesa = getDomainObject(rowIndex);
        switch (columnIndex) {
            case 0:
                return mesa.getIdMesa();
            case 1:
                return mesa.getMesa();
            case 2:
                return mesa.getCapacidad();
            default:
                throw new ArrayIndexOutOfBoundsException(columnIndex);
        }
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        Mesa mesa = getDomainObject(rowIndex);
        switch (columnIndex) {
            case 0:
                mesa.setIdMesa((Integer) value);
                break;
            case 1:
                mesa.setMesa((String) value);
                break;
            case 2:
                mesa.setCapacidad((Integer) value);
                break;
            default:
                throw new ArrayIndexOutOfBoundsException(columnIndex);
        }
        notifyTableCellUpdated(rowIndex, columnIndex);
    }

}
