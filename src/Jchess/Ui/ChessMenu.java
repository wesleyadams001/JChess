/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Jchess.Ui;

import Jchess.Core.Constants;
import Jchess.Core.Controller;
import java.awt.Desktop;
import java.io.File;
import java.net.URL;
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
    
  private static final long serialVersionUID = 7929908706478324051L;
  private final Controller controller;
    private final JMenu menu;
    private final JMenuItem openItem;
    private final JMenuItem saveItem;
    private final JMenuItem saveHistItem;
    private final JMenu helpMenu;
    private final JMenuItem dokiItem;
    private final JMenuItem chessItem;
    private final JMenuItem fenItem;
    private final JFileChooser fc;
    private JTextField filename = new JTextField(), dir = new JTextField();
    private JTextArea ta;

    /**
     * Gets the text area component.
     * @return The text area.
     */
    public JTextArea getTextArea() {
        return ta;
    }

    /**
     * Sets the text area component.
     * @param ta The text area.
     */
    public void setTextArea(JTextArea ta) {
        this.ta = ta;
    }
    
    /**
     * The default constructor for the chess Menu Bar
     * @param c The active Controller.
     * @param v The active Viewer.
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
              this.controller.loadFromFile(this.dir.getText() + File.separator + this.filename.getText());
              
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
              String fen = this.controller.createFEN();
              
              //append the fen to the text area
              this.ta.append(fen + "\n");
              
              //setup the file path
              String path = this.dir.getText() + File.separator + this.filename.getText();

              //call control save function
              this.controller.saveToFile(fen, path);
            }
            if (rVal == JFileChooser.CANCEL_OPTION) {
              filename.setText("You pressed cancel");
              dir.setText("");
            }

        });
        this.menu.add(saveItem);
        
        this.saveHistItem = new JMenuItem("Save Move History");
        this.saveHistItem.addActionListener(e->{
            //Open save dialog
            int rVal = fc.showSaveDialog(menu);
            if (rVal == JFileChooser.APPROVE_OPTION) {
              filename.setText(fc.getSelectedFile().getName());
              dir.setText(fc.getCurrentDirectory().toString());
              
              //Get the history from text area
              String history = this.ta.getText();
              
              //setup the file path
              String path = this.dir.getText() + File.separator + this.filename.getText();

              //call control save function
              this.controller.saveToFile(history, path);
            }
            if (rVal == JFileChooser.CANCEL_OPTION) {
              filename.setText("You pressed cancel");
              dir.setText("");
            }
        });
        this.menu.add(saveHistItem);
        this.add(menu);
        
        this.helpMenu = new JMenu("Help");
        
        this.chessItem = new JMenuItem("Chess Help");
        this.chessItem.addActionListener(a -> {
            openWebpage(Constants.CHESS_URL);
        });
        this.helpMenu.setToolTipText(Constants.CHESS_BLURB);
        this.helpMenu.add(chessItem);
        
        this.dokiItem = new JMenuItem("Doki Doki Literature Club");
        this.dokiItem.addActionListener(a -> {
            openWebpage(Constants.DOKI_URL);
        });
        this.dokiItem.setToolTipText(Constants.DOKI_BLURB);
        this.helpMenu.add(dokiItem);
        
        this.fenItem = new JMenuItem("FEN Notation");
        this.fenItem.addActionListener(a -> {
            openWebpage(Constants.FEN_URL);
        });
        this.fenItem.setToolTipText(Constants.FEN_BLURB);
        this.helpMenu.add(fenItem);
        this.add(helpMenu);
        
    }
    
    /**
     * a helper function to open webpages
     * @param url a String url 
     */
    private static void openWebpage(String url) {
        try {
            Desktop.getDesktop().browse(new URL(url).toURI());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}