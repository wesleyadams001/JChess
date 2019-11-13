/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Jchess.Ui;

import Jchess.Enums.ThemeColor;
import Jchess.Models.Board;
import Jchess.Models.Tile;
import javax.swing.JButton;
import javax.swing.border.LineBorder;

/**
 *
 * @author nehalpatel
 */
public class TileButton extends JButton {

    private static final long serialVersionUID = -2606234934969734128L;
    private final Tile tile;

    /**
     * Represents a Tile. Makes up a clickable portion of the Board on the Viewer.
     * @param tile A Tile.
     */
    public TileButton(Tile tile) {
        super();
        this.tile = tile;
    }

    /**
     * Returns the Tile which this button represents.
     * @return The Tile associated with this button.
     */
    public Tile getInnerTile() {
        return this.tile;
    }

    /**
     * Returns the row index relative to the Board.
     * @return The TileButton's row index.
     */
    public int getRow() {
        return tile.getPosition().getRow();
    }

    /**
     * Returns the column index relative to the Board.
     * @return The TileButton's column index.
     */
    public int getColumn() {
        return tile.getPosition().getColumn();
    }

    /**
     * Re-paints Tiles according to selection, highlight, etc.
     */
    public void decorate() {
        this.setBackground(tile.getColor());
        this.setIcon(tile.isOccupied() ? tile.getPiece().getImage() : null);
        this.setDisabledIcon(tile.isOccupied() ? tile.getPiece().getImage() : null);

        if (tile.isOccupied() && tile.getPiece().isSelected()) {
            // Highlight currently selected Tile.
            this.setBorderPainted(true);
            this.setBorder(new LineBorder(tile.getColor(), 5));
            this.setBackground(ThemeColor.Friendly.getColor());
        } else if (tile.isSpecial()) {
            // Highligh special moves.
            this.setBorderPainted(true);
            this.setBorder(new LineBorder(tile.getColor(), 5));
            this.setBackground(ThemeColor.Special.getColor());
        } else if (tile.isHighlighted()) {
            // Highlight possible moves.
            this.setBorderPainted(true);
            this.setBorder(new LineBorder(tile.getColor(), 5));
            this.setBackground(tile.isOccupied() ? ThemeColor.Enemy.getColor() : ThemeColor.Friendly.getColor());
        }
    }

    /**
     * Repaints the Tile and enables/disables clicks based on current Player, highlighted, etc.
     * @param board The Board to sync with.
     */
    public void syncWith(Board board) {
        decorate();

        if (tile.isOccupied() && tile.getPiece().isOwnedBy(board.getCurrentPlayer())) {
            // This Tile is in the current player's possession. 
            this.setEnabled(true);
        } else if (tile.isHighlighted()) {
            // This Tile is a possible move.
            this.setEnabled(true);
        } else if (!tile.isOccupied()){
            // This Tile is an empty Tile.
            this.setEnabled(true);
        } else {
            // This could holds an enemy Piece that isn't attackable.
            this.setEnabled(false);
        }
    }

}
