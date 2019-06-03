/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.control;

import java.awt.Dimension;
import javax.swing.JFrame;
import src.dao.MesaDao;
import src.dao.OrdenDao;
import src.gui.PanelMesaGui;
import src.gui.tablemodels.ProductosSimpleTableModel;
import src.model.Mesa;
import src.model.Orden;

/**
 *
 * @author NarF
 */
public class MesaViewFrame extends JFrame {

    private final JFrame me;

    public MesaViewFrame(Mesa mesa) {
        super();
        me = this;
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle(mesa.getMesa());
        setMinimumSize(new Dimension(200, 200));

        setContentPane(new PanelMesa(mesa));

        pack();
        this.setLocationRelativeTo(null);
    }

    private class PanelMesa extends PanelMesaGui {

        private final Mesa mesa;

        public PanelMesa(Mesa mesa) {
            this.mesa = mesa;

            jLabelTitle.setText(mesa.getMesa());

            ProductosSimpleTableModel productos = new ProductosSimpleTableModel();
            jTableProductos.setModel(productos);

            ProductosSimpleTableModel servidos = new ProductosSimpleTableModel();
            jTableProductos.setModel(servidos);

            if (mesa.getIdOrden() == 0) {
                setDisponible();
            } else {
                //setOcupada(mesa);//TODO
            }

            setBtnActions();

        }

        private void setOcupada(Orden orden) {
            jLabelEstado.setText("<html><b style=\"color:red;\">Ocupada</b></html>");
            jLabelIdOrden.setText(orden.getIdOrden() + "");

            jButtonCobrar.setEnabled(true);
            jButtonCerrarMesa.setEnabled(true);
            jButtonAbrir.setEnabled(false);
        }

        private void setDisponible() {
            jLabelEstado.setText("<html><b style=\"color:green;\">Disponible</b></html>");
            jLabelIdOrden.setText("");

            jButtonCobrar.setEnabled(false);
            jButtonCerrarMesa.setEnabled(false);
            jButtonAbrir.setEnabled(true);
        }

        void setBtnActions() {
            jButtonBack.addActionListener(e -> me.dispose());
            jButtonAbrir.addActionListener(e -> {
                Orden orden = new Orden();

                if (OrdenDao.getInstance().insert(orden) > 0) {
                    System.out.println("OK");
                }

                mesa.setIdOrden(orden.getIdOrden());
                MesaDao.getInstance().update(mesa);

            });
        }

    }
}
