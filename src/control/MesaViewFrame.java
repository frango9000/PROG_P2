/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.control;

import src.dao.MesasDao;
import src.dao.OrdenDao;
import src.dao.ServidoDao;
import src.gui.PanelMesaGui;
import src.gui.tablemodels.ProductosSimpleTableModel;
import src.gui.tablemodels.ServidoSimpleTableModel;
import src.model.*;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author NarF
 */
public class MesaViewFrame extends JFrame {

    private final ProductosSimpleTableModel productosModel = new ProductosSimpleTableModel();
    private final ServidoSimpleTableModel servidosModel = new ServidoSimpleTableModel();

    public MesaViewFrame(Mesa mesa) {
        super();
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle(mesa.getMesa());
        setMinimumSize(new Dimension(550, 550));

        setContentPane(new PanelMesa(mesa));

        pack();
        this.setLocationRelativeTo(null);
    }

    private class PanelMesa extends PanelMesaGui {

        private final Mesa mesa;

        public PanelMesa(Mesa mesa) {
            this.mesa = mesa;
            jLabelTitle.setText(mesa.getMesa());

            jPanelCenterLeft.add(jPanelTablaProductos);
            jPanelTablaProductos.setVisible(false);
            jButtonAgregar.setEnabled(false);
            jButtonAgregar.setBackground(PanelPrincipal.COLOR_DISPONIBLE);
            jButtonQuitar.setBackground(PanelPrincipal.COLOR_OCUPADA);
            jButtonLimpiarCuenta.setBackground(PanelPrincipal.COLOR_WARNING_RED);

            jTableServidos.setModel(servidosModel);
            jTableProductos.setModel(productosModel);

            if (jTableProductos.getColumnModel().getColumnCount() > 0) {
                jTableProductos.getColumnModel().getColumn(0).setMinWidth(50);
                jTableProductos.getColumnModel().getColumn(1).setMinWidth(50);
                jTableProductos.getColumnModel().getColumn(1).setMaxWidth(50);
            }
            if (jTableServidos.getColumnModel().getColumnCount() > 0) {
                jTableServidos.getColumnModel().getColumn(0).setMinWidth(50);
                jTableServidos.getColumnModel().getColumn(1).setMinWidth(50);
                jTableServidos.getColumnModel().getColumn(1).setMaxWidth(50);
            }

            if (mesa.getIdOrden() == 0) {
                setDisponible();
            } else {
                setOcupada(mesa.getOrden());
            }

            setBtnActions();

        }

        private void setOcupada(Orden orden) {
            jButtonAgregar.setEnabled(false);
            jButtonAbrir.setEnabled(false);
            jButtonCobrar.setEnabled(true);
            jButtonCerrarMesa.setEnabled(true);
            jButtonLimpiarCuenta.setEnabled(true);
            jButtonCobrar.setEnabled(true);

            jLabelEstado.setText("Ocupada");
            jLabelEstado.setForeground(PanelPrincipal.COLOR_FONT_OCUPADA);
            jPanelTop.setBackground(PanelPrincipal.COLOR_OCUPADA);

            jLabelIdOrden.setText(orden == null ? "" : orden.getId() + "");
            updateTotal();

            servidosModel.clearTableModelData();
            servidosModel.addRows(orden.getServidos());
            checkBtnQuitar();
        }

        private void setDisponible() {
            jButtonAgregar.setEnabled(false);
            jButtonCobrar.setEnabled(false);
            jButtonCerrarMesa.setEnabled(false);
            jButtonLimpiarCuenta.setEnabled(false);
            jButtonQuitar.setEnabled(false);
            jButtonAbrir.setEnabled(true);
            jButtonCobrar.setEnabled(false);

            jLabelEstado.setText("Disponible");
            jLabelEstado.setForeground(PanelPrincipal.COLOR_FONT_DISPONIBLE);
            jPanelTop.setBackground(PanelPrincipal.COLOR_DISPONIBLE);
            updateTotal();

            jLabelIdOrden.setText("");
            servidosModel.clearTableModelData();

        }

        private void setBtnActions() {
            jButtonBack.addActionListener(e -> SwingUtilities.getWindowAncestor(this).dispose());

            jButtonAbrir.addActionListener(e -> {
                Orden orden = new Orden();

                SessionDB.setAutoclose(false);
                OrdenDao.getInstance().insert(orden);
                mesa.setIdOrden(orden.getIdOrden());
                mesa.setOrden(orden);
                MesasDao.getInstance().update(mesa);
                SessionDB.setAutoclose(true);

                setOcupada(orden);
                if (jPanelTablaProductos.isVisible()) {
                    jButtonAgregar.setEnabled(true);
                    jTableProductos.setRowSelectionInterval(0, 0);
                }
                PanelPrincipal.colorMesas();
            });

            jButtonCerrarMesa.addActionListener(e -> {

                SessionDB.setAutoclose(false);
                mesa.getOrden().cerrarOrden();
                OrdenDao.getInstance().update(mesa.getOrden());
                mesa.setOrden(null);
                mesa.setIdOrden(0);
                MesasDao.getInstance().update(mesa);
                SessionDB.setAutoclose(true);

                setDisponible();

                checkBtnQuitar();

                PanelPrincipal.colorMesas();
            });

            jButtonQuitar.addActionListener(e -> {
                int selectedRow = jTableServidos.getSelectedRow();
                Servido servido = servidosModel.getDomainObject(selectedRow);
                mesa.getOrden().getServidos().remove(servido);
                mesa.getOrden().setTotal(mesa.getOrden().getTotal() - servido.getProducto().getPrecio());
                servidosModel.deleteRow(selectedRow);
                ServidoDao.getInstance().delete(servido);

                updateTotal();
                checkBtnQuitar();
            });

            JButton[] cats = new JButton[]{jButtonCat0, jButtonCat1, jButtonCat2, jButtonCat3, jButtonCat4, jButtonCat5, jButtonCat6, jButtonCat7};
            for (int i = 0; i < cats.length; i++) {
                final int n = i;
                cats[i].addActionListener(e -> {
                    productosModel.clearTableModelData();
                    productosModel.addRows(PanelPrincipal.productosCategorizados[n]);

                    jTableProductos.setRowSelectionInterval(0, 0);
                    switchPaneL();
                });
            }

            jButtonInicio.addActionListener(e -> {
                switchPaneL();
            });

            jButtonAgregar.addActionListener(e -> {
                Producto producto = productosModel.getDomainObject(jTableProductos.getSelectedRow());
                Servido servido = new Servido(mesa.getIdOrden(), producto);
                ServidoDao.getInstance().insert(servido);
                mesa.getOrden().addServido(servido);
                mesa.getOrden().setTotal(mesa.getOrden().getTotal() + producto.getPrecio());
                servidosModel.addRow(servido);
                jTableServidos.setRowSelectionInterval(jTableServidos.getRowCount() - 1, jTableServidos.getRowCount() - 1);
                updateTotal();
                checkBtnQuitar();
            });

            jButtonLimpiarCuenta.addActionListener(e -> {
                if (JOptionPane.showConfirmDialog(this, "Estas seguro que deseas borrar todo?", "Alerta!", JOptionPane.WARNING_MESSAGE) == 0) {
                    servidosModel.clearTableModelData();
                    ServidoDao.getInstance().deleteSome(mesa.getOrden().getServidos());
                    mesa.getOrden().getServidos().clear();
                    mesa.getOrden().setTotal(0);
                    updateTotal();
                    checkBtnQuitar();
                }
            });

            jButtonCobrar.addActionListener(e -> {
                if (mesa.getOrden().getTotal() > 0) {
                    JOptionPane.showMessageDialog(this, "El cliente debe pagar " + String.format("%.2f", mesa.getOrden().getTotal()) + " €", "Ticket", JOptionPane.INFORMATION_MESSAGE);
                }
            });
        }

        private void switchPaneL() {
            boolean actual = jPanelCategoriasBtns.isVisible();
            jPanelCategoriasBtns.setVisible(!actual);
            jPanelTablaProductos.setVisible(actual);
            if (mesa.getOrden() != null) {
                jButtonAgregar.setEnabled(actual);
                jTableProductos.setRowSelectionInterval(0, 0);
            }

        }

        private void checkBtnQuitar() {
            if (mesa.getOrden() != null && mesa.getOrden().getServidos().size() > 0) {
                jButtonQuitar.setEnabled(true);
                jButtonLimpiarCuenta.setEnabled(true);
                jTableServidos.setRowSelectionInterval(jTableServidos.getRowCount() - 1, jTableServidos.getRowCount() - 1);
            } else {
                jButtonQuitar.setEnabled(false);
                jButtonLimpiarCuenta.setEnabled(false);
            }
        }

        private void updateTotal() {
            jLabelTotal.setText(mesa.getOrden() == null ? "" : String.format("%.2f", mesa.getOrden().getTotal()));

        }
    }
}
