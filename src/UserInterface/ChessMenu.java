/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UserInterface;

import Controller.Controller;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * Chess menu that extends the JMenu swing component to make it easier to generate the chess menu options
 * @author Wesley
 */
public class ChessMenu extends JMenuBar {
    
    private final Controller controller;
    private final JMenu menu;
    private final JMenuItem openItem;
    private final JMenuItem saveItem;
    private final JFileChooser fc;
    private JTextField filename = new JTextField(), dir = new JTextField();
    private JButton open = new JButton("Open"), save = new JButton("Save");
    private JTextArea ta;

    /**
     * Gets the text area component
     * @return 
     */
    public JTextArea getTextArea() {
        return ta;
    }

    /**
     * sets the text area component
     * @param ta
     */
    public void setTextArea(JTextArea ta) {
        this.ta = ta;
    }
    
    /**
     * The default constructor for the chess Menu Bar
     */
    public ChessMenu(Controller c, Viewer v){
        this.controller = c;
        this.fc = new JFileChooser();
        this.menu = new JMenu("File");
        
        this.openItem = new JMenuItem("Open");
        this.openItem.addActionListener(e->{

            //show open dialog
            int rVal = fc.showOpenDialog(this.menu);
            if (rVal == JFileChooser.APPROVE_OPTION) {
              filename.setText(fc.getSelectedFile().getName());
              dir.setText(fc.getCurrentDirectory().toString());
              
              //Build out file path and call load
              this.controller.Load(this.dir.getText()+"\\"+this.filename.getText());
              
            }
            if (rVal == JFileChooser.CANCEL_OPTION) {
              filename.setText("You pressed cancel");
              dir.setText("");
            }
            
            
        
        });
        this.menu.add(openItem);
        
        this.saveItem = new JMenuItem("Save");
        this.saveItem.addActionListener(e->{
            
            //Open save dialog
            int rVal = fc.showSaveDialog(menu);
            if (rVal == JFileChooser.APPROVE_OPTION) {
              filename.setText(fc.getSelectedFile().getName());
              dir.setText(fc.getCurrentDirectory().toString());
              
              //create fen
              String fen = this.controller.CreateFen(this.controller.gameBoard);
              
              //append the fen to the text area
              this.ta.append(fen + "\n");
              
              //setup the file path
              String path = this.dir.getText() +"\\"+ this.filename.getText();

              //call control save function
              this.controller.Save(fen, path);
            }
            if (rVal == JFileChooser.CANCEL_OPTION) {
              filename.setText("You pressed cancel");
              dir.setText("");
            }

        });
        this.menu.add(saveItem);
        this.add(menu);
    }

}
