/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Jchess.Ui;

import static Jchess.Core.Constants.DIVIDER_LOCATION;
import static Jchess.Core.Constants.LARGE_DIMENSION;
import static Jchess.Core.Constants.SMALL_DIMENSION;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

/**
 * Extends the BoardFrame to allow for a split view of the chess board
 * @author Wesley
 */
public class BoardFrame extends JFrame{
    
    //Define the needed components 
    private final JSplitPane splitPane;  
    private final JPanel leftPanel;       
    private final JPanel rightPanel;    
        
    
    /**
     * The default constructor for the BoardFrame that takes a name for the Title of the frame and two content panels to display
     * @param name String name of the Frame.
     * @param boardPanel JPanel a panel with some content.
     * @param contentPanel JPanel a panel with some content.
     */
    public BoardFrame(String name, JPanel boardPanel, JPanel contentPanel){

        //Set the name/Title of the frame
        this.setName(name);
        this.setTitle(name);
        
        //Create the split pane to enable multiple components on the board
        //so the user can manipulate the left and right components visibility
        splitPane = new JSplitPane();

        // our board
        leftPanel = boardPanel;
        
        // our settings/control panel
        rightPanel = contentPanel;      

        // Default size of our window and its layout:
        setPreferredSize(new Dimension(SMALL_DIMENSION, LARGE_DIMENSION));  
        
        //The contentPane is the container that holds all our components
        //GridLayout is like a grid with 1 column and 1 row
        getContentPane().setLayout(new GridLayout());  
        
        //Only add split pane, GridLayout fills whole window
        getContentPane().add(splitPane);

        //Configures the split pane
        //Split the window horizontal
        splitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT); 
        splitPane.setDividerLocation(DIVIDER_LOCATION);  
        splitPane.setTopComponent(leftPanel); 
        splitPane.setBottomComponent(rightPanel);             

        // Apply layouts and sizes before making visible
        pack();   
    }
}
