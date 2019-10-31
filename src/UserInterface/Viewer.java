/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UserInterface;

import Board.Board;
import Board.Pair;
import Board.Tile;
import Enums.ThemeColor;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import Controller.Controller;
import javax.swing.border.LineBorder;
import UserInterface.EventMapping.TileDelegate;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import Controller.Constants;

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
    public BoardFrame boardFrame;
    private TileDelegate tileClickHandler = null;
    
    public Viewer(Controller c)
    {
        this.controller = c;
        this.board = this.controller.gameBoard;
        this.buttonMatrix = new JButton[board.rowCount][board.columnCount];
        // Display the board to the screen.
        setupFrame();  
    }    

    /**
     * Launches the Board window.
     */
    private void setupFrame() {
        int tileWidth = Constants.TILE_DIMENSION;
        int tileHeight = Constants.TILE_DIMENSION;

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

        this.boardFrame = new BoardFrame("Doki Doki Chess Club", boardPanel, controlPanel);
        this.boardFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.boardFrame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        this.boardFrame.setSize(1000, 1000);
        this.boardFrame.setVisible(true);
    }

    /**
     * Refreshes Tile display according to Board state.
     */
    public void updateButtons() {
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
        } else if (tile.isSpecial()) {
            // Highligh special moves.
            tileButton.setBorderPainted(true);
            tileButton.setBorder(new LineBorder(tile.getColor(), 5));
            tileButton.setBackground(ThemeColor.Special.getColor());
        } else if (tile.isHighlighted()) {
            // Highlight possible moves.
            tileButton.setBorderPainted(true);
            tileButton.setBorder(new LineBorder(tile.getColor(), 5));
            tileButton.setBackground(tile.isOccupied() ? ThemeColor.Enemy.getColor() : ThemeColor.Friendly.getColor());
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

    /**
     * Sets adds in and modifies the contents of the control panel that is placed on the board
     * @param controlPanel 
     */
    private void setUpControlPanel(JPanel controlPanel) {

        //Set layout
        controlPanel.setLayout(new BorderLayout());
        
        // input/output components
        // scrollable text area 
        JScrollPane sPane = new JScrollPane(); 
        
        // text area that is inside the scrollPane
        JTextArea textArea = new JTextArea();  
        
        //Add text area
        controlPanel.add(sPane.add(textArea), BorderLayout.CENTER);
        
        //Create the menu
        ChessMenu menu = new ChessMenu(this.controller, this);
        
        //set target text area
        menu.setTextArea(textArea);
        
        //Add menu to the control panel
        controlPanel.add(menu, BorderLayout.NORTH);

        //set the control panel to visible
        controlPanel.setVisible(true);
    }
}
