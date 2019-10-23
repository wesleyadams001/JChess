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
import Pieces.King;
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
        
        gameBoard = Factory.makeBoard(lightPlayer, darkPlayer, Factory.readFENFromFile("starter.fen"));
        
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
        possibleMoves.forEach((pair) -> {
            gameBoard.getTile(pair).setHighlighted(true);
        });

        // Highlight all special moves for the clicked Tile.
        Vector<Pair> specialMoves = piece.specialMoves(gameBoard);
        specialMoves.forEach((pair) -> {
            gameBoard.getTile(pair).setSpecial(true);
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

        // Prevent move from going through if Player needs a Check-escaping move.
        if (simulatedBoard.getCurrentPlayer().isKingUnderAttack(simulatedBoard)) {
            return MoveResult.InvalidEscape;
        } else {
            // The Player's King is not in check, therefore it is a valid move; run instruction to make move.
            gameBoard.movePiece(transientPiece.getCurrentPosition(), destination.getPosition());

            // Special moves require more functionality.
            this.handleSpecialMove(transientPiece, destination);

            // This signals the end of the turn.
            gameBoard.switchPlayers(); 

            // Reset selection, highlights, etc.
            gameViewer.resetForRender();

            return determineMoveResult();
        }
    }

    /**
     * Handles special moves.
     * @param transientPiece The "from" Piece.
     * @param destination The destination Tile.
     */
    private void handleSpecialMove(Piece transientPiece, Tile destination) {
        if (destination.isSpecial()) {
            // See if it's a special move of the King.
            if (transientPiece.getPieceType() == PieceType.King) { 
                King king = (King) transientPiece;
                Pair kingLocation = king.getCurrentPosition();

                // In order to get to this part of the code, the rooks have to be in the starting position, so it's safe to hardcode.
                Piece rookLeft = gameBoard.getPiece(kingLocation.offsettingColumn(-1));
                Piece rookRight = gameBoard.getPiece(kingLocation.offsettingColumn(1));

                // If the Rook is to the left of the King, move the Rook to the right of the King; vise versa.
                if (king.isRookEligibleForCastling(rookLeft)) {
                    gameBoard.movePiece(rookLeft.getCurrentPosition(), kingLocation.offsettingColumn(1));
                } else if (king.isRookEligibleForCastling(rookRight)) {
                    gameBoard.movePiece(rookRight.getCurrentPosition(), kingLocation.offsettingColumn(-1));
                }
            }
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
        }

        return MoveResult.BetaMove;
    }

    /**
     * A post-move hook to update values after a move is made.
     * @param moveResult The result of the move.
     */
    private void didCommitMove(MoveResult moveResult) {
        switch (moveResult) {
            case InvalidEscape:
                System.out.println("=================== INVALID MOVE, you are putting yourself in check! =========================");
                break;
            case Check:
                showMessageDialog(null, gameBoard.getCurrentPlayer().getName() + " is in check.");
                break;
            case Checkmate:
                showMessageDialog(null, "Checkmate. " + gameBoard.getEnemyPlayer().getName() + " wins!");
                break;
            case BetaMove:
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