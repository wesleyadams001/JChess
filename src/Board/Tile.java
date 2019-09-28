/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Board;

import Pieces.Piece;

/**
 *
 * @author nehalpatel
 */
public class Tile {

    private Piece piece;
    private boolean highlighted;
    private int color;
    private final Pair position;
    
    /**
     *
     * @param position The Pair representing the Tile's place on the Grid.
     */
    public Tile(Pair position) {
        this.position = position;
    }

    /**
     * Retrieve the Piece that is currently on this Tile.
     * @return The Piece if it exists, null otherwise.
     */
    public Piece getPiece() {
        return piece;
    }

    /**
     * Place a Piece on this Tile.
     * @param piece The Piece to place on this Tile.
     */
    public void setPiece(Piece piece) {
        // TODO: handle case where a Piece is already on this Tile.
        this.piece = piece;
    }

    /**
     * Check if the Tile is occupied with a Piece.
     * @return True if a Piece is on this Tile, false otherwise.
     */
    public boolean isOccupied() {
        return this.piece != null;
    }

    /**
     * Check if the Tile is highlighted.
     * @return True if this Tile should be highlighted on the Board.
     */
    public boolean isHighlighted() {
        return highlighted;
    }

    /**
     * 
     * @param highlighted
     */
    public void setHighlighted(boolean highlighted) {
        this.highlighted = highlighted;
    }

    /**
     *
     * @return
     */
    public int getColor() {
        return color;
    }

    /**
     *
     * @param color
     */
    public void setColor(int color) {
        this.color = color;
    }

    /**
     * Check if the Tile is part of the top or bottom row.
     * @return True if this Tile is in either the topmost or bottommost rows.
     */
    public boolean isHomeRow() {        
        return (this.position.getRow() == 0) || (this.position.getRow() == 7);
    }
    
    /**
     *
     */
    public void click() {
    }
    
    /**
     * Retrieve the Tile's position.
     * @return A Pair representing the Tile's position in the Grid.
     */
    public Pair getPosition() {
        return this.position;
    }

}
