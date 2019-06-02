/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.gui.editor;

import javax.swing.JOptionPane;
import src.dao.OrdenDao;
import src.gui.MainFrame;
import src.gui.tablemodels.OrdenTableModel;

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
        OrdenDao.getInstance().queryAll().forEach((id, orden) -> model.addRow(orden));
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
            JOptionPane.showMessageDialog(this, "Elige un " + nombre + " a editar", nombre + "s", JOptionPane.ERROR_MESSAGE);
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
                if (OrdenDao.getInstance().delete(idSelected) > 0) {
                    JOptionPane.showMessageDialog(this, nombre + " eliminado: " + idSelected, nombre + " Eliminado", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, nombre + " NO eliminado: " + idSelected, nombre + " Eliminado", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Elige una " + nombre + " a eliminar", nombre, JOptionPane.ERROR_MESSAGE);
        }
        refreshTable();
    }

}
