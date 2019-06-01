/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.gui;

import src.gui.template.MenuBar;
import src.gui.editor.MesasTablePanel;
import java.awt.CardLayout;
import java.awt.Dimension;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import src.gui.editor.CategoriasTablePanel;
import src.gui.editor.ProductosTablePanel;

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
    public final static String PAISESPANEL = "Paises Card";
    public final static String AUTORESPANEL = "Autores Card";
    public final static String EDITORIALESPANEL = "Editoriales Card";
    public final static String LIBROSPANEL = "Libros Card";

    private static MenuBar menu;

    public MainFrame() {
        initComponents();
    }

    public static JPanel getCards() {
        return cards;
    }

    public static CardLayout getCardLayout() {
        return cardLayout;
    }

    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Table Manager");
        setMinimumSize(new Dimension(600, 350));

        cardLayout = new CardLayout();
        cards = new JPanel(cardLayout);
        cards.add(new JPanel(), "Start");

        JPanel loadcard = new LoadPanel(false);
        cards.add(loadcard, LOADPANEL);

        JPanel newcard = new LoadPanel(true);
        cards.add(newcard, NEWPANEL);

        cards.add(new MesasTablePanel(), EDITMESAS);
        cards.add(new CategoriasTablePanel(), EDITCATEGORIAS);
        cards.add(new ProductosTablePanel(),EDITPRODUCTOS);

        cardLayout.show(cards, "START");
        this.setContentPane(cards);

        menu = new MenuBar();
        setJMenuBar(menu);

        setMenuActions();

        pack();
        this.setLocationRelativeTo(null);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }

    public void setMenuActions() {
        menu.jMenuItemSalir.addActionListener(e -> this.dispose());
        menu.jMenuItemCargar.addActionListener(e -> setCard(LOADPANEL));
        menu.jMenuItemNueva.addActionListener(e -> setCard(NEWPANEL));
        menu.jMenuItemEditMesas.addActionListener(e -> setCard(EDITMESAS));
        menu.jMenuItemEditCategorias.addActionListener(e -> setCard(EDITCATEGORIAS));
        menu.jMenuItemEditProductos.addActionListener(e -> setCard(EDITPRODUCTOS));
        
    }

    public static void setCard(String cardName) {
        cardLayout.show(cards, cardName);
    }
}
