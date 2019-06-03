/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.control;

import src.gui.editor.MesasTablePanel;
import java.awt.CardLayout;
import javax.swing.JPanel;
import src.dao.CategoriaDao;
import src.dao.MesaDao;
import src.dao.ProductoDao;
import src.gui.editor.CategoriasTablePanel;
import src.gui.editor.OrdenTablePanel;
import src.gui.editor.ProductosTablePanel;
import src.gui.editor.ServidosTablePanel;
import src.model.SessionDB;

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

    public final static String PAISESPANEL = "Paises Card";
    public final static String AUTORESPANEL = "Autores Card";
    public final static String EDITORIALESPANEL = "Editoriales Card";
    public final static String LIBROSPANEL = "Libros Card";

    private static MenuBar menu;

    public MainFrame() {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("El Chiringuito Playero");
        //setMinimumSize(new Dimension(200, 200));

        cardLayout = new CardLayout();
        cards = new JPanel(cardLayout);
        setContentPane(cards);

        menu = new MenuBar();
        setJMenuBar(menu);

        initCards();

        if (SessionDB.exists()) {
            if (SessionDB.isValid()) {
                initialQuery();
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

        cards.add(new PanelPrincipal(), MAINMENUPANEL);

        cards.add(new MesasTablePanel(), EDITMESAS);
        cards.add(new CategoriasTablePanel(), EDITCATEGORIAS);
        cards.add(new ProductosTablePanel(), EDITPRODUCTOS);
        cards.add(new OrdenTablePanel(), EDITORDENES);
        cards.add(new ServidosTablePanel(), EDITSERVIDOS);

    }

    private static void initialQuery() {
        MesaDao.getInstance().queryAll();
        CategoriaDao.getInstance().queryAll();
        ProductoDao.getInstance().queryAll();
        MesaDao.getInstance().getOrdenesActivas();
    }

    public void setMenuActions() {
        menu.jMenuItemSalir.addActionListener(e -> this.dispose());

    }

    public static void setCard(String cardName) {
        cardLayout.show(cards, cardName);
    }
}
