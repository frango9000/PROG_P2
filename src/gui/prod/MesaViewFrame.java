/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.gui.prod;

import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author NarF
 */
public class MesaViewFrame extends JFrame {

    public MesaViewFrame(JPanel panel) {
        super();
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Table Manager");
        setMinimumSize(new Dimension(200, 200));

        setContentPane(panel);

        pack();
        this.setLocationRelativeTo(null);
    }

}
