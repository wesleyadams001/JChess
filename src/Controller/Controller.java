/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Board.*;
import Enums.ThemeColor;
import Images.Images;
import Pieces.Piece;
import Player.Player;
import Player.Viewer;
import java.util.Vector;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author Wesley
 */
public class Controller extends Application {

    Viewer gameViewer; // For user interaction
    public Board gameBoard;

    Controller() { }

    /**
     * Creates the Controller instance.
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Controller gameController = new Controller();

        // It's important to load the images before the Pieces are initiated.
        gameController.loadImages();
        gameController.startGame();
    }

    /**
     * Read Piece images into memory.
     */
    private void loadImages() {
        Images pieceAssets = new Images();
        pieceAssets.loadImages();
    }

    /**
     * Sets up the Board and launches the Viewer.
     */
    private void startGame() {
        // TODO: Use text fields to set player names.
        Player lightPlayer = new Player("Light", ThemeColor.LightPiece);
        Player darkPlayer = new Player("Dark", ThemeColor.DarkPiece);
        
        gameBoard = Factory.makeBoard(lightPlayer, darkPlayer, Factory.readFENFromFile("animehoes.fen"));
        
        // Finally, launch the game viewer.
        gameViewer = new Viewer(this);
        gameViewer.setTileClickHandler(this::didClickTile);
    }

    /**
     * Handles Tile click events.
     * @param tile 
     */
    public void didClickTile(Tile tile)  {
        switch (gameBoard.determineClickIntent(tile)) {
            case Selection:
                // The player selected a new "transient" Piece.
                handleSelection(tile.getPiece());
                break;
            case Move:
                // The player selected a new location to move the "transient" Piece to.
                handleTurn(tile);
                break;
            case Deselection:
                // The player cancelled their selection.
                handleDeselection();
                break;
            case Other:
                // Something bizarre happened.
                break;
        }
    }

    /**
     * Handles a Tile selection intent.
     * @param piece 
     */
    private void handleSelection(Piece piece) {
        // Get Pieces in default state for move calculation.
        gameViewer.resetForRender();

        // Only the clicked Tile is selected.
        gameBoard.selectPiece(piece);

        // Highlight all possible moves for the clicked Tile.
        Vector<Pair> possibleMoves = piece.getPossibleMoves(gameBoard);
        possibleMoves.forEach((pair) -> {
            gameBoard.getTile(pair).setHighlighted(true);
        });
    }

    /**
     * Handles a Tile move intent.
     * @param clickedTile 
     */
    private void handleTurn(Tile clickedTile) {
        /** Create a temp state of the gameBoard to validate the move, 
         *  The friendly Piece that the player first clicked to initiate this move. Better known as the the "from" ...
         *  The move is made on the temp board, then we test if the player's king is still or becomes in check
         */
        Board temp = Factory.cloneBoard(gameBoard);
        temp.setCurrentPlayer(gameBoard.getCurrentPlayer() == gameBoard.getLightPlayer() ? temp.getLightPlayer() : temp.getDarkPlayer());
        temp.setEnemyPlayer(gameBoard.getCurrentPlayer() == gameBoard.getLightPlayer() ? temp.getDarkPlayer() : temp.getLightPlayer());
        Piece transientPiece = gameBoard.getSelectedPiece();
        
        temp.movePiece(transientPiece.getCurrentPosition(), clickedTile.getPosition());
        
        if(!Check.pairUnderAttack(temp.getCurrentPlayer().getLocationOfKing(), temp, temp.getEnemyPlayer())){
            // The players king is not in check, therefore it is a valid move, run instruction to make move
            // The turn itself happens here. We used clickedTile's position because it might not be occupied.
            gameBoard.movePiece(transientPiece.getCurrentPosition(), clickedTile.getPosition());
            gameBoard.switchPlayers(); 
            newTurn();
        }else{
            System.out.println("=================== INVALID MOVE, you are putting yourself in check! =========================");
        }
        
        

        // Reset selection, highlights, etc.
        gameViewer.resetForRender();
    }

    /**
     * Handles a Tile deselection intent.
     */
    private void handleDeselection() {
        // The player cancelled their selection, so we should reset selection, highlights, etc.
        gameViewer.resetForRender();
    }
    /**
     * Handles switching players and updating the ' check / mate ' state of the new player
     */
    private void newTurn(){
        if(Check.pairUnderAttack(gameBoard.getCurrentPlayer().getLocationOfKing(), gameBoard, gameBoard.getEnemyPlayer())){
            if(Check.kingCanBeSaved(gameBoard.getCurrentPlayer().getLocationOfKing(), gameBoard, gameBoard.getEnemyPlayer())){
                System.out.println("=================== Current Player is in CHECKMATE =========================");
            }else{
                System.out.println("=================== Current Player is in CHECK =========================");
            }
        }
    }
    
    @Override
    public void start(Stage primaryStage) {
        Button btn = new Button();
        btn.setText("Say 'Hello World'");
        btn.setOnAction((ActionEvent event) -> {
            System.out.println("Hello World!");
        });
        
        StackPane root = new StackPane();
        root.getChildren().add(btn);
        
        Scene scene = new Scene(root, 300, 250);
        
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
}