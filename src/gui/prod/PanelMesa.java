/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.gui.prod;

import src.gui.tablemodels.ProductosSimpleTableModel;
import src.model.Mesa;

/**
 *
 * @author NarF
 */
public class PanelMesa extends PanelMesaGui {

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

    }

}
