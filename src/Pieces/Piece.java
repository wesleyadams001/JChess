/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pieces;

import Board.Board;
import Board.Pair;
import Board.Tile;
import Enums.MoveType;
import Enums.PieceType;
import Enums.ThemeColor;
import Player.Player;
import java.util.Vector;
import javax.swing.ImageIcon;
import static Images.Images.Icons;

/**
 *
 * @author nehalpatel
 */
public abstract class Piece {

    private Pair position;
    private final Player player;
    
    private boolean selected;
    private boolean taken;
    private boolean hasTakenFirstMove;
    
    private final char letter;
    protected final ImageIcon image;
    protected final PieceType shortHand;

    public Piece(Player owner, PieceType type) {
        player = owner;
        shortHand = type;
        image = Icons.get(shortHand).get(owner.getColor());
        
        if (owner.getColor() == ThemeColor.LightPiece) {
            letter = shortHand.getLightLetter();
        } else {
            letter = shortHand.getDarkLetter();
        }
    }

    /**
     *
     * @param board
     * @param p
     * @return
     */
    public abstract Vector<Pair> specialMoves(Board board, Piece p);

    /**
     * Get a list of possible moves this Piece can make.
     * @param board
     * @return A collection of Pairs representing the possible moves for this Piece.
     */
    public abstract Vector<Pair> getPossibleMoves(Board board);

    /**
     *
     * @return
     */
    public Player getPlayer() {
        return player;
    }

    /**
     *
     * @return
     */
    public Pair getCurrentPosition() {
        return position;
    }

    /**
     *
     * @param position
     */
    public void setCurrentPosition(Pair position) {
        this.position = position;
    }
    
    /**
     *
     * @return
     */
    public ImageIcon getImage() {
        return image;
    }

    /**
     *
     * @return
     */
    public boolean isSelected() {
        return selected;
    }

    /**
     *
     * @param selected
     */
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    /**
     *
     * @return
     */
    public boolean isTaken() {
        return taken;
    }

    /**
     *
     * @param taken
     */
    public void setTaken(boolean taken) {
        this.taken = taken;
    }

    /**
     *
     * @return
     */
    public boolean hasTakenFirstMove() {
        return hasTakenFirstMove;
    }

    /**
     *
     * @param hasTakenFirstMove
     */
    public void setHasTakenFirstMove(boolean hasTakenFirstMove) {
        this.hasTakenFirstMove = hasTakenFirstMove;
    }
    
    
    /**
     * Check whether a move to a Tile is technically legal.(Only ensures that the Tile is not occupied by a friendly Piece.)
     * @param tile The Tile which you'd like to move this Piece to.
     * @param type
     * @return True if Tile is empty or holds an enemy Piece, false otherwise.
     */
    public boolean canMoveTo(Tile tile, MoveType type) {
        if (tile == null) {
            return false;
        }

        if (MoveType.EmptyTileOnly == type) {
            return !tile.isOccupied();
        }
        
        if (MoveType.EnemyPieceOnly == type) {
            if (tile.isOccupied()) {
                return tile.getPiece().player != this.player;
            } else {
                return false;
            }
        }
        
        if (MoveType.EmptyOrEnemyPiece == type) {
            if (tile.isOccupied()) {
                return tile.getPiece().player != this.player;
            } else {
                return true;
            }
        }

        return true;
    }

    public char getLetter() {
        return this.letter;
    }

    /**
     * gets the piece type of the piece
     * @return you guessed it 
     */
    public PieceType getPieceType() {
        return this.shortHand;
    }

}
