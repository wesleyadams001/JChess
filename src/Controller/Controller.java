/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Board.*;
import Enums.MoveResult;
import Enums.PieceType;
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
import static javax.swing.JOptionPane.showMessageDialog;

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
        
        gameBoard = Factory.makeBoard(lightPlayer, darkPlayer, Factory.readFENFromFile("checkmate.fen"));
        
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
                MoveResult moveResult = handleTurn(gameBoard.getTransientPiece(), tile);
                didCommitMove(moveResult);
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
        Vector<Pair> special = piece.specialMoves(gameBoard);
        special.forEach((pair) -> {
            gameBoard.getTile(pair).setSpecial(true);
        });
        possibleMoves.forEach((pair) -> {
            gameBoard.getTile(pair).setHighlighted(true);
        });
        
        
    }

    /**
     * Handles a Tile move intent.
     * @param origin The friendly Piece that the Player first clicked to initiate this move. Better known as the the "from"...
     * @param destination The destination Tile.
     */
    private MoveResult handleTurn(Piece transientPiece, Tile destination) {
        // Create a temp state of the gameBoard to validate the move, 
        // The move is made on the temp board, then we test if the player's king is still or becomes in check.
        Board simulatedBoard = gameBoard.simulatedWithMove(transientPiece, destination);
        Player simulatedCurrentPlayer = simulatedBoard.getCurrentPlayer();

        if (simulatedCurrentPlayer.isKingUnderAttack(simulatedBoard)) {
            return MoveResult.InvalidCheck;
        } else {
            // The players king is not in check, therefore it is a valid move, run instruction to make move
            // The turn itself happens here. We used clickedTile's position because it might not be occupied.
            gameBoard.movePiece(transientPiece.getCurrentPosition(), destination.getPosition());

            // special moves require more functionality
            if (destination.isSpecial()) {
                // see if its a special move of the king
                if (transientPiece.getPieceType() == PieceType.King) { 
                    // save the pair of the king and what should be the location of the rooks
                    // in order to get to this part of the code, the rooks have to be in the starting position, so its safe to hardcode
                    Pair king = gameBoard.getCurrentPlayer().getLocationOfKing();
                    Piece rookLeft = gameBoard.getPiece(new Pair(king.getRow(), king.getColumn()-1));
                    Piece rookRight = gameBoard.getPiece(new Pair(king.getRow(), king.getColumn()+1));

                    // if the rook is to the left of the king, move the rook to the right of the king, vise versa
                    if (rookLeft != null && rookLeft.getPieceType() == PieceType.Rook) { 
                        gameBoard.movePiece(rookLeft.getCurrentPosition(), new Pair(king.getRow(), king.getColumn()+1));
                    } else if (rookRight != null && rookRight.getPieceType() == PieceType.Rook) {
                        gameBoard.movePiece(rookRight.getCurrentPosition(), new Pair(king.getRow(), king.getColumn()-1));
                    }
                }
            }

            gameBoard.switchPlayers(); 

            // Reset selection, highlights, etc.
            gameViewer.resetForRender();

            return determineMoveResult();
        }
    }

    /**
     * Handles a Tile deselection intent.
     */
    private void handleDeselection() {
        // The player cancelled their selection, so we should reset selection, highlights, etc.
        gameViewer.resetForRender();
    }

    /**
     * Determines the result of a move with heuristics.
     * @return The move result.
     */
    private MoveResult determineMoveResult() {
        Player currentPlayer = gameBoard.getCurrentPlayer();

        if (currentPlayer.isKingUnderAttack(gameBoard)) {
            if (currentPlayer.canKingBeSaved(gameBoard)) {
                return MoveResult.Check;
            } else {
                return MoveResult.Checkmate;
            }
        }else{
            System.out.print("Player is not in check");
        }

        return MoveResult.Benign;
    }

    /**
     * A post-move hook to update values after a move is made.
     * @param moveResult The result of the move.
     */
    private void didCommitMove(MoveResult moveResult) {
        switch (moveResult) {
            case InvalidCheck:
                System.out.println("=================== INVALID MOVE, you are putting yourself in check! =========================");
                break;
            case Check:
                showMessageDialog(null, gameBoard.getCurrentPlayer().getName() + " is in check.");
                break;
            case Checkmate:
                showMessageDialog(null, "Checkmate. " + gameBoard.getEnemyPlayer().getName() + " wins!");
                break;
            case Benign:
                break;
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