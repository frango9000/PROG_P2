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
public class ServidoSimpleTableModel extends GenericDomainTableModel<Servido> {

    public ServidoSimpleTableModel() {
        super(Arrays.asList(new Object[]{"Producto", "Precio"}));
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return String.class;
            case 1:
                return Float.class;
        }
        throw new ArrayIndexOutOfBoundsException(columnIndex);
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Servido servido = getDomainObject(rowIndex);
        switch (columnIndex) {
            case 0:
                return servido.getProducto().getProducto();
            case 1:
                return servido.getProducto().getPrecio();
            default:
                throw new ArrayIndexOutOfBoundsException(columnIndex);
        }
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        Servido servido = getDomainObject(rowIndex);
        switch (columnIndex) {
            case 0:
                servido.getProducto().setProducto((String) value);
                break;
            case 1:
                servido.getProducto().setPrecio((Integer) value);
                break;
            default:
                throw new ArrayIndexOutOfBoundsException(columnIndex);
        }
        notifyTableCellUpdated(rowIndex, columnIndex);
    }

}
