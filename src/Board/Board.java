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
public final class Board {

    /**
     *
     */
    private Player currentPlayer;

    /**
     *
     */
    private Player enemyPlayer;

    /**
     *
     */
    private final Player lightPlayer;

    /**
     *
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

    private final Tile[][] matrix;

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

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public Player getEnemyPlayer() {
        return this.enemyPlayer;
    }
    
    public void setEnemyPlayer(Player enemy) {
        this.enemyPlayer = enemy;
    }

    public Player getLightPlayer() {
        return lightPlayer;
    }

    public Player getDarkPlayer() {
        return darkPlayer;
    }

    public void switchPlayers() {
        setCurrentPlayer(getCurrentPlayer() == getLightPlayer() ? getDarkPlayer() : getLightPlayer());
        setEnemyPlayer(getEnemyPlayer() == getLightPlayer() ? getDarkPlayer() : getLightPlayer());
    }

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
     * Get the Player's selected Piece.
     * @return The first Piece found with isSelected() being true, null otherwise.
     */
    public Piece getSelectedPiece() {
        return this.selectedPiece;
    }

    public void selectPiece(Piece piece) {
        piece.setSelected(true);
        this.selectedPiece = piece;
    }

    public void deselectPiece(Piece piece) {
        piece.setSelected(false);
        this.selectedPiece = null;
    }

    private Boolean clickIsSelection(Tile tile) {
        return (!tile.isHighlighted() &&
                tile.isOccupied());
    }
    
    private Boolean clickIsMove(Tile tile) {
        return (tile.isHighlighted() &&
                this.getSelectedPiece() != null);
    }

    private Boolean clickIsDeselection(Tile tile) {
        return (!tile.isHighlighted() &&
                !tile.isOccupied()
                );
    }

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
            toTile.removePiece();
        }

        Tile fromTile = getTile(from);
        if (fromTile.isOccupied()) {
            // Useful for tracking special moves for Pawn, etc.
            fromTile.getPiece().setHasTakenFirstMove(true);
        } else {
            // throw new Exception("The departing Piece doesn't exist.");
        }

        Piece fromPiece = fromTile.removePiece();
        toTile.setPiece(fromPiece);
        
        // =================
        // Move is finished.
        // =================
        
        if (toTile.getPiece().getPieceType() == PieceType.King){
            getCurrentPlayer().setLocationOfKing(toTile.getPosition());
        }
    }

}
