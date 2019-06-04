/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.control;

import java.awt.CardLayout;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import src.gui.LoadPanelGui;
import src.model.SessionDB;
import src.model.StaticHelpers;

/**
 *
 * @author NarF
 */
public class LoadPanel extends LoadPanelGui {

    public JPanel cards;
    public CardLayout layout;
    protected boolean newdb;

    public LoadPanel(boolean newD) {
        super();
        newdb = newD;
        cards = MainFrame.getCards();
        layout = (CardLayout) cards.getLayout();
        setButtonActions();
        String title;
        if (newdb) {
            title = "Nueva";
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        } else {
            title = "Cargar";
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        }
        jLabelTitle.setText(title);
        jBtnCargar.setText(title);

        jTextFieldBrowse.setText(System.getProperty("user.dir") + "/src/src/resources");
    }

    void setButtonActions() {
        jBtnBrowse.addActionListener(e -> {
            chooser.setCurrentDirectory(new File(jTextFieldBrowse.getText()));

            if (chooser.showOpenDialog(new JFrame()) == JFileChooser.APPROVE_OPTION) {
                jTextFieldBrowse.setText(chooser.getSelectedFile().getAbsolutePath());
                if (!newdb) {
                    setStatusLabels();
                }
            }
        });

        jBtnCargar.addActionListener(e -> {
            if (newdb) {
                newButtonAction();
            } else {
                loadButtonAction();
            }
        });
    }

    private void newButtonAction() {
        // TODO add your handling code here:
        if (jTextFieldBrowse.getText().trim().length() > 0) {
            File newfile = null;
            do {
                String nombre = JOptionPane.showInputDialog(this, "Introduce el nombre de la base de datos", "Nueva DB", 3);
                if (nombre == null || nombre.length() < 1) {
                    break;
                }
                File file = new File(jTextFieldBrowse.getText());

                String dir = file.getAbsolutePath();
                if (file.isFile()) {
                    String full = file.getAbsolutePath();
                    dir = full.substring(0, full.lastIndexOf("/"));
                }
                newfile = new File(dir + "/" + nombre + ".db");

                if (!newfile.exists()) {
                    break;
                } else {
                    JOptionPane.showMessageDialog(this, "File already exists", "Alert", JOptionPane.ERROR_MESSAGE);
                    newfile = null;
                }
            } while (true);

            if (newfile != null) {
                SessionDB.setDbFile(newfile);
                System.out.println("Initializing DB...");
                SessionDB.crearTablas();
                int i = JOptionPane.showConfirmDialog(this, "Insertar demo data?", "Nueva DB", JOptionPane.YES_NO_OPTION);
                if (i == 0) {
                    System.out.println("Inserting Demo Data...");
                    SessionDB.insertarDemoData();
                }
                if (SessionDB.isValid()) {
                    MenuBar.jMenuVer.setEnabled(true);
                    MainFrame.getCards().add(new PanelPrincipal(), MainFrame.MAINMENUPANEL);
                    MainFrame.setCard(MainFrame.MAINMENUPANEL);
                    JOptionPane.showMessageDialog(this, "DB creada correctamente", "Nueva DB", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    MenuBar.jMenuVer.setEnabled(false);
                    JOptionPane.showMessageDialog(this, "DB invalida. Contacta con el desarrollador.", "Cargando DB", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Creacion cancelada", "Cargando DB", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Elige una ruta", "Cargando DB", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadButtonAction() {
        // TODO add your handling code here:
        if (jTextFieldBrowse.getText().trim().length() > 0) {
            File loadfile = new File(jTextFieldBrowse.getText());
            if (loadfile.exists()) {
                SessionDB.setDbFile(loadfile);

                if (SessionDB.isValid()) {
                    MenuBar.jMenuVer.setEnabled(true);
                    MainFrame.getCards().add(new PanelPrincipal(), MainFrame.MAINMENUPANEL);
                    MainFrame.setCard(MainFrame.MAINMENUPANEL);
                } else {
                    MenuBar.jMenuVer.setEnabled(false);
                    JOptionPane.showMessageDialog(this, "DB Invalida", "Cargando DB", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Archivo inexistente", "Cargando DB", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Elige una DB", "Cargando DB", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void setStatusLabels() {
        File file = new File(jTextFieldBrowse.getText());
        SessionDB.setDbFile(file);

        String ok = "<html><b style=\"color:green;\">OK</b></hrml>";
        String err = "<html><b style=\"color:red;\">Invalid</b></hrml>";

        if (SessionDB.isValid()) {
            jLabStatus.setText(ok);
        } else {
            jLabStatus.setText(err);
        }
        jLabFileSize.setText(StaticHelpers.byteSizeFormatter(file.length()) + " bytes");
    }
}
