/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.gui;

import src.gui.template.GenericTablePanel;
import src.gui.template.MesasTableModel;

/**
 *
 * @author NarF
 */
public class MesasTablePanel extends GenericTablePanel {

    public MesasTablePanel() {
        super("Mesas");
        
                
        MesasTableModel tm = new MesasTableModel();
        setModel(tm);
        refreshTable();
    }

    @Override
    public void refreshTable() {
    }

    @Override
    public void editAction() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void backAction() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addAction() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteAction() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}
