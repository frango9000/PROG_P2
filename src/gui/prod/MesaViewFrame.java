/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.gui.prod;

import java.awt.Dimension;
import javax.swing.JFrame;
import src.gui.tablemodels.ProductosSimpleTableModel;
import src.model.Mesa;

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
        setTitle("Table Manager");
        setMinimumSize(new Dimension(200, 200));

        setContentPane(new PanelMesa(mesa));

        pack();
        this.setLocationRelativeTo(null);
    }

    private class PanelMesa extends PanelMesaGui {

        public PanelMesa(Mesa mesa) {

            jLabelTitle.setText(mesa.getMesa());

            ProductosSimpleTableModel productos = new ProductosSimpleTableModel();
            jTableProductos.setModel(productos);

            ProductosSimpleTableModel servidos = new ProductosSimpleTableModel();
            jTableProductos.setModel(servidos);

            if (mesa.getIdOrden() == 0) {
                jLabelEstado.setText("<html><b style=\"color:green;\">Disponible</b></html>");
                jLabelIdOrden.setText("");
            } else {
                jLabelEstado.setText("<html><b style=\"color:red;\">Ocupada</b></html>");
                jLabelIdOrden.setText(mesa.getIdOrden() + "");
            }
            
            setBtnActions();

        }
        
        void setBtnActions(){
            jButtonBack.addActionListener(e -> me.dispose());
        }

    }
}
