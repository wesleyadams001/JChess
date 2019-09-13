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
    private Boolean occupied;
    private Boolean highlighted;
    private Integer color;
    private Boolean isHomeRow;

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public Boolean getOccupied() {
        return occupied;
    }

    public void setOccupied(Boolean occupied) {
        this.occupied = occupied;
    }

    public Boolean getHighlighted() {
        return highlighted;
    }

    public void setHighlighted(Boolean highlighted) {
        this.highlighted = highlighted;
    }

    public Integer getColor() {
        return color;
    }

    public void setColor(Integer color) {
        this.color = color;
    }

    public Boolean getIsHomeRow() {
        return isHomeRow;
    }

    public void setIsHomeRow(Boolean isHomeRow) {
        this.isHomeRow = isHomeRow;
    }
    
    public Boolean click() {
        return null;
    }
    
    public Piece swapPiece(Piece with) {
        return null;
    }
}
