/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Board.Board;
import Board.Check;
import Enums.ThemeColor;
import Images.Images;
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
    private final Player player1;
    private final Player player2;
    
    private final Check check;
    private final Factory fen;
    
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
        player1 = new Player("One", ThemeColor.LightPiece);
        player2 = new Player("Two", ThemeColor.DarkPiece);
        
        // TODO: Make Factory generate Board instances.
        fen = new Factory();
        gameBoard = new Board(player1, player2, fen.loadFromFile("starter.fen"));
        
        check = new Check();
        
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

    private Boolean isSelection(Tile tile) {
        return (!tile.isHighlighted() &&
                tile.isOccupied());
    }
    
    private Boolean isMovement(Tile tile) {
        return (tile.isHighlighted() &&
                gameBoard.getTransientTile() != null);
    }

    private Boolean isCancel(Tile tile) {
        return (!tile.isHighlighted() &&
                !tile.isOccupied() &&
                gameBoard.getTransientTile() != null
                );
    }

    public void didClickTile(Tile clickedTile)  {
        if (isSelection(clickedTile)) {
            // The player selected a new "transient" Piece.
            handleSelection(clickedTile);
            
        } else if (isMovement(clickedTile)) {
            // The player selected a new location to move the "transient" Piece to.
            handleTurn(clickedTile);
            
        } else if (isCancel(clickedTile)) {
            // The player cancelled their selection.
            handleCancel(clickedTile);
            
        }
    }
    
    private void handleSelection(Tile clickedTile) {
        // Get Pieces in default state for move calculation.
        gameViewer.resetForRender();

        // Only the clicked Tile is selected.
        clickedTile.getPiece().setSelected(true);

        // Highlight all possible moves for the clicked Tile.
        Vector<Pair> possibleMoves = clickedTile.getPiece().getPossibleMoves(gameBoard);
        possibleMoves.forEach((pair) -> {
            gameBoard.getTile(pair).setHighlighted(true);
        });
    }
    
    private void handleTurn(Tile clickedTile) {
        // The friendly Piece that the player first clicked to intiate this move. Better known as the the "from"...
        Tile transientTile = gameBoard.getTransientTile();

        // The turn itself happens here. Then we pass it to the other player.
        gameBoard.movePiece(transientTile.getPosition(), clickedTile.getPosition());
        gameBoard.toggleCurrentPlayer();

        // Reset selection, highlights, etc.
        gameViewer.resetForRender();
    }

    private void handleCancel(Tile clickedTile) {
        // The player cancelled their selection, so we should reset selection, highlights, etc.
        gameViewer.resetForRender();
    }
}