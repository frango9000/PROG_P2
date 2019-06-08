/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.gui.editor;

import src.dao.OrdenesDao;
import src.gui.tablemodels.OrdenTableModel;

import javax.swing.*;

/**
 *
 * @author NarF
 */
public class OrdenTablePanel extends GenericTablePanel {

    public OrdenTablePanel() {
        super("Ordenes");

        OrdenTableModel tm = new OrdenTableModel();
        setModel(tm);
        setMainColsSize();
    }

    @Override
    public void refreshTable() {
        model.clearTableModelData();
        OrdenesDao.getInstance().queryAll().forEach((id, orden) -> model.addRow(orden));
    }

    @Override
    public void editAction() {
        int selectedRow = jTable.getSelectedRow();
        if (selectedRow > -1) {
            java.awt.EventQueue.invokeLater(() -> {
                int id = (int) jTable.getValueAt(selectedRow, 0);
                new OrdenFrame(id).setVisible(true);
            });
        } else {
            JOptionPane.showMessageDialog(this, "Elige una " + nombre + " a editar", nombre + "es", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void addAction() {
        java.awt.EventQueue.invokeLater(() -> {
            new OrdenFrame().setVisible(true);
        });
    }

    @Override
    public void deleteAction() {
        int selectedRow = jTable.getSelectedRow();
        if (selectedRow > -1) {
            int idSelected = (int) jTable.getValueAt(selectedRow, 0);
            int i = JOptionPane.showConfirmDialog(this, "Deseas eliminar la " + nombre + ": " + idSelected, "Eliminando " + nombre + "", JOptionPane.YES_NO_OPTION);
            if (i == 0) {
                if (OrdenesDao.getInstance().delete(idSelected) > 0) {
                    JOptionPane.showMessageDialog(this, nombre + " eliminada: " + idSelected, nombre + " Eliminada", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, nombre + " NO eliminada: " + idSelected, nombre + " Eliminada", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Elige una " + nombre + " a eliminar", nombre, JOptionPane.ERROR_MESSAGE);
        }
        refreshTable();
    }

}
