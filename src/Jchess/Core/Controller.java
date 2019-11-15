/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Jchess.Core;

import Jchess.Models.*;
import Jchess.Models.Tile;
import Jchess.Models.Factory;
import Jchess.Models.Pair;
import Jchess.Models.Board;
import Jchess.Enums.MoveResult;
import Jchess.Enums.PieceType;
import Jchess.Enums.ThemeColor;
import Jchess.Images.Images;
import Jchess.Models.Player;
import Jchess.Ui.Viewer;
import Jchess.Ui.EventMapping.StartMenuDelegate;
import Jchess.Ui.EventMapping.ViewerDelegate;
import javafx.application.Application;
import javafx.stage.Stage;
import Jchess.Ui.StartMenu;
import Jchess.Ui.Toaster;
import javax.swing.JOptionPane;
/**
 * Class that holds the controller.
 * @author Wesley
 */
public class Controller extends Application implements StartMenuDelegate, ViewerDelegate {

    Viewer gameViewer;

    /**
     * The active game Board instance.
     */
    public Board gameBoard;

    Controller() { }

    /**
     * Creates the Controller instance.
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Controller gameController = new Controller();
        gameController.launchStartMenu();
    }

    /**
     * Launches pregame window which prompts for player name and theme preference.
     */
    public void launchStartMenu() {
        StartMenu sm = new StartMenu(this);
        sm.setVisible(true);
    }
    
    /**
     * Handle the Start button event from the StartMenu.
     * @param FEN The FEN to load.
     */
    @Override
    public void didClickStartButton(String FEN) {
        StartMenu Open = new StartMenu(this);
        Open.setVisible(true);

        // Load images into memory. Used by Viewer to represent Pieces.
        Images.loadImages(UserPreferences.getTheme());

        // Use default FEN in case of an error.
        if (FEN == null || FEN.trim().isEmpty()) {
            FEN = Factory.readFENFromFile("starter.fen");
        }

        this.startGame(FEN);
    }
    
    /**
     * Sets up the Board and launches the Viewer from a loaded FEN.
     */
    private void startGame(String FEN) {
        Player lightPlayer = new Player(
            UserPreferences.getLightPlayerName(),
            ThemeColor.LightPiece
        );

        Player darkPlayer = new Player(
            UserPreferences.getDarkPlayerName(),
            ThemeColor.DarkPiece
        );

        // Make the board from the FEN.
        gameBoard = Factory.makeBoard(lightPlayer, darkPlayer, FEN);

        // Dispose of old boardFrame.
        if (gameViewer != null) {
            gameViewer.destroyWindow();
        }

        // Finally, launch the new game viewer.
        gameViewer = new Viewer(this);
        gameBoard.registerObserver(this.gameViewer);
    }

    /**
     * Handles Tile click events.
     * @param tile The Tile that was clicked.
     */
    @Override
    public void didClickTileButton(Tile tile)  {
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
     * Get a FEN representation of the active Board.
     * @return The FEN.
     */
    public String createFEN() {
        return gameBoard.createFEN();
    }
    
    /**
     * Starts the game with a loaded FEN.
     * @param fileName The file path to load the FEN from.
     */
    public void loadFromFile(String fileName) {
        startGame(Factory.readFENFromFile(fileName));
    }
    
    /**
     * Save a FEN to a particular file
     * @param FEN The FEN representation.
     * @param filePath The file path to save the FEN to.
     */
    public void saveToFile(String FEN, String filePath) {
        Factory.saveFENToFile(FEN, filePath);
    }

    /**
     * Handles a Tile selection intent.
     * @param piece 
     */
    private void handleSelection(Piece piece) {
        // Get Pieces in default state for move calculation.
        handleDeselection();

        // Only the clicked Tile is selected.
        gameBoard.selectPiece(piece);

        // Highlight all possible moves for the clicked Tile.
        piece
            .getPossibleMoves(gameBoard)
            .stream()
            .map(destination -> gameBoard.getTile(destination))
            .forEach(tile -> tile.setHighlighted(true));

        // Highlight all special moves for the clicked Tile.
        piece
            .getSpecialMoves(gameBoard)
            .stream()
            .map(destination -> gameBoard.getTile(destination))
            .forEach(tile -> tile.setSpecial(true));
    }

    /**
     * Handles a Tile move intent.
     * @param transientPiece The friendly Piece that the Player first clicked to initiate this move. Better known as the the "from"...
     * @param destination The destination Tile.
     * @return The move result.
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
            handleSpecialMove(transientPiece, destination);

            // This signals the end of the turn.
            gameBoard.switchPlayers(); 

            // Reset selection, highlights, etc.
            handleDeselection();

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
                Piece rookLeft = gameBoard.getPiece(kingLocation.offsettingColumn(-2));
                Piece rookRight = gameBoard.getPiece(kingLocation.offsettingColumn(1));
                
                // If the Rook is to the left of the King, move the Rook to the right of the King; vise versa.
                if (king.isRookEligibleForCastling(rookLeft)) {
                    gameBoard.movePiece(rookLeft.getCurrentPosition(), kingLocation.offsettingColumn(1));
                } else if (king.isRookEligibleForCastling(rookRight)) {
                    gameBoard.movePiece(rookRight.getCurrentPosition(), kingLocation.offsettingColumn(-1));
                }
            } else if (transientPiece.getPieceType() == PieceType.Pawn) {
                // Handle Pawn promotion.
                PieceType[] options = {
                    PieceType.Rook,
                    PieceType.Knight,
                    PieceType.Bishop,
                    PieceType.Queen,
                };
                
                int n = JOptionPane.showOptionDialog(
                    null,
                    null,
                    "Select piece to promote pawn to:",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]
                );
                
                PieceType playerChoice = options[n];
                Piece promotedPiece = null;
                
                // Promote based on selected Piece.
                switch(playerChoice) {
                    case Rook: promotedPiece = new Rook(gameBoard.getCurrentPlayer(), true); break;
                    case Knight: promotedPiece = new Knight(gameBoard.getCurrentPlayer()); break;
                    case Bishop: promotedPiece = new Bishop(gameBoard.getCurrentPlayer()); break;
                    case Queen: promotedPiece = new Queen(gameBoard.getCurrentPlayer()); break;
                    default: break;
                }
                
                // Set the promoted Piece.
                gameBoard.getTile(transientPiece.getCurrentPosition()).removePiece();
                destination.setPiece(promotedPiece);
            }
        }
    }

    /**
     * Handles a Tile deselection intent. Can also be used to clear selection, highlights, etc before/after moves.
     */
    private void handleDeselection() {
        // Deselect and de-highlight every Tile.
        gameBoard.resetTiles();

        // Sets Tile display back to default state.
        gameViewer.redrawBoard(gameBoard);
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
     * @param moveResult The move result.
     */
    private void didCommitMove(MoveResult moveResult) {
        switch (moveResult) {
            case InvalidEscape:
                Toaster.pop("INVALID MOVE, you are putting yourself in check!");
                break;
            case Check:
                Toaster.pop(gameBoard.getCurrentPlayer().getName() + " is in check.");
                break;
            case Checkmate:
                Toaster.pop("Checkmate. " + gameBoard.getEnemyPlayer().getName() + " wins!");
                break;
            case BetaMove:
                break;
        }
    }

    /**
     * Unused method for compliance with Application interface.
     * @param primaryStage Doesn't matter.
     */
    @Override
    public void start(Stage primaryStage) { }

}
