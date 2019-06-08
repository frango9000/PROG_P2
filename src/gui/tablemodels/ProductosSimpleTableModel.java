/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.gui.tablemodels;

import src.model.Producto;

import java.util.Arrays;

/**
 *
 * @author NarF
 */
public class ProductosSimpleTableModel extends GenericDomainTableModel<Producto> {

    public ProductosSimpleTableModel() {
        super(Arrays.asList("Producto", "Precio"));
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
        Producto producto = getDomainObject(rowIndex);
        switch (columnIndex) {
            case 0:
                return producto.getProducto();
            case 1:
                return producto.getPrecio();
            default:
                throw new ArrayIndexOutOfBoundsException(columnIndex);
        }
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        Producto producto = getDomainObject(rowIndex);
        switch (columnIndex) {
            case 0:
                producto.setProducto((String) value);
                break;
            case 1:
                producto.setPrecio((Integer) value);
                break;
            default:
                throw new ArrayIndexOutOfBoundsException(columnIndex);
        }
        notifyTableCellUpdated(rowIndex, columnIndex);
    }

}
