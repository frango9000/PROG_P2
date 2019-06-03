/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.gui.tablemodels;

import java.util.Arrays;
import src.model.Producto;

/**
 *
 * @author NarF
 */
public class ProductosTableModel extends GenericDomainTableModel<Producto> {

    public ProductosTableModel() {
        super(Arrays.asList(new Object[]{"id", "Producto", "Precio", "Categoria"}));
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
                return Float.class;
            case 3:
                return String.class;
        }
        throw new ArrayIndexOutOfBoundsException(columnIndex);
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Producto producto = getDomainObject(rowIndex);
        switch (columnIndex) {
            case 0:
                return producto.getIdProducto();
            case 1:
                return producto.getProducto();
            case 2:
                return producto.getPrecio();
            case 3:
                return producto.getCategoria().toString();
            default:
                throw new ArrayIndexOutOfBoundsException(columnIndex);
        }
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        Producto producto = getDomainObject(rowIndex);
        switch (columnIndex) {
            case 0:
                producto.setIdProducto((Integer) value);
                break;
            case 1:
                producto.setProducto((String) value);
                break;
            case 2:
                producto.setPrecio((Integer) value);
                break;
            case 3:
                producto.setIdCategoria((Integer) value);
                break;
            default:
                throw new ArrayIndexOutOfBoundsException(columnIndex);
        }
        notifyTableCellUpdated(rowIndex, columnIndex);
    }

}
