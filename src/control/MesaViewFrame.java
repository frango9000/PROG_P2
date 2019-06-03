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
    
    private final ProductosSimpleTableModel productosModel = new ProductosSimpleTableModel();
    private final ProductosSimpleTableModel servidosModel = new ProductosSimpleTableModel();
    

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
            jLabelEstado.setText("<html><b style=\"color:red;\">Ocupada</b></html>");
            jLabelIdOrden.setText(orden == null ? "" : orden.getId()+"");
            System.out.println(orden);

            jButtonCobrar.setEnabled(true);
            jButtonCerrarMesa.setEnabled(true);
            jButtonAbrir.setEnabled(false);
            
            servidosModel.clearTableModelData();
            servidosModel.addRows(orden.getServidos());
            
        }

        private void setDisponible() {
            jLabelEstado.setText("<html><b style=\"color:green;\">Disponible</b></html>");
            jLabelIdOrden.setText("");
            servidosModel.clearTableModelData();

            jButtonCobrar.setEnabled(false);
            jButtonCerrarMesa.setEnabled(false);
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
        }

    }
}
