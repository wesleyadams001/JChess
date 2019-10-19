/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Board.Board;
import Enums.ThemeColor;
import Images.Images;
import Pieces.Piece;
import Player.Player;
import Player.Viewer;
import Board.Factory;
import Board.Pair;
import Board.Tile;
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
    private final Player lightPlayer;
    private final Player darkPlayer;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Controller c = new Controller();
    }
    
    Controller() {
        // Read Piece images into memory.
        Images pieceAssets = new Images();
        pieceAssets.loadImages();
        
        // TODO: Use text fields to set player names.
        lightPlayer = new Player("Light", ThemeColor.LightPiece);
        darkPlayer = new Player("Dark", ThemeColor.DarkPiece);
        
        gameBoard = Factory.makeBoard(lightPlayer, darkPlayer, Factory.readFENFromFile("starter.fen"));
        
        // Finally, launch the game viewer.
        gameViewer = new Viewer(this);
        gameViewer.setTileClickHandler(this::didClickTile);
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

    private void handleTurn(Tile clickedTile) {
        // The friendly Piece that the player first clicked to intiate this move. Better known as the the "from"...
        Piece transientPiece = gameBoard.getSelectedPiece();

        // The turn itself happens here. We used clickedTile's position because it might not be occupied.
        gameBoard.movePiece(transientPiece.getCurrentPosition(), clickedTile.getPosition());
        gameBoard.switchPlayers();

        // Reset selection, highlights, etc.
        gameViewer.resetForRender();
    }

    private void handleDeselection() {
        // The player cancelled their selection, so we should reset selection, highlights, etc.
        gameViewer.resetForRender();
    }
}