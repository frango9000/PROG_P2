/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.control;

import src.gui.MenuBarGui;

import javax.swing.*;

import static src.control.MainFrame.*;

/**
 *
 * @author NarF
 */
public class MenuBar extends MenuBarGui {

    public MenuBar() {
        setActions();
    }

    private void setActions() {
        String info = "Este es un programa desarrollado en Java\n por\n" +
                "estudiantes del Centro de Formacion Profesional\n" +
                " Daniel Castelao de Vigo para el ciclo de D.A.M 1,\n" +
                " Modulo de Programacion.\n" +
                "\n" +
                "Este software esta pensado para facilitar la tarea\n" +
                " de organizacion y control del servicio de un negocio\n" +
                " de hosteleria. \n" +
                "\n" +
                "Version 0.0.1\n" +
                "\n" +
                "Autores. Francisco Sanchez y Emilio Mendez\n" +
                "\n" +
                "Agradecimientos especiales a Nina Fontaiña\n" +
                " Profesora del Modulo y Tutora del Curso.\n";


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
        jMenuItemInformacion.addActionListener(e -> JOptionPane.showMessageDialog(this, info, "Informacion", JOptionPane.INFORMATION_MESSAGE));
    }

}
