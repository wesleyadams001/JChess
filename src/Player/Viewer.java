/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Player;

import Board.Board;
import Board.Pair;
import Board.Tile;
import Enums.ThemeColor;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import Controller.Controller;
import javax.swing.border.LineBorder;
import Player.EventMapping.TileDelegate;
import java.awt.FlowLayout;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

// import java.awt.Container;

/**
 * Provides the viewing capabilities for the application
 * @author Wesley
 */
public class Viewer extends JPanel{
    
    public int mouseX, mouseY;//Mouse position
    public boolean pause;//if game is paused
    
    private final Controller controller;
    private final Board board;
    private final JButton[][] buttonMatrix;
    public JFrame boardFrame;

    /*
    private int xDimensions, yDimensions;
    private static JFrame frame; // The frame on which the Board is displayed
    private static JFrame info;
    private String p1Name;
    private String p2Name;
    private int p1Score;
    private int p2Score;
    private Vector<Pair>moves;
    */
    
    private TileDelegate tileClickHandler = null;
    
    public Viewer(Controller c)
    {
        controller = c;

        board = this.controller.gameBoard;
        buttonMatrix = new JButton[board.rowCount][board.columnCount];

        // Display the board to the screen.
        setupFrame();
        
    }
    
    

    /**
     * Launches the Board window.
     */
    private void setupFrame() {
        int tileWidth = 90;
        int tileHeight = 90;

        JPanel boardPanel = new JPanel();
        JPanel controlPanel = new JPanel();
        setUpControlPanel(controlPanel);
        
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

                // Store coordinates for click handler.
                tileButton.putClientProperty("rank", i);
                tileButton.putClientProperty("file", j);

                tileButton.addActionListener((ActionEvent e) -> {
                    handleClick(e);
                });

                boardPanel.add(tileButton);

                buttonMatrix[i][j] = tileButton;
            }
        }
        updateButtons();

        BoardFrame bf = new BoardFrame("Doki Doki Chess Club", boardPanel, controlPanel);
        bf.setDefaultCloseOperation(3);
        bf.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        bf.setSize(1000, 1000);
        bf.setVisible(true);
    }

    /**
     * Refreshes Tile display according to Board state.
     */
    private void updateButtons() {
        for (Tile[] row : board.getMatrix()) {
            for (Tile tile : row) {
                Pair tilePosition = tile.getPosition();
                JButton tileButton = buttonMatrix[tilePosition.getRow()][tilePosition.getColumn()];
                paintTileButton(tile, tileButton);
                updateTileButtonEnabled(tile, tileButton);
            }
        }
    }

    /**
     * Re-paints Tiles according to selection, highlight, etc.
     * @param tile
     * @param tileButton 
     */
    private void paintTileButton(Tile tile, JButton tileButton) {
        tileButton.setBackground(tile.getColor());
        tileButton.setIcon(tile.isOccupied() ? tile.getPiece().getImage() : null);
        tileButton.setDisabledIcon(tile.isOccupied() ? tile.getPiece().getImage() : null);

        if (tile.isOccupied() && tile.getPiece().isSelected()) {
            // Highlight currently selected Tile.
            tileButton.setBorderPainted(true);
            tileButton.setBorder(new LineBorder(tile.getColor(), 5));
            tileButton.setBackground(ThemeColor.Friendly.getColor());
        } else if (tile.isHighlighted()) {
            // Highlight possible moves.
            tileButton.setBorderPainted(true);
            tileButton.setBorder(new LineBorder(tile.getColor(), 5));
            tileButton.setBackground(tile.isOccupied() ? ThemeColor.Enemy.getColor() : ThemeColor.Friendly.getColor());
        } else if (tile.isSpecial()) {
            // Highligh special moves.
            tileButton.setBorderPainted(true);
            tileButton.setBorder(new LineBorder(tile.getColor(), 5));
            tileButton.setBackground(ThemeColor.Special.getColor());
        }
    }

    /**
     * Enables/Disables Tile clicks based on current Player, highlighted, etc.
     * @param tile
     * @param tileButton 
     */
    private void updateTileButtonEnabled(Tile tile, JButton tileButton) {
        if (tile.isOccupied() && tile.getPiece().getPlayer() == board.getCurrentPlayer()) {
            // This Tile is in the current player's possession. 
            tileButton.setEnabled(true);
        } else if (tile.isHighlighted()) {
            // This Tile is a possible move.
            tileButton.setEnabled(true);
        } else if (!tile.isOccupied()){
            // This Tile is an empty Tile.
            tileButton.setEnabled(true);
        } else {
            // This could holds an enemy Piece that isn't attackable.
            tileButton.setEnabled(false);
        }
    }

    /**
     * Sets Tile display back to default state.
     */
    public void resetForRender() {
        for (Tile[] row : board.getMatrix()) {
            for (Tile tile : row) {
                // Reset Tile presentation.
                JButton tileButton = buttonMatrix[tile.getPosition().getRow()][tile.getPosition().getColumn()];
                tileButton.setBorderPainted(false);
                tileButton.setBackground(tile.getColor());

                // Reset Tile state.
                tile.setHighlighted(false);
                tile.setSpecial(false);
                if (tile.isOccupied()) {
                    board.deselectPiece(tile.getPiece());
                }
            }
        }
    }
    
    /**
     * Maps the Controller's click handler to the Tiles.
     * @param handler 
     */
    public void setTileClickHandler(TileDelegate handler) {
        this.tileClickHandler = handler;
    }

    /**
     * Passes click command to Controller and redraws the Board.
     * @param e 
     */
    private void handleClick(ActionEvent e) {
        JButton target = (JButton) e.getSource();

        int x = (int) target.getClientProperty("rank");
        int y = (int) target.getClientProperty("file");
        
        Tile clickedTile = board.getTile(new Pair(x, y));
        
        this.tileClickHandler.didClick(clickedTile);
        
        updateButtons();
        boardFrame.repaint();
        boardFrame.revalidate();
    }

    private void setUpControlPanel(JPanel controlPanel) {
        JFileChooser fc = new JFileChooser();
        //controlPanel.add(fc);

        JTextArea tarea = new JTextArea(10, 10);

        JButton readButton = new JButton("OPEN FILE");
        readButton.addActionListener(ev -> {
          int returnVal = fc.showOpenDialog(controlPanel);
          if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            try {
              BufferedReader input = new BufferedReader(new InputStreamReader(
                  new FileInputStream(file)));
              tarea.read(input, "READING FILE :-)");
            } catch (Exception e) {
              e.printStackTrace();
            }
          } else {
            System.out.println("Operation is CANCELLED :(");
          }
        });

        controlPanel.add(tarea, BorderLayout.CENTER);
        controlPanel.add(readButton, BorderLayout.PAGE_END);

        controlPanel.setVisible(true);
    }
}
