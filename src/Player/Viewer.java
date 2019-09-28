/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Player;

import Board.Pair;
import Controller.Controller;
import Images.Images;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import Controller.Controller;
import Images.Images;
import java.awt.Container;
import java.awt.event.ActionListener;
import java.util.Vector;
import javax.swing.border.LineBorder;

/**
 * Provides the viewing capabilities for the application
 * @author Wesley
 */
public class Viewer extends JPanel{
    
    public int mouseX, mouseY;//Mouse position
    public boolean pause;//if game is paused
    private Images pieces;//Image class
    private int xDimensions, yDimensions;
    private Controller controller;
    private static JFrame frame; // The frame on which the board is displayed
    private static JFrame info;
    private String p1Name;
    private String p2Name;
    private int p1Score;
    private int p2Score;
    private Vector<Pair>moves;
    
    public Viewer(Controller c)
    {
        //initializeBoard();
        controller = c;

        // Display the board to the screen.
        setupFrame();

        //get Names from players
        setupNames();
        
    }
    
    private void setupNames() {
        info = new JFrame("Enter Names");
        info.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        info.setLocationRelativeTo(null);
        JLabel p1 = new JLabel("Player 1: ");
        final JLabel p2 = new JLabel("Player 2: ");

        final JTextField player1 = new JTextField("Name", 10);
        final JTextField player2 = new JTextField("Name", 10);
        
        JButton btn = new JButton();
        
        info.getContentPane().add(p1);
        info.getContentPane().add(p2);
        
        
        Container content = info.getContentPane();
        SpringLayout layout = new SpringLayout();
        content.setLayout(layout);

        Dimension s1 = player1.getPreferredSize();
        Dimension s2 = player2.getPreferredSize();
        player1.setBounds(100, 20 , s1.width, s1.height);
        player2.setBounds(100, 50, s2.width, s2.height);
        content.add(p1);
        content.add(player1);
        content.add(p2);
        content.add(player2);


        SpringLayout.Constraints  labelCons =
                layout.getConstraints(p1);
        labelCons.setX(Spring.constant(5));
        labelCons.setY(Spring.constant(10));

        //Adjust text field so that it is at
        //Right edge
        SpringLayout.Constraints textFieldCons =
                layout.getConstraints(player1);
        textFieldCons.setX(Spring.sum(Spring.constant(5),
                labelCons.getConstraint(SpringLayout.EAST)));
        textFieldCons.setY(Spring.constant(10));

        SpringLayout.Constraints  labelCon =
                layout.getConstraints(p2);
        labelCon.setX(Spring.constant(5));
        labelCon.setY(Spring.constant(50));

        //Adjust the text field so that it is at
        //Right edge
        SpringLayout.Constraints textFieldCon =
                layout.getConstraints(player2);
        textFieldCon.setX(Spring.sum(Spring.constant(5),
                labelCons.getConstraint(SpringLayout.EAST)));
        textFieldCon.setY(Spring.constant(50));


        JButton submit = new JButton("Start");
        content.add(submit);

        SpringLayout.Constraints buttonCon =
                layout.getConstraints(submit);
       buttonCon.setX(Spring.sum(Spring.constant(10),
                labelCons.getConstraint(SpringLayout.EAST)));
        buttonCon.setY(Spring.constant(90));
          
        info.pack();
        // info.setVisible(true);

        JFrame f = new JFrame();
        JButton button;

        JPanel p = new JPanel();
        p.setLayout(null);
        
        Images img = new Images();
        
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Color col;
                
                int add = 0;
                if (i % 2 == 0) {
                    add = 1;
                } else {
                    add = 0;
                }

                if ((j + i) % 2 == 0) {
                    col = Color.white;
                } else {
                    col = Color.black;
                }
                
                button = new JButton();
                
                if (j % 2 == 0) {
                    button.setBackground(col);
                } else {
                    button.setBackground(col);
                }

                button.setBorderPainted(false);
                button.setBorder(new LineBorder(Color.BLACK));
                button.setOpaque(true);
                button.setIcon(img.blackBerolinaImage);
                
                int xVal = 40;
            
                if (j > 0) {
                    xVal = xVal + (100 * j);
                }

                int yVal = (100 * (i + 1));

                button.setBounds(xVal, yVal, 100, 100);

                p.add(button);
            }
        }
        

        f.add(p);
        f.setDefaultCloseOperation(3);
        f.setSize(1000, 1000);
        f.setVisible(true);
       
    }
    
    private void setupFrame(){
        frame = new JFrame("Dokie, Dokie, Chess Club");
        frame.setLocationRelativeTo(this.info);
        
        
    }
}
