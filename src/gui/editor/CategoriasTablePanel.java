/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.gui.editor;

import src.dao.CategoriasDao;
import src.gui.tablemodels.CategoriasTableModel;

import javax.swing.*;

/**
 *
 * @author NarF
 */
public class CategoriasTablePanel extends GenericTablePanel {

    public CategoriasTablePanel() {
        super("Categorias");

        CategoriasTableModel tm = new CategoriasTableModel();
        setModel(tm);
        setMainColsSize();
    }

    @Override
    public void refreshTable() {
        model.clearTableModelData();
        CategoriasDao.getInstance().queryAll().forEach((id, categoria) -> model.addRow(categoria));
    }

    @Override
    public void editAction() {
        int selectedRow = jTable.getSelectedRow();
        if (selectedRow > -1) {
            java.awt.EventQueue.invokeLater(() -> {
                int id = (int) jTable.getValueAt(selectedRow, 0);
                new CategoriaFrame(id).setVisible(true);
            });
        } else {
            JOptionPane.showMessageDialog(this, "Elige una " + nombre + " a editar", nombre + "s", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void addAction() {
        java.awt.EventQueue.invokeLater(() -> {
            new CategoriaFrame().setVisible(true);
        });
    }

    @Override
    public void deleteAction() {
        int selectedRow = jTable.getSelectedRow();
        if (selectedRow > -1) {
            int idSelected = (int) jTable.getValueAt(selectedRow, 0);
            String nameSelected = (String) jTable.getValueAt(selectedRow, 1);
            int i = JOptionPane.showConfirmDialog(this, "Deseas eliminar la " + nombre + ": " + nameSelected, "Eliminando " + nombre + "", JOptionPane.YES_NO_OPTION);
            if (i == 0) {
                if (CategoriasDao.getInstance().delete(idSelected) > 0) {
                    JOptionPane.showMessageDialog(this, nombre + " eliminada: " + nameSelected, nombre + " Eliminada", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, nombre + " NO eliminada: " + nameSelected, nombre + " Eliminada", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Elige una " + nombre + " a eliminar", nombre, JOptionPane.ERROR_MESSAGE);
        }
        refreshTable();
    }

}
