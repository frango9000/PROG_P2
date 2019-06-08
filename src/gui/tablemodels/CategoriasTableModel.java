/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.gui.tablemodels;

import src.model.Categoria;

import java.util.Arrays;

/**
 *
 * @author NarF
 */
public class CategoriasTableModel extends GenericDomainTableModel<Categoria> {

    public CategoriasTableModel() {
        super(Arrays.asList("id", "Categoria"));
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
        }
        throw new ArrayIndexOutOfBoundsException(columnIndex);
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Categoria categoria = getDomainObject(rowIndex);
        switch (columnIndex) {
            case 0:
                return categoria.getId();
            case 1:
                return categoria.getCategoria();
            default:
                throw new ArrayIndexOutOfBoundsException(columnIndex);
        }
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        Categoria categoria = getDomainObject(rowIndex);
        switch (columnIndex) {
            case 0:
                categoria.setId((Integer) value);
                break;
            case 1:
                categoria.setCategoria((String) value);
                break;
            default:
                throw new ArrayIndexOutOfBoundsException(columnIndex);
        }
        notifyTableCellUpdated(rowIndex, columnIndex);
    }

}
