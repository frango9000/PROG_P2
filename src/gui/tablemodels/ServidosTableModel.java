/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.gui.tablemodels;

import java.util.Arrays;
import src.model.Servido;

/**
 *
 * @author NarF
 */
public class ServidosTableModel extends GenericDomainTableModel<Servido> {

    public ServidosTableModel() {
        super(Arrays.asList(new Object[]{"id", "idOrden", "idProducto"}));
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
                return Integer.class;
            case 2:
                return Integer.class;
        }
        throw new ArrayIndexOutOfBoundsException(columnIndex);
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Servido servido = getDomainObject(rowIndex);
        switch (columnIndex) {
            case 0:
                return servido.getIdServido();
            case 1:
                return servido.getIdOrden();
            case 2:
                return servido.getIdProducto();
            default:
                throw new ArrayIndexOutOfBoundsException(columnIndex);
        }
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        Servido servido = getDomainObject(rowIndex);
        switch (columnIndex) {
            case 0:
                servido.setIdServido((Integer) value);
                break;
            case 1:
                servido.setIdOrden((Integer) value);
                break;
            case 2:
                servido.setIdProducto((Integer) value);
                break;
            default:
                throw new ArrayIndexOutOfBoundsException(columnIndex);
        }
        notifyTableCellUpdated(rowIndex, columnIndex);
    }

}
