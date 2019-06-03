/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.control;

import java.util.Arrays;
import java.util.List;
import javax.swing.JButton;
import src.dao.CategoriaDao;
import src.dao.MesaDao;
import src.dao.ProductoDao;
import src.gui.PanelPrincipalGui;

/**
 *
 * @author NarF
 */
public class PanelPrincipal extends PanelPrincipalGui {

    public PanelPrincipal() {
        initialQuery();
        setActions();
    }


    private static void initialQuery() {
        MesaDao.getInstance().queryAll();
        CategoriaDao.getInstance().queryAll();
        ProductoDao.getInstance().queryAll();
        MesaDao.getInstance().getOrdenesActivas();
    }
    void setActions() {
        List<JButton> listaMesas = Arrays.asList(mesa1, mesa2, mesa3, mesa4, mesa5, mesa6, mesa7, mesa8, barra1, barra2, barra3, barra4, terraza1, terraza2, terraza3, terraza4, extra1, extra2, extra3, extra4, extra5, extra6);
        for (int i = 0; i < listaMesas.size(); i++) {
            final int k = i;
            listaMesas.get(i).addActionListener(e -> {
                java.awt.EventQueue.invokeLater(() -> {
                    new MesaViewFrame(MesaDao.getInstance().get(k + 1)).setVisible(true);
                });
            });
        }
    }
}
