/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.gui.editor;

import javax.swing.JOptionPane;
import src.dao.ProductoDao;
import src.gui.tablemodels.ProductosTableModel;

/**
 *
 * @author NarF
 */
public class ProductosTablePanel extends GenericTablePanel {

    public ProductosTablePanel() {
        super("Productos");

        ProductosTableModel tm = new ProductosTableModel();
        setModel(tm);
        setMainColsSize();
    }

    @Override
    public void refreshTable() {
        model.clearTableModelData();
        ProductoDao.getInstance().queryAll().forEach((id, producto) -> model.addRow(producto));
    }

    @Override
    public void editAction() {
        int selectedRow = jTable.getSelectedRow();
        if (selectedRow > -1) {
            java.awt.EventQueue.invokeLater(() -> {
                int id = (int) jTable.getValueAt(selectedRow, 0);
                new ProductoFrame(id).setVisible(true);
            });
        } else {
            JOptionPane.showMessageDialog(this, "Elige un " + nombre + " a editar", nombre + "s", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void addAction() {
        java.awt.EventQueue.invokeLater(() -> {
            new ProductoFrame().setVisible(true);
        });
    }

    @Override
    public void deleteAction() {
        int selectedRow = jTable.getSelectedRow();
        if (selectedRow > -1) {
            int idSelected = (int) jTable.getValueAt(selectedRow, 0);
            String nameSelected = (String) jTable.getValueAt(selectedRow, 1);
            int i = JOptionPane.showConfirmDialog(this, "Deseas eliminar el " + nombre + ": " + nameSelected, "Eliminando " + nombre + "", JOptionPane.YES_NO_OPTION);
            if (i == 0) {
                if (ProductoDao.getInstance().delete(idSelected) > 0) {
                    JOptionPane.showMessageDialog(this, nombre + " eliminado: " + nameSelected, nombre + " Eliminado", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, nombre + " NO eliminado: " + nameSelected, nombre + " Eliminado", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Elige un " + nombre + " a eliminar", nombre, JOptionPane.ERROR_MESSAGE);
        }
        refreshTable();
    }

}
