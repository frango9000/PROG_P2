/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.control;

import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JFrame;
import src.dao.MesaDao;
import src.dao.OrdenDao;
import src.dao.ServidoDao;
import src.gui.PanelMesaGui;
import src.gui.tablemodels.ProductosSimpleTableModel;
import src.gui.tablemodels.ServidoSimpleTableModel;
import src.model.Mesa;
import src.model.Orden;
import src.model.Producto;
import src.model.Servido;

/**
 *
 * @author NarF
 */
public class MesaViewFrame extends JFrame {

    private final JFrame me;

    private final ProductosSimpleTableModel productosModel = new ProductosSimpleTableModel();
    private final ServidoSimpleTableModel servidosModel = new ServidoSimpleTableModel();

    public MesaViewFrame(Mesa mesa) {
        super();
        me = this;
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle(mesa.getMesa());
        setMinimumSize(new Dimension(650, 650));

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

            jTableServidos.setModel(servidosModel);
            jTableProductos.setModel(productosModel);

            if (mesa.getIdOrden() == 0) {
                setDisponible();
            } else {
                setOcupada(mesa.getOrden());
            }

            setBtnActions();

        }

        private void setOcupada(Orden orden) {
            jLabelEstado.setText("Ocupada");
            jLabelEstado.setForeground(PanelPrincipal.COLOR_FONT_OCUPADA);
            jPanelTop.setBackground(PanelPrincipal.COLOR_OCUPADA);
            jLabelIdOrden.setText(orden == null ? "" : orden.getId() + "");
            System.out.println(orden);

            jButtonCobrar.setEnabled(true);
            jButtonCerrarMesa.setEnabled(true);
            jButtonLimpiarCuenta.setEnabled(true);
            jButtonQuitar.setEnabled(true);
            jButtonAgregar.setEnabled(true);

            jButtonAbrir.setEnabled(false);

            servidosModel.clearTableModelData();
            servidosModel.addRows(orden.getServidos());

        }

        private void setDisponible() {
            jLabelEstado.setText("Disponible");
            jLabelEstado.setForeground(PanelPrincipal.COLOR_FONT_DISPONIBLE);
            jPanelTop.setBackground(PanelPrincipal.COLOR_DISPONIBLE);
            jLabelIdOrden.setText("");
            servidosModel.clearTableModelData();

            jButtonCobrar.setEnabled(false);
            jButtonCerrarMesa.setEnabled(false);
            jButtonLimpiarCuenta.setEnabled(false);
            jButtonQuitar.setEnabled(false);
            jButtonAgregar.setEnabled(false);

            jButtonAbrir.setEnabled(true);
        }

        void setBtnActions() {
            jButtonBack.addActionListener(e -> me.dispose());

            jButtonAbrir.addActionListener(e -> {
                Orden orden = new Orden();

                if (OrdenDao.getInstance().insert(orden) > 0) {
                    System.out.println("Nueva orden OK " + orden.getIdOrden());
                }

                mesa.setIdOrden(orden.getIdOrden());
                mesa.setOrden(orden);
                MesaDao.getInstance().update(mesa);
                setOcupada(orden);
            });

            jButtonCerrarMesa.addActionListener(e -> {
                mesa.getOrden().cerrarOrden();
                OrdenDao.getInstance().update(mesa.getOrden());

                mesa.setOrden(null);
                mesa.setIdOrden(0);
                MesaDao.getInstance().update(mesa);

                setDisponible();
            });

            jButtonQuitar.addActionListener(e -> {
                int selectedRow = jTableServidos.getSelectedRow();
                Servido servido = servidosModel.getDomainObject(selectedRow);
                mesa.getOrden().getServidos().remove(servido);
                servidosModel.deleteRow(selectedRow);
                ServidoDao.getInstance().delete(servido);
            });

            JButton[] cats = new JButton[]{jButtonCat0, jButtonCat1, jButtonCat2, jButtonCat3, jButtonCat4, jButtonCat5, jButtonCat6, jButtonCat7};
            for (int i = 0; i < cats.length; i++) {
                final int n = i;
                cats[i].addActionListener(e -> {
                    productosModel.clearTableModelData();
                    productosModel.addRows(PanelPrincipal.productosCategorizados[n]);

                    jPanelCategoriasBtns.setVisible(false);
                    jPanelTablaProductos.setVisible(true);
                });
            }

            jButtonInicio.addActionListener(e -> {
                jPanelTablaProductos.setVisible(false);
                jPanelCategoriasBtns.setVisible(true);
            });

            jButtonAgregar.addActionListener(e -> {
                Producto producto = productosModel.getDomainObject(jTableProductos.getSelectedRow());
                Servido servido = new Servido(mesa.getIdOrden(), producto);
                ServidoDao.getInstance().insert(servido);
                mesa.getOrden().addServido(servido);
                servidosModel.addRow(servido);
            });
        }

    }
}
