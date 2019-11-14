/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Jchess.Ui;

import Jchess.Models.Board;
import Jchess.Models.Tile;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.stream.Stream;

import javax.swing.*;
import Jchess.Core.Controller;
import javax.swing.border.LineBorder;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import Jchess.Core.Constants;
import Jchess.Core.Observer;
import Jchess.Ui.EventMapping.ViewerDelegate;

/**
 * Provides the viewing capabilities for the application
 * @author Wesley
 */
public class Viewer extends JPanel implements Observer {

    private static final long serialVersionUID = 3652924868907836716L;
    private final Controller controller;
    private final Board board;
    private final TileButton[][] buttonMatrix;
    private final ViewerDelegate delegate;
    private BoardFrame boardFrame;
    private JTextArea textArea;
    
    /**
     * Displays the game to the User.
     * @param c The controller.
     */
    public Viewer(Controller c)
    {
        this.controller = c;
        this.board = this.controller.gameBoard;
        this.buttonMatrix = new TileButton[board.rowCount][board.columnCount];
        this.delegate = c;
        
        // Display the board to the screen.
        makeWindow();  
    }

    /**
     * Launches the Board window.
     */
    private void makeWindow() {
        JPanel boardPanel = new JPanel();
        setUpBoardPanel(boardPanel);

        JPanel controlPanel = new JPanel();
        setUpControlPanel(controlPanel);

        this.boardFrame = new BoardFrame("Doki Doki Chess Club", boardPanel, controlPanel);
        this.boardFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.boardFrame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        this.boardFrame.setSize(1000, 1000);
        this.boardFrame.setVisible(true);
    }

    private void setUpBoardPanel(JPanel boardPanel) {
        boardPanel.setLayout(new GridLayout(Constants.ROW_COUNT, Constants.COLUMN_COUNT));

        board
            .getTiles()
            .map(tile -> makeTileButton(tile))
            .forEach(tileButton -> {
                boardPanel.add(tileButton);
                buttonMatrix[tileButton.getRow()][tileButton.getColumn()] = tileButton;
            });
    }

    private TileButton makeTileButton(Tile tile) {
        int rowIndex = tile.getPosition().getRow();
        int columnIndex = tile.getPosition().getColumn();

        int x = (Constants.TILE_DIMENSION * columnIndex);
        int y = (Constants.TILE_DIMENSION * (rowIndex + 1));

        TileButton tileButton = new TileButton(tile);
        tileButton.setBorderPainted(false);
        tileButton.setBorder(new LineBorder(Color.BLACK));
        tileButton.setOpaque(true);
        tileButton.setBounds(x, y, Constants.TILE_DIMENSION, Constants.TILE_DIMENSION);
        tileButton.addActionListener(this::handleClick);
        tileButton.syncWith(board);

        return tileButton;
    }

    /**
     * Gets all the TileButtons.
     * @return Stream of all TileButtons.
     */
    public Stream<TileButton> getTileButtons() {
        // Get all the Tiles into one "array".
        return Arrays.stream(buttonMatrix).flatMap(Arrays::stream);
    }

    /**
     * Repaints all TileButtons based on board state.
     * @param board The Board.
     */
    public void redrawBoard(Board board) {
        getTileButtons().forEach(tileButton -> tileButton.syncWith(board));
    }

    /**
     * Passes click command to Controller and redraws the Board.
     * @param e Click event to be handled.
     */
    private void handleClick(ActionEvent e) {
        Tile clickedTile = ((TileButton) e.getSource()).getInnerTile();

        // Pass off to controller.
        delegate.didClickTileButton(clickedTile);

        // Refresh Tile display according to new Board state.
        redrawBoard(board);

        // Force repaint to work around slow UI refreshes.
        boardFrame.repaint();
        boardFrame.revalidate();
    }

    /**
     * Sets adds in and modifies the contents of the control panel that is placed on the board
     * @param controlPanel  Control panel to be set up for the game.
     */
    private void setUpControlPanel(JPanel controlPanel) {

        //Set layout
        controlPanel.setLayout(new BorderLayout());
        
        // input/output components
        // scrollable text area 
        JScrollPane sPane = new JScrollPane(); 
        
        // text area that is inside the scrollPane
        this.textArea = new JTextArea();  
        
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

    /**
     * Updates current FEN.
     * @param currentFen FEN string to be updated.
     */
    @Override
    public void update(String currentFen) {
        String value = this.textArea.getText();
        if (value.length() > 1) {
            this.textArea.append("\n" + currentFen);
        } else{
            this.textArea.append(currentFen);
        }
    }

    /**
     * Hides the Board window, then disposes it.
     */
    public void destroyWindow() {
        boardFrame.setVisible(false);
        boardFrame.dispose();
    }
}
