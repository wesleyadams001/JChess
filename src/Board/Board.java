/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Board;

import Enums.ClickType;
import Enums.PieceType;
import Pieces.Piece;
import Player.Player;

/**
 *
 * @author nehalpatel
 */
public class Board {

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
    public final int rowCount = 8;

    /**
     * The number of columns for this Board.
     */
    public final int columnCount = 8;

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

        this.matrix = new Tile[rowCount][columnCount];

        // Populate matrix with empty Tiles.
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
                Tile tile = new Tile(new Pair(rowIndex, columnIndex));
                this.matrix[rowIndex][columnIndex] = tile;
            }
        }
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

        if ((rowIndex > rowCount - 1) || (columnIndex > columnCount - 1)) {
            return false;
        }

        return true;
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

    public Tile[][] getMatrix(){
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

}
