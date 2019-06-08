/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.control;

import src.gui.editor.*;
import src.model.SessionDB;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author NarF
 */
public class MainFrame extends javax.swing.JFrame {

    private static JPanel cards;
    private static CardLayout cardLayout;

    public final static String LOADPANEL = "Load Card";
    public final static String NEWPANEL = "New Card";

    public final static String MAINMENUPANEL = "Main Menu Card";

    public final static String EDITMESAS = "Edit Mesas Card";
    public final static String EDITCATEGORIAS = "Edit Categorias Card";
    public final static String EDITPRODUCTOS = "Edit Productos Card";
    public final static String EDITORDENES = "Edit Ordenes Card";
    public final static String EDITSERVIDOS = "Edit Servidos Card";

    public static boolean DEBUG = true;
    public static boolean SQL_DEBUG = true;
    public static boolean SQL_CONN = false;

    private static MenuBar menu;

    public MainFrame() {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("El Chiringuito Playero");
        setMinimumSize(new Dimension(600, 675));

        cardLayout = new CardLayout();
        cards = new JPanel(cardLayout);
        setContentPane(cards);

        menu = new MenuBar();
        setJMenuBar(menu);

        initCards();

        if (SessionDB.exists()) {
            if (SessionDB.isValid()) {

                cards.add(new PanelPrincipal(), MAINMENUPANEL);

                MenuBar.jMenuVer.setEnabled(true);
                cardLayout.show(cards, MAINMENUPANEL);
            }
        } else {
            cardLayout.show(cards, NEWPANEL);
        }

        setMenuActions();

        pack();
        this.setLocationRelativeTo(null);
    }

    public static JPanel getCards() {
        return cards;
    }

    public static CardLayout getCardLayout() {
        return cardLayout;
    }

    private void initCards() {
        cards.add(new LoadPanel(false), LOADPANEL);
        cards.add(new LoadPanel(true), NEWPANEL);

        cards.add(new MesasTablePanel(), EDITMESAS);
        cards.add(new CategoriasTablePanel(), EDITCATEGORIAS);
        cards.add(new ProductosTablePanel(), EDITPRODUCTOS);
        cards.add(new OrdenTablePanel(), EDITORDENES);
        cards.add(new ServidosTablePanel(), EDITSERVIDOS);

    }

    public void setMenuActions() {
        menu.jMenuItemSalir.addActionListener(e -> this.dispose());

    }

    public static void setCard(String cardName) {
        cardLayout.show(cards, cardName);
    }
}
