/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Jchess.Models;

import Jchess.Enums.ClickType;
import Jchess.Enums.PieceType;
import Jchess.Core.Constants;
import Jchess.Core.Observer;
import Jchess.Core.Subject;
import java.util.ArrayList;
import java.util.stream.Stream;

/**
 * The base chess board class
 * @author nehalpatel
 */
public class Board implements Subject {

    /*
    * A strin to hold the current FEN for the board
    */
    private String currentFen;
    
    /**
     * Represents an array list of observer objects
     */
    private final ArrayList<Observer> observerList;
    
    /**
     * Represents the Player currently playing.
     */
    private Player currentPlayer;

    /**
     * Represents the Player waiting for their turn.
     */
    private Player enemyPlayer;

    /**
     * Represents the Player in charge of the light Pieces.
     */
    private final Player lightPlayer;

    /**
     * Represents the Player in charge of the dark Pieces.
     */
    private final Player darkPlayer;

    /**
     * The number of rows for this Board.
     */
    public final int rowCount = Constants.ROW_COUNT;

    /**
     * The number of columns for this Board.
     */
    public final int columnCount = Constants.COLUMN_COUNT;

    /**
     * Holds all the Pieces.
     */
    private final Tile[][] matrix;

    /**
     * Represents the currently selected Piece.
     */
    private Piece selectedPiece;

    /**
     *
     * @param one
     * @param two
     */
    public Board(Player one, Player two) {
        this.currentPlayer = one;
        this.enemyPlayer = two;

        this.lightPlayer = one;
        this.darkPlayer = two;
        this.observerList = new ArrayList<>(); 
        this.matrix = new Tile[rowCount][columnCount];

        // Populate matrix with empty Tiles.
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
                Tile tile = new Tile(new Pair(rowIndex, columnIndex));
                this.matrix[rowIndex][columnIndex] = tile;
            }
        }

        this.currentFen = Factory.serializeBoard(this);
    }

    /**
     * Returns the active Player. 
     * @return 
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Updates the active Player.
     * @param currentPlayer 
     */
    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    /**
     * Returns the inactive Player.
     * @return 
     */
    public Player getEnemyPlayer() {
        return this.enemyPlayer;
    }
    
    /**
     * Updates the inactive Player.
     * @param enemy 
     */
    public void setEnemyPlayer(Player enemy) {
        this.enemyPlayer = enemy;
    }

    /**
     * Returns the Player in control of the light Pieces.
     * @return 
     */
    public Player getLightPlayer() {
        return lightPlayer;
    }
    
    /**
     * Generates a fen from the current gameboard
     * @return
     */
    public String createFen(){
        String fen = Factory.serializeBoard(this);
        this.currentFen = fen;
        return fen;
    }

    /**
     * Returns the Player in control of the dark Pieces.
     * @return 
     */
    public Player getDarkPlayer() {
        return darkPlayer;
    }

    /**
     * Switches agency to the other player.
     */
    public void switchPlayers() {
        setCurrentPlayer(getCurrentPlayer() == getLightPlayer() ? getDarkPlayer() : getLightPlayer());
        setEnemyPlayer(getEnemyPlayer() == getLightPlayer() ? getDarkPlayer() : getLightPlayer());
    }

    /**
     * Determines if a Pair is within the bounds of the Board.
     * @param pair
     * @return 
     */
    public boolean isValidPair(Pair pair) {
        int rowIndex = pair.getRow();
        int columnIndex = pair.getColumn();

        if ((rowIndex < 0) || (columnIndex < 0)) {
            return false;
        }

        return !((rowIndex > rowCount - 1) || (columnIndex > columnCount - 1));
    }

    /**
     * Search for and retrieve a Tile.
     * @param position A pair of coordinates with which to search for a Tile.
     * @return The desired Tile if found, else null.
     */
    public Tile getTile(Pair position) {
        if (isValidPair(position)) {
            return matrix[position.getRow()][position.getColumn()];
        }

        return null;
    }

    /**
     * Search for and retrieve a Piece.
     * @param position A pair of coordinates with which to search for a Piece.
     * @return The desired Piece if found, else null.
     */
    public Piece getPiece(Pair position) {
        if (isValidPair(position)) {
            Tile tile = getTile(position);
            
            if (tile != null) {
                return tile.getPiece();
            }
        }

        return null;
    }

    /**
     * Get the Player's selected Piece.
     * @return The first Piece found with isSelected() being true, null otherwise.
     */
    public Piece getTransientPiece() {
        return this.selectedPiece;
    }

    /**
     * Marks a Piece as selected.
     * @param piece 
     */
    public void selectPiece(Piece piece) {
        piece.setSelected(true);
        this.selectedPiece = piece;
    }

    /**
     * Deselects a Piece.
     * @param piece 
     */
    public void deselectPiece(Piece piece) {
        piece.setSelected(false);
        this.selectedPiece = null;
    }

    /**
     * Determines if the Tile was clicked in an attempt to be selected.
     * @param tile
     * @return 
     */
    private Boolean clickIsSelection(Tile tile) {
        return (!tile.isHighlighted() &&
                tile.isOccupied());
    }
    
    /**
     * Determines if the Tile was clicked in an attempt to commit a move.
     * @param tile
     * @return 
     */
    private Boolean clickIsMove(Tile tile) {
        return ((tile.isHighlighted() || tile.isSpecial()) &&
                this.getTransientPiece() != null);
    }

    /**
     * Determines if the Tile was clicked in an attempt to cancel a previous selection.
     * @param tile
     * @return 
     */
    private Boolean clickIsDeselection(Tile tile) {
        return (!tile.isHighlighted() &&
                !tile.isOccupied()
                );
    }

    /**
     * Determines the user's intent in clicking the Tile.
     * @param tile
     * @return 
     */
    public ClickType determineClickIntent(Tile tile) {
        if (clickIsSelection(tile)) {
            return ClickType.Selection;
        } else if (clickIsMove(tile)) {
            return ClickType.Move;
        } else if (clickIsDeselection(tile)) {
            return ClickType.Deselection;
        }
        
        return ClickType.Other;
    }

    /**
     * Returns the 2D array which holds all the Pieces.
     * @return
     */
    public Tile[][] getMatrix() {
        return this.matrix;
    }

    /**
     * Move a Piece to another Tile.
     * 
     * @param from
     * @param to
     */
    public void movePiece(Pair from, Pair to) {
        Tile toTile = getTile(to);
        if (toTile.isOccupied()) {
            // throw new Exception("The arrival Tile isn't empty.");
            //removes the piece at destination pair
            toTile.removePiece();
        }

        Tile fromTile = getTile(from);
        if (fromTile.isOccupied()) {
            // Useful for tracking special moves for Pawn, etc.
            fromTile.getPiece().setHasTakenFirstMove(true);
        } else {
            // throw new Exception("The departing Piece doesn't exist.");
        }
        //removes moving piece from orig pair
        Piece fromPiece = fromTile.removePiece();
        //sets piece to destination pair
        toTile.setPiece(fromPiece);
        
        // =================
        // Move is finished.
        // =================
        
        // If King was moved, update King location tracker attribute of Player class.
        if (toTile.getPiece().getPieceType() == PieceType.King){
            getCurrentPlayer().setLocationOfKing(toTile.getPosition());
        }
        
        createFen();
        notifyObservers();
    }
    
    /**
     * Determines if a Pair can be attacked by a given Player.
     * @param targetPosition A Pair to test.
     * @param opponentPlayer The Player to simulate attacks with.
     * @return
     */
    public boolean isPairUnderAttack(Pair targetPosition, Player opponentPlayer) {
        // Create a collection of all the opponent's possible moves.
        Stream<Pair> opponentMoves = opponentPlayer.getPossibleMoves(this);
        // Resolves to true if any of the opponent's possible moves == targetPosition.
        return opponentMoves.anyMatch(possibleDestination -> possibleDestination == targetPosition);
    }

    /**
     * Returns a copy of Board with the move applied.
     * @param from The Piece to move.
     * @param to The Tile to move the Piece to.
     * @return The simulated Board.
     */
    public Board simulatedWithMove(Piece from, Tile to) {
        Board temp = Factory.cloneBoard(this);
        temp.movePiece(from.getCurrentPosition(), to.getPosition());
        return temp;
    }

    @Override
    public void registerObserver(Observer o) {
        observerList.add(o);
    }

    @Override
    public void unregisterObserver(Observer o) {
        observerList.remove(observerList.indexOf(o));
    }

    @Override
    public void notifyObservers() {
        observerList.forEach((o) -> {
            o.update(currentFen);
        });
    }

}
