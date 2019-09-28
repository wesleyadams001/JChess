/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Player;

import Board.Board;
import Board.Pair;
import Board.Tile;
import Images.Images;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import Controller.Controller;
import java.awt.Container;
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
    private final Controller controller;
    private static JFrame frame; // The frame on which the Board is displayed
    private static JFrame info;
    private String p1Name;
    private String p2Name;
    private int p1Score;
    private int p2Score;
    private Vector<Pair>moves;
    private final JButton[][] buttonMatrix;
    private final Board board;
    
    public Viewer(Controller c)
    {
        //initializeBoard();
        controller = c;

        board = this.controller.gameBoard;
        buttonMatrix = new JButton[board.rowCount][board.columnCount];

        // Display the board to the screen.
        setupFrame();

        //get Names from players
        // setupNames();
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
        info.setVisible(true);
    }

    private void setupFrame() {
        int tileWidth = 100;
        int tileHeight = 100;

        JFrame boardFrame = new JFrame("Dokie, Dokie, Chess Club");
        boardFrame.setLocationRelativeTo(this.info);

        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(null);

        for (int i = 0; i < buttonMatrix.length; i++) {
            for (int j = 0; j < buttonMatrix[i].length; j++) {
                int xVal = 40 + (tileWidth * j);
                int yVal = (tileHeight * (i + 1));

                JButton tileButton = new JButton();
                tileButton.setBorderPainted(false);
                tileButton.setBorder(new LineBorder(Color.BLACK));
                tileButton.setOpaque(true);
                tileButton.setBounds(xVal, yVal, tileWidth, tileHeight);
                tileButton.setName(i + "," + j);

                tileButton.addActionListener((ActionEvent e) -> {
                    handleClick(e);
                });

                boardPanel.add(tileButton);

                buttonMatrix[i][j] = tileButton;
            }
        }

        updateButtons();

        boardFrame.add(boardPanel);
        boardFrame.setDefaultCloseOperation(3);
        boardFrame.setSize(1000, 1000);
        boardFrame.setVisible(true);
    }

    private void updateButtons() {
        for (int i = 0; i < buttonMatrix.length; i++) {
            for (int j = 0; j < buttonMatrix[i].length; j++) {
                Tile tile = board.getTile(new Pair(i, j));
                JButton tileButton = buttonMatrix[i][j];

                if ((i + j) % 2 == 0) {
                    tileButton.setBackground(new Color(222, 184, 135));
                } else {
                    tileButton.setBackground(new Color(139, 69, 19));
                }

                if (tile.isOccupied()) {
                    tileButton.setIcon(tile.getPiece().getImage());

                    if (tile.getPiece().isSelected()) {
                        tileButton.setBackground(Color.blue);
                    }
                }

                if (tile.isHighlighted()) {
                    tileButton.setBackground(Color.red);
                }
            }
        }
    }

    private void resetForRender() {
        for (Tile[] row : board.getMatrix()) {
            for (Tile tile : row) {
                tile.setHighlighted(false);

                if (tile.isOccupied()) {
                    tile.getPiece().setSelected(false);
                }
            }
        }
    }

    private void handleClick(ActionEvent e) {
        resetForRender();

        JButton target = (JButton) e.getSource();

        String tileName = target.getName();

        int x = Character.getNumericValue(tileName.charAt(0));
        int y = Character.getNumericValue(tileName.charAt(2));

        Tile tile = board.getTile(new Pair(x, y));

        if (tile.isOccupied()) {
            tile.getPiece().setSelected(true);

            Vector<Pair> possibleMoves = tile.getPiece().getPossibleMoves(board);

            possibleMoves.forEach((pair) -> {
                board.getTile(pair).setHighlighted(true);
            });
        }

        updateButtons();
    }
}
