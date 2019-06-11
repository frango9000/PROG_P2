/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.gui.editor;

import src.dao.ServidoDao;
import src.gui.tablemodels.ServidosTableModel;

import javax.swing.*;

/**
 *
 * @author NarF
 */
public class ServidosTablePanel extends GenericTablePanel {

    public ServidosTablePanel() {
        super("Servidos");

        ServidosTableModel tm = new ServidosTableModel();
        setModel(tm);
        setMainColsSize();
    }

    @Override
    public void refreshTable() {
        model.clearTableModelData();
        ServidoDao.getInstance().queryAll().forEach((id, servido) -> model.addRow(servido));
    }

    @Override
    public void editAction() {
        int selectedRow = jTable.getSelectedRow();
        if (selectedRow > -1) {
            java.awt.EventQueue.invokeLater(() -> {
                int id = (int) jTable.getValueAt(selectedRow, 0);
                new ServidoFrame(id).setVisible(true);
            });
        } else {
            JOptionPane.showMessageDialog(this, "Elige un producto " + nombre + " a editar", nombre + "s", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void addAction() {
        java.awt.EventQueue.invokeLater(() -> {
            new ServidoFrame().setVisible(true);
        });
    }

    @Override
    public void deleteAction() {
        int selectedRow = jTable.getSelectedRow();
        if (selectedRow > -1) {
            int idSelected = (int) jTable.getValueAt(selectedRow, 0);
            int i = JOptionPane.showConfirmDialog(this, "Deseas eliminar el producto " + nombre + ": " + idSelected, "Eliminando el producto " + nombre + "", JOptionPane.YES_NO_OPTION);
            if (i == 0) {
                if (ServidoDao.getInstance().delete(idSelected) > 0) {
                    JOptionPane.showMessageDialog(this, "Producto " + nombre + " eliminado: " + idSelected, nombre + " Eliminado", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Producto " + nombre + " NO eliminado: " + idSelected, nombre + " Eliminado", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Elige un producto " + nombre + " a eliminar", nombre, JOptionPane.ERROR_MESSAGE);
        }
        refreshTable();
    }

    @Override
    public void cacheTable() {
        ServidoDao.getInstance().getCache().forEach((id, servido) -> model.addRow(servido));
    }

}
