/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.control;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.JButton;
import src.dao.CategoriaDao;
import src.dao.MesaDao;
import src.dao.OrdenDao;
import src.dao.ProductoDao;
import src.dao.ServidoDao;
import src.gui.PanelPrincipalGui;
import src.model.Orden;
import src.model.Producto;
import src.model.SessionDB;

/**
 *
 * @author NarF
 */
public class PanelPrincipal extends PanelPrincipalGui {

    public static ArrayList<Producto>[] productosCategorizados = new ArrayList[8];

    private static List<JButton> listaMesasButtons;

    public static final Color COLOR_DISPONIBLE = new Color(168, 230, 207);
    public static final Color COLOR_OCUPADA = new Color(227, 128, 74);
    public static final Color COLOR_FONT_DISPONIBLE = new Color(151, 184, 88);// @TODO ajsutar colores
    public static final Color COLOR_FONT_OCUPADA = new Color(168, 230, 207);
    public static final Color COLOR_WARNING_RED = new Color(219, 102, 81);

    public PanelPrincipal() {
        listaMesasButtons = Arrays.asList(mesa1, mesa2, mesa3, mesa4, mesa5, mesa6, mesa7, mesa8,
                barra1, barra2, barra3, barra4,
                terraza1, terraza2, terraza3, terraza4,
                extra1, extra2, extra3, extra4, extra5, extra6);
        initialQuery();
        setActions();
        colorMesas();
    }

    public static void initialQuery() {
        SessionDB.setAutoclose(false);
        CategoriaDao.getInstance().queryAll();
        ProductoDao.getInstance().queryAll();
        categorizarProductos();
        ArrayList<Orden> ordenesActivas = MesaDao.getInstance().getOrdenesActivas();
        ServidoDao.getInstance().query(ordenesActivas);
        MesaDao.getInstance().queryAll();
        SessionDB.setAutoclose(true);
    }

    public static void categorizarProductos() {
        for (int i = 0; i < productosCategorizados.length; i++) {
            productosCategorizados[i] = new ArrayList<>();
        }
        ProductoDao.getInstance().getAll().forEach((id, pro) -> {
            if (pro.getIdCategoria() < 9) {
                productosCategorizados[pro.getIdCategoria() - 1].add(pro);
            }
        });
    }

    public static void clearMemory() {
        CategoriaDao.getInstance().getAll().clear();
        ProductoDao.getInstance().getAll().clear();
        OrdenDao.getInstance().getAll().clear();
        ServidoDao.getInstance().getAll().clear();
        MesaDao.getInstance().getAll().clear();
        System.gc();
        initialQuery();
    }

    private static void setActions() {
        for (int i = 0; i < listaMesasButtons.size(); i++) {
            final int k = i;
            listaMesasButtons.get(i).addActionListener(e -> {
                java.awt.EventQueue.invokeLater(() -> {
                    new MesaViewFrame(MesaDao.getInstance().get(k + 1)).setVisible(true);
                });
            });
        }
    }

    public static void colorMesas() {
        for (int i = 0; i < listaMesasButtons.size(); i++) {
            if (MesaDao.getInstance().getAll().get(i + 1).getIdOrden() == 0) {
                listaMesasButtons.get(i).setBackground(COLOR_DISPONIBLE);
            } else {
                listaMesasButtons.get(i).setBackground(COLOR_OCUPADA);
            }
        }

    }

}
