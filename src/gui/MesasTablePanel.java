/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.gui;

import java.util.HashMap;
import src.dao.MesaDao;
import src.gui.template.GenericTablePanel;
import src.gui.template.MesasTableModel;
import src.model.Mesa;

/**
 *
 * @author NarF
 */
public class MesasTablePanel extends GenericTablePanel {

    public MesasTablePanel() {
        super("Mesas");
        
                
        MesasTableModel tm = new MesasTableModel();
        setModel(tm);
    }

    @Override
    public void refreshTable() {
        MesaDao md = MesaDao.getInstance();
        HashMap<Integer, Mesa> mesas = md.queryAll();
        
        model.clearTableModelData();
        
        mesas.forEach((id, mesa) -> model.addRow(mesa) );
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
