/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.gui;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

/**
 *
 * @author NarF
 */
public final class MenuBar extends JMenuBar {

    public JMenu jMenuEditar;
    public JMenu jMenuInicio;
    public JMenuItem jMenuItemCargar;
    public JMenuItem jMenuItemCocina;
    public JMenuItem jMenuItemEditMesas;
    public JMenuItem jMenuItemEditCategorias;
    public JMenuItem jMenuItemEditProductos;
    public JMenuItem jMenuItemEditOrdenes;
    public JMenuItem jMenuItemEditServidos;
    public JMenuItem jMenuItemInformacion;
    public JMenuItem jMenuItemMesas;
    public JMenuItem jMenuItemNueva;
    public JMenuItem jMenuItemSalir;
    public JMenu jMenuMas;
    public JMenu jMenuVer;
    private JPopupMenu.Separator jSeparator1;
    private JPopupMenu.Separator jSeparator2;

    public MenuBar() {
        initComponents();
    }

    public void initComponents() {
        jMenuInicio = new JMenu();
        jMenuItemNueva = new JMenuItem();
        jMenuItemCargar = new JMenuItem();
        jSeparator1 = new JPopupMenu.Separator();
        jMenuItemSalir = new JMenuItem();
        jMenuVer = new JMenu();
        jMenuItemMesas = new JMenuItem();
        jMenuItemCocina = new JMenuItem();
        jSeparator2 = new JPopupMenu.Separator();
        jMenuEditar = new JMenu();
        jMenuItemEditMesas = new JMenuItem();
        jMenuItemEditProductos = new JMenuItem();
        jMenuItemEditCategorias = new JMenuItem();
        jMenuItemEditOrdenes = new JMenuItem();
        jMenuItemEditServidos = new JMenuItem();
        jMenuMas = new JMenu();
        jMenuItemInformacion = new JMenuItem();

        jMenuInicio.setText("Inicio");

        jMenuItemNueva.setText("Nueva");
        jMenuInicio.add(jMenuItemNueva);

        jMenuItemCargar.setText("Cargar");
        jMenuInicio.add(jMenuItemCargar);
        jMenuInicio.add(jSeparator1);

        jMenuItemSalir.setText("Salir");
        jMenuInicio.add(jMenuItemSalir);

        this.add(jMenuInicio);

        jMenuVer.setText("Ver");

        jMenuItemMesas.setText("Mesas");
        jMenuVer.add(jMenuItemMesas);

        jMenuItemCocina.setText("Cocina");
        jMenuVer.add(jMenuItemCocina);
        jMenuVer.add(jSeparator2);

        jMenuEditar.setText("Editar");

        jMenuItemEditMesas.setText("Mesas");
        jMenuEditar.add(jMenuItemEditMesas);

        jMenuItemEditProductos.setText("Productos");
        jMenuEditar.add(jMenuItemEditProductos);

        jMenuItemEditCategorias.setText("Categorias");
        jMenuEditar.add(jMenuItemEditCategorias);
        
        jMenuItemEditOrdenes.setText("Ordenes");
        jMenuEditar.add(jMenuItemEditOrdenes);
        
        jMenuItemEditServidos.setText("Servidos");
        jMenuEditar.add(jMenuItemEditServidos);

        jMenuVer.add(jMenuEditar);

        this.add(jMenuVer);

        jMenuMas.setText("Mas");

        jMenuItemInformacion.setText("Informacion");
        jMenuMas.add(jMenuItemInformacion);

        this.add(jMenuMas);

    }

}
