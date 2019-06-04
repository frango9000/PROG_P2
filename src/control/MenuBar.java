/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.control;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import static src.control.MainFrame.*;
import src.gui.MenuBarGui;

/**
 *
 * @author NarF
 */
public class MenuBar extends MenuBarGui {

    public MenuBar() {
        setActions();
    }

    private void setActions() {
        jMenuItemCargar.addActionListener(e -> setCard(LOADPANEL));
        jMenuItemNueva.addActionListener(e -> setCard(NEWPANEL));

        jMenuItemMesas.addActionListener(e -> setCard(MAINMENUPANEL));

        jMenuItemEditMesas.addActionListener(e -> setCard(EDITMESAS));
        jMenuItemEditCategorias.addActionListener(e -> setCard(EDITCATEGORIAS));
        jMenuItemEditProductos.addActionListener(e -> setCard(EDITPRODUCTOS));
        jMenuItemEditOrdenes.addActionListener(e -> setCard(EDITORDENES));
        jMenuItemEditServidos.addActionListener(e -> setCard(EDITSERVIDOS));
        jMenuItemLimpiarCache.addActionListener(e -> {
            PanelPrincipal.clearMemory();
            JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(this), "Cache eliminado", "Limpiando", JOptionPane.INFORMATION_MESSAGE);
        });
    }

}
